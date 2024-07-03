const url = '/api/stock';
function init(){
    // call the get all table method
    getAllStock();
    getAllKitchenCategory();
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
    $('#stockAddForm').on('submit', function(event) {
        event.preventDefault();
        saveStock();
      });
}

// get all table method
function getAllStock(){
    $.ajax({
        url: '/api/stocks',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#stockList').DataTable({
                ajax:{
                    url:'/api/stocks',
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'description' },
                    { data: 'amount' },
                    { data: 'unit' },
                    { data: 'brand' },
                    { data: 'supplier' },
                    { data: 'articleNumber' },
                    { data: 'price' },
                    { data: 'tax' },
                    { data: 'pricePerUnit' },
                    { data: 'stock' },
                    { data: 'stockValue' },
                    { data: 'limit' },
                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.id + '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.id + '" ><i class="fa-solid fa-trash"></i></button>';
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
function saveStock(){
   const description = $('#description').val();
   const amount = $('#amount').val();
   const unit = $('#unit').val();
   const brand = $('#brand').val();
   const supplier = $('#supplier').val();
   const articleNumber = $('#articleNumber').val();
   const price = $('#price').val();
   const tax = $('#tax').val();
   const pricePerUnit = $('#pricePerUnit').val();
   const stock = $('#stock').val();
   const stockValue = $('#stockValue').val();
   const limit = $('#limit').val();
   const categoryId = $('#category').val();
    // create a Stock object
    const stockData = {
    description: description,
    amount: amount,
    unit: unit,
    brand: brand,
    supplier: supplier,
    articleNumber: articleNumber,
    price: price,
    tax: tax,
    pricePerUnit: pricePerUnit,
    stock: stock,
    stockValue: stockValue,
    limit: limit
    };
    console.log(stockData);
    $.ajax({
        url: url ,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(stockData),
        success: function(data) {
            $('#stockAddForm')[0].reset();
            alert("Stock Item added successfully");
//            getAllStock();
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
            getAllTable();
        },
        error: function (error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}
function getAllKitchenCategory(){
    $.ajax({
        url: '/api/category/list',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // list all the data in a select option
            $('#category').empty();
            $('#category').append('<option value="">Select Category</option>');
            $.each(data, function(index, category) {
                $('#category').append('<option value="' + category.category_id + '">' + category.categoryName + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}