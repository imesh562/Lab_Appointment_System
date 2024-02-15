<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="assets/login/style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper" >
        <img src="assets/login/images/scientist.png" alt="Image" class="left-image">
        <form action="" class="login-form">
            <h1>Login</h1>
            <div class="input-box">
                <input type="text" placeholder="User ID" required>
                <i class='bx bxs-user'></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Password" required>
                <i class='bx bxs-lock-alt' ></i>
            </div>
            <div class="remember-forgot">
                <label><input type="checkbox"> Remember me</label>
            </div>

            <button type="submit" class="btn">Login</button>
            <div class="register-link">
                <p>Don't have an account?
                <a href="signup.jsp">Register</a></p>
            </div>
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
            }).then(() => {
                window.location.replace("login.jsp")
            })
        })
    }
</script>
</html>