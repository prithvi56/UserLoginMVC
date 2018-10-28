<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>wait</title>
	<style>
            input{
                background-color:aliceblue;
                height: 30px;
                width: 280px;
            }
            button {
                background-color: lightgrey;
                width: 140px;
                height: 30px;
                cursor: pointer;
            }
            .section{
                border:2px solid #666666;
                width: 300px;
                margin-top: 120px;
                margin-left: 35%;
                margin-bottom: 106px;
                padding: 20px;
                padding-left: 60px;
                padding-right: 60px;
                background-color:azure;
                border-radius:15px;
            }
        </style>
</head>
<body>
	<div class="section">
		<center style="color:red;">please wait</center>
          <form name="myform" method="post" action="./loginServ" onsubmit="return validateform()" >
            <b><center>Login</center></b><br>
              
            User Name: <br>
            <input type="text" name="name" placeholder="Enter the username" ><br>
            <br>
                
            Password: <br>
            <input type="password" name="passwd" placeholder="Enter the password" required><br>
            <br>
                
            <button type="submit">SignIn</button>
                <a href="profile.html"><button type="button">SignUp</button></a>
          </form> 
        </div>
    
        <script>  
            function validateform(){
            	var name=document.myform.name.value;  
                var password=document.myform.passwd.value;
            	var regx = new RegExp("^[a-zA-Z ]*$");
            	var res = regx.test(name);
            	
            	var filter = /[@$,<>#:?_*&;]/g;
                
            	if(!res){
					alert("Name can only have alphabets!!!");
					return false;
                }
                else if (!password.match(filter)) {
            	alert("Password must have atleast one special character");  
                    return false;
                }
            }  
        </script>
</body>
</html>