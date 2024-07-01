function init(){
        new DataTable("#tableList",{
            sWidth: "40%",
            bAutoWidth: false,
        });
    $(".deleteButton").click(function() {
        console.log("Delete button clicked")
        deleteTable();
    });
    }
function getAllTable(){
    $.ajax({
        url: '/api/table/all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            var tableBody = $('#tableList tbody');
            tableBody.empty(); // Clear any existing rows

            $.each(data, function(index, table) {
                var row = $('<tr></tr>');
                row.append('<td>' + table.tableNumber + '</td>');
                row.append('<td>' + table.tableCapacity + '</td>');
                row.append(
                    '<td class="flex gap-x-3">' +
                    '<button data-id="' + table.id + '" data-capacity="' + table.tableCapacity + '" class="editButton text-indigo-600 hover:text-indigo-900"><i class="fa-solid fa-pen"></i></button>' +
                    '<button data-id="' + table.id + '" class="deleteButton text-red-600 hover:text-red-900"><i class="fa-solid fa-trash"></i></button>' +
                    '</td>'
                );
                tableBody.append(row);
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}
// delete table
function deleteTable(){

    var tableId = $(this).data('id');
    $.ajax({
        url: '/api/table/delete/' + tableId,
        type: 'DELETE',
        success: function(data) {
            alert("Table deleted successfully")
            getAllTable();
        },
        error: function(error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}


// $("#addButton").click(function() {
//     $("#addFormDiv").toggleClass("hidden");
// });
// $(".closeButtonAdd").click(function() {
//     $("#addFormDiv").toggleClass("hidden");
// });
// $(".closeButtonUpdate").click(function() {
//     $("#updateFormDiv").toggleClass("hidden");
// });
// $(".editButton").click(function() {
//     // Get the table id and table capacity from the button
//     var tableId = $(this).data("id")
//     var tableCapacity = $(this).data("capacity");
//
//     // Set the form action with the table id same as the controller mapping
//     $("#updateForm").attr("action", "/api/table/update" + tableId);
//     // Set the input field value with the table capacity in the HTML input field
//     $("#updateForm input[name='tableCapacity']").val(tableCapacity);
//     // Show the update form using the hidden class and x button
//     $("#updateFormDiv").removeClass("hidden");
// });