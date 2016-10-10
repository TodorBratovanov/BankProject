package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.entity.User;

@Controller
public class HomeController {

	@Autowired
	private IUserDAO user;

	@Autowired
	private IAdminDAO admin;

	@Autowired
	private IAccountDAO account;

	@RequestMapping(value = { "/", "/index" }, method = GET)
	public String loadHome(Model model) {
		return "index";
	}
	
	@RequestMapping(value = { "/", "/index" }, method = POST)
	public String home(Model model) {
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

	@RequestMapping(value = "/register", method = GET)
	public String register(Model model) {
		return "register";
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
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String showAccounts() throws Exception {
		return "accounts";
	}

}
