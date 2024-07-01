    $(document).ready(function() {
        new DataTable("#menuList");
        $(".deleteButton").click(function(event) {
            if (!confirm("Are you sure you want to delete this menuItem?")) {
                event.preventDefault();
            }
        });
        $("#addButton").click(function() {
            $("#addMenuDiv").toggleClass("hidden");
        });
        $(".closeButtonAdd").click(function() {
            $("#addMenuDiv").toggleClass("hidden");
        });
        $(".closeButtonUpdate").click(function() {
            $("#updateMenuItemDiv").toggleClass("hidden");
        });
        $(".editButton").click(function() {
        // Get the table id and table capacity from the button
            var menuItemId = $(this).data("id")
           // var tableCapacity = $(this).data("capacity");

        // Set the form action with the table id same as the controller mapping
        $("#updateForm").attr("action", "/chef/menu/updateMenu/" + menuItemId);
          // Set the input field value with the table capacity in the HTML input field
        //$("#updateForm input[name='tableCapacity']").val(tableCapacity);
        // Show the update form using the hidden class and x button
        $("#updateMenuDiv").removeClass("hidden");
        });
    });