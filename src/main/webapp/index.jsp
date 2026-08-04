<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sunrise Dental Clinic Management System</title>

<%
    // Get the context path for proper URL construction
    String contextPath = request.getContextPath();
%>

<!-- Redirect to Login page after 2 seconds -->
<meta http-equiv="refresh" content="2;url=<%= contextPath %>/Login">

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: Arial, Helvetica, sans-serif;
    }

    body {
        background: #f4f9fc;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        width: 600px;
        background: white;
        padding: 40px;
        border-radius: 10px;
        box-shadow: 0 0 15px rgba(0,0,0,0.15);
        text-align: center;
    }

    h1 {
        color: #0077b6;
        margin-bottom: 15px;
        font-size: 28px;
    }

    h2 {
        color: #333;
        margin-bottom: 20px;
        font-size: 20px;
    }

    p {
        color: #666;
        font-size: 16px;
        margin-bottom: 15px;
    }

    .features {
        text-align: left;
        display: inline-block;
        margin: 10px 0 20px 0;
        padding: 0;
        list-style: none;
    }

    .features li {
        padding: 8px 0;
        color: #444;
        font-size: 15px;
    }

    .features li:before {
        content: "✓ ";
        color: #0077b6;
        font-weight: bold;
    }

    .loading {
        color: #0077b6;
        font-weight: bold;
        font-size: 14px;
    }

    .loading:after {
        content: "...";
        animation: dots 1.5s steps(4, end) infinite;
    }

    @keyframes dots {
        0% { content: ""; }
        25% { content: "."; }
        50% { content: ".."; }
        75% { content: "..."; }
    }

    .btn {
        display: inline-block;
        margin-top: 20px;
        padding: 12px 30px;
        background: #0077b6;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        font-weight: bold;
        transition: background 0.3s ease;
    }

    .btn:hover {
        background: #005f8c;
        transform: translateY(-2px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    .btn:active {
        transform: translateY(0);
    }

    .clinic-icon {
        font-size: 48px;
        margin-bottom: 10px;
    }

    .footer {
        margin-top: 20px;
        color: #999;
        font-size: 12px;
    }
</style>

</head>
<body>

<div class="container">

    <div class="clinic-icon">🏥</div>

    <h1>Sunrise Dental Clinic</h1>

    <h2>Dental Clinic Management System</h2>

    <p>
        Welcome to the Sunrise Dental Clinic Management System.
    </p>

    <p>
        This system allows staff members to:
    </p>

    <ul class="features">
        <li>Register Patients</li>
        <li>Manage Appointments</li>
        <li>Search Patient Records</li>
        <li>Calculate Bills</li>
        <li>Print Bills</li>
        <li>Help</li>
    </ul>

    <p class="loading">
        Redirecting to Login Page
    </p>

    <a href="<%= contextPath %>/Login" class="btn">
        Continue to Login
    </a>

    <div class="footer">
        &copy; 2024 Sunrise Dental Clinic. All rights reserved.
    </div>

</div>

<script>
    // Ensure redirect happens even if meta refresh fails
    setTimeout(function() {
        window.location.href = '<%= contextPath %>/Login';
    }, 3000);
</script>

</body>
</html>