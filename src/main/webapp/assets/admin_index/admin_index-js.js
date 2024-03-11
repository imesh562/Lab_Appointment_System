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

function cancelAppointment(appointmentId) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AdminHomeData',
        data: 'action-type=CancelAppointment&appointmentId='+appointmentId,
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

//filter in home
function filterAppointments() {
    var selectedValue = document.getElementById("filter-appointment").value;
    getTableData("&filter="+selectedValue);
}

//create buttons to the table
function createTableButtons(iconClass, buttonClass) {
    const button = document.createElement('button');
    const icon = document.createElement('i');
    icon.classList.add('fa', iconClass);
    button.classList.add(buttonClass);
    button.appendChild(icon);
    return button;
}

//Function to populate the table
function populateTable() {
    const appointmentList = document.getElementById('appointments-list');

    //check appintmentList exist
    if (appointmentList) {
        // Loop the appointments obj
        appointments.forEach(appointment => {
            // Create a new table row
            const row = document.createElement('tr');

            // Create table cells
            const testNameCell = document.createElement('td');
            const AppointmentNoCell = document.createElement('td');
            const scheduledTimeCell = document.createElement('td');
            const createdTimeCell = document.createElement('td');
            const doctorCell = document.createElement('td');
            const priceCell = document.createElement('td');
            const statusCell = document.createElement('td');

            // Set the content of the cells
            testNameCell.textContent = appointment.testName;
            AppointmentNoCell.textContent = appointment.appointmentId;
            scheduledTimeCell.textContent = appointment.scheduleTime;
            createdTimeCell.textContent = appointment.createdDate;
            doctorCell.textContent = appointment.doctorName;
            priceCell.textContent = appointment.amount;
            statusCell.textContent = appointment.statusType;

            // Create table cells for buttons
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
                            cancelAppointment(appointment.appointmentId)
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