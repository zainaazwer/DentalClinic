<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sunrise Dental Clinic Management System</title>

<!-- Redirect to Login page after 2 seconds -->
<meta http-equiv="refresh" content="2;url=login">

<style>
    *{
        margin:0;
        padding:0;
        box-sizing:border-box;
        font-family:Arial, Helvetica, sans-serif;
    }

    body{
        background:#f4f9fc;
        display:flex;
        justify-content:center;
        align-items:center;
        height:100vh;
    }

    .container{
        width:600px;
        background:white;
        padding:40px;
        border-radius:10px;
        box-shadow:0 0 15px rgba(0,0,0,0.15);
        text-align:center;
    }

    h1{
        color:#0077b6;
        margin-bottom:15px;
    }

    h2{
        color:#333;
        margin-bottom:20px;
    }

    p{
        color:#666;
        font-size:16px;
        margin-bottom:15px;
    }

    .loading{
        color:#0077b6;
        font-weight:bold;
    }

    .btn{
        display:inline-block;
        margin-top:20px;
        padding:10px 20px;
        background:#0077b6;
        color:white;
        text-decoration:none;
        border-radius:5px;
    }

    .btn:hover{
        background:#005f8c;
    }
</style>

</head>

<body>

<div class="container">

    <h1>Sunrise Dental Clinic</h1>

    <h2>Dental Clinic Management System</h2>

    <p>
        Welcome to the Sunrise Dental Clinic Management System.
    </p>

    <p>
        This system allows staff members to:
    </p>

    <ul style="text-align:left; display:inline-block; margin-top:10px;">
        <li>Register Patients</li>
        <li>Manage Appointments</li>
        <li>Search Patient Records</li>
        <li>Calculate Bills</li>
        <li>Print Bills</li>
        <li>Help</li>
    </ul>

    <br><br>

    <p class="loading">
        Redirecting to Login Page...
    </p>

    <a href="login" class="btn">
        Continue to Login
    </a>

</div>

</body>
</html>