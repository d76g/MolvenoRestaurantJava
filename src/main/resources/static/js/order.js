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
        $('#table-number').text($(this).attr('data-table'));
        getOrderItem(orderId);
    });
    // delete order
    $('#orderList').on('click', '.deleteButton', function () {
        const orderId = $(this).attr('data-id');
        currentReservationId = $(this).attr('data-reservation-id');
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                deleteOrder(orderId);
            }
        })
    });

    // place order
    $('#confirmOrderButton').on('click', function() {
        placeOrder();
    });

}

function getOrderItem(orderId) {
    $.ajax({
        url: url + '/' + orderId,
        type: 'GET',
        success: function (data) {
            Order.orderItems = data.orderItems;
            displayOrderItems();
            updateOrderTotal();
        },
        error: function(error) {
            console.error("There was an error fetching the order item data:", error);
        }
    });
}
function placeOrder() {
    $.ajax({
        url: url + '/place',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(Order),
        success: function(data) {
            console.log(data);
            getOrderList();
            $('#menuList').toggleClass("hidden");
            Swal.fire({
                icon: 'success',
                title: 'Order placed successfully',
                showConfirmButton: false,
                timer: 1500
            });
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: error.responseJSON.message,
            });
        }
    });

}
function getOrderList(){
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
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
                language:{
                    // change the default language of the data table
                    url: dataTableLanguageUrl,
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
                    { "data": "status" },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            const tableNumbers = row.reservation.tables.map(table => table.tableNumber).join(', ');
                            return '<button data-id="'+ row.orderId +'" data-reservation-id="'+ row.reservation.id +'" data-table="' + tableNumbers +'"  class="viewOrderItems text-indigo-600 hover:text-indigo-900">' +
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
            getOrderList();
            changeReservationStatus(currentReservationId, 'ATTENDED');
        },
        error: function(error) {
            console.error("There was an error deleting the order:", error);
        }
    });
}