const url = '/api/menu/';
let menu_id;
function init(){
    // call the get all menu method
    getAllMenu();
    getAllMenuCategory();
    getAllSubCategory();
    getAllMealTime();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const menuItemId = $(this).data('id');
        console.log(menuItemId);
        Swal.fire({
                        title: 'Are you sure to delete this menu item?',
                        text: "You won't be able to revert this!",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Yes, delete it!'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            deleteMenuItem(menuItemId);
                        }
                    })
//        if (confirm("Are you sure you want to delete this menuItem?")){
//            deleteMenuItem(menuItemId);
//        } else {
//            return false;
//        }
    });
    // Event listener for the showStock  button
    $(document).on('click', '.showStock', function (){
        menu_id =  $(this).data('id');
//        const menuItemId = $(this).data('id');
//      console.log(menuItemId);
     window.location.href = '/menuItemStock?menuId=' + menu_id + '&menuName=' + $(this).data('name');


    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addMenuItemFormDiv").toggleClass("hidden");
    });
    // Event listener for the add menuitem form submission
    $('#saveMenuForm').on('submit', function (e){
        e.preventDefault();
         $("#addMenuItemFormDiv").toggleClass("hidden");
        addMenuItem();
    })
    // Event listener for the close button
    $(".closeButton").click(function() {
        $("#addMenuItemFormDiv").toggleClass("hidden");
        $('#saveMenuForm')[0].reset();
        $('#menuItemId').val(0);

    });
    // Event listener for the close button
    $(document).on('click', '.editButton', function(){
        // get the menuItem details
        const menuItemId = $(this).data('id');
        const itemName = $(this).data('name');
        const description = $(this).data('description');
        const price = $(this).data('price');
        const image = $(this).data('image');
        const menuCategory = $(this).data('menu-category');
        const subCategory = $(this).data('sub-category');
        const mealTime = $(this).data('meal-time');

        console.log('Edit');
        console.log(menuItemId);
        // show the update form
                $("#addMenuItemFormDiv").toggleClass("hidden");
                // set the new menuItem details
                $('#menuItemId').val(menuItemId);
                $('#menuItemName').val(itemName);
                $('#description').val(description);
                $('#price').val(price);
                $('#image').val(image);
                $('#menuCategory').val(menuCategory);
                $('#subCategory').val(subCategory);
                $('#mealTime').val(mealTime);


    });
    // Event listener for the update menuItem form submission
    $('#updateMenuItemForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the menuItem
        if(confirm("Are you sure you want to update this menu item?")){
            updateMenuItem();
        } else {
            return false;
        }
    });
}

// get all menu method
function getAllMenu() {
    $.ajax({
        url: url + 'all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the menu using the data from the server
            $('#menuList').DataTable({
                ajax: {
                    url: url + 'all',
                    dataSrc: ''
                },
                // destroy the menuItem before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // Add a new column for the showStock button
                    {
                        data: null,
                        render: function(data, type, row) {
                            return `
                                <button class="showStock text-red-600 hover:text-red-900" data-id="${row.menuItem_id}"data-name="${row.item_name}">
                                    <i class="fa-solid fa-carrot"></i>
                                </button>
                            `;
                        }
                    },
                    { data: 'item_name' },
                    { data: 'description' },
                    { data: 'price' },
                    { data: 'image' },
                    { data: 'menuCategoryName' },
                    { data: 'subCategoryName' },
                    { data: 'mealTimeName' },
                    // Action column for edit and delete buttons
                    {
                        data: null,
                        render: function(data, type, row) {
                            return `
                                <a class="editButton text-blue-600 hover:text-blue-900"
                                   data-id="${row.menuItem_id}"
                                   data-name="${row.item_name}"
                                   data-description="${row.description}"
                                   data-price="${row.price}"
                                   data-image="${row.image}"
                                   data-menu-category="${row.menuCategoryId}"
                                   data-sub-category="${row.subCategoryId}"
                                   data-meal-time="${row.mealTimeId}">
                                   <i class="fa-solid fa-pen"></i>
                                </a>
                                <button class="deleteButton text-red-600 hover:text-red-900" data-id="${row.menuItem_id}">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            `;
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the menuItem data:", error);
        }
    });
}
// add menuItem method
function addMenuItem(){
    const menuItem_id = $('#menuItemId').val();
    const itemName = $('#menuItemName').val();
    const description = $('#description').val();
    const price = $('#price').val();
    const image = $('#image').val();
    const menuCategoryId = $('#menuCategory').val();
    const menuCategoryName =  $('#menuCategory option:selected').text();
    const subCategoryId = $('#subCategory').val();
    const subCategoryName =  $('#subCategory option:selected').text();
    const mealTimeId = $('#mealTime').val();
    const mealTimeName =  $('#mealTime option:selected').text();

    console.log(menuItem_id);
    // create a menuItem object
    const menuItem = {
       menuItem_id : menuItem_id,
       item_name : itemName,
       description : description,
       price : price,
       image : image,
       menuCategoryId:menuCategoryId,
       subCategoryId:subCategoryId,
       mealTimeId:mealTimeId,
       menuCategoryName:menuCategoryName,
       subCategoryName:subCategoryName,
       mealTimeName:mealTimeName
    };
    $.ajax({
        url: url + 'add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(menuItem),
        success: function(data) {
            $("#addFormDiv").toggleClass("hidden");
            getAllMenu();
            $('#saveMenuForm')[0].reset();
            $('#menuItemId').val(0);
            Swal.fire({
                            icon: 'success',
                            title: 'Menu Item is saved successfully',
                            showConfirmButton: false,
                            timer: 1500
            });

        },
        error: function(error) {
            console.error("There was an error adding the menuItem:", error);
        }
    });

}


// delete menuITem
function deleteMenuItem(menuItem_id){
    $.ajax({
        url: url + menuItem_id,
        type: 'DELETE',
        success: function (data) {
            getAllMenu();

        },
        error: function (error) {
            console.error("There was an error deleting the menuItem:", error);
        }
    });
}
function getAllMenuCategory(){
    $.ajax({
        url: '/api/menuCategory/all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // list all the data in a select option
            $('#menuCategory').empty();
            $.each(data, function(index, category) {
                $('#menuCategory').append('<option value="' + category.menuCategory_id + '">' + category.menuCategory_name + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the menuCategory data:", error);
        }
    });
}
function getAllSubCategory(){
    $.ajax({
        url: '/api/subCategory/all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // list all the data in a select option
            $('#subCategory').empty();
            $.each(data, function(index, category) {
                $('#subCategory').append('<option value="' + category.subCategory_id + '">' + category.subCategory_name + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the subCategory data:", error);
        }
    });
}

function getAllMealTime(){
    $.ajax({
        url: '/api/mealTime/all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // list all the data in a select option
            $('#mealTime').empty();
            $.each(data, function(index, category) {
                $('#mealTime').append('<option value="' + category.mealTime_id + '">' + category.mealTime_name + '</option>');
            });
        },
        error: function(error) {
            console.error("There was an error fetching the mealTime data:", error);
        }
    });
}



