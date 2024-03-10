var testList = JSON.parse(testData);
var disabledDates = [];
var selectedTestId;
var selectedTestName;
var selectedDate;

const dropdownInputField = document.getElementById("lab-test");
const dateField = document.getElementById("datepicker-box");
const technicianText = document.getElementById("technician")
const priceText = document.getElementById("price")

function addOption(value) {
    var datalist = document.getElementById('options');
    var option = document.createElement('option');
    option.value = value.testName;
    option.id = value.testId;
    datalist.appendChild(option);
}

for (var i = 0; i < testList.length; i++) {
    addOption(testList[i]);
}

function clearDropdown() {
    dropdownInputField.value = "";
}

function fillDropdown() {
    if(selectedTestId != null){
        dropdownInputField.value = selectedTestName;
    }
}

function filterOptions() {
    var filter, datalist, options;
    filter = dropdownInputField.value.toUpperCase();
    datalist = document.getElementById('options');
    options = datalist.getElementsByTagName('option');
    for (var i = 0; i < options.length; i++) {
        if (options[i].value.toUpperCase().indexOf(filter) > -1) {
            options[i].style.display = '';
        } else {
            options[i].style.display = 'none';
        }
    }
}

function handleOptionSelection() {
    selectedTestName = dropdownInputField.value;
    dropdownInputField.blur();
    for (var i = 0; i < testList.length; i++) {
        if(testList[i].testName === dropdownInputField.value){
            technicianText.textContent = "Technician: "+testList[i].technician;
            priceText.textContent = "Price (LKR): "+testList[i].price;
            dateField.style.display = "block";
            selectedTestId = testList[i].testId;
            fillDropdown();
            onSelectTest(selectedTestId);
            break;
        }
    }
}

function onSelectTest(selectedLabTest) {
    showLoader();
    $.ajax({
        type: 'POST',
        url: 'AddNewAppointment',
        data: 'testId=' + selectedLabTest + '&action-type=GetDisabledDates',
        error: function(response) {
            Swal.close();
            showDialogBox('Something went wrong', 'Please try again', 'error');
        },
        success: function(response) {
            Swal.close();
            var jsonData = JSON.parse(response);
            var newData = jsonData.data;
            if(jsonData.isSuccess){
                selectedDate = null;
                document.getElementById("datepicker").value = "";
                disabledDates = [];
                disabledDates.push(...newData);
            } else {
                showDialogBox('Something went wrong', 'Please try again', 'error');
            }
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('add-appointment');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        const doctorName = formData.get('doctor-name');

        if(selectedTestId == null || selectedTestId === ""){
            showDialogBox("Operation Failed.", "Please select a test before proceeding.", "error");
        } else if(selectedDate == null || selectedDate === ""){
            showDialogBox("Operation Failed.", "Please select a date before proceeding.", "error");
        } else {
            showLoader();
            $.ajax({
                type: 'POST',
                url: 'AddNewAppointment',
                data: 'action-type=AddNewAppointment&selectedDate='+selectedDate+'&doctorName='+doctorName+'&selectedTestId='+selectedTestId,
                error: function(response) {
                    Swal.close();
                    showDialogBox('Something went wrong', 'Please try again', 'error');
                },
                success: function(response) {
                    Swal.close();
                    var jsonData = JSON.parse(response);
                    if(jsonData.isSuccess){
                        showDialogBox('Success', 'We have sent you an Email with details regarding your appointment.', 'success', function(){window.history.back();},);
                    } else {
                        showDialogBox('Failure', jsonData.message, 'error');
                    }
                }
            });
        }
    });
});

$( function() {
    $( "#datepicker" ).datepicker({
        minDate: 0,
        beforeShowDay: function(date){
            var string = jQuery.datepicker.formatDate('yy-mm-dd', date);
            return [ disabledDates.indexOf(string) === -1 ]
        },
        onSelect: function(dateText, inst) {
            selectedDate = dateText;
        }
    });
});

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