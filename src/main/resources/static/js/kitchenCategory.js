const url = '/api/category/';
function init(){
    // call the get all table method
    getAllCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const tableId = $(this).data('id');
        console.log(tableId);
        if (confirm("Are you sure you want to delete this category?")){
            deleteTable(tableId);
        } else {
            return false;
        }
    });
    // Event listener for the show

    $(document).on('click', '.editButton', function (){
        const categoryId = $(this).data('id');
        const categoryName = $(this).data('category');
        $('#categoryId').val(categoryId);
        $('#categoryName').val(categoryName);
        $("#addFormDiv").toggleClass("hidden");
    });
    $('#showForm').click(function (){
        $("#addFormDiv").toggleClass("hidden");
    });
    // Event listener for the add table form submission
    $('#saveCategoryForm').on('submit', function (e){
        e.preventDefault();
        addCategory();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addFormDiv").toggleClass("hidden");
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
function getAllCategory(){
    $.ajax({
        url: url + 'list',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#categoryList').DataTable({
                ajax:{
                    url: url + 'list',
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'categoryName' },
                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            console.log('row', row.categoryName)
                            return '<button  class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.category_id + '" data-category="' + row.categoryName + '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.category_id + '" ><i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}

// add table method
function addCategory(){
    const categoryName = $('#categoryName').val();
    const categoryId = $('#categoryId').val();
    console.log(categoryName, categoryId)
    // create a category object
    const category = {
        category_id: categoryId,
        categoryName: categoryName,
    };
    $.ajax({
        url: url + 'save',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(category),
        success: function(data) {
            alert("Category saved successfully")
            $("#addFormDiv").toggleClass("hidden");
            $('#saveCategoryForm')[0].reset();
            $('#categoryId').val('0');
            getAllCategory();
        },
        error: function(error) {
            console.error("There was an error adding the table:", error);
        }
    });

}
// delete table
function deleteTable(tableId){
    $.ajax({
        url: url + 'delete/' + tableId,
        type: 'DELETE',
        success: function (data) {
            getAllCategory();
        },
        error: function (error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}
