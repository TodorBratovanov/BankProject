package com.starbank.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.starbank.exceptions.MessageException;
import com.starbank.exceptions.UserSessionException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.ITransactionDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.entity.Account;
import com.starbank.model.entity.Card;
import com.starbank.model.entity.Credit;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.model.entity.Message;
import com.starbank.model.entity.Transaction;
import com.starbank.model.entity.User;
import com.starbank.model.entity.UserSession;

@Controller
public class HomeController {

	private static final int FINALIZE_TRANSACTION_TIME = 10000;
	private static boolean isConnectionCreated = false;

	@Autowired
	private User user;

	@Autowired
	private IUserDAO userDao;

	@Autowired
	private IAdminDAO admin;

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
	private IMessageDAO message;

	@Autowired
	private IUserSessionDAO infoSession;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private ITransactionDAO userStatement;
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String loadHome(HttpServletRequest request) {
		HttpSession session = request.getSession();

		// if (!isConnectionCreated) {
		// Thread transactionFinalizer = new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// while (true) {
		// try {
		// Thread.sleep(FINALIZE_TRANSACTION_TIME);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// try {
		// new
		// TransactionFinalizerRepository(appContext.getBean(DataSource.class),
		// appContext.getBean(TransactionTemplate.class)).finalizeAllUserTransactions();
		// } catch (Exception e) {
		// System.out.println("Still not db connection. will clean later.");
		// }
		// }
		// }
		// });
		//
		// transactionFinalizer.setDaemon(true);
		// transactionFinalizer.start();
		//
		// isConnectionCreated = true;
		// }

		if (session.getAttribute("user_id") != null) {
			return "index";
		}

		return "login";
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
	public String home(Model model) {
		// try {
		// user.registerUser(null);
		// } catch (UserException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogIn(Model model) {

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) throws Exception {
		String userEmail = request.getParameter("email");
		String userPassword = request.getParameter("password");
		HttpSession session = request.getSession();

		int user_id = 0;
		System.err.println("=========================   email === " + userEmail);
		System.err.println("=========================   password === " + userPassword);

		if (userDao.isRegistered(userEmail)) {
			if (userDao.isRegistrationConfirmed(userEmail)) {
				user_id = userDao.loginUser(userEmail, userPassword);
				if (user_id > 0) {
					session.setAttribute("user_id", user_id);
					session.setMaxInactiveInterval(-1);
					System.err.println("registered *******************************************");
					return "index";
				}
				return "login";
			}
			System.err.println("registration not confirmed *******************************************");
			return "login";
		}
		System.err.println("not registered *******************************************");
		return "register";

	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(Model model) {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute User regUser, Model model, HttpServletRequest request) {
		System.err.println("register user:   " + regUser);
		try {
			String email = regUser.getEmail();
			if (!userDao.isRegistered(email)) {
				userDao.registerUser(regUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
	}

	@RequestMapping(value = { "/messages" }, method = RequestMethod.GET)
	public String getMessages(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if ((session == null) || (session.getAttribute("user_id") == null)) {
			return "redirect:" + "/login";
		}
		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
				new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
		// session.invalidate();
		List<Message> messages = new ArrayList<>();
		List<Message> partOfMessages = new ArrayList<>();
		try {
			messages = message.getAllMessages((Integer) session.getAttribute("user_id"));
		} catch (MessageException e) {
			e.printStackTrace();
		}

		int numberOfPages = (messages.size() % 5 == 0 ? (messages.size() / 5) : (messages.size() / 5) + 1);
		int numberOfUser = userDao.countUsers();
		int numberOfLikes = userDao.countLikes();
		session.setAttribute("number_pages", numberOfPages);
		session.setAttribute("countusers", numberOfUser);
		session.setAttribute("likes", numberOfLikes);
		Integer currentPage = null;
		if (request.getParameter("id") != null) {
			currentPage = Integer.parseInt(request.getParameter("id"));
			System.err.println("PAGE:" + Integer.parseInt(request.getParameter("id")));
		}

		for (int index = 1; index <= 5; index++) {
			if ((currentPage == null) || (currentPage == 1)) {
				if (messages.size() > 0) {
					if ((messages.size()) <= index) {
						break;
					}
					partOfMessages.add(messages.get(index - 1));
				}
			} else {

				if (((index - 1) + (5 * (currentPage - 1))) == messages.size()) {
					System.err.println("SIZE:" + ((index - 1) + (5 * (currentPage - 1))));

					break;
				}
				partOfMessages.add(messages.get(((index - 1) + (5 * (currentPage - 1)))));

			}
		}
		session.setAttribute("messages", partOfMessages);
		return "/messages";

	}

	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public String showInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		// System.err.println("SESSSSSSSION "+ session.equals(null));

		if ((session == null) || (session.getAttribute("user_id") == null)) {

			return "redirect:" + "/login";
		}
		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
				new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
		List<UserSession> sessions = new ArrayList<>();
		List<UserSession> partOfSessions = new ArrayList<>();
		try {
			sessions = infoSession.getAllSessions((Integer) session.getAttribute("user_id"));
		} catch (UserSessionException e) {
			e.printStackTrace();
		}

		int numberOfSessions = (sessions.size() % 5 == 0 ? (sessions.size() / 5) : (sessions.size() / 5) + 1);
		session.setAttribute("numberofsessions", numberOfSessions);

		Integer currentPage = null;
		if (request.getParameter("id") != null) {
			currentPage = Integer.parseInt(request.getParameter("id"));

		}
		System.err.println("PAGE:" + request.getParameter("id"));

		for (int index = 1; index <= 5; index++) {
			if ((currentPage == null) || (currentPage == 1)) {
				if ((sessions.size()) <= index) {
					break;
				}
				partOfSessions.add(sessions.get(index - 1));
			} else {

				if (((index - 1) + (5 * (currentPage - 1))) >= sessions.size()) {
					System.err.println("SIZE:" + ((index - 1) + 5) * (currentPage - 1));
					break;
				}
				partOfSessions.add(sessions.get(((index - 1) + (5 * (currentPage - 1)))));

			}
		}

		session.setAttribute("sessions", partOfSessions);

		return "/sessions";
	}

	@RequestMapping(value = "/likes", method = RequestMethod.GET)
	public String clickLike(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);

		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
				new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());

		// System.err.println("SESSSSSSSION "+ session.equals(null));
		if ((session == null) || (session.getAttribute("user_id") == null)) {

			return "/login";
		}
		String url = request.getHeader("Referer");
		userDao.clickLike((int) session.getAttribute("user_id"));
		return "redirect:" + url.substring((url.lastIndexOf('/') + 1), url.length());

	}

	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String showPayments(HttpServletRequest request, Model model) throws Exception {

		try {
			HttpSession session = request.getSession(false);
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
					new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			if ((session == null) || (session.getAttribute("user_id") == null)) {

				return "/login";
			}
			User user = (User) session.getAttribute("user");

			List<Account> accounts = new ArrayList<>();
			accounts = account.showUserAccounts(2);
			model.addAttribute("accounts", accounts);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "payments";
	}

	@RequestMapping(value = "/utility", method = RequestMethod.GET)
	public String showUtility() throws Exception {
		return "utility";
	}

	@RequestMapping(value = "/statements", method = RequestMethod.GET)
	public String showStatements(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession(false);
		
		if ((session == null) || (session.getAttribute("user_id") == null)) {

			return "redirect:" + "/login";
		}
		if ( (request.getParameter("strdate") == null) && (request.getParameter("enddate") == null)) {
			return "/statements";
		}
		String startDate = request.getParameter("strdate")+" 00:00:00";
		String endDate = request.getParameter("enddate")+" 23:59:59";
		
		if (LocalDateTime.parse(request.getParameter("strdate")+"T00:00:00.001").isAfter(LocalDateTime.parse(request.getParameter("enddate")+"T00:00:00.001"))) {
			System.err.println("ERRRROR TIME");
			model.addAttribute("error_time","Start date must be before end date!");
		}
		
		System.err.println("DATEEEEEEEEEEEEEEEEEEEEEEEE:" + LocalDateTime.now());
		System.err.println("PARSE:" + LocalDateTime.parse(request.getParameter("strdate")+"T00:00:00.001"));
		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
				new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
		
		
		
//		System.err.println("DATEEEEEEEEEEEEEEEEEEEEEEEE:" + startDate);
//		System.err.println("DATEEEEEEEEEEEEEEEEEEEEEEEE:" + endDate);
		List<Transaction> transactions = userStatement.getAllTransactions((int) session.getAttribute("user_id")
				,startDate,endDate);
		for (Transaction transaction : transactions) {
			System.err.println("TRANSACTION"+transaction);
		}
		session.setAttribute("transactions", transactions);
		return "statements";
	}
	
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfile() throws Exception {
		return "profile";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		long lastTime = session.getLastAccessedTime();
		String description = pageDescription(request);

		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"), new Date(lastTime), description,
				request.getRemoteAddr());
		session.invalidate();

		return "login";
	}

	private String pageDescription(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName()
				+ ("http".equals(request.getScheme()) && request.getServerPort() == 80
						|| "https".equals(request.getScheme()) && request.getServerPort() == 443 ? ""
								: ":" + request.getServerPort())
				+ request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)

	public String showAccounts(HttpServletRequest request, Model model) throws Exception {

		try {
			HttpSession session = request.getSession(false);

			User user = (User) session.getAttribute("user");

			List<CurrentAccount> currentAccounts = new ArrayList<>();
			currentAccounts = currentAccount.showCurrentAccounts(2);
			model.addAttribute("currentAccounts", currentAccounts);

			List<Deposit> deposits = new ArrayList<>();
			deposits = deposit.showDeposits(2);
			model.addAttribute("deposits", deposits);

			List<Credit> credits = new ArrayList<>();
			credits = credit.showCredits(2);
			model.addAttribute("credits", credits);

			List<Card> cards = new ArrayList<>();
			cards = card.showCards(2);
			model.addAttribute("cards", cards);

			for (CurrentAccount ca : currentAccounts) {
				System.err.println(ca.getCreditLimit());
			}
			for (Deposit d : deposits) {
				System.err.println(d.getInterest());
			}
			for (Credit c : credits) {
				System.err.println(c.getPayment());
			}
			for (Card cr : cards) {
				System.err.println(cr.getNumber());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "accounts";
	}
}
