<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dentalclinic.model.Bill" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calculate Bill | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:60%;
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

label{
    font-weight:bold;
}

input[type=text],
input[type=number]{
    width:100%;
    padding:10px;
    margin-top:5px;
    margin-bottom:15px;
    border:1px solid #ccc;
    border-radius:5px;
}

select{
    width:100%;
    padding:10px;
    margin-top:5px;
    margin-bottom:15px;
    border:1px solid #ccc;
    border-radius:5px;
}

button{
    width:100%;
    padding:12px;
    background:#1976d2;
    color:white;
    border:none;
    border-radius:5px;
    cursor:pointer;
    font-size:16px;
}

button:hover{
    background:#0d47a1;
}

.result{
    margin-top:25px;
    background:#e8f5e9;
    padding:20px;
    border-radius:6px;
}

.result h3{
    color:#2e7d32;
}

.back{
    text-align:center;
    margin-top:25px;
}

.back a{
    text-decoration:none;
    color:#1976d2;
    font-weight:bold;
}

.error{
    color:red;
    text-align:center;
    font-weight:bold;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<h2>Calculate Bill</h2>

<form action="calculateBill" method="post">

<label>Appointment ID</label>
<input type="number"
       name="appointmentId"
       required>

<label>Patient ID</label>
<input type="number"
       name="patientId"
       required>

<label>Patient Name</label>
<input type="text"
       name="patientName"
       required>

<label>Patient Number</label>
<input type="text"
       name="patientNumber"
       required>

<label>Patient Contact</label>
<input type="text"
       name="patientContact"
       required>

<label>Treatment Type</label>
<input type="text"
       name="treatmentType"
       required>

<label>Treatment Description</label>
<input type="text"
       name="treatmentDescription"
       required>

<label>Treatment Cost ($)</label>
<input type="number"
       step="0.01"
       name="treatmentCost"
       required>

<label>Consultation Fee ($)</label>
<input type="number"
       step="0.01"
       name="consultationFee"
       value="50.00"
       required>

<label>Amount Paid ($)</label>
<input type="number"
       step="0.01"
       name="amountPaid"
       required>

<label>Payment Method</label>

<select name="paymentMethod">

<option value="CASH">Cash</option>
<option value="CARD">Card</option>

</select>

<button type="submit">
Calculate Bill
</button>

</form>

<%
Bill bill = (Bill) request.getAttribute("bill");

if(bill != null){
%>

<div class="result">

<h3>Bill Summary</h3>

<p><strong>Patient:</strong>
<%= bill.getPatientName() %>
</p>

<p><strong>Treatment:</strong>
<%= bill.getTreatmentType() %>
</p>

<p><strong>Treatment Cost:</strong>
$<%= String.format("%.2f", bill.getTreatmentCost()) %>
</p>

<p><strong>Consultation Fee:</strong>
$<%= String.format("%.2f", bill.getConsultationFee()) %>
</p>

<p><strong>Total Amount:</strong>
<strong>$<%= String.format("%.2f", bill.getTotalAmount()) %></strong>
</p>

<p><strong>Amount Paid:</strong>
$<%= String.format("%.2f", bill.getAmountPaid()) %>
</p>

<p><strong>Balance:</strong>
$<%= String.format("%.2f", bill.calculateBalance()) %>
</p>

<p><strong>Payment Method:</strong>
<%= bill.getPaymentMethodDisplay() %>
</p>

</div>

<%
}
%>

<%
String error = (String)request.getAttribute("error");

if(error != null){
%>

<p class="error">
<%= error %>
</p>

<%
}
%>

<div class="back">

<a href="dashboard.jsp">
← Back to Dashboard
</a>

</div>

</div>

</div>

</body>
</html>