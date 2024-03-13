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
        url: 'CustomerHomeData',
        data: 'action-type=GetCustomerTableData'+type,
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
        url: 'CustomerHomeData',
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

function downloadDocument(appointmentId) {
    var fileUrl = "CustomerHomeData?actionType=DownloadDocument&appointmentId="+appointmentId;
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
            const doctorCell = document.createElement('td');
            const priceCell = document.createElement('td');
            const statusCell = document.createElement('td');

            testNameCell.textContent = appointment.testName;
            AppointmentNoCell.textContent = appointment.appointmentId;
            scheduledTimeCell.textContent = appointment.scheduleTime;
            doctorCell.textContent = appointment.doctorName;
            priceCell.textContent = appointment.amount;
            statusCell.textContent = appointment.statusType;

            const downloadCell = document.createElement('td');
            const cancelCell = document.createElement('td');
            let btnDownload;
            let btnCancel;

            if(appointment.status === 3){
                btnDownload = createTableButtons('fa-download', 'btn-download');
                btnDownload.addEventListener('click', () => {
                    downloadDocument(appointment.appointmentId)
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

            if(appointment.status === 3){
                downloadCell.appendChild(btnDownload);
            }
            if(appointment.status === 1){
                cancelCell.appendChild(btnCancel);
            }

            // Append cells to the row
            row.appendChild(testNameCell);
            row.appendChild(AppointmentNoCell);
            row.appendChild(scheduledTimeCell);
            row.appendChild(doctorCell);
            row.appendChild(priceCell);
            row.appendChild(statusCell);
            row.appendChild(downloadCell);
            row.appendChild(cancelCell);

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