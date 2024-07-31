const url = '/api/menuCategory/';
let menuCategoryMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    menuCategoryMessages = messages ? JSON.parse(messages) : null;
    // call the get all menuCategory method
    getAllMenuCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const menuCategoryId = $(this).data('id');
        console.log(menuCategoryId);
        Swal.fire({
            title: menuCategoryMessages['Are-you-sure-delete'],
            text: menuCategoryMessages['You-wont-be-able-to-revert-this'],
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: menuCategoryMessages['Yes-delete-it'],
            cancelButtonText: menuCategoryMessages['Cancel']
        }).then((result) => {
            if (result.isConfirmed) {
                deleteMenuCategory(menuCategoryId);
            }
        })
    });
    $(document).on('click', '.editButton', function (){
        const menuCategoryId = $(this).data('id');
        const categoryName = $(this).data('category');
        $('#menuCategoryId').val(menuCategoryId);
        $('#menuCategoryName').val(categoryName);
        $("#addMenuCategoryFormDiv").toggleClass("hidden");
    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addMenuCategoryFormDiv").toggleClass("hidden");
    });
    // Event listener for the add menuCategory form submission
    $('#saveMenuCategoryForm').on('submit', function (e){
        e.preventDefault();
        addMenuCategory();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addMenuCategoryFormDiv").toggleClass("hidden");
        $('#saveMenuCategoryForm')[0].reset();
        $('#menuCategoryId').val('0');
    });

    // Event listener for the update table form submission
    $('#updateMenuCategoryForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the menuCategory
        updateMenuCategory();
    });
}

// get all menuCategory method
function getAllMenuCategory(){
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
    $.ajax({
        url: url + 'all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#menuCategoryList').DataTable({
                ajax:{
                    url: url + 'all',
                    dataSrc: ''
                },
                language:{
                    url: dataTableLanguageUrl
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form

                     { data: 'menuCategory_name' },

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.menuCategory_id + '" data-category="' + row.menuCategory_name+ '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.menuCategory_id + '" ><i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the menuCategory data:", error);
        }
    });
}

// add menuCategory
function addMenuCategory(){
    const menuCategoryId = $('#menuCategoryId').val();
    const menuCategoryName = $('#menuCategoryName').val();
    // create a menuCategory object
    const menuCategory = {
        menuCategory_id: menuCategoryId,
        menuCategory_name: menuCategoryName
    };
    $.ajax({
        url: url + 'add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(menuCategory),
        success: function(data) {
            $("#addMenuCategoryFormDiv").toggleClass("hidden");
            $('#saveMenuCategoryForm')[0].reset();
            $('#menuCategoryId').val('0');
            getAllMenuCategory();
        },
        error: function(error) {
            console.error("There was an error adding the menuCategory:", error);
        }
    });

}
// delete menuCategory
function deleteMenuCategory(menuCategoryId){
    $.ajax({
        url: url + menuCategoryId,
        type: 'DELETE',
        success: function (data) {
            getAllMenuCategory();
        },
        error: function (error) {
            console.error("There was an error deleting the menuCategory:", error);
        }
    });
}
