const url = '/api/reservation/';
function initReservation(){
    console.log("Reservation page loaded");
    getAllReservations();
    $(".myBtn").click(function(){
        $("#addReservationDiv").removeClass("hidden");
    });
    $(".closeButtonAdd").click(function(){
        $("#addReservationDiv").addClass("hidden");
    });
    $(".closeButtonUpdate").click(function(){
        $("#updateReservationDiv").addClass("hidden");
    });
    $('#guest').change(function() {
        if ($(this).is(':checked')) {
            $('#roomNumberDiv').removeClass('hidden');
        } else {
            $('#roomNumberDiv').addClass('hidden');
        }
    });
    // Time slots for reservation
    const startTime = new Date();
    startTime.setHours(7);
    startTime.setMinutes(30);
    const endTime = new Date();
    endTime.setHours(23);
    endTime.setMinutes(30);

    const timeSlotsGrid = $('#timeSlotsGrid');

    while (startTime <= endTime) {
        const hours = startTime.getHours().toString().padStart(2, '0');
        const minutes = startTime.getMinutes().toString().padStart(2, '0');
        const timeString = `${hours}:${minutes}`;

        const timeSlot = $('<div></div>')
            .addClass("time-slot bg-gray-50 p-2 rounded text-center cursor-pointer hover:bg-gray-100")
            .attr('data-time', timeString)
            .text(timeString)
            .click(function() {
                $('.time-slot').removeClass('bg-gray-50').addClass('bg-green-100');
                $(this).removeClass('bg-gray-50').addClass('bg-green-100 text-white');
                $('#reservationTime').val($(this).data('time'));
            });

        timeSlotsGrid.append(timeSlot);  // Append the time slot to the grid

        startTime.setMinutes(startTime.getMinutes() + 60); // Increment by 60 minutes
    }
    // for date picker
    flatpickr(".flatpickr", {
        enableTime: false,
        dateFormat: "Y-m-d",
        minDate: "today",
        altFormat: "F j, Y",
        maxDate: new Date().fp_incr(30),
    });
    // Event listener for the update table form submission
    $('#createReservation').on('submit', function(event) {
        event.preventDefault();
        addReservation()
    });
    $(document).on('click', '.deleteButton', function (){
        const reservationId = $(this).data('id');
        if (confirm("Are you sure you want to delete this reservation?")){
            deleteReservation(reservationId);
        } else {
            return false;
        }
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
        console.log(reservationDate);
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
            $('#roomNumberDiv').removeClass('hidden');
        } else {
            $('#roomNumberDiv').addClass('hidden');
        }
        $('#updateReservationId').val(reservationId);
    });
    $('#updateReservation').on('submit', function(event) {
        event.preventDefault();
        updateReservation()
    });
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
                $("#addReservationDiv").addClass("hidden");
                $('#createReservation')[0].reset();
                getAllReservations();
            },
            error: function (error) {
                console.log(error);
                alert('An error occurred while creating the reservation');
            }
        }
    )
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
    console.log(reservationId);
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
                getAllReservations();
            },
            error: function (error) {
                console.log(error);
                alert('An error occurred while updating the reservation');
            }
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
        },
        error: function (error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}

// get all reservations method
function getAllReservations(){
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
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                "columns": [
                    {
                        "data": "tables",
                        "render": function(data, type, row) {
                            let tablesHtml = '<ul>';
                            data.forEach(function(table) {
                                tablesHtml += '<li>Table NO.: ' + table.tableNumber + ', Seats: ' + table.tableCapacity + '</li>';
                            });
                            tablesHtml += '</ul>';
                            return tablesHtml;
                        }
                    },
                    { "data": "customerFirstName" },
                    { "data": "customerLastName" },
                    { "data": "customerEmail" },
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
                        "data": "reservationStatus",
                        "render": function(data, type, row) {
                            return '<a href="/reservations/editStatus/' + row.id + '" class="editStatus">' + data + '</a>';
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
                                'data-guests="' + row.numberOfGuests + '" class="editButton text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-pen"></i></button> ' +
                                '<button data-id="'+ row.id +'" class="deleteButton text-red-600 hover:text-red-900">' +
                                '<i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
                "createdRow": function(row, data, dataIndex) {
                    if(data.reservationStatus === 'CANCELLED') {
                        $(row).addClass('bg-red-200');
                    } else if(data.reservationStatus === 'ATTENDED') {
                        $(row).addClass('bg-green-400');
                    }
                },
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}