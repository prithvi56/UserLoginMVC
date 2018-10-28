package com.caps.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caps.model.beans.Authentic;
import com.caps.model.beans.Student;
import com.caps.model.dao.StudentDAO;

@WebServlet("/loginServ")
public class StudentServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean valid = true;
		RequestDispatcher dispatcher = null;
		String userName = req.getParameter("name");
		String passwd = req.getParameter("passwd");
		
		 for(int i=0;i<userName.length(); i++)
         {  
             char c = userName.charAt(i);  
             if(!((c>='a' && c<='z')||(c>='A' && c<='Z')))
             {
                  valid =  false;
                  System.out.println("name"+valid);
             }
         }
		 if(valid) {
			 for(int i=0;i<passwd.length(); i++)
	         {  
	             char c = passwd.charAt(i);  
	             if(!((c>=33 && c<=47)||c=='@')&&(!((c>=48 && c<=57)||(c>=65 && c<=90)||(c>=97 && c<=122))))
	             {
	                  valid =  false;
	                  System.out.println("pwd"+valid);
	             }
	         }
		 }
		 DateFormat format = new SimpleDateFormat("HH:mm");
		 Calendar cal = Calendar.getInstance();
		 String s2 = format.format(new Date());
		 int h2 = Integer.parseInt(s2.substring(0, 2));
		 int m2 = Integer.parseInt(s2.substring(3, 5));
		 int sysTime = h2*60+m2;
		
		if(valid) {
			HttpSession session = req.getSession();
			Student s = null;
			StudentDAO obj = new StudentDAO();
			int count = Authentic.getAttempt();
			System.out.println(Authentic.isLock());
			
			if(Authentic.isLock()) {
				if((Authentic.getLockTime() - sysTime)<=0) {
					System.out.println(Authentic.getLockTime()-sysTime);
					Authentic.setLock(false);
					Authentic.setAttempt(0);
				}
				else {
					System.out.println(Authentic.getLockTime()-sysTime);
					System.out.println("Wait");
					dispatcher = req.getRequestDispatcher("/locked.jsp");
					dispatcher.forward(req, resp);
				}
			}
			if(!Authentic.isLock()){
				if(Authentic.getAttempt()<1)
				{
					Authentic.setAttempt(++count);
					System.out.println(count);
					s = obj.login(userName,passwd);
					if(s!=null) {
						session.setAttribute("name", s.getFirstName());
						session.setAttribute("type", s.getType());
						req.setAttribute("s", s);
						
						dispatcher = req.getRequestDispatcher("/success.jsp");
						dispatcher.forward(req, resp);
					}
					else {
						dispatcher = req.getRequestDispatcher("/checking.jsp");
						dispatcher.forward(req, resp);
					}
				}
				else {
					Authentic.setLock(true);
					cal.add(Calendar.HOUR, 1);
					String s1 = format.format(cal.getTime());
				 	int h1 = Integer.parseInt(s1.substring(0, 2));
					int m1 = Integer.parseInt(s1.substring(3, 5));
					int time1 = h1*60+m1;
					
					Authentic.setLockTime(time1);
					System.out.println(Authentic.isLock());
					System.out.println("account locked");
					
					dispatcher = req.getRequestDispatcher("/locked.jsp");
					dispatcher.include(req, resp);
				}
			}
		}
		else {
			System.out.println("me");
			dispatcher = req.getRequestDispatcher("/checking.jsp");
			dispatcher.forward(req, resp);
			
		}
	}
}
