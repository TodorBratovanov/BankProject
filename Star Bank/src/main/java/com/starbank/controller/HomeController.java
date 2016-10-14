package com.starbank.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.starbank.exceptions.MessageException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.ICardDAO;
import com.starbank.model.dao.ICreditDAO;
import com.starbank.model.dao.ICurrentAccountDAO;
import com.starbank.model.dao.IDepositDAO;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.entity.Account;
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
	private ApplicationContext appContext;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String loadHome(HttpServletRequest request) {
		HttpSession session = request.getSession();

//		if (!isConnectionCreated) {
//			Thread transactionFinalizer = new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					while (true) {
//						try {
//							Thread.sleep(FINALIZE_TRANSACTION_TIME);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						try {
//							new TransactionFinalizerRepository(appContext.getBean(DataSource.class), 
//									appContext.getBean(TransactionTemplate.class)).finalizeAllUserTransactions();
//						} catch (Exception e) {
//							System.out.println("Still not db connection. will clean later.");
//						}
//					}
//				}
//			});
//
//			transactionFinalizer.setDaemon(true);
//			transactionFinalizer.start();
//			
//			isConnectionCreated = true;
//		}
		
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
		// session.setMaxInactiveInterval(5);

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

	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public ModelAndView getMessages(ModelAndView model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		// System.err.println("SESSSSSSSION "+ session.equals(null));
		if ((session == null) || (session.getAttribute("user_id") == null)) {
			model.setViewName("/login");
			// session.invalidate();
			return model;
		}

		model = new ModelAndView("messages");

		// session.invalidate();
		List<Message> messages = new ArrayList<>();
		try {
			messages = message.getAllMessages((Integer) session.getAttribute("user_id"));
		} catch (MessageException e) {
			e.printStackTrace();
		}
		int numberOfUser = userDao.countUsers();
		int numberOfLikes = userDao.countLikes();

		model.addObject("countusers", numberOfUser);
		model.addObject("likes", numberOfLikes);
		model.addObject("messages", messages);
		model.setViewName("/messages");

		return model;

	}

	@RequestMapping(value = "/information", method = RequestMethod.GET)
	public String showInfo() throws Exception {
		return "information";
	}

	@RequestMapping(value = "/payments", method = RequestMethod.GET)
	public String showPayments(HttpServletRequest request, Model model) throws Exception {
		
		try {
			HttpSession session = request.getSession(false);

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
	public String showStatements() throws Exception {
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
		System.err.println("dadadadadadadadadadadadadadadadadadad" + new Date(lastTime));
		System.err.println("DATE:" + session.getAttribute("user_id"));
		System.err.println("IP:" + request.getRemoteAddr());
		userSession.insertSessionInfo((Integer) session.getAttribute("user_id"), new Date(lastTime),
				request.getRemoteAddr());
		session.invalidate();

		return "login";
	}

	@RequestMapping(value="/accounts",method=RequestMethod.GET)

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
