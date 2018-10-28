<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Details</title>
  <style type="text/css">
  	button {
                background-color: lightgrey;
                width: 140px;
                height: 30px;
                margin-left: 400px;
                cursor: pointer;
            }
  </style>
</head>
<body>
	<table border=1px cellspacing=0px cellpadding=5px style="width:40%;">
	  <tr>
	  	<th>SID</th>
	  	<th>FIRSTNAME</th>
	  	<th>LASTNAME</th>
	  	<th>GENDER</th>
	  	<th>PASSWORD</th>
	  	<th>TYPE</th>
	  </tr>
	  <tr>
	    <td>${s.sid}</td>
		<td>${s.firstName}</td>
		<td>${s.lastName}</td>
		<td>${s.gender}</td>
		<td>${s.password}</td>
		<td>${s.type}</td>
	  </tr>
	</table>
	<br>
	<h2><a href=viewAllStudents>Click to see all Student's details...</a></h2>
	<form action="./logout" method="post">
		<h1><button type="submit">LogOut</button></h1>
	</form>
	
</body>
</html>