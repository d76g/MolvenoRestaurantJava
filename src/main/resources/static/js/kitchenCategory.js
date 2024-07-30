const url = '/api/category/';
let categoryLocalMessages;


function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    categoryLocalMessages = messages ? JSON.parse(messages) : null
    // call the get all table method
    getAllCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const categoryId = $(this).data('id');
        console.log(categoryId);
        Swal.fire({
                   title:categoryLocalMessages['Are-you-sure'],
                   text: categoryLocalMessages['You-wont-be-able-to-revert-this'],
                   icon: 'warning',
                   showCancelButton: true,
                   confirmButtonColor: '#3085d6',
                   cancelButtonColor: '#d33',
                   confirmButtonText: categoryLocalMessages['Yes-delete-it'],
                     cancelButtonText: categoryLocalMessages['Cancel']
               }).then((result) => {
                   if (result.isConfirmed) {
                       deleteCategory(categoryId);
                   }
    });
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
         $('#saveCategoryForm')[0].reset();
         $('#categoryId').val('0');
    });



}

// get all table method
function getAllCategory(){
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
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
                language:{
                    url: dataTableLanguageUrl
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
            Swal.fire({
                            icon: 'success',
                            title:categoryLocalMessages ['stock-category-save-successfully'],
                            showConfirmButton: false,
                            timer: 1500})

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
function deleteCategory(categoryId){
    $.ajax({
        url: url + 'delete/' + categoryId,
        type: 'DELETE',
        success: function (data) {
            getAllCategory();
        },
        error: function (error) {
            console.error("There was an error deleting the table:", error);
        }
    });
}
