const url = 'api/reservation/';
function init(){
    $(".myBtn").click(function(){
        $("#addReservationDiv").removeClass("hidden");
    });
    $(".closeButtonAdd").click(function(){
        $("#addReservationDiv").addClass("hidden");
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
        isGuest: isGuest,
        roomNumber: roomNumber
    };
    console.log(reservation);

    $.ajax(
        {
            url: url + 'add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(reservation),
            success: function(data) {
                $("#addReservationDiv").addClass("hidden");
                $('#createReservation')[0].reset();
                alert('Reservation created successfully');
            },
            error: function (error) {
                console.log(error);
                alert('An error occurred while creating the reservation');
            }
        }
    )

}
