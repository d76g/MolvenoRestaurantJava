let currentReservationId;
const url = '/api/reservation/'
let isGuest ;
let orderStatus;
let frontDeskMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    frontDeskMessages = messages ? JSON.parse(messages) : null;
    getAllReservationsForToday();
    const reservationDiv = $("#todayReservationDiv")
    reservationDiv.on("click", "#attendedButton", function(){
        const id = $(this).attr("date-id");
        Swal.fire({
            title: frontDeskMessages['Are-you-sure'],
            text: frontDeskMessages['You-wont-be-able-to-revert-this'],
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: frontDeskMessages['Yes-check-in'],
            cancelButtonText: frontDeskMessages['Cancel']
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: frontDeskMessages['Checked-in'],
                    text: frontDeskMessages['The-customer-has-been-checked-in'],
                    icon: "success"
                });
                changeReservationStatus(id, "ATTENDED");
            }
        });
        getAllReservationsForToday();
    });
    reservationDiv.on("click", "#makePayment", function(){
        const id = $(this).attr("date-id");
        isGuest = $(this).attr("data-guest");
        console.log(isGuest)
        getOrdersForReservation(id);
    });
    reservationDiv.on("click", "#cancelledButton", function(){
        const id = $(this).attr("date-id");
        Swal.fire({
            title: frontDeskMessages['Are-you-sure'],
            text: frontDeskMessages['You-wont-be-able-to-revert-this'],
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: frontDeskMessages['Yes-cancel-it'],
            cancelButtonText: frontDeskMessages['Cancel']

        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: frontDeskMessages['Cancelled'],
                    text: frontDeskMessages['Reservation-Cancelled'],
                    icon: "success"
                });
                changeReservationStatus(id, "CANCELLED");
                getAllReservationsForToday()
            }
        });
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
            displayReservations(data, frontDeskMessages);
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
            getAllReservationsForToday();
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: frontDeskMessages['Opps'],
                text: frontDeskMessages[error.responseJSON.message],
                showConfirmButton: false,
                timer: 1500
            })
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
function displayReservations(data, messages) {
    // set total number of reservation
    if (data.length > 0) {
        if (data.length === 1) {
            $("#totalReservation").text(data.length +' ' + messages['Reservation']);
        } else {
            $("#totalReservation").text(data.length +' ' +messages['Reservations']);
        }
    }
    const reservationDiv = $("#todayReservationDiv");
    reservationDiv.empty();
    if (data.length == 0) {
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
        orderStatus = order.status;
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
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">${frontDeskMessages['Item-Name']}</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">${frontDeskMessages['Quantity']}</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">${frontDeskMessages['Item-Price']}</th>
        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">${frontDeskMessages['Total-price']}</th>
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
        <td colspan="3" class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium text-gray-500">${frontDeskMessages['Total-price']}</td>
        <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${totalOrderPrice}</td>
      </tr>
    </tbody>
  </table>
`;

    // Display Swal popup
    Swal.fire({
        title: frontDeskMessages['Order-Details'],
        html: htmlContent,
        showCancelButton: true,
        confirmButtonText: frontDeskMessages['Make-Payment'],
        cancelButtonText: frontDeskMessages['Cancel'],
        width: '40rem',
    }).then((result) => {
        if (result.isConfirmed) {
           if (orderStatus === "PLACED"){
               console.log(isGuest)
               if (isGuest === "true") {
                   Swal.fire(frontDeskMessages['Order-sent-to-hotel-room'], '', 'success');
               } else {
                   Swal.fire(frontDeskMessages['Payment-processed'], '', 'success');
               }
               changeReservationStatus(reservationId, "PAID");
               makePayment(reservationId);
               getAllReservationsForToday();
               isGuest = false;
           } else {
                Swal.fire({
                    icon: 'error',
                    title: frontDeskMessages['Pending-Order'],
                    text: frontDeskMessages['Order-not-placed-yet'],
                    showConfirmButton: false,
                    timer: 1500
                })

           }
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
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-blue-400 flex justify-center items-center rounded-md text-white">
                    <p>${frontDeskMessages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${frontDeskMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime} - ${reservationTimePlus3hours}</p>
                </div>
                <div>
                    <p><i class='bx bxs-phone px-2 text-green-500'></i>${reservation.customerPhone}</p>
                    <p><i class='bx bxs-envelope px-2 text-green-500'></i>${reservation.customerEmail}</p>
                    <p class=""><i class='bx bxs-hotel px-2 text-green-500'></i>${frontDeskMessages['Hotel-Guest']} ${reservation.guest ? frontDeskMessages['Yes'] : frontDeskMessages['No']} , ${frontDeskMessages['Room-NO']}. ${reservation.roomNumber}</p>
                </div>
            </div>
        </div>
    `);
    return card;
}

function createCard(reservation, messages) {
    const tableNumbers = reservation.tables.map(table => table.tableNumber).join(", ");
    let card;
    if(reservation.reservationStatus === "CONFIRMED"){
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-blue-400 flex justify-center items-center rounded-md text-white">
                    <p>${messages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${frontDeskMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                    <div class="flex justify-between px-2">
                        <button id="attendedButton" date-id="${reservation.id}" class="bg-blue-400 text-white rounded-md p-2 hover:bg-blue-500" >${messages['Check-In']}</button>
                        <button id="cancelledButton" date-id="${reservation.id}" class="bg-red-400 text-white rounded-md p-2 hover:bg-red-500" >${messages['Cancel']}</button>
                    </div>
                    <div class="px-2">
                        <button id="showAllDetails" date-id="${reservation.id}" class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400" >${messages['View-Details']}</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else if(reservation.reservationStatus === "ATTENDED"){
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-green-300 flex justify-center items-center rounded-md text-black">
                    <p>${messages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${frontDeskMessages['Guests']}</p>
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
    } else if (reservation.reservationStatus === "ORDERED") {
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-red-400 flex justify-center items-center rounded-md text-white">
                    <p>${messages['Table-NO']}. ${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${frontDeskMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                    <div class="px-2 flex gap-x-1" >
                        <button id="makePayment" date-id="${reservation.id}" data-guest="${reservation.guest}"
                         class="pay-bill bg-blue-300 text-black rounded-md p-2 w-full hover:bg-blue-400">${messages['Pay-Bill']}</button>
                        <button date-id="${reservation.id}" data-table-numbers="${tableNumbers}" class="takeOrder bg-blue-300 text-black rounded-md p-2 w-full hover:bg-blue-400">${messages['Take-order']}</button>
                    </div>
                    </div>
                    <div class="px-2">  
                        <button id="showAllDetails" date-id="${reservation.id}" 
                        class="bg-green-300 text-black rounded-md p-2 w-full hover:bg-green-400">${messages['View-Details']}</button>
                    </div>
                </div>
            </div>
        </div>
    `);
    } else if (reservation.reservationStatus === "PAID") {
        card = $(`
        <div class="h-72 w-56 bg-gray-100 shadow-sm rounded-lg font-sans">
            <div class="p-2 flex flex-col gap-2">
                <div class="py-3 font-bold bg-red-400 flex justify-center items-center rounded-md text-white">
                    <p>${messages['Table-NO']}.${tableNumbers}</p>
                </div>
                <div>
                    <p class="font-bold"><i class='bx bxs-user px-2 text-blue-500'></i>${reservation.customerFirstName} ${reservation.customerLastName}</p>
                    <p><i class='bx bxs-group px-2 text-green-500'></i><span class="font-bold">${reservation.numberOfGuests}</span> ${frontDeskMessages['Guests']}</p>
                </div>
                <div>
                    <p><i class='bx bxs-calendar px-2 text-green-500'></i>${reservation.reservationDate}</p>
                    <p><i class='bx bxs-time px-2 text-green-500'></i>${reservation.reservationTime}</p>
                </div>
                <div class="w-full flex flex-col gap-y-2">
                <div class="px-2">
                        <button class="pay-bill bg-blue-600 text-black rounded-md p-2 w-full hover:bg-blue-400 font-bold text-white" disabled>${messages['Paid']}</button>
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