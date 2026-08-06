<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dentalclinic.model.Patient" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Patient | Sunrise Dental Clinic</title>

<style>

body{
    font-family: Arial, sans-serif;
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

input[type=number]{
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
    border:1px solid #ddd;
    text-align:center;
}

.message{
    text-align:center;
    color:red;
    font-weight:bold;
    margin-top:20px;
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

        <form action="SearchPatient" method="get">

            <label>Enter Patient ID:</label><br>

            <input
                type="number"
                name="patientId"
                placeholder="Enter Patient ID"
                required>

            <button type="submit">
                Search
            </button>

        </form>

    </div>

    <div class="card">

        <h2>Patient Details</h2>

        <%

        Patient patient = (Patient) request.getAttribute("patient");

        String error = (String) request.getAttribute("error");

        if(patient != null){

        %>

        <table>

            <tr>
                <th>Patient ID</th>
                <th>Full Name</th>
                <th>Address</th>
                <th>Phone Number</th>
            </tr>

            <tr>

                <td><%= patient.getPatientId() %></td>

                <td><%= patient.getFullName() %></td>

                <td><%= patient.getAddress() %></td>

                <td><%= patient.getPhoneNumber() %></td>

            </tr>

        </table>

        <%

        } else if(error != null){

        %>

        <p class="message">
            <%= error %>
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