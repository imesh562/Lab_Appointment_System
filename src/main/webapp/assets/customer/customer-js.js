////// navigation bar//////
const Navigation = {

    //func to handle the routes and change the styles of the buttons for the relevent route
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

//event listner to call the highlightButton func
document.addEventListener('DOMContentLoaded', function () {
    Navigation.highlightButton();
});
///// end of navigation bar///////

/////////home//////////
//////appointments table//////
//appointments object

//after the backend connected, remove below all the dummy data (after the backend implemented below object should be let appointments = [];)
let appointments = [
    { id: 1, testName: 'Test-1', createdDate: '2020-10-10', scheduledDate: '2020-11-11', price: 5000, status: 'pending', result: 'Results' },
    { id: 2, testName: 'Test-2', createdDate: '2020-10-10', scheduledDate: '2020-11-11', price: 5000, status: 'pending', result: 'Results' },
    { id: 3, testName: 'Test-1', createdDate: '2020-10-10', scheduledDate: '2020-11-11', price: 5000, status: 'pending', result: 'Results' },
    { id: 4, testName: 'Test-1', createdDate: '2020-10-10', scheduledDate: '2020-11-11', price: 5000, status: 'pending', result: 'Results' },
    { id: 5, testName: 'Test-1', createdDate: '2020-10-10', scheduledDate: '2020-11-11', price: 5000, status: 'pending', result: 'Results' },
];

//filter in home
function filterAppointments() {
    var selectedValue = document.getElementById("filter-appointment").value;
    //get the appointments related to the selected value and assign into the above appointments object
    console.log(selectedValue);
};

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
            const createdDateCell = document.createElement('td');
            const scheduledDateCell = document.createElement('td');
            const priceCell = document.createElement('td');
            const statusCell = document.createElement('td');
            const resultCell = document.createElement('td');

            // Set the content of the cells
            testNameCell.textContent = appointment.testName;
            createdDateCell.textContent = appointment.createdDate;
            scheduledDateCell.textContent = appointment.scheduledDate;
            priceCell.textContent = appointment.price;
            statusCell.textContent = appointment.status;
            resultCell.textContent = appointment.result;

            // Create table cells for buttons
            const downloadCell = document.createElement('td');
            const cancelCell = document.createElement('td');

            // Create buttons
            const btnDownload = createTableButtons('fa-download', 'btn-download');
            const btnCancel = createTableButtons('fa-ban', 'btn-cancel');

            //event lister to download button. reffer appointment.id to do things related to selected record
            btnDownload.addEventListener('click', () => {
                console.log(appointment.id);
            });

            // Add event listener to the cancel button. reffer appointment.id to do things related to selected record
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
                        // implement your shit when a user confirmation
                        console.log(`Appointment ${appointment.testName} has been cancelled.`);
                    }
                });
            });

            // Append buttons to cells
            downloadCell.appendChild(btnDownload);
            cancelCell.appendChild(btnCancel);

            // Append cells to the row
            row.appendChild(testNameCell);
            row.appendChild(createdDateCell);
            row.appendChild(scheduledDateCell);
            row.appendChild(priceCell);
            row.appendChild(statusCell);
            row.appendChild(resultCell);
            row.appendChild(downloadCell);
            row.appendChild(cancelCell);

            // Append the row to the table body
            appointmentList.appendChild(row);
        });
    }
}

//call the above function
populateTable();

/////end of appointments table///////
//////////end home////////////

///////////add appointment///////////

//select lab test

const labTests = [
    { id: 1, name: 'test-11' },
    { id: 2, name: 'test-21' },
    { id: 3, name: 'test-31' },
]

const dropdownInputField = document.getElementById("lab-test");
const labTestDropdown = document.getElementById("lab-tests-dropdown");
let selectedLabtest = null;

labTests.forEach(labTest => {
    let button = document.createElement("button");
    button.textContent = labTest.name;

    //select a labt test
    button.addEventListener("click", () => {
        selectedLabtest = labTest.name;
        dropdownInputField.value = selectedLabtest;
        console.log(selectedLabtest)
        labTestDropdown.style.display = 'none';
    });

    //append the buttons to the dropdown
    labTestDropdown.appendChild(button);
});

function handleFocus() {
    labTestDropdown.style.display = 'flex';
}

// Attach event listeners
dropdownInputField.addEventListener("focus", handleFocus);

//submit the form
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('add-appointment');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        // Get form data
        const formData = new FormData(form);
        const labTest = formData.get('lab-test');
        const date = formData.get('date');
        const nameInCard = formData.get('name-in-card');
        const cardType = formData.get('card-type');
        const cvc = formData.get('cvc');
        const cardNumber = formData.get('card-number');

        console.log(labTest);
        console.log(date);
        console.log(nameInCard);
        console.log(cardType);
        console.log(cvc);
        console.log(cardNumber);
    });
});
