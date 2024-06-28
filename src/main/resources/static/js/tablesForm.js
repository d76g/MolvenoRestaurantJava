    $(document).ready(function() {
        new DataTable("#tableList");
        $(".deleteButton").click(function(event) {
            if (!confirm("Are you sure you want to delete this table?")) {
                event.preventDefault();
            }
        });
        $("#addButton").click(function() {
            $("#addFormDiv").toggleClass("hidden");
        });
        $(".closeButtonAdd").click(function() {
            $("#addFormDiv").toggleClass("hidden");
        });
        $(".closeButtonUpdate").click(function() {
            $("#updateFormDiv").toggleClass("hidden");
        });
        $(".editButton").click(function() {
        // Get the table id and table capacity from the button
            var tableId = $(this).data("id")
            var tableCapacity = $(this).data("capacity");

        // Set the form action with the table id same as the controller mapping
        $("#updateForm").attr("action", "/admin/tables/updateTable/" + tableId);
          // Set the input field value with the table capacity in the HTML input field
        $("#updateForm input[name='tableCapacity']").val(tableCapacity);
        // Show the update form using the hidden class and x button
        $("#updateFormDiv").removeClass("hidden");
        });
    });