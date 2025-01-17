const url = '/api/subCategory/';
let subCategoryMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    subCategoryMessages = messages ? JSON.parse(messages) : null;
    // call the get all subCategory method
    getAllSubCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const subCategoryId = $(this).data('id');
        console.log(subCategoryId);

        Swal.fire({
            title: subCategoryMessages['Are-you-sure-delete'],
            text: subCategoryMessages['You-wont-be-able-to-revert-this'],
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: subCategoryMessages['Yes-delete-it'],
            cancelButtonText: subCategoryMessages['Cancel']
        }).then((result) => {
            if (result.isConfirmed) {
                deleteSubCategory(subCategoryId);
            }
        })
    });
    $(document).on('click', '.editButton', function (){
        const subCategoryId = $(this).data('id');
        const categoryName = $(this).data('category');
        $('#subCategoryId').val(subCategoryId);
        $('#subCategoryName').val(categoryName);
        $("#addSubCategoryFormDiv").toggleClass("hidden");
    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addSubCategoryFormDiv").toggleClass("hidden");
    });
    // Event listener for the add subcategory form submission
    $('#saveSubCategoryForm').on('submit', function (e){
        e.preventDefault();
        addSubCategory();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addSubCategoryFormDiv").toggleClass("hidden");
        $('#saveSubCategoryForm')[0].reset();
        $('#subCategoryId').val('0');
    });

    // Event listener for the update table form submission
    $('#updateSubCategoryForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the subcategory
       updateSubCategory();
    });
}

// get all subcategory method
function getAllSubCategory(){
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
            $('#subCategoryList').DataTable({
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

                    { data: 'subCategory_name' },

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.subCategory_id + '" data-category="' + row.subCategory_name+ '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.subCategory_id + '" ><i class="fa-solid fa-trash"></i></button>';
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

// add subcategory
function addSubCategory(){
    const subCategoryId = $('#subCategoryId').val();
    const subCategoryName = $('#subCategoryName').val();
    // create a subcategory object
    const subCategory = {
        subCategory_id: subCategoryId,
        subCategory_name: subCategoryName
    };
    $.ajax({
        url: url + 'add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(subCategory),
        success: function(data) {
            $("#addSubCategoryFormDiv").toggleClass("hidden");
            $('#saveSubCategoryForm')[0].reset();
            $('#subCategoryId').val('0');
            getAllSubCategory();
        },
        error: function(error) {
            console.error("There was an error adding the subcategory:", error);
        }
    });

}
// delete subcategory
function deleteSubCategory(subCategoryId){
    $.ajax({
        url: url + subCategoryId,
        type: 'DELETE',
        success: function (data) {
            getAllSubCategory();
        },
        error: function (error) {
            console.error("There was an error deleting the subCategory:", error);
        }
    });
}
