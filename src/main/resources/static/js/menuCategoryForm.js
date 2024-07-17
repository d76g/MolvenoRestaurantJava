const url = '/api/menuCategory/';
function init(){
    // call the get all menuCategory method
    getAllMenuCategory();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const menuCategoryId = $(this).data('id');
        console.log(menuCategoryId);
        if (confirm("Are you sure you want to delete this menuCategory?")){
            deleteMenuCategory(menuCategoryId);
        } else {
            return false;
        }
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
    });

    // Event listener for the update table form submission
    $('#updateMenuCategoryForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the menuCategory
        if(confirm("Are you sure you want to update this menu item?")){
            updateMenuCategory();
        } else {
            return false;
        }
    });
}

// get all menuCategory method
function getAllMenuCategory(){
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
