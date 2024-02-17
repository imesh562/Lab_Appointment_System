<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="assets/login/login-style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>
    <div class="wrapper" >
        <img src="assets/login/images/scientist.png" alt="Image" class="left-image">
        <form action="Login" class="login-form" method="POST" name="login-form" onsubmit="showLoader()">
            <input type="hidden" name="action-type" value="Login">
            <h1>Login</h1>
            <div class="input-box">
                <input type="text" placeholder="User ID" name="user_id" maxlength="8" required>
                <i class='bx bxs-id-card'></i>
            </div>
            <div class="input-box">
                <input type="password" placeholder="Password" name="password" id="password" maxlength="24" required>
                <button type="button" class="toggle_button" onclick="togglePasswordVisibility('password', 'passwordEyeOpenIcon', 'passwordEyeClosedIcon')">
                    <i class='bx bxs-show' id="passwordEyeOpenIcon"></i>
                    <i class='bx bxs-hide' id="passwordEyeClosedIcon" style="display: none;"></i>
                </button>
            </div>
            <button type="submit" class="btn">Login</button>
            <div class="register-link">
                <p>Don't have an account?
                <a href="signup.jsp">Register</a></p>
            </div>
        </form>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="assets/login/login-js.js"></script>
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