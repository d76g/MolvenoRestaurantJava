let currentReservationId;
const url = '/api/reservation/'
function init(){
    console.log("Front Desk Home Page");
    getAllReservationsForToday();
    const reservationDiv = $("#todayReservationDiv");
    reservationDiv.on("click", "#attendedButton", function(){
        const id = $(this).attr("date-id");
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, check in!"
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Checked In!",
                    text: "The customer has been checked in successfully",
                    icon: "success"
                });
                changeReservationStatus(id, "ATTENDED");
            }
        });
        getAllReservationsForToday();
    });
    reservationDiv.on("click", "#makePayment", function(){
        const id = $(this).attr("date-id");
        getOrdersForReservation(id);
    });
    reservationDiv.on("click", "#cancelledButton", function(){
        const id = $(this).attr("date-id");
        if (!confirm("Are you sure you want to cancel this reservation?")) return;
        changeReservationStatus(id, "CANCELLED");
        alert("Reservation Cancelled")
    });
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
        console.log("Take Order for reservation: " + id + " at table: " + tableNumbers);
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
            console.log('Reservations for today: ', data);
            displayReservations(data);
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
function makePayment(reservationId){
    $.ajax({
        url: `${url}${reservationId}/payment`,
        type: "POST",
        success: function(data) {

        },
        error: function(error) {
            console.log(error);
        }
    });
}
function displayReservations(data) {
    const reservationDiv = $("#todayReservationDiv");
    reservationDiv.empty();
    if (data.length == 0) {
        reservationDiv.append($("<p class='text-gray-500 '>No reservations for today</p>"));
        return;
    }
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
// get all orders for a reservation
function getOrdersForReservation(reservationId){
    $.ajax(
        {
            url: `/api/order/reservation/${reservationId}`,
            type: "GET",
            contentType: "application/json",
            success: function(data){
                displayOrderItemsPop(data, reservationId)
            },
            error: function(error){
                console.log(error)
            }
        }
    )

}

function displayOrderItemsPop(data, reservationId) {
    // Summarize quantities for the given reservation ID
    var summedQuantities = {};
    var totalOrderPrice = 0;
    for (const order of data) {
            for (const orderItem of order.orderItems) {
                totalOrderPrice += orderItem.quantity * orderItem.itemPrice;
                if (summedQuantities[orderItem.menuName]) {
                    summedQuantities[orderItem.menuName].quantity += orderItem.quantity;
                } else {
                    summedQuantities[orderItem.menuName] = {
                        image: orderItem.image,
                        itemPrice: orderItem.itemPrice,
                        menuName: orderItem.menuName,
                        quantity: orderItem.quantity
                    };
                }
            }
    }

    var htmlContent = `
  <table class="min-w-full divide-y divide-gray-200">
    <thead class="bg-gray-50">
      <tr>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Item Name</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Quantity</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Item Price</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Price</th>
      </tr>
    </thead>
    <tbody class="bg-white divide-y divide-gray-200">
`;
    for (const itemName in summedQuantities) {
        var item = summedQuantities[itemName];
        var totalPrice = item.quantity * item.itemPrice;
        htmlContent += `
      <tr>
        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">${item.menuName}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${item.quantity}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${item.itemPrice}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${totalPrice}</td>
      </tr>
    `;
    }
    htmlContent += `
      <tr>
        <td colspan="3" class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium text-gray-500">Total Price</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${totalOrderPrice}</td>
      </tr>
    </tbody>
  </table>
`;

    // Display Swal popup
    Swal.fire({
        title: 'Order Summary',
        html: htmlContent,
        showCancelButton: true,
        confirmButtonText: 'Make Payment',
        cancelButtonText: 'Cancel',
        width: '40rem',
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire('Payment processed!', '', 'success');
            changeReservationStatus(reservationId, "PAID");
            makePayment(reservationId);
            getAllReservationsForToday();
        }
    });
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
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime} to ${reservationTimePlus3hours}</p>
                </div>
                <div>
                    <p><i class='bx bxs-phone px-2 text-green-500'></i>${reservation.customerPhone}</p>
                    <p><i class='bx bxs-envelope px-2 text-green-500'></i>${reservation.customerEmail}</p>
                    <p class=""><i class='bx bxs-hotel px-2 text-green-500'></i>Hotel Guest: ${reservation.guest ? 'Yes' : 'No'} , Room NO. ${reservation.roomNumber}</p>
                </div>
            </div>
        </div>
    `);
    return card;
}

function createCard(reservation) {
    const tableNumbers = reservation.tables.map(table => table.tableNumber).join(", ");
    let card;
    if(reservation.reservationStatus === "CONFIRMED"){
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
                        <button id="attendedButton" date-id="${reservation.id}" class="bg-blue-400 text-white rounded-md p-2 hover:bg-blue-500">Check In</button>
                        <button id="cancelledButton" date-id="${reservation.id}" class="bg-red-400 text-white rounded-md p-2 hover:bg-red-500">Cancelled</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400     ">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else if(reservation.reservationStatus === "ATTENDED"){
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
                        <button date-id="${reservation.id}" data-table-numbers="${tableNumbers}" class="takeOrder bg-blue-300 text-black rounded-md p-2 w-full hover:bg-blue-400">Take Order</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else if (reservation.reservationStatus === "ORDERED") {
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-4 flex flex-col gap-2">
                <div class="py-3 font-bold bg-red-400 flex justify-center items-center rounded-md text-white">
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
                        <button id="makePayment" date-id="${reservation.id}" class="pay-bill bg-blue-300 text-black rounded-md p-2 w-full hover:bg-blue-400">Pay Bill</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else if (reservation.reservationStatus === "PAID") {
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-4 flex flex-col gap-2">
                <div class="py-3 font-bold bg-red-400 flex justify-center items-center rounded-md text-white">
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
                        <button class="pay-bill bg-blue-600 text-black rounded-md p-2 w-full hover:bg-blue-400 font-bold text-white" disabled>PAID</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400">View Details</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    }
    return card;
}