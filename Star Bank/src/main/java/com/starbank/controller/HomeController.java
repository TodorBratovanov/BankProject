package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.google.gson.Gson;
import com.starbank.exceptions.MessageException;
import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.entity.Message;
import com.starbank.model.entity.User;

@Controller
public class HomeController {

	@Autowired
	private IUserDAO user;

	@Autowired
	private IAdminDAO admin;

	@Autowired
	private IAccountDAO account;
	
	@Autowired
	private IMessageDAO message;

	@RequestMapping(value = { "/", "/index" }, method = GET)
	public String loadHome(Model model, HttpServletRequest request) {
		System.out.println("=================================== " + request.getParameter("like") + " ===========================");
		return "index";
	}
	
	@RequestMapping(value = { "/", "/index" }, method = POST)
	public String home(Model model) {
		try {
			user.registerUser(null);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	@RequestMapping(value = "/confirmLogin", method = RequestMethod.POST)
	public String confirmLogin(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		System.out.println("");
		System.out.println("login");
		System.out.println("");
		int id = 0;
		System.err.println("==========================   " + request.getParameter("email") + "    =================================");
		if (user.isRegistered(request.getParameter("email"))) {
			if (user.isRegistrationConfirmed(request.getParameter("email"))) {
				id = user.loginUser(request.getParameter("email"), request.getParameter("password"));
			}
		}

		session.setAttribute("userId", id);

		return "index";

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm() throws Exception {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login() throws Exception {
		return "login";
	}

	@RequestMapping(value = "/register", method = GET)
	public String loadRegister(Model model) {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = POST)
	public String register(Model model) {
		return "login";
	}

	@RequestMapping(value = "/register2", method = POST)
	public String register2(@ModelAttribute User regUser, Model model, HttpServletRequest request) {
		try {
			String email = regUser.getEmail();
			if (!user.isRegistered(email)) {
				user.registerUser(regUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "login";
	}
	
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public ModelAndView getMessages() {
		ModelAndView model = new ModelAndView("messages");
		List<Message> messages = new ArrayList<>();
		try {
			messages = message.getAllMessages(1);
		} catch (MessageException e) {
			e.printStackTrace();
		}

		model.addObject("messages", messages);

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

}
