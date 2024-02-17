<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="assets/signup/signup-style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <img src="assets/signup/images/register.png" alt="Image" class="left-image">
        <form action="Register" class="register-form" method="POST" name="register-form" onsubmit="return validateForm()" >
            <input type="hidden" name="action-type" value="Register">
            <h1>Register</h1>
            <div class="input-box">
                <input type="text" placeholder="First Name" name="first_name" maxlength="30" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="text" placeholder="Last Name" name="last_name" maxlength="50" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input placeholder="E-mail" name="e_mail" type="email" maxlength="100" required>
                <i class='bx bxs-envelope' ></i>
            </div>
            <div class="input-box">
                <input type="text" placeholder="Phone Number" name="phone_number" maxlength="15" required>
                <i class='bx bxs-phone' ></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Password" name="password" id="password" maxlength="24" required>
                <button type="button" class="toggle_button" onclick="togglePasswordVisibility('password', 'passwordEyeOpenIcon', 'passwordEyeClosedIcon')">
                    <i class='bx bxs-show' id="passwordEyeOpenIcon"></i>
                    <i class='bx bxs-hide' id="passwordEyeClosedIcon" style="display: none;"></i>
                </button>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Confirm Password" name="confirm_password" id="confirm_password" maxlength="24" required>
                <button type="button" class="toggle_button" onclick="togglePasswordVisibility('confirm_password', 'confirmPasswordEyeOpenIcon', 'confirmPasswordEyeClosedIcon')">
                    <i class='bx bxs-show' id="confirmPasswordEyeOpenIcon"></i>
                    <i class='bx bxs-hide' id="confirmPasswordEyeClosedIcon" style="display: none;"></i>
                </button>
            </div>
            <button name="register-button" type="submit" class="btn">Register</button>
        </form>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="assets/signup/signup-js.js"></script>
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
</html>