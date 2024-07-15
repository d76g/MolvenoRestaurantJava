const url = '/api/order';

function init() {
    console.log("init order");
    getOrderList();

    // view order items
    $('#orderList').on('click', '.viewOrderItems', function () {
        $('#menuList').toggleClass("hidden");
        const orderId = $(this).attr('data-id');
        Order.id = orderId;
        currentReservationId = $(this).attr('data-reservation-id');
        getOrderItem(orderId);
    });
    // delete order
    $('#orderList').on('click', '.deleteButton', function () {
        const orderId = $(this).attr('data-id');
        currentReservationId = $(this).attr('data-reservation-id');
        if (!confirm('Are you sure you want to delete this order?')) return;
        deleteOrder(orderId);
    });
}

function getOrderItem(orderId) {
    $.ajax({
        url: url + '/' + orderId,
        type: 'GET',
        success: function(data) {
            Order.orderItems = data.orderItems;
            displayOrderItems();
            updateOrderTotal();
        },
        error: function(error) {
            console.error("There was an error fetching the order item data:", error);
        }
    });
}

function getOrderList(){
    $.ajax({
        url: url ,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#orderList').DataTable({
                ajax:{
                    url: url,
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                "columns": [
                    {
                        "data": "reservation.tables",
                        "render": function(data, type, row) {
                            let tablesHtml = '<ul>';
                            data.forEach(function(table) {
                                tablesHtml += '<li>' + table.tableNumber +'</li>';
                            });
                            tablesHtml += '</ul>';
                            return tablesHtml;
                        }
                    },
                    { "data": " customerFullName",
                      "render": function(data, type, row) {
                        return row.reservation.customerFirstName + ' ' + row.reservation.customerLastName;
                      }
                    },
                    { "data": "totalPrice" },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button data-id="'+ row.orderId +'" data-reservation-id="'+ row.reservation.id +'" class="viewOrderItems text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-eye"></i></button> '
                        }
                    },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button data-id="'+ row.orderId +'" data-reservation-id="'+ row.reservation.id +'" class="deleteButton text-red-600 hover:text-red-900">' +
                                '<i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the order data:", error);
        }
    });
}
function changeReservationStatus(id, status){
    $.ajax({
        url: `/api/reservation/${id}/status/${status}`,
        type: "POST",
        success: function(data) {
            console.log(data);
        },
        error: function(error) {
            console.log(error);
        }
    });
}

function deleteOrder(orderId) {
    $.ajax({
        url: url + '/' + orderId,
        type: 'DELETE',
        success: function(data) {
            console.log(data);
            getOrderList();
            changeReservationStatus(currentReservationId, 'ATTENDED');
        },
        error: function(error) {
            console.error("There was an error deleting the order:", error);
        }
    });
}