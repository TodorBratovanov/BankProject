package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.starbank.exceptions.MessageException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.entity.Message;
import com.starbank.model.entity.User;

import io.undertow.server.session.Session;

@Controller
public class HomeController {

	@Autowired
	private User user;

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	private IAdminDAO admin;

	@Autowired
	private IAccountDAO account;
	
	@Autowired 
	private IUserSessionDAO userSession;

	@Autowired
	private IMessageDAO message;

	@RequestMapping(value = { "/", "/index" }, method = GET)
	public String loadHome(HttpServletRequest request) {
		HttpSession session = request.getSession();

		if (session.getAttribute("user_id") != null) {
			return "index";
		}

		return "login";
	}
	
	@RequestMapping(value = { "/", "/index" }, method = POST)
	public String home(Model model) {
//		try {
//			user.registerUser(null);
//		} catch (UserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogIn(Model model) {
		
		return "login";
	}
	

	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) throws Exception {
		String userEmal = request.getParameter("email");
		String userPassword = request.getParameter("password");
		HttpSession session = request.getSession();
	//	session.setMaxInactiveInterval(5);
		
		int user_id = 0;
		
		if (userDao.isRegistered(userEmal)){
			if (userDao.isRegistrationConfirmed(userEmal)) {
				user_id = userDao.loginUser(userEmal, userPassword);
				if (user_id > 0) {
					session.setAttribute("user_id", user_id);
					session.setMaxInactiveInterval(15);
					return "index";
				}
				return "login";
			}

			return "login";
		}

		return "register";

	}

	@RequestMapping(value = "/register", method = GET)
	public String showRegister(Model model) {
		return "register";
	}



	@RequestMapping(value = "/register", method = POST)
	public String register(@ModelAttribute User regUser, Model model, HttpServletRequest request) {

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
//		System.err.println("SESSSSSSSION "+  session.equals(null));
		if ((session == null) || (session.getAttribute("user_id") == null)) {
			 model.setViewName("/login");
			// session.invalidate();
			 return model;
		}
	
		model = new ModelAndView("messages");
		
		//session.invalidate();
		List<Message> messages = new ArrayList<>();
		try {
			messages = message.getAllMessages((Integer)session.getAttribute("user_id"));
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
	public String showPayments() throws Exception {
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
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String showAccounts() throws Exception {
		return "accounts";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showProfile() throws Exception {
		return "profile";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false); 
		long lastTime = session.getLastAccessedTime();
		System.err.println("dadadadadadadadadadadadadadadadadadad"+new Date(+lastTime));
		System.err.println("DATE:"+session.getAttribute("user_id"));
		System.err.println("IP:"+request.getRemoteAddr());
		userSession.insertSessionInfo((Integer)session.getAttribute("user_id"), new Date(+lastTime), request.getRemoteAddr());
		session.invalidate();
		
		return "login";
	}
}
