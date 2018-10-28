package com.caps.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caps.model.beans.Authentic;

@WebServlet("/logout")
public class LogOut extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		
		if(session!=null) {
			System.out.println(Authentic.getAttempt());
			Authentic.setAttempt(0);
			System.out.println(Authentic.getAttempt());
			session.invalidate();
			
			Cookie[] cookies = req.getCookies();
			
			if(cookies!=null) {
				for(Cookie c:cookies) {
					if(c.getValue().equalsIgnoreCase("JSESSIONID")) {
						c.setMaxAge(0);
						resp.addCookie(c);
					}
				}	
			}
			resp.sendRedirect("./login.html");
		}
		else {
			resp.sendRedirect("./login.html");
		}
	}
}
