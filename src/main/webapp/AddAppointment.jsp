<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Add Appointment | Sunrise Dental Clinic</title>

<style>

body{
    font-family: Arial, sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:500px;
    margin:40px auto;
    background:white;
    padding:30px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
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
input[type=date],
input[type=time]{

    width:100%;
    padding:10px;
    margin-top:5px;
    border:1px solid #ccc;
    border-radius:5px;
    box-sizing:border-box;

}


button{

    width:100%;
    margin-top:25px;
    padding:12px;

    background:#1976d2;
    color:white;

    border:none;
    border-radius:5px;

    font-size:16px;
    cursor:pointer;

}


button:hover{

    background:#0d47a1;

}


.message{

    text-align:center;
    font-weight:bold;

}


.success{

    color:green;

}


.error{

    color:red;

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


<h2>Register Appointment</h2>


<%

String success = (String) request.getAttribute("success");
String error = (String) request.getAttribute("error");


if(success != null){

%>

<p class="message success">
<%= success %>
</p>

<%

}


if(error != null){

%>

<p class="message error">
<%= error %>
</p>

<%

}

%>



<form action="addAppointment" method="post">


<label>Patient ID</label>

<input type="text"
       name="patientId"
       required>



<label>Patient Name</label>

<input type="text"
       name="patientName"
       required>



<label>Dentist Name</label>

<input type="text"
       name="dentistName"
       required>



<label>Treatment Type</label>

<input type="text"
       name="treatmentType"
       placeholder="Example: Cleaning, Filling, Extraction"
       required>



<label>Appointment Date</label>

<input type="date"
       name="appointmentDate"
       required>



<label>Appointment Time</label>

<input type="time"
       name="appointmentTime"
       required>



<button type="submit">
Register Appointment
</button>


</form>



<div class="back">

<a href="dashboard.jsp">
← Back to Dashboard
</a>

</div>


</div>


</body>

</html>