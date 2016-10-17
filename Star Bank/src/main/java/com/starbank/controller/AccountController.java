package com.starbank.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.exceptions.IbanException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.ITransactionDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.Card;
import com.starbank.model.entity.Credit;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.model.entity.Transaction;

@Controller
public class AccountController {

	private static final int HTTPS_PORT = 443;
	private static final int HTTP_PORT = 80;

	@Autowired
	private IAccountDAO account;

	@Autowired
	private ICurrentAccountDAO currentAccount;

	@Autowired
	private IDepositDAO deposit;

	@Autowired
	private ICreditDAO credit;

	@Autowired
	private ICardDAO card;

	@Autowired
	private IUserSessionDAO userSession;

	@Autowired
	private TransactionFinalizerRepository finalizer;

	@Autowired
	private ITransactionDAO userStatement;

	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String showPayments(HttpServletRequest request, Model model) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "/login";
			}
			int userId = (int) session.getAttribute("user_id");
			userSession.insertSessionInfo(userId,
					new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "/login";
			}
			List<Account> accounts = new ArrayList<>();
			accounts = account.showUserAccounts(userId);
			model.addAttribute("accounts", accounts);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
		return "payments";
	}

	@RequestMapping(value = "/payments", method = RequestMethod.POST)
	public String payments(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "/login";
			}
			String sender = (String) request.getParameter("sender");
			String receiverMy = (String) request.getParameter("receiverMy");
			String receiverOther = (String) request.getParameter("receiverOther");
			String transferType = (String) request.getParameter("transferType");
			double amount;
			if (!sender.equalsIgnoreCase(receiverMy) && !sender.equalsIgnoreCase(receiverOther)) {
				if (request.getParameter("sender") != null && sender != null
						&& ((receiverMy != null && !receiverMy.equals("0"))
								|| (receiverOther != null) && (!receiverOther.equals("0")))
						&& transferType != null) {
					amount = Double.parseDouble(request.getParameter("amount"));
					if (amount > 0) {
						if (transferType.equals("my")) {
							account.transferMoneyToMyAccount(sender, receiverMy, amount);
							finalizer.insertTransactions(sender, receiverMy, amount);
						} else {
							if (transferType.equals("other")) {
								account.transferMoneyToOtherAccount(sender, amount, receiverOther);
								finalizer.insertTransactions(sender, receiverOther, amount);
							}
						}
					}
				} else {
					return "payments";
				}
			} else {
				throw new IbanException("Attempt to send to same iban!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
		return "payments";
	}

	@RequestMapping(value = "/utility", method = RequestMethod.GET)
	public String showUtility() throws Exception {
		return "utility";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.GET)
	public String showStatements(HttpServletRequest request, Model model) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "redirect:" + "/login";
			}
			if ((request.getParameter("strdate") == null) && (request.getParameter("enddate") == null)) {
				return "/statements";
			}
			String startDate = request.getParameter("strdate") + " 00:00:00";
			String endDate = request.getParameter("enddate") + " 23:59:59";

			if (LocalDateTime.parse(request.getParameter("strdate") + "T00:00:00.001")
					.isAfter(LocalDateTime.parse(request.getParameter("enddate") + "T00:00:00.001"))) {
				model.addAttribute("error_time", "Start date must be before end date!");
			}
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
					new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			List<Transaction> transactions = userStatement.getAllTransactions((int) session.getAttribute("user_id"),
					startDate, endDate);
			session.setAttribute("transactions", transactions);
			return "statements";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String showAccounts(HttpServletRequest request, Model model) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "/login";
			}
			int userId = (int) session.getAttribute("user_id");
			List<CurrentAccount> currentAccounts = new ArrayList<>();
			currentAccounts = currentAccount.showCurrentAccounts(userId);
			model.addAttribute("currentAccounts", currentAccounts);

			List<Deposit> deposits = new ArrayList<>();
			deposits = deposit.showDeposits(userId);
			model.addAttribute("deposits", deposits);

			List<Credit> credits = new ArrayList<>();
			credits = credit.showCredits(userId);
			model.addAttribute("credits", credits);

			List<Card> cards = new ArrayList<>();
			cards = card.showCards(userId);
			model.addAttribute("cards", cards);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
		return "accounts";
	}

	private String pageDescription(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName()
				+ ("http".equals(request.getScheme()) && request.getServerPort() == HTTP_PORT
						|| "https".equals(request.getScheme()) && request.getServerPort() == HTTPS_PORT ? ""
								: ":" + request.getServerPort())
				+ request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
	}
}
