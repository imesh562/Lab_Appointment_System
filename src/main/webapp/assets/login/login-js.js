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
