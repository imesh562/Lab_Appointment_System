const labTests = []
var disabledDates = [];
let selectedLabTest = null;
var parsedList = JSON.parse(testData);

for (var i = 0; i < parsedList.length; i++) {
    labTests.push(parsedList[i]);
}

const dropdownInputField = document.getElementById("lab-test");
const labTestDropdown = document.getElementById("lab-tests-dropdown");
const dateField = document.getElementById("datepicker-box");
const technicianText = document.getElementById("technician")
const priceText = document.getElementById("price")


labTests.forEach(labTest => {
    let button = document.createElement("button");
    button.textContent = labTest.testName;

    button.addEventListener("click", () => {
        selectedLabTest = labTest;
        dropdownInputField.value = selectedLabTest.testName;
        console.log(selectedLabTest)
        labTestDropdown.style.display = 'none';
        technicianText.textContent = "Technician: "+selectedLabTest.technician;
        priceText.textContent = "Price (LKR): "+selectedLabTest.price;
        dateField.style.display = "block";
        onSelectTest(selectedLabTest);
    });

    labTestDropdown.appendChild(button);
});
function onSelectTest(selectedLabTest) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AddNewAppointment',
        data: 'testId=' + selectedLabTest.testId + '&action-type=GetDisabledDates',
        error: function(response) {
            Swal.close();
        },
        success: function(response) {
            Swal.close();
            var jsonData = JSON.parse(response);
            var newData = jsonData.data;
            disabledDates = [];
            disabledDates.push(...newData);
            console.log(disabledDates);
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
    }, 10000);
}

function handleFocus() {
    labTestDropdown.style.display = 'flex';
}

dropdownInputField.addEventListener("focus", handleFocus);

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

$( function() {
    $( "#datepicker" ).datepicker({
        minDate: 0,
        beforeShowDay: function(date){
            var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            return [ disabledDates.indexOf(string) === -1 ]
        }
    });
});