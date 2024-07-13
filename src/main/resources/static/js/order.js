const url = '/api/order';

function init() {
    getOrderList();

    // view order items
    $('#orderList').on('click', '.viewOrderItems', function () {



    });
}

function getOrderItem(orderId) {
    $ajax({
        url: url + '/' + orderId,
        type: 'GET',
        success: function(data) {
            console.log(data);
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
                    { "data": "total_price" },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button class="viewOrderItems text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-eye"></i></button> '
                        }
                    },
                    {
                        "data": null,
                        "render": function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-pen"></i></button> ' +
                                '<button data-id="'+ row.id +'" class="deleteButton text-red-600 hover:text-red-900">' +
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