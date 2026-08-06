<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Patient | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial, sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:450px;
    margin:40px auto;
    background:#fff;
    padding:30px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,.2);
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

input[type=text],
textarea{
    width:100%;
    padding:10px;
    margin-top:5px;
    border:1px solid #ccc;
    border-radius:5px;
    box-sizing:border-box;
}

textarea{
    resize:vertical;
}

input[type=submit]{
    width:100%;
    padding:12px;
    margin-top:25px;
    background:#1976d2;
    color:white;
    border:none;
    border-radius:5px;
    font-size:16px;
    cursor:pointer;
}

input[type=submit]:hover{
    background:#0d47a1;
}

.success{
    color:green;
    text-align:center;
    font-weight:bold;
}

.error{
    color:red;
    text-align:center;
    font-weight:bold;
}

.back{
    display:block;
    text-align:center;
    margin-top:20px;
}

.back a{
    text-decoration:none;
    color:#1976d2;
    font-weight:bold;
}

</style>

</head>

<body>

<div class="container">

<h2>Add New Patient</h2>

<%
String success=(String)request.getAttribute("success");
String error=(String)request.getAttribute("error");

if(success!=null){
%>

<p class="success"><%=success%></p>

<%
}

if(error!=null){
%>

<p class="error"><%=error%></p>

<%
}
%>

<form action="AddPatient" method="post">

<label>Full Name</label>

<input type="text"
name="fullName"
required>

<label>Address</label>

<textarea
name="address"
rows="4"
required></textarea>

<label>Phone Number</label>

<input type="text"
name="phoneNumber"
required>

<input type="submit"
value="Add Patient">

</form>

<div class="back">
<a href="Dashboard.jsp">← Back to Dashboard</a>
</div>

</div>

</body>
</html>