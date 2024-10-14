function initMenu(){
    getAllMenu()

     $("#all").on("click", function(){
           getAllMenu();
       });
     $("#breakfast").on("click", function(){
           getMenuByMealTime('Breakfast');
     });
     $("#lunch").on("click", function(){
            getMenuByMealTime('Lunch');
     });
     $("#dinner").on("click", function(){
           getMenuByMealTime('Dinner');
     });
}

function getAllMenu(){
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

function getMenuByMealTime(mealTime){
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

    $('#menuList').empty();
    for (const menu of data) {
        const card = menuCard(menu);
        $('#menuList').append(card);
    }
}

function menuCard(menu){
    return `
    <a href="#menu">
                    <div class="bg-secondColor w-72 h-56 rounded-md group hover:scale-105 transition ease-in-out">
                        <div class="w-full h-full">
                            <div class="relative w-full h-full bg-cover bg-center rounded-md group" style="background-image: url(${menu.imageUrl})">
                                <div class="absolute z-40 text-white text-lg flex items-end justify-center w-full h-full font-mono ">
                                    <p class="bg-secondColor px-1 rounded-sm group-hover:hidden mb-3 transition ease-in-out">${menu.item_name}</p>
                                    <div class="justify-center items-center w-full h-full  transition ease-in-out duration-700 hidden group-hover:flex">
                                        <p class="text-center font-sans">${menu.description}</p>
                                    </div>
                                </div>
                                <div class="absolute z-10 bg-black w-full h-full opacity-25 hidden group-hover:block transition ease-in-out rounded-md"></div>
                            </div>
                        </div>
                    </div>
    </a>
    `
}