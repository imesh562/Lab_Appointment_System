<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<% String testData = (String)request.getAttribute("labTests"); %>
<script>
    var testData = '<%= testData %>';
</script>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Appointment</title>
    <link rel="stylesheet" href="assets/customer/customer-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        $( function() {
            var disabledDates = ["2024-02-20", "2024-02-21", "2024-02-22"];
            $( "#datepicker" ).datepicker({
                minDate: 0, // Disallow past days
                beforeShowDay: function(date){
                    var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
                    return [ disabledDates.indexOf(string) == -1 ]
                }
            });
        });
    </script>
</head>

<body>
<div class="nav-bar-main">
    <a id="a-logo" href="customer_index.jsp">
        <img src="assets/customer/images/logo.webp" alt="">
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
    <div class="add-appointment-main">
        <form id="add-appointment">
            <div class="inp-box">
                <label for="lab-test">Lab Test:</label>
                <input type="text" id="lab-test" name="lab-test" required>
                <div class="dropdown-box" id="lab-tests-dropdown"></div>
                <p id="technician"></p>
                <p id="price"></p>
            </div>
            <div class="inp-box" id="datepicker-box" style="display: none;">
                <label for="datepicker">Select a date:</label>
                <input type="text" id="datepicker">
            </div>
            <div class="inp-box">
                <label for="doctor-name">Doctor's name:</label>
                <input type="text" id="doctor-name" name="doctor-name" required>
            </div>
            <div class="inp-box">
                <label for="name-in-card">Name in card:</label>
                <input type="text" id="name-in-card" name="name-in-card" required>
            </div>
            <div class="inp-box">
                <div class="inp-grid">
                    <div>
                        <label for="card-type">Card type:</label><br>
                        <select name="card-type" id="card-type">
                            <option value="visa">Visa</option>
                            <option value="master">Master</option>
                        </select>
                    </div>
                    <div>
                        <label for="cvc">CVC:</label><br>
                        <input type="number" id="cvc" name="cvc" required>
                    </div>
                </div>
            </div>
            <div class="inp-box">
                <label for="card-number">Card number:</label>
                <input type="number" id="card-number" name="card-number" required>
            </div>
            <div class="submit-btn-wrapper">
                <button class="btn-submit" id="submit-appointment">SUBMIT</button>
            </div>
        </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="assets/customer/customer-js.js"></script>
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
