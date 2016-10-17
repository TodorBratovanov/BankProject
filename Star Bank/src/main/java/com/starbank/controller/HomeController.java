package com.starbank.controller;

import java.sql.Date;

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

import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.dao.repo.TransactionFinalizerRepository;
import com.starbank.model.entity.User;

@Controller
public class HomeController {

	private static final int SESSION_TIME = 180;
	private static final int HTTPS_PORT = 443;
	private static final int HTTP_PORT = 80;
	private static final int FINALIZE_TRANSACTION_TIME = 10000;
	private static boolean isConnectionCreated = false;

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	private User user;

	@Autowired
	private IUserSessionDAO userSession;

	@Autowired
	private ApplicationContext appContext;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String loadHome(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
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
			if (session.getAttribute("user_id") != null) {
				return "index";
			}
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/login";
		}
	}

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.POST)
	public String home(Model model) {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogIn(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) throws Exception {
		try {
			String userEmail = request.getParameter("email");
			String userPassword = request.getParameter("password");
			HttpSession session = request.getSession();

			int user_id = 0;

			if (userDao.isRegistered(userEmail)) {
				if (userDao.isRegistrationConfirmed(userEmail)) {
					user_id = userDao.loginUser(userEmail, userPassword);
					if (user_id > 0) {
						session.setAttribute("user_id", user_id);
						session.setMaxInactiveInterval(SESSION_TIME);
						return "index";
					}
					return "login";
				}
				return "login";
			}
			return "register";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/login";
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(Model model) {
		model.addAttribute(new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute User regUser, Model model, HttpServletRequest request) {
		try {
			System.err.println(regUser);
			String email = regUser.getEmail();
			if (!userDao.isRegistered(email)) {
				userDao.registerUser(regUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/login";
		}
		return "login";
	}

	@RequestMapping(value = "/likes", method = RequestMethod.GET)
	public String clickLike(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "/login";
			}
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
					new Date(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			String url = request.getHeader("Referer");
			userDao.clickLike((int) session.getAttribute("user_id"));
			return "redirect:" + url.substring((url.lastIndexOf('/') + 1), url.length());
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfile() throws Exception {
		return "profile";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "redirect:" + "/login";
			}
			long lastTime = session.getLastAccessedTime();
			String description = pageDescription(request);
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"), new Date(lastTime), description,
					request.getRemoteAddr());
			session.invalidate();
			return "redirect:" + "/login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/login";
		}
	}

	private String pageDescription(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName()
				+ ("http".equals(request.getScheme()) && request.getServerPort() == HTTP_PORT
						|| "https".equals(request.getScheme()) && request.getServerPort() == HTTPS_PORT ? ""
								: ":" + request.getServerPort())
				+ request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
	}

}
