package com.starbank.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.dto.RegisterDTO;
import com.starbank.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	protected UserService userService;
	
	@RequestMapping(value = {"/", "/index"}, method = GET)
	public String home(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/confirmLogin", method = RequestMethod.POST)
	public String confirmLogin(@RequestBody RegisterDTO reg, Model model, HttpSession session) 
			throws Exception{
		
		System.out.println("");
		System.out.println("login");
		System.out.println("");
		
		int id = userService.findByEmailAndPassword(reg.getEmail(), reg.getPassword());
		
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
	public String register2(Model model, @RequestBody RegisterDTO reg) throws Exception {
		
		userService.create(reg.getFirstName(), reg.getMiddleName(), reg.getLastName(),"+359877556221", reg.getEmail(), 
				reg.getPassword(), reg.getAddress(), reg.getEgn());
		
		System.out.println("");
		System.out.println("register2");
		System.out.println("");
		
		return "index";
		
		//return "redirect:/index";
	}
	
}
