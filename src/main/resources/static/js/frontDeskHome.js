function init(){
    console.log("Front Desk Home Page");
    getAllReservationsForToday();

    $("#todayReservationDiv").on("click", "#attendedButton", function(){
        const id = $(this).attr("date-id");
        if (!confirm("Are you sure the customer has checked in?")) return;
        changeReservationStatus(id, "ATTENDED");
        alert("Customer Checked In")
    });
    $("#todayReservationDiv").on("click", "#cancelledButton", function(){
        const id = $(this).attr("date-id");
        if (!confirm("Are you sure you want to cancel this reservation?")) return;
        changeReservationStatus(id, "CANCELLED");
        alert("Reservation Cancelled")
    });
    $("#todayReservationDiv").on("click", "#showAllDetails", function(){
        const id = $(this).attr("date-id");
        getReservationById(id);
    });

    $(".closePopUp").on("click", function(){
        $('#reservationDetailsPopup').toggleClass("hidden");
    });


}
function getReservationById(id) {
    console.log("Get all reservations for today");
    $.ajax({
        url: "/api/reservation/" + id,
        type: "GET",
        success: function(data) {
            console.log(data);
            displayReservationPopupDetails(data);
        },
        error: function(error) {
            console.log(error);
        }
    });
}
function getAllReservationsForToday() {
    console.log("Get all reservations for today");
    $.ajax({
        url: "/api/reservation/today",
        type: "GET",
        success: function(data) {
            console.log(data);
            displayReservations(data);
        },
        error: function(error) {
            console.log(error);
        }
    });
}

function displayReservations(data) {
    const reservationDiv = $("#todayReservationDiv");
    reservationDiv.empty();
    for (const reservation of data) {
        const card = createCard(reservation);
        reservationDiv.append(card);
    }
}
function displayReservationPopupDetails(reservation) {
    const reservationDiv = $("#reservationDetailsDiv");
    reservationDiv.empty();
    const card = createPopUp(reservation);
    reservationDiv.append(card);
    $('#reservationDetailsPopup').toggleClass("hidden");
}

function createPopUp(reservation) {
    const tableNumbers = reservation.tables.map(table => table.tableNumber).join(", ");
    const card = $(`
        <div class="bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-4 flex flex-col gap-2">
                <div class="py-3 font-bold bg-blue-400 flex justify-center items-center rounded-md text-white">
                    <p>Table NO. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i>for <span class="font-bold">${reservation.numberOfGuests}</span> people</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div>
                    <p><i class='bx bxs-phone px-2 text-green-500'></i>${reservation.customerPhone}</p>
                    <p><i class='bx bxs-envelope px-2 text-green-500'></i>${reservation.customerEmail}</p>
                    <p>Is guest? ${reservation.guest} : room number ${reservation.roomNumber}</p>
                </div>
            </div>
        </div>
    `);
    return card;

}

function createCard(reservation) {
    const tableNumbers = reservation.tables.map(table => table.tableNumber).join(", ");
    let card;
    if(reservation.reservationStatus == "CONFIRMED"){
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-4 flex flex-col gap-2">
                <div class="py-3 font-bold bg-blue-400 flex justify-center items-center rounded-md text-white">
                    <p>Table NO. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i>for <span class="font-bold">${reservation.numberOfGuests}</span> people</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                    <div class="flex justify-evenly">
                        <button id="attendedButton" date-id="${reservation.id}" class="bg-blue-400 text-white rounded-md p-2">Check In</button>
                        <button id="cancelledButton" date-id="${reservation.id}" class="bg-red-400 text-white rounded-md p-2">Cancelled</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else {
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-4 flex flex-col gap-2">
                <div class="py-3 font-bold bg-green-300 flex justify-center items-center rounded-md text-black">
                    <p>Table NO. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i>for <span class="font-bold">${reservation.numberOfGuests}</span> people</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                    <div class="px-2">
                        <button class="bg-blue-300 text-black rounded-md p-2 w-full">Take Order</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    }
    return card;
}

function changeReservationStatus(id, status){
    $.ajax({
        url: `/api/reservation/${id}/status/${status}`,
        type: "POST",
        success: function(data) {
            console.log(data);
            getAllReservationsForToday();
        },
        error: function(error) {
            console.log(error);
        }
    });
}