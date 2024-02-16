<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="assets/signup/style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper">
        <img src="assets/signup/images/register.png" alt="Image" class="left-image">
        <form action="Register" class="register-form" method="POST">
            <input type="hidden" name="action-type" value="Register">
            <h1>Register</h1>
            <div class="input-box">
                <input type="text" placeholder="First Name" name="first_name" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="text" placeholder="Last Name" name="last_name" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input placeholder="E-mail" name="e_mail" type="email" required>
                <i class='bx bxs-envelope' ></i>
            </div>
            <div class="input-box">
                <input type="text" placeholder="Phone Number" name="phone_number" required>
                <i class='bx bxs-phone' ></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Password" name="password" required>
                <i class='bx bxs-lock-alt' ></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Confirm Password" name="confirm_password" required>
                <i class='bx bxs-lock-alt' ></i>
            </div>

            <button type="submit" class="btn">Register</button>
        </form>
    </div>
</body>
<script defer type="module">
    import Swal from 'https://cdn.jsdelivr.net/npm/sweetalert2@11.7.27/+esm';

    const message = '<%= request.getAttribute("message") %>';
    const title = '<%= request.getAttribute("title") %>';
    const icon = '<%= request.getAttribute("icon") %>';
    if (message !== "null") {
        document.addEventListener('DOMContentLoaded', () => {
            Swal.fire({
                title: title,
                text: message,
                icon: icon,
                confirmButtonText: 'Close'
            })
        })
    }
</script>
</html>