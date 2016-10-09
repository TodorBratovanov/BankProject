package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.exceptions.UserException;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.entity.User;

@Controller
public class HomeController {
	
	@Autowired
	private IUserDAO user;
	
	@RequestMapping(value = {"/", "/index"}, method = GET)
	public String home(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/confirmLogin", method = RequestMethod.POST)
	public String confirmLogin(HttpServletRequest request, Model model, HttpSession session) 
			throws Exception{
		
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
	public String showLoginForm() throws Exception{
		return "login";
	}
	
	@RequestMapping(value = "/register", method = GET)
	public String register(Model model) {
		return "register";
	}
	
	@RequestMapping(value = "/register2", method = POST)
	public String register2(@ModelAttribute User regUser, Model model) {
		
		try {
			if (!user.isRegistered(regUser.getEmail())) {
				user.registerUser(regUser);
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "register";
	}
//	public String register2(Model model, @RequestBody RegisterDTO reg) throws Exception {
//		
//		userService.create(reg.getFirstName(), reg.getMiddleName(), reg.getLastName(),"+359877556221", reg.getEmail(), 
//				reg.getPassword(), reg.getAddress(), reg.getEgn());
//		
//		System.out.println("");
//		System.out.println("register2");
//		System.out.println("");
//		
//		return "index";
//		
//		//return "redirect:/index";
//	}
	
}
