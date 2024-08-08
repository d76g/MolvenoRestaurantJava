const url = '/api/stock';
const url2 = '/api/menu-item-stock/menu/'
let menu_id;
let menuName;
let menuItemMessages;
function init(){
    function getQueryParameter(param){
    var urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
    }
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    menuItemMessages = JSON.parse(messages);
    menu_id = getQueryParameter('menuId');
    // call the get all table method
    getAllStock();
    getAllMenuItemStock();
    menu_id = getQueryParameter('menuId');
    menuName = getQueryParameter('menuName');
    $('#menuName').text(menuName);
    console.log(menu_id);
    // Event listener for the close button
        $(".closeButtonAdd").click(function() {
            $("#addIngredientFormDiv").toggleClass("hidden");
            $('#saveIngredientForm')[0].reset();
            $('#menuItemStock_id').val(0);
        });
    $(document).on('click', '.deleteButton', function (){
            const menuItemStockId = $(this).data('id');
            Swal.fire({
                title:menuItemMessages['Are-you-sure-delete'],
                showCancelButton: true,
                confirmButtonText: menuItemMessages['Yes-delete-it'],
                cancelButtonText: menuItemMessages['Cancel']
            }).then((result) => {
                if (result.isConfirmed) {
                    deleteIngredient(menuItemStockId);
                }
            });
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
                  $('#menuItemStock_id').val('0');
                 getAllMenuItemStock();
                 Swal.fire({
                        title: menuItemMessages['stock-saved-successfully'],
                        icon: 'success',
                        confirmButtonText: menuItemMessages['ok']
                 })
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
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
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
                language:{
                    // change the default language of the data table
                    url: dataTableLanguageUrl,
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form

                    { data: null,
                        render: function(data, type, row) {
                            return data.kitchenStock.description + ' / ' + data.kitchenStock.unit;
                        }
                    },
                    { data: 'amount' },

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.id + '" data-amount="' + row.amount+ '" data-stock="' + row.kitchenStock.id+ '"><i class="fa-solid fa-pen"></i></button> ' +
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
                $('#stockList').append('<option value="' + stock.id + '">' + stock.description +' / '+ stock.unit + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the stock data:", error);
        }
    });



}
