let menuDiv = $("#menuListContainer");
// define an Order object
const Order = {
    id: 0,
    reservationId: 0,
    orderItems : [
        // add order items here
    ]
}

// Function to add an order item to the order
function addOrderItem(menuItemId, quantity, price, itemName, image){
    // Check if the item is already in the order
    const existingItem = Order.orderItems.find(item => item.menuId === menuItemId);
    if(existingItem){
        existingItem.quantity += quantity;
    } else {
        const orderItem = {
            menuId: menuItemId,
            quantity: quantity,
            itemPrice: price,
            menuName: itemName,
            image: image
        };
        Order.orderItems.push(orderItem);
    }
}


// define a function to remove an order item from the order
function removeOrderItem(menuItemId){

    Order.orderItems = Order.orderItems.filter(item => item.menuId !== Number(menuItemId));
}



function initMenuList(){
    console.log("init")
    getMenuItems();
    // remove an item from the order
    $("#OrderMenuList").on("click", ".removeItem", function(){
        const menuItemId = $(this).attr("data-id");
        console.log(menuItemId);
        $(this).closest('.order-item').remove(); // Remove the closest order item element
        removeOrderItem(menuItemId);
        displayOrderItems();
        console.log(Order.orderItems);
        // remove item from display
        updateOrderTotal();
    });

    $("#closeMenuList").on("click", function(){
        $('#menuList').toggleClass("hidden");
    });


}

//TODO:: List of all the menu items
function getMenuItems(){
    // get the current time
    const currentTime = new Date();
    const hours = currentTime.getHours();
    console.log(hours);
    let mealTime = "Breakfast";
    if (hours >= 12 && hours < 16){
        mealTime = "Lunch";
    } else if (hours >= 16) {
        mealTime = "Dinner";
    }
    $.ajax(
        {
            url: "/api/menu/" + mealTime,
            type: "GET",
            contentType: "application/json",
            success: function(data){
                displayMenu(data)
            },
            error: function(error){
                console.log(error)
            }
        }
    )
}
function displayMenu(data){

    menuDiv.empty();
    for (const menu of data) {
        const card = menuCard(menu);
        menuDiv.append(card);
    }
}

//TODO:: Add menu item to the order
menuDiv.on("submit", "#addItemToOrder", function(event){
    event.preventDefault();
    const menuItemId = parseInt($(this).find("#menuItemId").val());
    const quantity = parseInt($(this).find("#quantity").val());
    const price = parseFloat($(this).find("#menuItemPrice").val());
    const itemName = $(this).find("#menuItemName").val();
    const image = $(this).find("#menuItemImage").val();
    if (quantity < 1){
        alert("Quantity must be greater than 0");
        return;
    }
    addOrderItem(menuItemId, quantity, price, itemName, image);
    displayOrderItems();
    updateOrderTotal();
});
// Function to display order items
function displayOrderItems() {
    const orderDiv = $("#OrderMenuList");
    orderDiv.empty();
    for (const orderItem of Order.orderItems) {
        const orderItemDiv = OrderMenuItem(orderItem.menuName, orderItem.itemPrice, orderItem.quantity, orderItem.image, orderItem.menuId);
        orderDiv.append(orderItemDiv);
    }
}
// Function to update the total order price
function updateOrderTotal() {
    const total = Order.orderItems.reduce((sum, item) => sum + (item.itemPrice * item.quantity), 0);
    $("#totalPrice").text(`${total}`);
}
function menuCard(menu){
    return `
    <div class="card" style="width: 18rem;">
        <div class="w-52 h-56 bg-white rounded-md drop-shadow-sm">
              <div class="h-1/2">
                <img src="${menu.image}" alt="food" class="w-full h-full object-cover rounded-md p-1">
              </div>
              <div class="space-y-1">
                <div class="px-2">
                  <h1 class="text-md font-bold">${menu.item_name}</h1>
                  <p class="text-xs truncate h-5"">${menu.description}</p>
                  <p class="text-sm font-bold">${menu.price} &#165</p>
                </div>
                <form id="addItemToOrder" class="flex flex-cols justify-evenly">
                  <!--Display counter-->
                    <input type="hidden" value="${menu.menuItem_id}" id="menuItemId">
                    <input type="hidden" value="${menu.item_name}" id="menuItemName">
                    <input type="hidden" value="${menu.image}" id="menuItemImage">
                    <input type="hidden" value="${menu.price}" id="menuItemPrice">
                    <input type="number" id="quantity" class="w-16 text-xs rounded-md border px-1" min="1" value="1">
                    <button type="submit" class="orderButton bg-blue-500 text-white rounded-md px-2 py-1">Add</button>
                </form>
          </div>
    </div>
    `
}

function OrderMenuItem(ItemName, Price, Quantity, Image, menuItemId){
    return `
    <div class="w-full h-20 bg-white rounded-md flex order-item" date-id="{menuItemId}">
              <div>
                <img src="${Image}" alt="Item Image" class="w-20 h-20 rounded-md p-1 object-cover">
              </div>
              <div class="py-1 px-2 w-4/5">
                  <div class="flex justify-between">
                    <p class="text-md font-bold">${ItemName}</p>
                    <span data-id="${menuItemId}" class="removeItem text-red-600 cursor-pointer">x</span>
                  </div>
                  <div class="text-xs flex gap-x-2">
                    <p>Price: ${Price} &#165;</p>
                    <p>Quantity: ${Quantity}</p>
                    <p>Total: ${Price * Quantity} &#165;</p>
                  </div>
              </div>
            </div>
    `
}


// save the order to the server
$("#orderButton").on("click", function(){
    if (Order.orderItems.length === 0){
        alert("Please add items to the order");
        return;
    }
    Order.reservationId = currentReservationId;

    $.ajax(
        {
            url: "/api/order",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(Order),
            success: function(data){
                console.log(data)
                // reset the order
                Order.orderItems = [];
                $("#OrderMenuList").empty();
                $('#menuList').toggleClass("hidden");
                updateOrderTotal();
                changeReservationStatus(currentReservationId, "ORDERED");
                // check if URL is at /home
                if (window.location.pathname === "/home"){
                    getAllReservationsForToday();
                }
                Swal.fire({
                    icon: 'success',
                    title: 'Order Placed Successfully',
                    text: 'Order has been sent to the kitchen',
                    timer: 3000
                })
            },
            error: function(error){
                Swal.fire({
                    icon: 'error',
                    title: 'Order Failed',
                    text: 'Order could not be saved',
                    timer: 3000
                })
            }
        }
    )
})
