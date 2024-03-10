<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Appointment</title>
    <link rel="stylesheet" href="assets/add_appointment/add_appointment-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
    <a class="nav-link" href="CustomerLogout?actionType=Logout">
        <i class="fa fa-sign-out"></i>
    </a>
</div>
<div class="container">
    <div class="add-appointment-main">
        <form id="add-appointment">
            <div class="inp-box">
                <label for="lab-test">*Lab Test :</label>
                <input type="text" id="lab-test" autocomplete="off" oninput="filterOptions()" onfocus="clearDropdown()" onfocusout="fillDropdown()" onchange="handleOptionSelection()" list="options">
                <datalist id="options"></datalist>
                <div style="display: flex; justify-content: space-between;">
                    <p style="font-size: small" id="technician"></p>
                    <p style="font-size: small" id="price"></p>
                </div>
            </div>
            <div class="inp-box"  id="datepicker-box" style="display: none;">
                <label for="datepicker">*Select a date :</label>
                <input type="text" autocomplete="off" id="datepicker">
                <p style="color: red; font-size: small">Note: Some dates on the calendar may be disabled due to full capacity.</p>
            </div>
            <div class="inp-box">
                <label for="doctor-name">Doctor's name (Optional) :</label>
                <input type="text" id="doctor-name" name="doctor-name">
            </div>
            <div class="submit-btn-wrapper">
                <button class="btn-submit" id="submit-appointment">SUBMIT</button>
            </div>
        </form>
    </div>
</div>
<% String testData = (String)request.getAttribute("labTests"); %>
<script>
    var testData = '<%= testData %>';
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="assets/add_appointment/add_appointment-js.js"></script>
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
