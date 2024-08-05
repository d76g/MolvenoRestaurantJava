const url = '/api/reservation/';
let reservationLocaleText;
function initReservation(){
    console.log("Reservation page loaded");
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    reservationLocaleText = messages ? JSON.parse(messages) : null;
    getAllReservations();
    displayTimeSlots();
    $(".myBtn").click(function(){
        $("#addReservationDiv").removeClass("hidden");
    });
    $(".closeButtonAdd").click(function(){
        $("#addReservationDiv").addClass("hidden");
    });
    $(".closeButtonUpdate").click(function(){
        $("#updateReservationDiv").addClass("hidden");
    });
    $(".closeButtonStatus").click(function(){
        $("#updateReservationStatusForm").addClass("hidden");
    });
    $("#closeButtonPopup").click(function(){
        $("#reservationPopup").addClass("hidden");
    });
    $('#guest').change(function() {
        if ($(this).is(':checked')) {
            $('#roomNumberDiv').removeClass('hidden');
            // set the input to required
            $('#roomNumber').prop('required', true);
        } else {
            $('#roomNumberDiv').addClass('hidden');
            $('#roomNumber').val('').prop('required', false);
        }
    });
    $('#updateGuest').change(function() {
        if ($(this).is(':checked')) {
            $('#updateRoomNumberDiv').removeClass('hidden');
            // set the input to required
            $('#updateRoomNumber').prop('required', true);
        } else {
            $('#updateRoomNumberDiv').addClass('hidden');
            $('#updateRoomNumber').val('').prop('required', false);
        }
    });

    // for date picker
    flatpickr(".flatpickr", {
        enableTime: false,
        dateFormat: "Y-m-d",
        minDate: "today",
        altFormat: "F j, Y",
        maxDate: new Date().fp_incr(30),
        disableMobile: "true",
    });
    // Event listener for the update table form submission
    $('#createReservation').on('submit', function(event) {
        event.preventDefault();
        $('.time-slot').removeClass('bg-green-300').addClass('bg-gray-50');
        if (!this.checkValidity()) {
            const roomNumber = $('#roomNumber');
            if (roomNumber.prop('required') && !roomNumber.val()) {
                // Ensure the roomNumberDiv is visible so the browser can focus the invalid input
                $('#roomNumberDiv').removeClass('hidden');
            }
            event.preventDefault(); // Prevent form submission to show the validation errors
        }
        if (window.location.pathname === '/reservations') {
            addReservation()
        } else {
            addReservationForGuest()
        }
        $('#reservationTime').val('');

    });
    $('#saveReservationStatus').on('submit', function(event) {
        event.preventDefault();
        const reservationId = $('#reservationId4Status').val();
        const status = $('#status').val();
        console.log(reservationId, status)
        updateReservationStatus(reservationId, status);
        $("#updateReservationStatusForm").addClass("hidden");
    });
    $(document).on('click', '.editStatus', function(){
        $("#updateReservationStatusForm").toggleClass("hidden");
        const reservationId = $(this).data('id');
        const status = $(this).data('status');
        $('#status').val(status);
        $('#reservationId4Status').val(reservationId);
    });
    $(document).on('click', '.deleteButton', function (){
        const reservationId = $(this).data('id');
            Swal.fire({
                title: reservationLocaleText['Are-you-sure'],
                text: reservationLocaleText['You-wont-be-able-to-revert-this'],
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: reservationLocaleText['Yes-delete-it']
            }).then((result) => {
                if (result.isConfirmed) {
                    deleteReservation(reservationId);
                }
            })
    });
    // Event listener for the close button
    $(document).on('click', '.editButton', function(){
        // get the table id and capacity
        const reservationId = $(this).data('id');
        const customerFirstName = $(this).data('first-name');
        const customerLastName = $(this).data('last-name');
        const customerEmail = $(this).data('email');
        const reservationDate = $(this).data('date');
        const reservationTime = $(this).data('time');
        const numberOfGuests = $(this).data('guests');
        const roomNumber = $(this).data('room-number');
        const isGuest = $(this).data('guest');
        const reservationStatus = $(this).data('status');
        const customerPhone = $(this).data('phone');
        // show the update form
        $("#updateReservationDiv").toggleClass("hidden");
        // set the new table capacity and id in the form
        $('#updateCustomerFirstName').val(customerFirstName);
        $('#updateCustomerLastName').val(customerLastName);
        $('#updateCustomerEmail').val(customerEmail);
        $('#updateReservationDate').val(reservationDate);
        $('#reservationTime').val(reservationTime);
        $('#updateNumberOfGuests').val(numberOfGuests);
        $('#updateRoomNumber').val(roomNumber);
        $('#updateGuest').prop('checked', isGuest);
        $('#updateCustomerPhone').val(customerPhone);
        $('#updateReservationStatus').val(reservationStatus);
        if(isGuest){
            $('#updateRoomNumberDiv').removeClass('hidden');
            console.log(roomNumber)
        } else {
            $('#updateRoomNumberDiv').addClass('hidden');
        }
        $('#updateReservationId').val(reservationId);
        $('#currentReservationTime').empty();
        $('#currentReservationTime').append(reservationTime);
    });
    $('#updateReservation').on('submit', function(event) {
        event.preventDefault();
        updateReservation()
    });
}
function displayTimeSlots(){
    // Time slots for reservation
    const startTime = new Date();
    startTime.setHours(7);
    startTime.setMinutes(30);
    const endTime = new Date();
    endTime.setHours(23);
    endTime.setMinutes(30);

    const timeSlotsGrid = $('.timeSlotsGrid');

    while (startTime <= endTime) {
        const hours = startTime.getHours().toString().padStart(2, '0');
        const minutes = startTime.getMinutes().toString().padStart(2, '0');
        const timeString = `${hours}:${minutes}`;

        const timeSlot = $('<div></div>')
            .addClass("time-slot bg-gray-50 p-2 rounded text-center cursor-pointer hover:bg-gray-100")
            .attr('data-time', timeString)
            .text(timeString)
            .click(function() {
                $(this).toggleClass('bg-gray-50').toggleClass('bg-green-300');
                // toggle the bg color of the other time slots
                $(this).siblings().removeClass('bg-green-300').addClass('bg-gray-50');
                $('#reservationTime').val($(this).data('time'));
            });

        timeSlotsGrid.append(timeSlot);  // Append the time slot to the grid

        startTime.setMinutes(startTime.getMinutes() + 60); // Increment by 60 minutes
    }
}
// add reservation
function addReservation() {
    const customerFirstName = $('#customerFirstName').val();
    const customerLastName = $('#customerLastName').val();
    const customerEmail = $('#customerEmail').val();
    const customerPhone = $('#customerPhone').val();
    const reservationDate = $('#reservationDate').val();
    const reservationTime = $('#reservationTime').val();
    const numberOfGuests = $('#numberOfGuests').val();
    const isGuest = $('#guest').is(':checked');
    const roomNumber = $('#roomNumber').val();
    // create a table object
    const reservation = {
        customerFirstName: customerFirstName,
        customerLastName: customerLastName,
        customerEmail: customerEmail,
        customerPhone: customerPhone,
        reservationDate: reservationDate,
        reservationTime: reservationTime,
        numberOfGuests: numberOfGuests,
        guest: isGuest,
        roomNumber: roomNumber
    };
    $.ajax(
        {
            url: url + 'add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservation),
            success: function(data) {
                const id = data.id;
                $("#addReservationDiv").addClass("hidden");
                $('#createReservation')[0].reset();
                getAllReservations();

                    Swal.fire({
                        icon: 'success',
                        title: reservationLocaleText['Reservation-Saved'],
                        text: reservationLocaleText['Reservation-has-been-saved'],
                        timer: 3000
                    })

            },
            error: function (error) {
                Swal.fire({
                    icon: 'error',
                    title: reservationLocaleText['Something-went-wrong'],
                    text: reservationLocaleText[error.responseJSON.message],
                });
            }
        }
    )
}
function addReservationForGuest() {
    const customerFirstName = $('#customerFirstName').val();
    const customerLastName = $('#customerLastName').val();
    const customerEmail = $('#customerEmail').val();
    const customerPhone = $('#customerPhone').val();
    const reservationDate = $('#reservationDate').val();
    const reservationTime = $('#reservationTime').val();
    const numberOfGuests = $('#numberOfGuests').val();
    const isGuest = $('#guest').is(':checked');
    const roomNumber = $('#roomNumber').val();
    // create a table object
    const reservation = {
        customerFirstName: customerFirstName,
        customerLastName: customerLastName,
        customerEmail: customerEmail,
        customerPhone: customerPhone,
        reservationDate: reservationDate,
        reservationTime: reservationTime,
        numberOfGuests: numberOfGuests,
        guest: isGuest,
        roomNumber: roomNumber
    };
    $.ajax(
        {
            url: url + 'add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservation),
            success: function(data) {
                const id = data.id;
                if (id !== undefined) {
                    $("#addReservationDiv").addClass("hidden");
                    $('#createReservation')[0].reset();
                    showReservationPopup(id);
                }
            },
            error: function (error) {
                const errorMessage = error.responseJSON.message;
                console.log(errorMessage);
                const message = reservationLocaleText[errorMessage];
                console.log(message);
                $("#errorMessage").text(message).removeClass("hidden");
                $('#addReservationDiv').removeClass("hidden");
            }
        }
    )
}
function hideCheckAnimation() {
    setTimeout(function() {
        $('#checkAnimation').fadeOut(1000); // 1000 milliseconds = 1 second
    }, 100); // Wait for 1.5 seconds before starting the fadeOut
}

function showReservationPopup(reservationId) {
    $.ajax({
        url: url + reservationId,
        type: 'GET',
        success: function(data) {
            // create the table using the data from the server
            const reservation = data;
            const popUpId = '#reservationPopup';

            // show the popup
            $(popUpId).removeClass('hidden');
            $(popUpId).find('#customerFirstName').text(reservation.customerFirstName);
            $(popUpId).find('#customerLastName').text(reservation.customerLastName);
            $(popUpId).find('#customerEmail').text(reservation.customerEmail);
            $(popUpId).find('#customerPhone').text(reservation.customerPhone);
            $(popUpId).find('#reservationDate').text(reservation.reservationDate);
            $(popUpId).find('#reservationTime').text(reservation.reservationTime);
            $(popUpId).find('#numberOfGuests').text(reservation.numberOfGuests);

            // Check if tables are present
            if (reservation.tables && reservation.tables.length > 0) {
                let tablesHtml = '<ul>';
                reservation.tables.forEach(function(table) {
                    tablesHtml += '<li>'+ reservationLocaleText['Table-NO'] + table.tableNumber + '</li>';
                });
                tablesHtml += '</ul>';
                $(popUpId).find('#tables').html(tablesHtml);
            } else {
                // Display SweetAlert2 error if no tables
                Swal.fire({
                    icon: 'error',
                    title: reservationLocaleText['Opps'] || 'Oops',
                    text: reservationLocaleText['Something-went-wrong'] || 'Something went wrong',
                });
                $(popUpId).find('#tables').html('<p>No tables available</p>');
            }
        },
        error: function(error) {
            // Display SweetAlert2 error on ajax error
            Swal.fire({
                icon: 'error',
                title: reservationLocaleText['Opps'] || 'Oops',
                text: reservationLocaleText['Something-went-wrong'] || 'Something went wrong',
            });
        }
    });
    hideCheckAnimation();
}


// update reservation
function updateReservation(){
    const reservationId = $('#updateReservationId').val();
    const customerFirstName = $('#updateCustomerFirstName').val();
    const customerLastName = $('#updateCustomerLastName').val();
    const customerEmail = $('#updateCustomerEmail').val();
    const customerPhone = $('#updateCustomerPhone').val();
    const reservationDate = $('#updateReservationDate').val();
    const reservationTime = $('#reservationTime').val();
    const numberOfGuests = $('#updateNumberOfGuests').val();
    const isGuest = $('#updateGuest').is(':checked');
    const roomNumber = $('#updateRoomNumber').val();
    const reservationStatus = $('#updateReservationStatus').val();
    console.log(reservationStatus);
    // create a table object
    const reservation = {
        id: reservationId,
        customerFirstName: customerFirstName,
        customerLastName: customerLastName,
        customerEmail: customerEmail,
        customerPhone: customerPhone,
        reservationDate: reservationDate,
        reservationTime: reservationTime,
        numberOfGuests: numberOfGuests,
        guest: isGuest,
        roomNumber: roomNumber,
        reservationStatus: reservationStatus
    };
    console.log(reservation);
    $.ajax(
        {
            url: url + 'update/' + reservationId,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservation),
            success: function(data) {
                $("#updateReservationDiv").addClass("hidden");
                $('#updateReservation')[0].reset();
                Swal.fire({
                    icon: 'success',
                    title: reservationLocaleText['Reservation-Updated'],
                    text: reservationLocaleText['Reservation-has-been-updated'],
                    timer: 3000
                })
                getAllReservations();
            },
            error: function (error) {
                Swal.fire({
                    icon: 'error',
                    title: reservationLocaleText['Something-went-wrong'],
                    text: reservationLocaleText[error.responseJSON.message],
                });            }
        }
    )

}
// delete reservation
function deleteReservation(tableId){
    $.ajax({
        url: url + 'delete/' + tableId,
        type: 'DELETE',
        success: function (data) {
            getAllReservations();
            Swal.fire({
                icon: 'success',
                title: reservationLocaleText['Reservation-Deleted'],
                text: reservationLocaleText['Reservation-has-been-deleted'],
                timer: 3000
            })
        },
        error: function (error) {
            Swal.fire({
                icon: 'error',
                title: reservationLocaleText['Something-went-wrong'],
                text: reservationLocaleText[error.responseJSON.message],
            });
        }
    });
}

// get all reservations method
function getAllReservations(){
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
    $.ajax({
        url: url + 'all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#reservationList').DataTable({
                ajax:{
                    url: url + 'all',
                    dataSrc: ''
                },
                language:{
                    // change the default language of the data table
                    url: dataTableLanguageUrl,
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                "columns": [
                    {
                        "data": "tables",
                        "render": function(data, type, row) {
                            let tablesHtml = '<ul>';
                            data.forEach(function(table) {
                                tablesHtml += '<li>Table NO.: ' + table.tableNumber + ', Seats: ' + table.capacity + '</li>';
                            });
                            tablesHtml += '</ul>';
                            return tablesHtml;
                        }
                    },
                    { "data": "customerFirstName" },
                    { "data": "customerLastName" },
                    { "data": "customerEmail" },
                    { "data": "customerPhone" },
                    { "data": "reservationDate" },
                    { "data": "reservationTime" },
                    { "data": "numberOfGuests" },
                    {
                        "data": "guest",
                        "render": function(data, type, row) {
                            return data ? 'Yes' : 'No';
                        }
                    },
                    { "data": "roomNumber" },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button class="editStatus cursor-pointer" data-id="' + row.id + '" data-status="' + row.reservationStatus + '">' + reservationLocaleText[row.reservationStatus] + '</button>';
                        }
                    },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button data-id ="' + row.id + '" ' +
                                'data-first-name="' + row.customerFirstName + '" ' +
                                'data-last-name="' + row.customerLastName + '" ' +
                                'data-phone="' + row.customerPhone + '" ' +
                                'data-email="' + row.customerEmail + '" ' +
                                'data-date="' + row.reservationDate + '" ' +
                                'data-time="' + row.reservationTime + '" ' +
                                'data-room-number="' + row.roomNumber + '" '+
                                'data-guest="' + row.guest + '" ' +
                                'data-status="' + row.reservationStatus + '" ' +
                                'data-guests="' + row.numberOfGuests + '" class="editButton text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-pen"></i></button> ' +
                                '<button data-id="'+ row.id +'" class="deleteButton text-red-600 hover:text-red-900">' +
                                '<i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
                rowCallback: function(row, data) {
                    if (data.reservationStatus === 'CANCELLED') {
                        $(row).addClass('cancelled');
                    }
                    if (data.reservationStatus === 'PAID') {
                        $(row).addClass('attended');
                    }
                    if (data.reservationStatus === 'ORDERED') {
                        $(row).addClass('ordered');
                    }
                },

            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}

// update reservation status
function updateReservationStatus(reservationId, status){
    $.ajax({
        url: url + reservationId + '/status/' + status,
        type: 'POST',
        success: function(data) {
            Swal.fire({
                icon: 'success',
                title: reservationLocaleText['Reservation-Status-Updated'],
                text: reservationLocaleText['Reservation-status-has-been-updated'],
                timer: 3000
            })
            getAllReservations();
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: reservationLocaleText['Opps'] ,
                text: reservationLocaleText[error.responseJSON.message],
            })
        }
    });
}