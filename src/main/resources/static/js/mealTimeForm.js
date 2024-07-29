const url = '/api/mealTime/';
function init(){
    // call the get allMealTime method
    getAllMealTime();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const mealTimeId = $(this).data('id');
        console.log(mealTimeId);
        if (confirm("Are you sure you want to delete this mealTime?")){
            deleteMealTime(mealTimeId);
        } else {
            return false;
        }
    });
    $(document).on('click', '.editButton', function (){
        const mealTimeId = $(this).data('id');
        const categoryName = $(this).data('category');
        $('#mealTimeId').val(mealTimeId);
        $('#mealTimeName').val(categoryName);
        $("#addMealTimeFormDiv").toggleClass("hidden");
    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addMealTimeFormDiv").toggleClass("hidden");
    });
    // Event listener for the add table form submission
    $('#saveMealTimeForm').on('submit', function (e){
        e.preventDefault();
        addMealTime();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addMealTimeFormDiv").toggleClass("hidden");
        $('#saveMealTimeForm')[0].reset();
        $('#mealTimeId').val('0');
    });

    // Event listener for the update table form submission
    $('#updateMealTimeForm').on('submit', function(event) {
        event.preventDefault();
        // confirm if the user wants to update the meal time
        if(confirm("Are you sure you want to update this meal time?")){
            updateMealTime();
        } else {
            return false;
        }
    });
}

// get all mealtime method
function getAllMealTime(){
    $.ajax({
        url: url + 'all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#mealTimeList').DataTable({
                ajax:{
                    url: url + 'all',
                    dataSrc: ''
                },
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form

                    { data: 'mealTime_name' },

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button class="editButton text-indigo-600 hover:text-indigo-900" data-id="' + row.mealTime_id + '" data-category="' + row.mealTime_name+ '"><i class="fa-solid fa-pen"></i></button> ' +
                                '<button class="deleteButton text-red-600 hover:text-red-900" data-id="' + row.mealTime_id + '" ><i class="fa-solid fa-trash"></i></button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the mealtime data:", error);
        }
    });
}

// add mealtime
function addMealTime(){
    const mealTimeId = $('#mealTimeId').val();
    const mealTimeName = $('#mealTimeName').val();
    // create a mealtime object
    const mealTime = {
        mealTime_id: mealTimeId,
        mealTime_name: mealTimeName
    };
    $.ajax({
        url: url + 'add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(mealTime),
        success: function(data) {
            $("#addMealTimeFormDiv").toggleClass("hidden");
            $('#saveMealTimeForm')[0].reset();
            $('#mealTimeId').val('0');
            getAllMealTime();
        },
        error: function(error) {
            console.error("There was an error adding the mealtime:", error);
        }
    });

}
// delete mealtime
function deleteMealTime(mealTimeId){
    $.ajax({
        url: url + mealTimeId,
        type: 'DELETE',
        success: function (data) {
            getAllMealTime();
        },
        error: function (error) {
            console.error("There was an error deleting the mealtime:", error);
        }
    });
}
