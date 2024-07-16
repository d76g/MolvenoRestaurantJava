function init(){
    console.log("Chef.js loaded")
    getItemsBelowLimit();
}
const stockItems = [];
function getItemsBelowLimit(){
    console.log("getItemsBelowLimit called")
    $.ajax({
        url: "/api/stock/checkLimit",
        type: "GET",
        success: function(data){
            // show alert for each item

            data.forEach(item => {
                stockItems.push(item.description);
            });
            console.log(stockItems);
            showAlerts().then(r =>
                console.log("Alerts shown"));
            async function showAlerts() {
                for (const item of stockItems) {
                    await Swal.fire({
                        position: 'top-end',
                        icon: 'warning',
                        title: 'Stock Alert',
                        text: item + " is below the limit",
                        confirmButtonText: 'OK',
                        toast: true,
                        timer: 4000
                    });
                }
            }


        }
    })
}