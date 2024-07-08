const url = '/api/stock';
function init(){
    // call the get all table method
    getAllStock();
    getAllKitchenCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const tableId = $(this).data('id');
        console.log(tableId);
        if (confirm("Are you sure you want to delete this item from stock?")){
            deleteStock(tableId);
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
        const data = {
            id: $(this).data('id'),
            description: $(this).data('description'),
            amount: $(this).data('amount'),
            unit: $(this).data('unit'),
            brand: $(this).data('brand'),
            supplier: $(this).data('supplier'),
            articleNumber: $(this).data('article-number'),
            price: $(this).data('price'),
            tax: $(this).data('tax'),
            pricePerUnit: $(this).data('price-per-unit'),
            stock: $(this).data('stock'),
            stockValue: $(this).data('stock-value'),
            limit: $(this).data('limit'),
            category: $(this).data('category'),
        };
        let queryParameters = $.param(data);
        window.location.href = '/chef/stock/form?' + queryParameters;
    });

    // Event listener for the update table form submission
    $('#stockAddForm').on('submit', function(event) {
        event.preventDefault();
        saveStock();
      });
}
// read the query parameters and assign them to the form
function readQueryParameters(){
    // remove the %20 from the query parameters
    const queryParameters = decodeURIComponent(window.location.search).substring(1).split('&');
    if (queryParameters.length > 0){
        queryParameters.forEach(function(queryParameter) {
            const data = queryParameter.split('=');
            const key = data[0];
            const value = data[1];
            $('#' + key).val(value);
            console.log(key + " " + value)
        });
    }

}
// get all table method
function getAllStock(){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#stockList').DataTable({
                ajax:{
                    url:url,
                    dataSrc: ''
                },
                sWidth: "70%",
                bAutoWidth: false,
                autoWidth: false,
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'description' },
                    { data: 'amount' },
                    { data: 'unit' },
                    { data: 'stock' },
                    { data: 'stockValue' },
                    { data: 'price' },
                    { data: 'pricePerUnit' },
                    { data: 'category.categoryName'},
                    { data: 'brand' },
                    { data: 'supplier' },
                    { data: 'articleNumber' },
                    { data: 'tax' },
                    { data: 'limit' },
                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<a class="editButton text-blue-600 hover:text-blue-900"' +
                                '" data-id = "' + row.id +
                                '" data-description = "' + row.description +
                                '" data-amount = "' + row.amount +
                                '" data-unit = "' + row.unit +
                                '" data-brand = "' + row.brand +
                                '" data-supplier = "' + row.supplier +
                                '" data-article-number = "' + row.articleNumber +
                                '" data-price = "' + row.price +
                                '" data-tax = "' + row.tax +
                                '" data-price-per-Unit = "' + row.pricePerUnit +
                                '" data-stock = "' + row.stock +
                                '" data-stock-value = "' + row.stockValue +
                                '" data-limit = "' + row.limit + '"' +
                                '" data-category = "' + row.category.id + '"' +
                                '><i class="fa-solid fa-pen"></i></a> ' +
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
function saveStock() {
    const id = $('#id').val();
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
    const category = $('#category option:selected').text();

    // Verify that categoryId is not null or empty
    if (!categoryId) {
        alert("Please select a category.");
        return;
    }

    // Create a Stock object
    const stockData = {
        id: id,
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
        limit: limit,
        category: {
            id: categoryId,
            categoryName: category
        },
    };

    console.log(stockData);

    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(stockData),
        success: function(data) {
            $('#stockAddForm')[0].reset();
            alert("Stock Item added successfully");
            // redirect to the table page
            window.location.href = '/chef/stocks';
            // getAllStock();
        },
        error: function(error) {
            console.error("There was an error adding the stock item:", error);
        }
    });
}
// delete table
function deleteStock(stockId){
    $.ajax({
        url: url  +'/'+ stockId,
        type: 'DELETE',
        success: function (data) {
            getAllStock ();
            console.log("Stock Item deleted successfully")
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
            $.each(data, function(index, category) {
                $('#category').append('<option value="' + category.category_id + '">' + category.categoryName + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}