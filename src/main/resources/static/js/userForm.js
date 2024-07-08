const url = '/api/users';
function init(){
    // call the get all user method
    getAllUser();
    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const userId = $(this).data('id');
        if (confirm("Are you sure you want to delete this user?")){
            deleteUser(userId);
            getAllUser();
        } else {
            return false;
        }
    });
    // Event listener for the add button
    $("#addButton").click(function() {
        $("#addFormDiv").toggleClass("hidden");
    });
    // Event listener for the add user form submission
    $('#saveUserForm').on('submit', function (e){
        e.preventDefault();
        saveUser();
    })
    // Event listener for the close button
    $(".closeButtonAdd").click(function() {
        $("#addFormDiv").toggleClass("hidden");
    });
    // Event listener for the close button
    $(document).on('click', '.editButton', function(){
        // get the user id  name ,email and password
        const userId = $(this).data('id');
        const userName = $(this).data('name');
        const userEmail = $(this).data('email');
        const userPassword = $(this).data('password');
         const roleId = $(this).data('role');
        // show the update form
        $("#addFormDiv").toggleClass("hidden");
        // set the new user id,name,email and paasword in the form
        $('#userId').val(userId);
        $('#username').val(userName);
        $('#email').val(userEmail);
        $('#password').val(userPassword);
        $('#role').val(roleId);

    });

}

// get all user method
function getAllUser(){
    $.ajax({
        url: url + '/all',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the user using the data from the server
            $('#userList').DataTable({
                ajax:{
                    url: url + '/all',
                    dataSrc: ''
                },
                // destroy the user before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'userName' },
                    { data: 'email' },
                    {data: 'roleName'},

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button ' +
                                   'data-id="' + row.userId + '" ' +
                                   'data-name="' + row.userName + '" ' +
                                   'data-email="' + row.email + '" ' +
                                   'data-password="' + row.password + '" ' +
                                   'data-role="' + row.roleId + '" ' +
                                   'data-role-name="' + row.roleName + '" ' +
                                   'class="editButton text-indigo-600 hover:text-indigo-900">' +
                                   '<i class="fa-solid fa-pen"></i>' +
                                   '</button> ' +
                                   '<button class="deleteButton text-red-600 hover:text-red-900" ' +
                                   'data-id="' + row.userId + '">' +
                                   '<i class="fa-solid fa-trash"></i>' +
                                   '</button>';
                        }
                    }
                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the user data:", error);
        }
    });
}

// add and update user
function saveUser(){
    const id = $('#userId').val();
    const userName = $('#username').val();
    const userEmail = $('#email').val();
    const userPassword = $('#password').val();
    const userRoleId = $('#role').val();
    const userRole =  $('#role option:selected').text();

       // create a user object
    const user = {
        userId: id,
        userName: userName,
        email: userEmail,
        password: userPassword,
        roleId: userRoleId,
        roleName:userRole
    };
    console.log(user);
    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function(data) {
            $("#addFormDiv").toggleClass("hidden");
            $('#saveUserForm')[0].reset();
            getAllUser();
        },
        error: function(error) {
            console.error("There was an error adding the user:", error);
        }
    });

}
// delete user
function deleteUser(userId){
    $.ajax({
        url: url +'/'+ userId,
        type: 'DELETE',
        success: function (data) {
            getAllUser();
        },
        error: function (error) {
            console.error("There was an error deleting the user:", error);
        }
    });
}
