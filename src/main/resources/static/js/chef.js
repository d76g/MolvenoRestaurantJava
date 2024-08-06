let api = '/api/analysis/'

let chefLocalizationMessages;
function init(){
    const lang =  Cookies.get("language") || "en";
    const messages = localStorage.getItem(`messages_${lang}`);
    chefLocalizationMessages = JSON.parse(messages);
    getItemsBelowLimit();
    getOrders();
    getMenu();
    getFamousMenu();
}
const stockItems = [];
function getItemsBelowLimit(){
    $.ajax({
        url: "/api/stock/checkLimit",
        type: "GET",
        success: function(data){
            // show alert for each item

            data.forEach(item => {
                stockItems.push(item.description);
            });
            showAlerts().then(r => console.log("Alerts shown"));

            async function showAlerts() {
                for (const item of stockItems) {
                    await Swal.fire({
                        position: 'top-end',
                        icon: 'warning',
                        title: chefLocalizationMessages['Stock-Alert'],
                        text: item + ' ' +chefLocalizationMessages['is-low-on-stock'],
                        confirmButtonText: chefLocalizationMessages['ok'],
                        toast: true,
                        timer: 4000
                    });
                }
            }
        }
    })
}
function getOrders(){
    $.ajax({
        url: api + 'orders',
        type: 'GET',
        success: function(data){
            $('#orders').text(data)
        }
    })
}
function getMenu(){
    $.ajax({
        url: api + 'menu',
        type: 'GET',
        success: function(data){
            $('#menu').text(data)
        }
    })
}
function getFamousMenu(){
    $.ajax({
        url: api + 'menu/famous',
        type: 'GET',
        success: function(data){
            $('#famousMenu').text(data)
        }
    })
}