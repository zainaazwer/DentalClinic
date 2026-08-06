<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Error | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:60%;
    margin:100px auto;
}

.card{
    background:white;
    padding:35px;
    text-align:center;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
}

h1{
    color:#d32f2f;
}

p{
    font-size:18px;
    color:#555;
}

.error-box{
    background:#ffebee;
    padding:20px;
    border-left:5px solid #d32f2f;
    margin-top:20px;
}

.button{
    display:inline-block;
    margin-top:25px;
    padding:12px 25px;
    background:#1976d2;
    color:white;
    text-decoration:none;
    border-radius:5px;
}

.button:hover{
    background:#0d47a1;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<h1>Something Went Wrong</h1>

<p>
An unexpected error occurred while processing your request.
</p>


<div class="error-box">

<%
String errorMessage = (String) request.getAttribute("error");

if(errorMessage != null){
%>

<p>
<strong>Error:</strong>
<%= errorMessage %>
</p>

<%
}else{
%>

<p>
Please try again later.
</p>

<%
}
%>

</div>


<a class="button" href="Dashboard.jsp">
Return to Dashboard
</a>


</div>

</div>

</body>

</html>