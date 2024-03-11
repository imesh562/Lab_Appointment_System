<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin-Home</title>
    <link rel="stylesheet" href="assets/admin_index/admin_index-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<div class="nav-bar-main">
    <a id="a-logo" href="admin_index.jsp">
        <img src="assets/images/logo.webp" alt="">
    </a>
    <a class="nav-link" id="nav-home" href="admin_index.jsp">
        <i class="fa fa-home"></i>
    </a>
    <a class="nav-link" href="AdminHomeData?actionType=Logout">
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
    </div>
    <div class="appointment-table">
        <table class="table-appointments">
            <thead>
            <tr>
                <th>Test Name</th>
                <th>Appointment No</th>
                <th>Scheduled Date</th>
                <th>Created Date</th>
                <th>Doctor</th>
                <th>Price (LKR)</th>
                <th>Status</th>
                <th>Download</th>
                <th>Proceed</th>
                <th>Cancel</th>
            </tr>
            </thead>
            <tbody id="appointments-list"></tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="assets/admin_index/admin_index-js.js"></script>
<script src="https://js.stripe.com/v3/"></script>
<script>
    const message = '<%= request.getAttribute("message") %>';
    const title = '<%= request.getAttribute("title") %>';
    const icon = '<%= request.getAttribute("icon") %>';
    if (message !== "null") {
        document.addEventListener('DOMContentLoaded', () => {
            showDialogBox(title, message, icon);
        })
    }
</script>
</body>
</html>