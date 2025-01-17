const url = '/api/order';
let orderLocalizationMessages;
let orderId;



function init() {
    console.log("init order");
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    orderLocalizationMessages = JSON.parse(messages);
    getOrderList();
    // view order items
    $('#orderList').on('click', '.viewOrderItems', function () {
        $('#menuList').toggleClass("hidden");
        orderId = $(this).attr('data-id');
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
            title: orderLocalizationMessages['Are-you-sure'],
            text: orderLocalizationMessages['You-wont-be-able-to-revert-this'],
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: orderLocalizationMessages['Yes-delete-it'],
            cancelButtonText: orderLocalizationMessages['Cancel']
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
    // cancel order
    $('#cancelOrderButton').on('click', function() {
       cancelOrder(orderId);
    });

}
function cancelOrder(orderId) {
$.ajax({
    url: url + '/cancel/' + orderId,
    type: 'POST',
    success: function(data) {
        getOrderList();
        Swal.fire({
            icon: 'success',
            title: orderLocalizationMessages['Order-Cancelled'],
            showConfirmButton: false,
            timer: 1500
        });
    },
    error: function(error) {
        Swal.fire({
            icon: 'error',
            title: orderLocalizationMessages['Opps'],
            text: orderLocalizationMessages[error.responseJSON.message],

        });
    }
})
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
                title: orderLocalizationMessages['Order-Placed-Successfully'],
                showConfirmButton: false,
                timer: 1500
            });
        },
        error: function(error) {
            console.log(error);
            Swal.fire({
                icon: 'error',
                title: orderLocalizationMessages['Opps'],
                text: orderLocalizationMessages[error.responseJSON.message],
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
                    { "data": null,
                        "render": function(data, type, row) {
                            return orderLocalizationMessages[row.status];
                        }
                    },
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
            Swal.fire({
                icon: 'success',
                title: orderLocalizationMessages['Order-Deleted'],
                message: orderLocalizationMessages['Order-Deleted'],
                showConfirmButton: false,
                timer: 1500
            });
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: orderLocalizationMessages['Opps'],
                text: orderLocalizationMessages['Cannot-delete-this-order'],
            });
        }
    });
}