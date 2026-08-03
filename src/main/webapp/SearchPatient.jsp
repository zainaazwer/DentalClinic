<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Patient" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Patient | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial, sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:80%;
    margin:40px auto;
}

.card{
    background:white;
    padding:25px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
    margin-bottom:25px;
}

h2{
    text-align:center;
    color:#1976d2;
}

label{
    font-weight:bold;
}

input[type=text]{
    width:70%;
    padding:10px;
    margin:10px 0;
    border:1px solid #ccc;
    border-radius:5px;
}

button{
    padding:10px 20px;
    background:#1976d2;
    color:white;
    border:none;
    border-radius:5px;
    cursor:pointer;
}

button:hover{
    background:#0d47a1;
}

table{
    width:100%;
    border-collapse:collapse;
    margin-top:20px;
}

th{
    background:#1976d2;
    color:white;
    padding:12px;
}

td{
    padding:10px;
    border-bottom:1px solid #ddd;
    text-align:center;
}

tr:hover{
    background:#f1f1f1;
}

.message{
    text-align:center;
    color:red;
    font-weight:bold;
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

<div class="container">

<div class="card">

<h2>Search Patient</h2>

<form action="searchPatient" method="get">

<label>Enter Patient Name or Phone Number:</label><br>

<input type="text"
       name="keyword"
       placeholder="Enter name or phone number"
       required>

<button type="submit">
Search
</button>

</form>

</div>


<div class="card">

<h2>Patient Details</h2>

<%
List<Patient> patients =
(List<Patient>) request.getAttribute("patients");

if(patients != null && !patients.isEmpty()){

%>

<table>

<tr>
<th>Patient ID</th>
<th>Name</th>
<th>Address</th>
<th>Phone Number</th>
</tr>


<%
for(Patient patient : patients){
%>

<tr>

<td>
<%= patient.getPatientId() %>
</td>

<td>
<%= patient.getFullName() %>
</td>

<td>
<%= patient.getAddress() %>
</td>

<td>
<%= patient.getPhoneNumber() %>
</td>

</tr>

<%
}
%>

</table>


<%
}else if(patients != null){
%>

<p class="message">
No patient records found.
</p>

<%
}
%>


</div>


<div class="back">

<a href="dashboard.jsp">
← Back to Dashboard
</a>

</div>


</div>

</body>

</html>