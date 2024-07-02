const url = '/api/reservation/';
function init(){
    // call the get all table method
    getAllTable();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const tableId = $(this).data('id');
        console.log(tableId);
        if (confirm("Are you sure you want to delete this table?")){
            deleteTable(tableId);
        } else {
            return false;
        }
    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addFormDiv").toggleClass("hidden");
    });
    // Event listener for the add table form submission
    $('#addTableForm').on('submit', function (e){
        e.preventDefault();
        addTable();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addFormDiv").toggleClass("hidden");
    });
    // Event listener for the close button
    $(document).on('click', '.editButton', function(){
        // get the table id and capacity
        const tableId = $(this).data('id');
        const tableCapacity = $(this).data('capacity');
        // show the update form
        $("#updateFormDiv").toggleClass("hidden");
        // set the new table capacity and id in the form
        $('#updateTableCapacity').val(tableCapacity);
        $('#updateTableId').val(tableId);

    });
    // Event listener for the update table form submission
    $('#updateTableForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the table
        if(confirm("Are you sure you want to update this table?")){
            updateTable();
        } else {
            return false;
        }
    });
}

// get all table method
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
                            return '<a href="/reservations/editReservation/' + row.id + '" ' +
                                'data-first-name="' + row.customerFirstName + '" ' +
                                'data-last-name="' + row.customerLastName + '" ' +
                                'data-email="' + row.customerEmail + '" ' +
                                'data-date="' + row.reservationDate + '" ' +
                                'data-time="' + row.reservationTime + '" ' +
                                'data-guests="' + row.numberOfGuests + '" class="editButton text-indigo-600 hover:text-indigo-900">' +
                                '<i class="fa-solid fa-pen"></i></a> ' +
                                '<a href="/reservations/deleteReservation/' + row.id + '" class="deleteButton text-red-600 hover:text-red-900">' +
                                '<i class="fa-solid fa-trash"></i></a>';
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

// add table method
function addTable(){
    const tableNumber = $('#tableNumber').val();
    const tableCapacity = $('#tableCapacity').val();
    // create a table object
    const table = {
        tableNumber: tableNumber,
        tableCapacity: tableCapacity
    };
    $.ajax({
        url: url + 'add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(table),
        success: function(data) {
            $("#addFormDiv").toggleClass("hidden");
            $('#addTableForm')[0].reset();
            getAllTable();
        },
        error: function(error) {
            console.error("There was an error adding the table:", error);
        }
    });

}
// update table method
function updateTable(){
    const tableId =  $('#updateTableId').val() ;
    const tableCapacity = $('#updateTableCapacity').val();
    console.log(tableCapacity);
    const table = {
        id: tableId,
        tableCapacity: tableCapacity
    };
    $.ajax({
        url: url + 'update/' + tableId,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(table),
        success: function(data) {
            // hide the update form use the form ID to hide the form
            $("#updateFormDiv").toggleClass("hidden");
            // reset the form
            $('#updateTableForm')[0].reset();
            // call the get all table function to refresh the table
            getAllTable();
        },
        error: function(error) {
            console.error("There was an error updating the table:", error);
        }
    });


}
// delete table
function deleteTable(tableId){
    $.ajax({
        url: url + 'delete/' + tableId,
        type: 'DELETE',
        success: function (data) {
            getAllTable();
        },
        error: function (error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}
