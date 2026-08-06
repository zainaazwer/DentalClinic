<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dentalclinic.model.Appointment" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Search Appointment | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
    background-image: url("images/searchappointment.jpg");
}

.container{
    width:80%;
    margin:40px auto;
}

.card{
    background:white;
    padding:25px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,.2);
    margin-bottom:20px;
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
    margin-top:10px;
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

.message{
    color:red;
    text-align:center;
    font-weight:bold;
}

.back{
    margin-top:20px;
    text-align:center;
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

<div class="card">

<h2>Search Appointment</h2>

<form action="SearchAppointment" method="get">

<label>Appointment ID</label><br>

<input
type="text"
name="appointmentId"
required>

<button type="submit">
Search
</button>

</form>

</div>


<div class="card">

<h2>Appointment Details</h2>

<%

Appointment appointment =
(Appointment) request.getAttribute("appointment");

if(appointment != null){

%>

<table>

<tr>
<th>Appointment ID</th>
<th>Patient ID</th>
<th>Patient Name</th>
<th>Dentist Name</th>
<th>Treatment</th>
<th>Date</th>
<th>Time</th>
</tr>

<tr>

<td>
<%= appointment.getAppointmentId() %>
</td>

<td>
<%= appointment.getPatientId() %>
</td>

<td>
<%= appointment.getPatientName() %>
</td>

<td>
<%= appointment.getDentistName() %>
</td>

<td>
<%= appointment.getTreatmentType() %>
</td>

<td>
<%= appointment.getAppointmentDate() %>
</td>

<td>
<%= appointment.getAppointmentTime() %>
</td>

</tr>

</table>

<%

}else if(request.getParameter("appointmentId") != null){

%>

<p class="message">
Appointment not found.
</p>

<%

}

%>

</div>

<div class="back">

<a href="Dashboard.jsp">
← Back to Dashboard
</a>

</div>

</div>

</body>

</html>