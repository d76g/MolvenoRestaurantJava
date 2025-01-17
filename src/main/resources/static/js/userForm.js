const url = '/api/users';
let userMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    userMessages = messages ? JSON.parse(messages) : null;
    // call the get all user method
    getAllUser();

    // Event listener for the delete button
    $(document).on('click', '.deleteButton', function (){
        const userId = $(this).data('id');
        Swal.fire({
            title: userMessages['Are-you-sure'],
            text: userMessages['You-wont-be-able-to-revert-this'],
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: userMessages['Yes-delete-it'],
            cancelButtonText: userMessages['Cancel']
        }).then((result) => {
            if (result.isConfirmed) {
                deleteUser(userId);
            }
        })
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
         $('#saveUserForm')[0].reset();
         $('#userId').val(0);
         $('#password').attr('type','password');
         $('label[for="password"]').css('display', 'block');
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

        // set the new user id,username,first name,lastname,email,password and role in the form
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
    const urlParams = new URLSearchParams(window.location.search);
    let currentLang = urlParams.get('lang') || Cookies.get('language') || 'en';
    let dataTableLanguageUrl = '/i18n/en-GB.json'; // default to English

    if (currentLang === 'zh') {
        dataTableLanguageUrl = '/i18n/zh-HANT.json';
    }
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
                language:{
                    url: dataTableLanguageUrl
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
            title: userMessages['Password-error'],
            text: userMessages['Password-message'],
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
            $('#password').attr('type','password');
            $('label[for="password"]').css('display', 'block');
            $('#saveUserForm')[0].reset();
            getAllUser();
            Swal.fire({
                icon: 'success',
                title: userMessages['User-saved'],
                toast: true,
                position: 'top-end',
                timer: 5000,
            });
        },
        error: function(error) {
            Swal.fire({
                icon: 'error',
                title: userMessages['Opps'],
                text: userMessages[error.responseJSON.message],
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
           getAllUser();
        },
        error: function (error) {
            console.error("There was an error deleting the user:", error);
            alert('there was an error deleting the user.');
        }
    });
}
