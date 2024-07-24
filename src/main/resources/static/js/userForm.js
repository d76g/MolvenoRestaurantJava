const url = '/api/users';
function init(){
    // call the get all user method
    getAllUser();

    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const userId = $(this).data('id');
        if (confirm("Are you sure you want to delete this user?")){
            deleteUser(userId);
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

    // Event listener for the edit button

    $(document).on('click', '.editButton', function(){
        // get the user id,username,  firstname ,lastname ,email and password
        const userId = $(this).data('id');
        const userName = $(this).data('user-name');
        const firstName = $(this).data('first-name');
        const lastName = $(this).data('last-name');
        const userEmail = $(this).data('email');
        const userPassword = $(this).data('password');
        const roleId = $(this).data('role');

        // show the update form
        $("#addFormDiv").toggleClass("hidden");

        // set the new user id,username,first name,lastname,email,paasword and role in the form
        $('#userId').val(userId);
        $('#username').val(userName);
        $('#firstname').val(firstName);
        $('#lastname').val(lastName);
        $('#email').val(userEmail);
        $('#role').val(roleId);

        // hide password filed
        $('#password').attr('type','hidden');
        $('label[for="password"]').css('display', 'none');

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
                    {data: 'userName'},
                    { data: 'firstName' },
                    { data: 'lastName' },
                    { data: 'email' },
                    {data: 'roleName'},

                    // action column for delete and update
                    {
                        data: 'action',
                        render: function(data, type, row) {
                            return '<button ' +
                                   'data-id="' + row.userId + '" ' +
                                   'data-user-name="' + row.userName + '" ' +
                                   'data-first-name="' + row.firstName + '" ' +
                                   'data-last-name="' + row.lastName + '" ' +
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
function validatePassword(password) {
    // Regular expression for validating a complex password
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordPattern.test(password);
}
// add and update user
function saveUser(){
    const id = $('#userId').val();
    const userName = $('#username').val();
    const firstName = $('#firstname').val();
    const lastName = $('#lastname').val();
    const userEmail = $('#email').val();
    const userPassword = $('#password').val();
    const userRoleId = $('#role').val();
    const userRole =  $('#role option:selected').text()
    console.log(userPassword)
    // Check if the password is complex enough
    if (!validatePassword(userPassword) && userPassword !== '') {
        Swal.fire({
            icon: 'error',
            title: 'Password Validation Error',
            text: 'Password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character.',
            toast: true,
            position: 'top-end',
            timer: 5000,
        });
        return;
    }

       // create a user object
    const user = {
        userId: id,
        userName: userName,
        firstName: firstName,
        lastName: lastName,
        email: userEmail,
        password: userPassword,
        roleId: userRoleId,
        roleName:userRole
    };

    $.ajax({
        url: url,
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(user),
        success: function(data) {

            $("#addFormDiv").toggleClass("hidden");

            $('#saveUserForm')[0].reset();

            getAllUser();
             Swal.fire({
                icon: 'success',
                title: 'User Saved',
                text: 'User has been saved successfully!',
                toast: true,
                position: 'top-end',
                timer: 5000,
             });
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: 'User Save Error',
                text: error.responseJSON.message,
                toast: true,
                position: 'top-end',
                timer: 5000,
            });
        }
    });

}
// delete user
function deleteUser(userId){
    $.ajax({
        url: url +'/'+ userId,
        type: 'DELETE',
        success: function (data) {
        alert('User has been deleted successfully!');
            getAllUser();
        },
        error: function (error) {
            console.error("There was an error deleting the user:", error);
            alert('there was an error deleting the user.');
        }
    });
}
