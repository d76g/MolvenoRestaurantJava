$(document).ready(function() {
    $('#togglePassword').on('click', function() {
        const passwordField = $('#password');
        const passwordFieldType = passwordField.attr('type');
        const icon = $(this).find('i');

        if (passwordFieldType === 'password') {
            passwordField.attr('type', 'text');
            icon.removeClass('fa-eye').addClass('fa-eye-slash'); // Toggle icon
        } else {
            passwordField.attr('type', 'password');
            icon.removeClass('fa-eye-slash').addClass('fa-eye'); // Toggle icon
        }
    });

    // change language
    // Function to switch the language
    function switchLanguage(lang) {
        console.log("Switching language to:", lang);
        Cookies.set('language', lang, { expires: 7 });
        location.reload();
    }

    // Event listener for the language icon
    $('#language-icon').on('click', function() {
        switchLanguage('en');
        $('#language-icon').hide();
        $('#language-icon-zh').show();
    });

    $('#language-icon-zh').on('click', function() {
        switchLanguage('zh');
        $('#language-icon-zh').hide();
        $('#language-icon').show();
    });

    // Check the current language and set the appropriate icon
    const currentLang = Cookies.get('language') || 'en';
    console.log("Current language:", currentLang);
    if (currentLang === 'zh') {
        $('#language-icon-zh').hide();
        $('#language-icon').show();
    } else {
        $('#language-icon').hide();
        $('#language-icon-zh').show();
    }
});