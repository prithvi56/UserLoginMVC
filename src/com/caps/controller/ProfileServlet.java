package com.caps.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caps.model.beans.Student;
import com.caps.model.dao.StudentDAO;

@WebServlet("/create_profile")
public class ProfileServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String fName = req.getParameter("fname").toUpperCase();
		String lName = req.getParameter("lname").toUpperCase();
		String gender = req.getParameter("gender").toUpperCase();
		String password = req.getParameter("password");
		String type = req.getParameter("type").toUpperCase();
		
		Student s = new Student();
		s.setFirstName(fName);
		s.setLastName(lName);
		s.setGender(gender);
		s.setPassword(password);
		s.setType(type);
		
		StudentDAO obj = new StudentDAO();
		boolean res = obj.createProfile(s);
		RequestDispatcher dispatcher = null;
		if(res) {
			dispatcher = req.getRequestDispatcher("/profileCreated.jsp");
			dispatcher.forward(req, resp);
		}
		else {
			dispatcher = req.getRequestDispatcher("/fails.jsp");
			dispatcher.forward(req, resp);
		}
	}
}
