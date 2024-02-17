function validateForm() {
    const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
    const phonePattern = /[^0-9+]/g;
    let firstName = document.forms["register-form"]["first_name"].value.trim();
    const lastName = document.forms["register-form"]["last_name"].value.trim();
    const email = document.forms["register-form"]["e_mail"].value.trim();
    const phoneNumber = document.forms["register-form"]["phone_number"].value.trim();
    const password = document.forms["register-form"]["password"].value;
    const confirmPassword = document.forms["register-form"]["confirm_password"].value;

    if(firstName.length > 30 || firstName === ""){
        showDialogBox("Operation Failed.", "Invalid First Name", "error");
        return false;
    } else if (lastName.length > 50 || lastName === "") {
        showDialogBox("Operation Failed.", "Invalid Last Name", "error");
        return false;
    } else if(!emailRegex.test(email)){
        showDialogBox("Operation Failed.", "Invalid E-mail", "error");
        return false;
    } else if(phoneNumber.length > 15 || phoneNumber.length < 6 || phonePattern.test(phoneNumber)){
        showDialogBox("Operation Failed.", "Invalid Phone Number", "error");
        return false;
    } else if (!(password.length >= 6 && password.length <= 24)){
        showDialogBox("Operation Failed.", "Password length must be between 6 and 24 characters.", "error");
        return false;
    } else if(!password.match(/[A-Z]/) || !password.match(/\d/)){
        showDialogBox("Operation Failed.", "Password does not meet the criteria: Must contain at least one uppercase letter and one number.", "error");
        return false;
    } else if(password !== confirmPassword){
        showDialogBox("Operation Failed.", "Passwords do not match", "error");
        return false;
    } else {
        showLoader();
        return true;
    }
}

function showDialogBox(title, message, icon) {
    Swal.fire({
        title: title,
        text: message,
        icon: icon,
        confirmButtonText: 'Close'
    })
}

function showLoader() {
    Swal.fire({
        title: 'Loading...',
        allowOutsideClick: false,
        showConfirmButton: false,
        didOpen: () => {
            Swal.showLoading()
        }
    })

    setTimeout(() => {
        Swal.close();
    }, 10000);
}

function togglePasswordVisibility(pwFieldElementId, openEyeId, closedEyeId) {
    var passwordField = document.getElementById(pwFieldElementId);
    var eyeOpenIcon = document.getElementById(openEyeId);
    var eyeClosedIcon = document.getElementById(closedEyeId);

    if (passwordField.type === "password") {
        passwordField.type = "text";
        eyeOpenIcon.style.display = "none";
        eyeClosedIcon.style.display = "block";
    } else {
        passwordField.type = "password";
        eyeOpenIcon.style.display = "block";
        eyeClosedIcon.style.display = "none";
    }
}
