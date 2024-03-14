////// navigation bar//////
const Navigation = {

    highlightButton() {
        const currentPage = window.location.pathname;
        const homeButton = document.getElementById('nav-home');
        const addAppointmentButton = document.getElementById('nav-add-appointment');

        if (currentPage.includes('index.html')) {
            homeButton.style.backgroundColor = 'rgba(0, 106, 255, 0.144)';
            // addAppointmentButton.style.backgroundColor = '';
        } else if (currentPage.includes('add-appointment.html')) {
            addAppointmentButton.style.backgroundColor = 'rgba(0, 106, 255, 0.144)';
            // homeButton.style.backgroundColor = '';
        }
    }
};

document.addEventListener('DOMContentLoaded', function () {
    Navigation.highlightButton();
});
///// end of navigation bar///////

/////////home//////////

let appointments = [];
getTableData("&filter=All");

function getTableData(type) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AdminHomeData',
        data: 'action-type=GetAllAppointmentsData'+type,
        error: function(response) {
            Swal.close();
            showDialogBox('Something went wrong', 'Please try again', 'error');
        },
        success: function(response) {
            Swal.close();
            var jsonData = JSON.parse(response);
            var newData = jsonData.data;
            if(jsonData.isSuccess){
                appointments = [];
                const appointmentList = document.getElementById('appointments-list');
                appointmentList.innerHTML = '';
                appointments.push(...newData);
                populateTable();
            } else {
                showDialogBox('Something went wrong', 'Please try again', 'error');
            }
        }
    });
}

function cancelAppointment(appointmentId, customerEmail) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AdminHomeData',
        data: 'action-type=CancelAppointment&appointmentId='+appointmentId+"&customerEmail="+customerEmail,
        error: function(response) {
            Swal.close();
            showDialogBox('Something went wrong', 'Please try again', 'error');
        },
        success: function(response) {
            Swal.close();
            var jsonData = JSON.parse(response);
            if(jsonData.isSuccess){
                filterAppointments();
            } else {
                showDialogBox('Something went wrong', 'Please try again', 'error');
            }
        }
    });
}

function uploadFile(appointmentId, customerId, customerEmail) {
    showLoader();
    const fileInput = document.createElement('input');
    fileInput.type = 'file';
    fileInput.style.display = 'none';
    fileInput.accept = 'application/pdf';
    document.body.appendChild(fileInput);
    fileInput.click();

    fileInput.addEventListener('change', function() {
        const file = this.files[0];

        if (file.size > 10 * 1024 * 1024) {
            Swal.close();
            showDialogBox('Error', 'File size exceeds 10MB. Please select a smaller file.', 'error');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);
        formData.append('action-type', "FileUpload");
        formData.append('appointmentId', appointmentId);
        formData.append('customerEmail', customerEmail);
        formData.append('customerId', customerId);

        fetch('AdminHomeData', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                Swal.close();
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Network response was not ok.');
                }
            })
            .then(data => {
                if (data.isSuccess) {
                    showDialogBox('Success', data.message, 'success', filterAppointments);
                } else {
                    showDialogBox('Error', data.message, 'error', filterAppointments);
                }

            })
            .catch(error => {
                showDialogBox('Error', 'An error occurred while uploading the file.', 'error');
            })
            .finally(() => {
                document.body.removeChild(fileInput);
            });

    });
}

function confirmPayment(appointmentId, customerEmail) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AdminHomeData',
        data: 'action-type=ConfirmPayment&appointmentId='+appointmentId+"&customerEmail="+customerEmail,
        error: function(response) {
            Swal.close();
            showDialogBox('Something went wrong', 'Please try again', 'error');
        },
        success: function(response) {
            Swal.close();
            var jsonData = JSON.parse(response);
            if(jsonData.isSuccess){
                filterAppointments();
            } else {
                showDialogBox('Something went wrong', 'Please try again', 'error');
            }
        }
    });
}

function downloadDocument(appointmentId, userId) {
    var fileUrl = "AdminHomeData?actionType=DownloadDocument&appointmentId="+appointmentId+"&userId="+userId;
    var iframe = document.createElement('iframe');
    iframe.style.display = "none";
    document.body.appendChild(iframe);
    iframe.src = fileUrl;
}

function filterAppointments() {
    var selectedValue = document.getElementById("filter-appointment").value;
    getTableData("&filter="+selectedValue);
}

function createTableButtons(iconClass, buttonClass) {
    const button = document.createElement('button');
    const icon = document.createElement('i');
    icon.classList.add('fa', iconClass);
    button.classList.add(buttonClass);
    button.appendChild(icon);
    return button;
}

function populateTable() {
    const appointmentList = document.getElementById('appointments-list');

    if (appointmentList) {
        appointments.forEach(appointment => {
            const row = document.createElement('tr');

            const testNameCell = document.createElement('td');
            const AppointmentNoCell = document.createElement('td');
            const scheduledTimeCell = document.createElement('td');
            const createdTimeCell = document.createElement('td');
            const doctorCell = document.createElement('td');
            const priceCell = document.createElement('td');
            const statusCell = document.createElement('td');

            testNameCell.textContent = appointment.testName;
            AppointmentNoCell.textContent = appointment.appointmentId;
            scheduledTimeCell.textContent = appointment.scheduleTime;
            createdTimeCell.textContent = appointment.createdDate;
            doctorCell.textContent = appointment.doctorName;
            priceCell.textContent = appointment.amount;
            statusCell.textContent = appointment.statusType;

            const downloadCell = document.createElement('td');
            const proceedCell = document.createElement('td');
            const cancelCell = document.createElement('td');
            let btnDownload;
            let btnProceed;
            let btnCancel;

            // Create buttons
            if(appointment.status === 3){
                btnDownload = createTableButtons('fa-download', 'btn-download');
                btnDownload.addEventListener('click', () => {
                    downloadDocument(appointment.appointmentId, appointment.customerId)
                });
            }
            if(appointment.status < 3){
                btnProceed = createTableButtons('fa-arrow-circle-right', 'btn-proceed');
                btnProceed.addEventListener('click', () => {
                    if(appointment.status === 1){
                        Swal.fire({
                            title: 'Confirm Payment',
                            text: `Please confirm the customer payment.`,
                            icon: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: '#d33',
                            cancelButtonColor: '#3085d6',
                            confirmButtonText: 'Yes',
                            cancelButtonText: 'No'
                        }).then((result) => {
                            if (result.isConfirmed) {
                                confirmPayment(appointment.appointmentId, appointment.email);
                            }
                        });
                    } else if(appointment.status === 2){
                        uploadFile(appointment.appointmentId, appointment.customerId, appointment.email);
                    }
                });
            }

            if(appointment.status === 1){
                btnCancel  = createTableButtons('fa-ban', 'btn-cancel');
                btnCancel.addEventListener('click', () => {
                    Swal.fire({
                        title: 'Are you sure?',
                        text: `You are about to cancel the appointment ${appointment.testName}.`,
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#d33',
                        cancelButtonColor: '#3085d6',
                        confirmButtonText: 'Yes',
                        cancelButtonText: 'No'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            cancelAppointment(appointment.appointmentId, appointment.email)
                        }
                    });
                });
            }

            // Append buttons to cells
            if(appointment.status === 3){
                downloadCell.appendChild(btnDownload);
            }
            if(appointment.status < 3){
                proceedCell.appendChild(btnProceed);
            }
            if(appointment.status === 1){
                cancelCell.appendChild(btnCancel);
            }

            // Append cells to the row
            row.appendChild(testNameCell);
            row.appendChild(AppointmentNoCell);
            row.appendChild(scheduledTimeCell);
            row.appendChild(createdTimeCell);
            row.appendChild(doctorCell);
            row.appendChild(priceCell);
            row.appendChild(statusCell);
            row.appendChild(downloadCell);
            row.appendChild(proceedCell);
            row.appendChild(cancelCell);

            // Append the row to the table body
            appointmentList.appendChild(row);
        });
    }
}

function showDialogBox(title, message, icon, onClose) {
    Swal.fire({
        title: title,
        text: message,
        icon: icon,
        confirmButtonText: 'Close',
    }).then((result) => {
        if (result.isConfirmed || result.dismiss === Swal.DismissReason.close) {
            if(onClose != null){
                onClose();
            }
        }
    });
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
    }, 30000);
}