<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.User" %>

<%
User loggedUser = (User) session.getAttribute("user");

if (loggedUser == null) {
    response.sendRedirect("Login.jsp");
    return;
}

if (!"Admin".equals(loggedUser.getRole())) {
    response.sendRedirect("Dashboard.jsp");
    return;
}

List<User> users = (List<User>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Manage Users | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
    background-image: url("images/manageusers.jpg");
}

.container{
    width:85%;
    margin:40px auto;
}

.card{
    background:white;
    padding:25px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,.2);
}

h2{
    text-align:center;
    color:#ff1493;
}

.add-btn{
    display:inline-block;
    padding:10px 20px;
    background:#ff1493;
    color:white;
    text-decoration:none;
    border-radius:5px;
    margin-bottom:20px;
}

.add-btn:hover{
    background:#0d47a1;
}

table{
    width:100%;
    border-collapse:collapse;
}

th{
    background:#ff1493;
    color:white;
    padding:12px;
}

td{
    padding:10px;
    text-align:center;
    border-bottom:1px solid #ddd;
}

tr:hover{
    background:#f1f1f1;
}

.edit{
    background:#388e3c;
    color:white;
    padding:6px 12px;
    border-radius:4px;
    text-decoration:none;
}

.delete{
    background:#d32f2f;
    color:white;
    padding:6px 12px;
    border-radius:4px;
    text-decoration:none;
}

.message{
    text-align:center;
    color:red;
    font-weight:bold;
}

.back{
    margin-top:20px;
    text-align:center;
}

.back a{
    text-decoration:none;
    color:#ff1493;
    font-weight:bold;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<h2>Manage Users</h2>

<a href="AddUser.jsp" class="add-btn">+ Add New User</a>

<%
if (users != null && !users.isEmpty()) {
%>

<table>

<tr>
    <th>User ID</th>
    <th>Full Name</th>
    <th>Username</th>
    <th>Password</th>
    <th>Email</th>
    <th>Role</th>
    <th>Actions</th>
</tr>

<%
for(User user : users){
%>

<tr>

<td>
<%= user.getUserId() %>
</td>


<td>
<%= user.getFullName() %>
</td>


<td>
<%= user.getUsername() %>
</td>


<td>
<%= user.getPassword() == null ? "" : user.getPassword() %>
</td>


<td>
<%= user.getEmail() == null ? "" : user.getEmail() %>
</td>


<td>
<%= user.getRole() %>
</td>


<td>

<a class="edit"
href="EditUser?userId=<%= user.getUserId() %>">
Edit
</a>


<a class="delete"
href="DeleteUser?userId=<%= user.getUserId() %>"
onclick="return confirm('Are you sure you want to delete this user?');">
Delete
</a>

</td>

</tr>

<%
}
%>

</table>

<%
} else {
%>

<p class="message">No users found.</p>

<%
}
%>

<div class="back">
<a href="Dashboard.jsp">← Back to Dashboard</a>
</div>

</div>

</div>

</body>
</html>