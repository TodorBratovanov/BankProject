package com.starbank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.exceptions.AccountException;
import com.starbank.exceptions.InvalidEmailException;
import com.starbank.exceptions.InvalidStringException;
import com.starbank.model.dao.IAccountDAO;
import com.starbank.model.dao.IAdminDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.entity.User;

@Controller
public class AdminController {

	@Autowired
	private IUserDAO userDao;

	@Autowired
	private IAdminDAO admin;

	@Autowired
	private IAccountDAO account;

	@RequestMapping(value = "/admin-index", method = RequestMethod.GET)
	public String showAdminHome() throws Exception {
		return "admin-index";
	}

	@RequestMapping(value = "/admin-confirm", method = RequestMethod.GET)
	public String showAdminConfirm(HttpServletRequest request, Model model) throws Exception {
		try {
			List<User> users = new ArrayList<>();
			users = userDao.showUsersWaitingConfirmation();
			model.addAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/admin-index";
		}
		return "admin-confirm";
	}

	@RequestMapping(value = "/admin-confirm", method = RequestMethod.POST)
	public String adminConfirm(HttpServletRequest request) throws Exception {
		try {
			String userEmail = request.getParameter("email");
			admin.confirmUserRegistration(userEmail);
			openAccount(request, userEmail);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/admin-index";
		}
		return "admin-confirm";
	}

	@RequestMapping(value = "/admin-open", method = RequestMethod.GET)
	public String showAdminOpen(HttpServletRequest request, Model model) throws Exception {
		try {
			List<User> users = new ArrayList<>();
			users = userDao.showAllUsers();
			model.addAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/admin-index";
		}
		return "admin-open";
	}

	@RequestMapping(value = "/admin-open", method = RequestMethod.POST)
	public String adminOpen(HttpServletRequest request) throws Exception {
		try {
			String userEmail = request.getParameter("email");
			admin.confirmUserRegistration(userEmail);

			openAccount(request, userEmail);

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/admin-index";
		}
		return "admin-open";
	}

	@RequestMapping(value = "/admin-close", method = RequestMethod.GET)
	public String showAdminClose() throws Exception {
		return "admin-close";
	}

	@RequestMapping(value = "/admin-message", method = RequestMethod.GET)
	public String showAdminMessage() throws Exception {
		return "admin-message";
	}

	private void openAccount(HttpServletRequest request, String userEmail)
			throws InvalidEmailException, InvalidStringException, AccountException {
		if (request.getParameter("years") != null && request.getParameter("amount") != null) {
			String accountType = request.getParameter("accountType");
			int validation = Integer.parseInt(request.getParameter("years"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			String iban = request.getParameter("iban");
			String cardNumber = request.getParameter("number");

			account.openAccount(userEmail, accountType, amount, iban, validation, cardNumber);
		} else {
			throw new AccountException("Wrong validity period or amount!");
		}
	}

}
