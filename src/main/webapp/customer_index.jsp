<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="assets/customer_index/customer_index-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
</head>
<body>
<div class="nav-bar-main">
    <a id="a-logo" href="customer_index.jsp">
        <img src="assets/images/logo.webp" alt="">
    </a>
    <a class="nav-link" id="nav-home" href="customer_index.jsp">
        <i class="fa fa-home"></i>
    </a>
    <a class="nav-link" id="nav-add-appointment" href="AddNewAppointment">
        <i class="fa fa-plus-circle"></i>
    </a>
    <a class="nav-link" href="CustomerLogout?action-type=Logout">
        <i class="fa fa-sign-out"></i>
    </a>
</div>
<div class="container">
    <div class="top-dropdown-btn-wrapper">
        <div class="filter-select-wrapper">
            <div class="filter-icon-box">
                <i class="fa fa-sliders" aria-hidden="true"></i>
            </div>
            <select class="select-filter" onchange="filterAppointments()" name="" id="filter-appointment">
                <option value="All">All</option>
                <option value="Upcoming">Upcoming</option>
                <option value="Pending">Pending</option>
                <option value="Completed">Completed</option>
                <option value="Canceled">Canceled</option>
            </select>
        </div>
        <a class="a-add-appointment" href="AddNewAppointment">
            <i class="fa fa-plus-circle"></i>
            <p>Add Appointment</p>
        </a>
    </div>
    <div class="appointment-table">
        <table class="table-appointments">
            <thead>
            <tr>
                <th>Test Name</th>
                <th>Created Date</th>
                <th>Scheduled Date</th>
                <th>Price</th>
                <th>Status</th>
                <th>Result</th>
                <th>Download</th>
                <th>Cancel</th>
            </tr>
            </thead>
            <tbody id="appointments-list"></tbody>
        </table>
    </div>
</div>
<script src="assets/customer_index/customer_index-js.js"></script>
</body>
</html>