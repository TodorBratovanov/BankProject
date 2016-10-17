package com.starbank.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.starbank.model.dao.IMessageDAO;
import com.starbank.model.dao.IUserDAO;
import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.entity.Message;

@Controller
public class MessageController {
	
	private static final int MESSAGES_PER_PAGE = 5;
	private static final int HTTPS_PORT = 443;
	private static final int HTTP_PORT = 80;

	@Autowired
	private IUserDAO userDao;
	
	@Autowired
	private IMessageDAO message;
	
	@Autowired
	private IUserSessionDAO userSession;
	
	@RequestMapping(value = { "/messages" }, method = RequestMethod.GET)
	public String getMessages(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "redirect:" + "/login";
			}
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
					new Timestamp(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			List<Message> messages = new ArrayList<>();
			List<Message> partOfMessages = new ArrayList<>();
			messages = message.getAllMessages((Integer) session.getAttribute("user_id"));
			int numberOfPages = (messages.size() % MESSAGES_PER_PAGE == 0 ? (messages.size() / MESSAGES_PER_PAGE)
					: (messages.size() / MESSAGES_PER_PAGE) + 1);
			int numberOfUser = userDao.countUsers();
			int numberOfLikes = userDao.countLikes();
			session.setAttribute("number_pages", numberOfPages);
			session.setAttribute("countusers", numberOfUser);
			session.setAttribute("likes", numberOfLikes);
			Integer currentPage = null;
			
			loadMessagesPerPage(request, messages, partOfMessages, currentPage);
			
			session.setAttribute("messages", partOfMessages);
			return "/messages";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
	}
	
	private void loadMessagesPerPage(HttpServletRequest request, List<Message> messages, List<Message> partOfMessages,
			Integer currentPage) {
		if (request.getParameter("id") != null) {
			currentPage = Integer.parseInt(request.getParameter("id"));
		}

		for (int index = 1; index <= MESSAGES_PER_PAGE; index++) {
			if ((currentPage == null) || (currentPage == 1)) {
				if (messages.size() > 0) {
					if ((messages.size()) <= index) {
						break;
					}
					partOfMessages.add(messages.get(index - 1));
				}
			} else {
				if (((index - 1) + (MESSAGES_PER_PAGE * (currentPage - 1))) == messages.size()) {
					System.err.println("SIZE:" + ((index - 1) + (MESSAGES_PER_PAGE * (currentPage - 1))));
					break;
				}
				partOfMessages.add(messages.get(((index - 1) + (MESSAGES_PER_PAGE * (currentPage - 1)))));
			}
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
