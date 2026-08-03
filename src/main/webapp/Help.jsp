<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Help | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:80%;
    margin:40px auto;
}

.card{
    background:white;
    padding:30px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
}

h1{
    text-align:center;
    color:#1976d2;
}

h2{
    color:#1565c0;
    margin-top:30px;
}

ol{
    line-height:1.8;
}

ul{
    line-height:1.8;
}

.note{
    background:#e3f2fd;
    padding:15px;
    border-left:5px solid #1976d2;
    margin-top:25px;
}

.back{
    text-align:center;
    margin-top:30px;
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

<h1>Sunrise Dental Clinic Management System</h1>

<h2>User Guide</h2>

<p>
This guide helps new staff use the Sunrise Dental Clinic Management System.
Follow the steps below when using the application.
</p>

<h2>1. Login</h2>

<ol>

<li>Open the system.</li>

<li>Enter your username and password.</li>

<li>Click the <strong>Login</strong> button.</li>

<li>The Dashboard will appear after successful login.</li>

</ol>

<h2>2. Add a Patient</h2>

<ol>

<li>Select <strong>Add Patient</strong> from the dashboard.</li>

<li>Enter the patient's details.</li>

<li>Click <strong>Save</strong>.</li>

<li>The patient record will be stored in the database.</li>

</ol>

<h2>3. Search a Patient</h2>

<ol>

<li>Select <strong>Search Patient</strong>.</li>

<li>Enter the patient name or phone number.</li>

<li>Click <strong>Search</strong>.</li>

<li>The patient information will be displayed.</li>

</ol>

<h2>4. Add an Appointment</h2>

<ol>

<li>Select <strong>Add Appointment</strong>.</li>

<li>Choose the patient.</li>

<li>Enter the dentist, treatment, date and time.</li>

<li>Click <strong>Save Appointment</strong>.</li>

</ol>

<h2>5. Search Appointments</h2>

<ol>

<li>Select <strong>Search Appointment</strong>.</li>

<li>Enter the appointment ID.</li>

<li>Click <strong>Search</strong>.</li>

<li>The appointment details will appear.</li>

</ol>

<h2>6. View Appointment List</h2>

<ol>

<li>Select <strong>Appointment List</strong>.</li>

<li>All appointments will be displayed.</li>

<li>Review appointment details when required.</li>

</ol>

<h2>7. Calculate a Bill</h2>

<ol>

<li>Select <strong>Calculate Bill</strong>.</li>

<li>Enter the patient and treatment details.</li>

<li>The consultation fee is added automatically.</li>

<li>Click <strong>Calculate Bill</strong>.</li>

</ol>

<h2>8. Print Bill</h2>

<ol>

<li>Open the completed bill.</li>

<li>Review all billing details.</li>

<li>Click <strong>Print Bill</strong>.</li>

<li>Provide the printed bill to the patient.</li>

</ol>

<div class="note">

<strong>Important Notes</strong>

<ul>

<li>Always verify patient details before saving.</li>

<li>Check appointment dates carefully to avoid double bookings.</li>

<li>Review the bill before printing.</li>

<li>Logout after completing your work to keep patient information secure.</li>

</ul>

</div>

<div class="back">

<a href="dashboard.jsp">
← Back to Dashboard
</a>

</div>

</div>

</div>

</body>
</html>