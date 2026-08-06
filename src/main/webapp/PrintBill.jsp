<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dentalclinic.model.Bill" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Print Bill | Sunrise Dental Clinic</title>

<style>

body{
    font-family:Arial,sans-serif;
    background:#f4f6f9;
    margin:0;
}

.container{
    width:700px;
    margin:40px auto;
}

.receipt{
    background:white;
    padding:30px;
    border-radius:8px;
    box-shadow:0 2px 8px rgba(0,0,0,0.2);
}

h1{
    text-align:center;
    color:#1976d2;
}

h3{
    text-align:center;
    color:#666;
}

table{
    width:100%;
    border-collapse:collapse;
    margin-top:20px;
}

td{
    padding:10px;
    border-bottom:1px solid #ddd;
}

.label{
    font-weight:bold;
    width:40%;
}

.total{
    font-size:18px;
    font-weight:bold;
    color:#1976d2;
}

.buttons{
    text-align:center;
    margin-top:30px;
}

button{
    padding:12px 25px;
    background:#1976d2;
    color:white;
    border:none;
    border-radius:5px;
    cursor:pointer;
    margin:5px;
    font-size:15px;
}

button:hover{
    background:#0d47a1;
}

a{
    text-decoration:none;
}

@media print{

    button{
        display:none;
    }

    body{
        background:white;
    }

    .receipt{
        box-shadow:none;
        border:none;
    }

}

</style>

<script>

function printBill(){
    window.print();
}

</script>

</head>

<body>

<%
Bill bill = (Bill) request.getAttribute("bill");
%>

<div class="container">

<div class="receipt">

<h1>Sunrise Dental Clinic</h1>
<h3>Patient Bill Receipt</h3>

<% if(bill != null){ %>

<table>

<tr>
<td class="label">Bill ID</td>
<td><%= bill.getBillId() %></td>
</tr>

<tr>
<td class="label">Bill Date</td>
<td><%= bill.getBillDate() %></td>
</tr>

<tr>
<td class="label">Appointment ID</td>
<td><%= bill.getAppointmentId() %></td>
</tr>

<tr>
<td class="label">Patient ID</td>
<td><%= bill.getPatientId() %></td>
</tr>

<tr>
<td class="label">Patient Name</td>
<td><%= bill.getPatientName() %></td>
</tr>

<tr>
<td class="label">Patient Number</td>
<td><%= bill.getPatientNumber() %></td>
</tr>

<tr>
<td class="label">Contact Number</td>
<td><%= bill.getPatientContact() %></td>
</tr>

<tr>
<td class="label">Treatment</td>
<td><%= bill.getTreatmentType() %></td>
</tr>

<tr>
<td class="label">Description</td>
<td><%= bill.getTreatmentDescription() %></td>
</tr>

<tr>
<td class="label">Treatment Cost</td>
<td>$<%= String.format("%.2f", bill.getTreatmentCost()) %></td>
</tr>

<tr>
<td class="label">Consultation Fee</td>
<td>$<%= String.format("%.2f", bill.getConsultationFee()) %></td>
</tr>

<tr>
<td class="label total">Total Amount</td>
<td class="total">
$<%= String.format("%.2f", bill.getTotalAmount()) %>
</td>
</tr>

<tr>
<td class="label">Amount Paid</td>
<td>$<%= String.format("%.2f", bill.getAmountPaid()) %></td>
</tr>

<tr>
<td class="label">Balance</td>
<td>$<%= String.format("%.2f", bill.calculateBalance()) %></td>
</tr>

<tr>
<td class="label">Payment Method</td>
<td><%= bill.getPaymentMethodDisplay() %></td>
</tr>

<tr>
<td class="label">Payment Date</td>
<td><%= bill.getPaymentDate() %></td>
</tr>

</table>

<% } else { %>

<h3>No bill available.</h3>

<% } %>

<div class="buttons">

<button onclick="printBill()">
Print Bill
</button>

<button onclick="location.href='Dashboard.jsp'">
Back to Dashboard
</button>

</div>

</div>

</div>

</body>
</html>