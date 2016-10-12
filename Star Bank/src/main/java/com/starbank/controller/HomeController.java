package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextLoader;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.starbank.exceptions.MessageException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.entity.Card;
import com.starbank.model.entity.Credit;
import com.starbank.model.entity.CurrentAccount;
import com.starbank.model.entity.Deposit;
import com.starbank.model.entity.Message;
import com.starbank.model.entity.User;

@Controller
public class HomeController {

	private static final int FINALIZE_TRANSACTION_TIME = 10000;
	private static boolean isConnectionCreated = false;
	
	@Autowired
	private IUserDAO user;

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
	private IMessageDAO message;

	@Autowired
	private ApplicationContext appContext;
	
	@RequestMapping(value = { "/", "/index" }, method = GET)
	public String loadHome(Model model, HttpServletRequest request) {
		
		if (!isConnectionCreated) {
			Thread transactionFinalizer = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(FINALIZE_TRANSACTION_TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						try {
							new TransactionFinalizerRepository(appContext.getBean(DataSource.class), 
									appContext.getBean(TransactionTemplate.class)).finalizeAllUserTransactions();
						} catch (Exception e) {
							System.out.println("Still not db connection. will clean later.");
						}
					}
				}
			});

			transactionFinalizer.setDaemon(true);
			transactionFinalizer.start();
			
			isConnectionCreated = true;
		}
		
		try {
			if (user.isRegistered("kokoko@abv.bg")) {
				System.err.println("OK" + "    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			} else {
				System.err.println("FUCK" + "     ----------------------------------------------------------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
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
