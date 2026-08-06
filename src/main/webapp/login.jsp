<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login | Sunrise Dental Clinic</title>

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:Arial, Helvetica, sans-serif;
}

body{

    background:#f4f8fb;
    background-image: url("images/login.jpg");
    display:flex;
    justify-content:center;
    align-items:center;
    height:100vh;
}

.login-box{

    width:420px;
    background:#ffffff;
    padding:40px;
    border-radius:10px;
    box-shadow:0 5px 15px rgba(0,0,0,.2);
}

h1{

    text-align:center;
    color:#0077b6;
    margin-bottom:8px;
}

h3{

    text-align:center;
    color:#555;
    margin-bottom:30px;
    font-weight:normal;
}

label{

    display:block;
    margin-top:15px;
    margin-bottom:5px;
    font-weight:bold;
    color:#333;
}

input[type=text],
input[type=password]{

    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:5px;
    font-size:15px;
}

input:focus{

    outline:none;
    border:1px solid #0077b6;
}

button{

    width:100%;
    padding:13px;
    margin-top:25px;
    background:#0077b6;
    color:white;
    border:none;
    border-radius:5px;
    cursor:pointer;
    font-size:16px;
}

button:hover{

    background:#005f87;
}

.error{

    background:#ffe5e5;
    color:red;
    padding:10px;
    border-radius:5px;
    margin-bottom:15px;
    text-align:center;
}

.success{

    background:#e7ffe7;
    color:green;
    padding:10px;
    border-radius:5px;
    margin-bottom:15px;
    text-align:center;
}

.footer{

    text-align:center;
    margin-top:20px;
    color:#777;
    font-size:13px;
}

</style>

</head>

<body>

<div class="login-box">

    <h1>Sunrise Dental Clinic</h1>

    <h3>Management System Login</h3>

    <% if(request.getAttribute("error") != null){ %>

        <div class="error">

            <%= request.getAttribute("error") %>

        </div>

    <% } %>

    <% if(request.getAttribute("message") != null){ %>

        <div class="success">

            <%= request.getAttribute("message") %>

        </div>

    <% } %>

    <form action="Login" method="post">

        <label>Username</label>

        <input
            type="text"
            name="username"
            value="<%= request.getAttribute("username") == null ? "" : request.getAttribute("username") %>"
            required>

        <label>Password</label>

        <input
            type="password"
            name="password"
            required>

        <button type="submit">

            Login

        </button>

    </form>

    <div class="footer">

        © 2026 Sunrise Dental Clinic Management System

    </div>

</div>

</body>
</html>