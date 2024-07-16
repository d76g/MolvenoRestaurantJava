const url = '/api/stock';
function init(){
    // call the get all table method
    getAllStock();
}
function getAllStock(){
    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            // create the table using the data from the server
            $('#stockList').DataTable({
                ajax:{
                    url:url,
                    dataSrc: ''
                },
                sWidth: "70%",
                bAutoWidth: false,
                autoWidth: false,
                // destroy the table before creating a new one
                "bDestroy": true,
                // define the columns (use the data key to map the data to the columns) and the data to be displayed
                columns: [
                    // number of columns depends on the data of your model and the name of the id field in the form
                    { data: 'description' },
                    { data: 'amount' },
                    { data: 'stock' },


                ],
            });
        },
        error: function(error) {
            console.error("There was an error fetching the table data:", error);
        }
    });
}