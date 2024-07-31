let currentReservationId;
const url = '/api/reservation/'
let waiterMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    waiterMessages = messages ? JSON.parse(messages) : null;
    getAllReservationsForToday();
    const reservationDiv = $("#todayReservationDiv")
    reservationDiv.on("click", "#showAllDetails", function(){
        const id = $(this).attr("date-id");
        getReservationById(id);
    });

    $(".closePopUp").on("click", function(){
        $('#reservationDetailsPopup').toggleClass("hidden");
    });
    reservationDiv.on("click", ".takeOrder", function(){
        const id = $(this).attr("date-id");
        const tableNumbers = $(this).attr("data-table-numbers");
        currentReservationId = id;
        $('#table-number').text(tableNumbers);
        $('#menuList').toggleClass("hidden");
    });
}
function getReservationById(id) {
    console.log("Get all reservations for today");
    $.ajax({
        url: url + id,
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
        url: url + "today",
        type: "GET",
        success: function(data) {
            displayReservations(data, waiterMessages);
        },
        error: function(error) {
            console.log(error);
        }
    });
}
function changeReservationStatus(id, status){
    $.ajax({
        url: `${url}${id}/status/${status}`,
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
function displayReservations(data, messages) {
    // set total number of reservation
    const attendedReservations = data.filter(reservation => reservation.reservationStatus === "ATTENDED");
        // count the number of reservatiosn with status ATTENDED
        if (attendedReservations.length === 1) {
            $("#totalReservation").text(attendedReservations.length +' ' + messages['Reservation']);
        } else {
            $("#totalReservation").text(attendedReservations.length +' ' +messages['Reservations']);
        }
    const reservationDiv = $("#todayReservationDiv");
    reservationDiv.empty();
    if (attendedReservations.length == 0) {
        let noReservationText= messages['No-Reservations-for-today'];
        reservationDiv.append($("<p class='text-gray-500 '>"+noReservationText+"</p>"));
        return;
    }
    for (const reservation of data) {
        const card = createCard(reservation, messages);
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
    const reservationTime = reservation.reservationTime;
    // convert text to date object
    const time = new Date("1970-01-01T" + reservationTime + "Z");
    // add 3 hours to the time
    time.setHours(time.getHours() + 2);
    // convert time back to text
    const reservationTimePlus3hours = time.toTimeString().slice(0, 5);
    const card = $(`
        <div class="bg-gray-100 shadow-sm rounded-lg font-sans my-3">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-blue-400 flex justify-center items-center rounded-md text-white">
                    <p>${waiterMessages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${waiterMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime} - ${reservationTimePlus3hours}</p>
                </div>
                <div>
                    <p><i class='bx bxs-phone px-2 text-green-500'></i>${reservation.customerPhone}</p>
                    <p><i class='bx bxs-envelope px-2 text-green-500'></i>${reservation.customerEmail}</p>
                    <p class=""><i class='bx bxs-hotel px-2 text-green-500'></i>${waiterMessages['Hotel-Guest']} ${reservation.guest ? waiterMessages['Yes'] : waiterMessages['No']} , ${waiterMessages['Room-NO']}. ${reservation.roomNumber}</p>
                </div>
            </div>
        </div>
    `);
    return card;
}
function createCard(reservation, messages) {
    const tableNumbers = reservation.tables.map(table => table.tableNumber).join(", ");
    let card;
    if(reservation.reservationStatus === "ATTENDED"){
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-green-300 flex justify-center items-center rounded-md text-black">
                    <p>${messages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${waiterMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                    <div class="px-2">
                        <button date-id="${reservation.id}" data-table-numbers="${tableNumbers}" class="takeOrder bg-blue-300 text-black rounded-md p-2 w-full hover:bg-blue-400">${messages['Take-order']}</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400">${messages['View-Details']}</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    }
    return card;
}