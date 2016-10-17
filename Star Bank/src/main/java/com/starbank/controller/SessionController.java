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

import com.starbank.model.dao.IUserSessionDAO;
import com.starbank.model.entity.UserSession;

@Controller
public class SessionController {
	
	private static final int MESSAGES_PER_PAGE = 5;
	private static final int HTTPS_PORT = 443;
	private static final int HTTP_PORT = 80;
	
	@Autowired
	private IUserSessionDAO userSession;

	@Autowired
	private IUserSessionDAO infoSession;
	
	@RequestMapping(value = "/sessions", method = RequestMethod.GET)
	public String showInfo(HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if ((session == null) || (session.getAttribute("user_id") == null)) {
				return "redirect:" + "/login";
			}
			userSession.insertSessionInfo((Integer) session.getAttribute("user_id"),
					new Timestamp(session.getLastAccessedTime()), pageDescription(request), request.getRemoteAddr());
			List<UserSession> sessions = new ArrayList<>();
			List<UserSession> partOfSessions = new ArrayList<>();
			sessions = infoSession.getAllSessions((Integer) session.getAttribute("user_id"));

			int numberOfSessions = (sessions.size() % MESSAGES_PER_PAGE == 0 ? (sessions.size() / MESSAGES_PER_PAGE)
					: (sessions.size() / MESSAGES_PER_PAGE) + 1);
			session.setAttribute("numberofsessions", numberOfSessions);
			Integer currentPage = null;
			
			loadSessionsPerPage(request, sessions, partOfSessions, currentPage);
			
			session.setAttribute("sessions", partOfSessions);

			return "/sessions";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + "/index";
		}
	}
	
	private void loadSessionsPerPage(HttpServletRequest request, List<UserSession> sessions,
			List<UserSession> partOfSessions, Integer currentPage) {
		if (request.getParameter("id") != null) {
			currentPage = Integer.parseInt(request.getParameter("id"));
		}

		for (int index = 1; index <= MESSAGES_PER_PAGE; index++) {
			if ((currentPage == null) || (currentPage == 1)) {
				if ((sessions.size()) <= index) {
					break;
				}
				partOfSessions.add(sessions.get(index - 1));
			} else {
				if (((index - 1) + (MESSAGES_PER_PAGE * (currentPage - 1))) >= sessions.size()) {
					System.err.println("SIZE:" + ((index - 1) + MESSAGES_PER_PAGE) * (currentPage - 1));
					break;
				}
				partOfSessions.add(sessions.get(((index - 1) + (MESSAGES_PER_PAGE * (currentPage - 1)))));
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
