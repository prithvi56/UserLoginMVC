package com.caps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.jdbc.Driver;

@WebServlet("/viewAllStudents")
public class ViewAllStudentsServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstName = null;
		HttpSession session = req.getSession(false);;
		PrintWriter out = resp.getWriter();
		
		if(session!=null) {
			firstName = (String) session.getAttribute("name");
			out.print("<h1>Hello "+firstName+"</h1>");
			String type = (String) session.getAttribute("type");
			if(type.equals("A")) {
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				
				try {
					java.sql.Driver driverRef = new Driver();
					DriverManager.registerDriver(driverRef);
					
					String dbUrl="jdbc:mysql://localhost:3306/capsv4_db"
									+ "?user=root&password=kaatil";
					con = DriverManager.getConnection(dbUrl); //1st version of getConnection
		
					String sql = "select * from students";
		
					stmt = con.createStatement();
					rs = stmt.executeQuery(sql);
		
					out.print("<table border=1px cellspacing=0px cellpadding=5px style=\"width:40%;\"");
					out.print("<tr><th>SID</th>"+"<th>FIRSTNAME</th>"+"<th>LASTNAME</th>"+
								"<th>GENDER</th>"+"<th>PASSWORD</th>"+"<th>TYPE</th></tr>");
					
						while(rs.next()){
							int regno = rs.getInt("sid");
							String firstname = rs.getString("firstname");
							String lastname = rs.getString("lastname");
							String gender = rs.getString("gender");
							String passwd = rs.getString("password");
							type = rs.getString("type");
			
							out.print("<tr><td>"+regno+"</td>"+"<td>"+firstname+"</td>"+"<td>"+lastname+"</td>"+
									"<td>"+gender+"</td>"+"<td>"+passwd+"</td>"+"<td>"+type+"</td></tr>");
						}
						out.print("</table>");
						out.print("<br>");
						out.print("<form style=\"margin-top: 10px; margin-left: 400px;\"action=\"./logout\" method=\"post\">" +
								"<h1><button type=\"submit\" style=\"background-color: lightgrey;\r\n" + "width: 140px;\r\n" +
								"height: 30px;\r\n " + "cursor: pointer;\">LogOut</button></h1>"+"</form> ");
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					if(con!=null) {
						try {
							con.close();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					if(stmt!=null) {
						try {
							stmt.close();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					if(rs!=null) {
						try {
							rs.close();
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
					out.close();
				}
			}
			else {
				out.print("<h1>You do not have the permission!!!</h1>");
			}
		}
		else {
				out.print("<h1><marquee direction=\"right\" behavior=\"alternate\">You must have to login first...</marquee></h1>");
				out.print("<h1><center><a href=login.html>Login</a></center></h1>");
		}
	}
}
