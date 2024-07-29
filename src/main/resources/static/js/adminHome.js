let api = '/api/analysis/'

function init(){
    getRevenue()
    getGuests();
    getOrders();
    getTodayReservations();
    getReservation();
    getMenu();
    getFamousMenu();
    getTables();
}

function getRevenue(){
    $.ajax({
        url: api + 'revenue',
        type: 'GET',
        success: function(data){
            $('#revenue').text(data)
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

function getTodayReservations(){
    $.ajax({
        url: api + 'reservations/today',
        type: 'GET',
        success: function(data){
            $('#todayReservations').text(data)
        }
    })
}

function getReservation(){
    $.ajax({
        url: api + 'reservations',
        type: 'GET',
        success: function(data){
            $('#reservations').text(data)
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

function getGuests(){
    $.ajax({
        url: api + 'guests',
        type: 'GET',
        success: function(data){
            $('#guests').text(data)
        }
    })
}

function getTables(){
    $.ajax({
        url: api + 'tables',
        type: 'GET',
        success: function(data){
            $('#tables').text(data)
        }
    })
}