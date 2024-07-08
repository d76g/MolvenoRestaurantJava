const url = '/api/table/';
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
function getAllTable(){
    $.ajax({
        url: url + 'all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#tableList').DataTable({
                ajax:{
                    url: url + 'all',
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'tableNumber' },
                    { data: 'tableCapacity' },
                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.id + '" data-capacity="' + row.tableCapacity + '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.id + '" ><i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            alert(error.responseJSON.message)
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
            alert(error.responseJSON.message)
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
            alert(error.responseJSON.message)
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
            alert(error.responseJSON.message)
        }
    });
}
