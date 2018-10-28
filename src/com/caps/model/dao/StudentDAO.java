package com.caps.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import com.caps.model.beans.Student;
import com.mysql.jdbc.Driver;

public class StudentDAO {

	public Student login(String userName, String passwd) {
		
		Student s = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			java.sql.Driver driverRef = new Driver();
			DriverManager.registerDriver(driverRef);
			System.out.println("Driver Loaded...");
			
			/*
			 * 2. Get the DB Connection via Driver
			 */
						String dbUrl="jdbc:mysql://localhost:3306/capsv4_db"
								+ "?user=root&password=root";
			con = DriverManager.getConnection(dbUrl); //1st version of getConnection

			System.out.println("Connected...");
			
			String sql = "select * from students where "
					+ " firstname=? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, passwd);
			rs = pstmt.executeQuery();

			if(rs.next()){
				int sid = rs.getInt("sid");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String gender = rs.getString("gender");
				passwd = rs.getString("password");
				String type = rs.getString("type");

				s = new Student();
				s.setSid(sid);
				s.setFirstName(firstName);
				s.setLastName(lastName);
				s.setGender(gender);
				s.setPassword(passwd);
				s.setType(type);
			}
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
			if(pstmt!=null) {
				try {
					pstmt.close();
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
		}
		return s;
	}
	
	public boolean createProfile(Student s) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String path = "M:/Files/db.properties";
			FileReader f = new FileReader(path);
			Properties p = new Properties();
			p.load(f);
			
			String dbUrl="jdbc:mysql://localhost:3306/capsv4_db";
			con = DriverManager.getConnection(dbUrl, p);

			String sql = "insert into students(firstname, lastname, gender, password, type) values( ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
		
				pstmt.setString(1, s.getFirstName());
				pstmt.setString(2, s.getLastName());
				pstmt.setString(3, s.getGender());
				pstmt.setString(4, s.getPassword());
				pstmt.setString(5, s.getType());
				
			int count = 0;
			synchronized (this) {
				count = pstmt.executeUpdate();
			}
			if(count>0) {
				return true;
			}
			else {
				return false;
			}
				
				
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
			if(pstmt!=null) {
				try {
					pstmt.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void show(HttpServletResponse resp) throws IOException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		PrintWriter out = resp.getWriter();
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
					String firstName = rs.getString("firstname");
					String lastName = rs.getString("lastname");
					String gender = rs.getString("gender");
					String passwd = rs.getString("password");
					String type = rs.getString("type");
	
					out.print("<tr><td>"+regno+"</td>"+"<td>"+firstName+"</td>"+"<td>"+lastName+"</td>"+
							"<td>"+gender+"</td>"+"<td>"+passwd+"</td>"+"<td>"+type+"</td></tr>");
					
				}
				out.print("</table>");
				out.print("<br>");
				out.print("<form style=\"margin-top: 10px;\" action=\"./logout\" method=\"post\">"+"<button type=\"submit\">LogOut</button>"+"</form>");
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
}
