<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.dentalclinic.model.User" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Edit User | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial, sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{

    width:500px;
    margin:40px auto;
    background:white;
    padding:30px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);

}

h2{

    text-align:center;
    color:#1976d2;

}

label{

    display:block;
    margin-top:15px;
    font-weight:bold;

}

input, select{

    width:100%;
    padding:10px;
    margin-top:5px;
    border:1px solid #ccc;
    border-radius:5px;
    box-sizing:border-box;

}

button{

    width:100%;
    margin-top:25px;
    padding:12px;
    background:#1976d2;
    color:white;
    border:none;
    border-radius:5px;
    cursor:pointer;
    font-size:16px;

}

button:hover{

    background:#0d47a1;

}

.message{

    text-align:center;
    font-weight:bold;

}

.success{

    color:green;

}

.error{

    color:red;

}

.back{

    text-align:center;
    margin-top:20px;

}

.back a{

    color:#1976d2;
    text-decoration:none;
    font-weight:bold;

}

</style>

</head>


<body>


<%

User loggedUser = (User) session.getAttribute("user");


if(loggedUser == null || 
   !"Admin".equals(loggedUser.getRole())){


    response.sendRedirect("Dashboard.jsp");
    return;

}


User user = (User) request.getAttribute("user");


String error = (String) request.getAttribute("error");

String success = (String) request.getAttribute("success");



if(error != null){

%>

<p class="message error">
    <%= error %>
</p>

<%

}


if(success != null){

%>

<p class="message success">
    <%= success %>
</p>

<%

}



if(user == null){

%>


<p class="message error">
    User details not found.
</p>


<%

}else{

%>


<div class="container">


<h2>
Edit User
</h2>


<form action="EditUser" method="post">


<input type="hidden"
       name="userId"
       value="<%= user.getUserId() %>">



<label>
Full Name
</label>


<input type="text"
       name="fullName"
       value="<%= user.getFullName() %>"
       required>



<label>
Username
</label>


<input type="text"
       name="username"
       value="<%= user.getUsername() %>"
       required>



<label>
Email
</label>


<input type="email"
       name="email"
       value="<%= user.getEmail() == null ? "" : user.getEmail() %>">



<label>
Password
</label>


<input type="password"
       name="password"
       placeholder="Enter new password or leave blank to keep current password">



<label>
Role
</label>


<select name="role" required>


<option value="Admin"
<%= "Admin".equals(user.getRole()) ? "selected" : "" %>>
Administrator
</option>



<option value="Receptionist"
<%= "Receptionist".equals(user.getRole()) ? "selected" : "" %>>
Receptionist
</option>


</select>



<button type="submit">
Update User
</button>


</form>



<div class="back">

<a href="ManageUsers">
← Back to Manage Users
</a>

</div>


</div>


<%

}

%>


</body>

</html>