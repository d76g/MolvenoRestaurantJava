
// define an Order object
const Order = {
    reservationId: 1,
    orderItems : [
        // add order items here
    ]
}

// define an OrderItem object
const OrderItem = {
    menuId: 0,
    quantity: 0,
    price: 0
}

// define a function to add an order item to the order
function addOrderItem(menuItemId, quantity, price){
    const orderItem = Object.create(OrderItem);
    orderItem.menuId = menuItemId;
    orderItem.quantity = quantity;
    orderItem.price = price;
    Order.orderItems.push(orderItem);
}

function init(){
    console.log("init")
    getMenuItems();

}

//TODO:: List of all the menu items

function getMenuItems(){

    $.ajax(
        {
            url: "/api/menu/all",
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
    const menuDiv = $("#menuListContainer");
    menuDiv.empty();
    for (const menu of data) {
        const card = menuCard(menu);
        menuDiv.append(card);
    }
}

//TODO:: Add menu item to the order
$("#menuListContainer").on("submit", "#addItemToOrder", function(event){
    event.preventDefault();
    const menuItemId = $(this).find("#menuItemId").val();
    const quantity = $(this).find("#quantity").val();
    const price = $(this).find("#menuItemPrice").val();
    const itemName = $(this).find("#menuItemName").val();
    const image = $(this).find("#menuItemImage").val();
    addOrderItem(menuItemId, quantity, price);
    const orderDiv = $("#OrderMenuList");
    const orderItem = OrderMenuItem(itemName, price, quantity, image);
    orderDiv.append(orderItem);
    console.log(Order);
    // reset the quantity to 1
    $(this).find("#quantity").val(1);
    // update the order total price
    updateOrderTotal();
});

function updateOrderTotal(){
    let total = 0;
    for (const orderItem of Order.orderItems) {
        total += orderItem.quantity * orderItem.price;
    }
    $("#totalPrice").text(total);
}


function menuCard(menu){
    return `
    <div class="card" style="width: 18rem;">
        <div class="w-52 h-56 bg-slate-50 rounded-sm drop-shadow-sm">
              <div class="h-1/2">
                <img src="${menu.image}" alt="food" class="w-full h-full object-cover rounded-md p-1">
              </div>
              <div class="space-y-1">
                <div class="px-2">
                  <h1 class="text-md font-bold">${menu.item_name}</h1>
                  <p class="text-sm">${menu.description}</p>
                  <p class="text-xs">${menu.price}</p>
                </div>
                <form id="addItemToOrder" class="flex flex-cols justify-evenly">
                  <!--Display counter-->
                    <input type="hidden" value="${menu.menuItem_id}" id="menuItemId">
                    <input type="hidden" value="${menu.item_name}" id="menuItemName">
                    <input type="hidden" value="${menu.image}" id="menuItemImage">
                    <input type="hidden" value="${menu.price}" id="menuItemPrice">
                    <input type="number" id="quantity" class="w-16 text-xs rounded-md " value="1">
                    <button type="submit" class="orderButton bg-blue-500 text-white rounded-md px-2 py-1">Add</button>
                </form>
          </div>
    </div>
    `
}

function OrderMenuItem(ItemName, Price, Quantity, Image){
    return `
    <div class="w-full h-20 bg-white rounded-md flex">
              <div>
                <img src="${Image}" alt="Item Image" class="w-20 h-20 rounded-md p-1 object-cover">
              </div>
              <div class="py-1 px-2">
                  <p class="text-lg font-bold">${ItemName}</p>
                  <div class="text-sm flex gap-x-2">
                    <p>Price: ${Price}</p>
                    <p>Quantity: ${Quantity}</p>
                  </div>
              </div>
            </div>
    `
}

// save the order to the server
$("#orderButton").on("click", function(){
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
                updateOrderTotal();
            },
            error: function(error){
                console.log(error)
            }
        }
    )
})