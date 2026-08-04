<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dentalclinic.model.User" %>

<%
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = user.getRole();
%>


<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Dashboard | Sunrise Dental Clinic</title>


<style>

body{
    margin:0;
    font-family:Arial, Helvetica, sans-serif;
    background:#f4f6f9;
}

.header{
    background:#1976d2;
    color:white;
    padding:20px;
}

.header h1{
    margin:0;
}

.container{
    width:90%;
    margin:30px auto;
}

.card{
    background:white;
    padding:20px;
    border-radius:8px;
    box-shadow:0 2px 6px rgba(0,0,0,0.2);
    margin-bottom:20px;
}

.menu{
    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(220px,1fr));
    gap:20px;
}

.menu a{
    text-decoration:none;
    color:white;
    background:#2196F3;
    padding:18px;
    border-radius:8px;
    text-align:center;
    font-weight:bold;
    transition:.3s;
}

.menu a:hover{
    background:#1565c0;
}

.logout{
    display:inline-block;
    margin-top:20px;
    background:#d32f2f;
    color:white;
    padding:10px 18px;
    text-decoration:none;
    border-radius:5px;
}

.logout:hover{
    background:#b71c1c;
}

.footer{
    text-align:center;
    margin-top:40px;
    color:#777;
}

</style>


</head>


<body>


<div class="header">

<h1>
Sunrise Dental Clinic Management System
</h1>

</div>



<div class="container">



<div class="card">

<h2>
Welcome, <%= user.getFullName() %>
</h2>


<p>
<strong>Username:</strong> 
<%= user.getUsername() %>
</p>


<p>
<strong>Role:</strong> 
<%= user.getRole() %>
</p>


<p>
You are successfully logged in.
</p>


</div>




<div class="card">


<h2>
Menu
</h2>



<div class="menu">



<%
if("Administrator".equals(role)){
%>


<a href="manageUsers">
Manage Users
</a>


<a href="listAppointment">
View Appointments
</a>


<%
}
%>




<%
if("Receptionist".equals(role)){
%>



<a href="addPatient.jsp">
Add Patient
</a>


<a href="searchPatient.jsp">
Search Patient
</a>


<a href="addAppointment.jsp">
Register Appointment
</a>


<a href="listAppointment">
View Appointments
</a>


<a href="searchAppointment.jsp">
Search Appointment
</a>


<a href="calculateBill.jsp">
Calculate Bill
</a>


<a href="printBill.jsp">
Print Bill
</a>



<%
}
%>




<a href="help.jsp">
Help
</a>



</div>



<a class="logout" href="logout">
Logout
</a>



</div>




<div class="footer">

© 2026 Sunrise Dental Clinic Management System

</div>



</div>



</body>

</html>