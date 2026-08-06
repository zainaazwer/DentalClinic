<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dentalclinic.model.Appointment" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">
<title>Appointment List | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:90%;
    margin:40px auto;
}

.card{
    background:white;
    padding:25px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
}

h2{
    text-align:center;
    color:#1976d2;
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
    border:1px solid #ddd;
    padding:10px;
    text-align:center;
}

tr:nth-child(even){
    background:#f8f8f8;
}

tr:hover{
    background:#eeeeee;
}

.message{
    text-align:center;
    color:red;
    font-weight:bold;
    margin-top:20px;
}

.back{
    margin-top:25px;
    text-align:center;
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

<h2>Appointment List</h2>


<%

List<Appointment> appointments =
(List<Appointment>) request.getAttribute("appointments");


if(appointments != null && !appointments.isEmpty()){

%>


<table>

<tr>
<th>Appointment ID</th>
<th>Patient ID</th>
<th>Patient Name</th>
<th>Dentist</th>
<th>Treatment</th>
<th>Date</th>
<th>Time</th>
</tr>


<%

for(Appointment appointment : appointments){

%>


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


<%

}

%>


</table>


<%

}
else if(appointments != null){

%>


<p class="message">
No appointments found.
</p>


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