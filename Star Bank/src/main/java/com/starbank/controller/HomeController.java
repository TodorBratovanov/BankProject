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
	private IMessageDAO message;

	@RequestMapping(value = { "/", "/index" }, method = GET)
	public String home(Model model) {
		return "index";
	}

	@RequestMapping(value = "/confirmLogin", method = RequestMethod.POST)
	public String confirmLogin(HttpServletRequest request, Model model, HttpSession session) throws Exception {

		System.out.println("");
		System.out.println("login");
		System.out.println("");
		int id = 0;
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

	// @RequestMapping(value = "/messages", method = RequestMethod.GET)
	// public String showMails() throws Exception{
	// return "messages";
	// }
	@RequestMapping(value = "/register", method = GET)
	public String register(Model model) {
		return "register";
	}

	@RequestMapping(value = "/register2", method = POST)
	public String register2(@ModelAttribute User regUser, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("###############################   " + user);
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");

		try {
			response.getWriter().print(new Gson().toJson(regUser));
			if (!user.isRegistered(regUser.getEmail())) {
				user.registerUser(regUser);
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "register";
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

}
