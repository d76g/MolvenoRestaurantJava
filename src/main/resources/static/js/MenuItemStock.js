const url = '/api/stock';
const url2 = '/api/menu-item-stock/menu/'
let menu_id;
function init(){
    function getQueryParameter(param){
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
    }
    menu_id = getQueryParameter('menuId');
    // call the get all table method
    getAllStock();
    getAllMenuItemStock();
    menu_id = getQueryParameter('menuId');

    console.log(menu_id);
    // Event listener for the close button
        $(".closeButtonAdd").click(function() {
            $("#addSubCategoryFormDiv").toggleClass("hidden");
        });
    $(document).on('click', '.deleteButton', function (){
            const menuItemStockId = $(this).data('id');
            console.log(menuItemStockId);
            if (confirm("Are you sure you want to delete this ingredient?")){
                deleteIngredient(menuItemStockId);
            } else {
                return false;
            }
        });

    // Event listener for the add button
        $("#addButton").click(function() {
            $("#addIngredientFormDiv").toggleClass("hidden");
        })
     // Event listener for the edit button
    $(document).on('click', '.editButton', function (){
            const menuItemStock_id = $(this).data('id');
            const amount = $(this).data('amount');
            $('#menuItemStock_id').val(menuItemStock_id);
            $('#amount').val(amount);
            $('#stockList').val($(this).data('stock'));


            $("#addIngredientFormDiv").toggleClass("hidden");
        });
        // Event listener for the add button
        $("#addButton").click(function() {
            $("#addSubCategoryFormDiv").toggleClass("hidden");
        });

    // Event listener for the add ingredients to menu item
        $('#saveIngredientForm').on('submit', function (e){
            e.preventDefault();
             $("#addIngredientFormDiv").toggleClass("hidden");
                addIngredient();
        })
}

function getQueryParameter(param){
var urlParams = new URLSearchParams(window.location.search);
return urlParams.get(param);
}
function addIngredient()
{
    const menuItemStock_id = $('#menuItemStock_id').val();

    const stock_id = $('#stockList').val();
    const amount = $('#amount').val();

    const ingredients = {
           id: menuItemStock_id,
           menuId : menu_id,
           kitchenStockId : stock_id,
           amount : amount,
    }

     console.log(ingredients);

     $.ajax({
             url: '/api/menuItemStock',
             type: 'POST',
             contentType: 'application/json',
             data: JSON.stringify(ingredients),
             success: function(data) {
                 $("#addFormDiv").toggleClass("hidden");
                 $('#saveIngredientForm')[0].reset();
                 getAllMenuItemStock();
             },
             error: function(error) {
                 console.error("There was an error adding ingredients to the menuItem:", error);
             }
         });
}

function deleteIngredient(menuItemStockId){
    $.ajax({
        url: '/api/menu-item-stock/delete/' + menuItemStockId,
        type: 'DELETE',
        success: function (data) {
            getAllMenuItemStock();
        },
        error: function (error) {
            console.error("There was an error deleting the subCategory:", error);
        }
    });
}

function getAllMenuItemStock(){
$.ajax({
        url: url2 + menu_id,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#menuItemStockList').DataTable({
                ajax:{
                    url: url2 + menu_id,
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form

                    { data: 'kitchenStock.description' },
                    { data: 'amount' },

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.id + '" data-category="' + row.amount+ '" data-stock="' + row.kitchenStock.id+ '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.id + '" ><i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the subcategory data:", error);
        }
    });

}


function getAllStock(){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // list all the data in a select option
            $('#stockList').empty();
            $.each(data, function(index, stock) {
                $('#stockList').append('<option value="' + stock.id + '">' + stock.description + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the stock data:", error);
        }
    });



}
