let goodsPromise = null;
let savedGoodsData = null;

let quantityMapOfGoods = new Map();    


/* Loading goods data */
function getGoods(){
    //if savedd data is not null return the same data
    if(savedGoodsData){
        //Retrun the JSON data wrapped in a promise
        return Promise.resolve(savedGoodsData);
    }

    if (goodsPromise) { return goodsPromise; }
    //We set the goodsPromise to the fetch function
    //If we try to call it multiple time it will not be called until 
    //promise is cleared
    goodsPromise = fetch("http://localhost:8080/goods/all")
    .then( response => validateResponse(response) )
    .then( jsonData => saveJsonData(jsonData) )
    .catch((error) => {
        goodsPromise = null;//We clear the promise to allow a new call to happen 
        //if the data fails to load
        throw error;
    })
    //We must have this return if there is no data loaded and no promise in progress
    //This is the return that actually returns the data with out it we get
    //undefined
    return goodsPromise;
}

function validateResponse(response){
//If bad response throw exception
if(!response.ok){
    throw new Error("Could not connect to API")
}
//If response ok then parse it to JSON 
return response.json();
}

function saveJsonData(jsonData){
    savedGoodsData = jsonData;// save the json from fetch call to api
    goodsPromise = null;//clear promise to allow new calls
    return jsonData;//Could also return savedGoodsData
        //  since it is the same object now
}

function reloadGoodsData(){
    savedGoodsData = null;//make sure saved data is removed
    return  getGoods();
}

/* Load the goodsContainers with goods info */
async function loadGoodsToDivs() {
    //Get the list of goods
    const listOfGoods = await getGoods();
    //Get the main container to store all goods
    const mainContainer = document.getElementById("mainContainer");

    listOfGoods.forEach(good => {
        //create a div 
        const goodsConatinerDiv = document.createElement("div");
        goodsConatinerDiv.classList.add("goodsContainer");//Add css class to it
        goodsConatinerDiv.id = `${good.id}`

        const nameP = document.createElement("p");
        nameP.classList.add("mainInfoParagraph");
        nameP.innerHTML = `${good.name}`
        goodsConatinerDiv.appendChild(nameP);

        const descriptionP = document.createElement("p");
        descriptionP.classList.add("extraInfo");
        descriptionP.innerHTML = `${good.description}`
        goodsConatinerDiv.appendChild(descriptionP);

        const priceP = document.createElement("p");
        priceP.id = "itemPrice"+`${good.id}`;
        priceP.classList.add("mainInfoParagraph");
        priceP.innerHTML = `${good.price}`
        goodsConatinerDiv.appendChild(priceP);

        const addToOrderButton = document.createElement("button");
        addToOrderButton.classList.add("orderButton");
        addToOrderButton.id = `${good.id}`;
        addToOrderButton.innerHTML = "Add to order";
        addToOrderButton.addEventListener("click", event => addToCart(event))
        goodsConatinerDiv.appendChild(addToOrderButton);

        mainContainer.appendChild(goodsConatinerDiv);
    });


}

function addToCart(event) {
    addItemToOrder(event);
    const cartCounter = document.getElementById("cartItemCount");
    cartCounter.innerHTML = parseInt(cartCounter.innerHTML) + 1;
}

function addItemToOrder(event){
    const buttonClicked = event.target; //Get the button element that was clicked
    //When event got triggered
    const goodsId = parseInt(buttonClicked.id);//Convert the id that is string to int

    if(!quantityMapOfGoods.has(goodsId)){
        const priceQuantity = {
            price: parseFloat(document.getElementById("itemPrice"+`${goodsId}`).innerHTML), 
            quantity: 1};
        quantityMapOfGoods.set(goodsId, priceQuantity);
    }else{
        const existingMapEntry = quantityMapOfGoods.get(goodsId);
        existingMapEntry.quantity += 1;
    }

    console.log(quantityMapOfGoods);
}

function createOrder(event) {
    document.getElementById("cartItemCount"). innerHTML = 0;//Reset item count in cart

    const order = createOrderObject();

    fetch("http://localhost:8080/orders", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(order)
    })
    .then( response => validateResponse(response) )
    .then( () => {
        alert("Order created succesfully!")
    })
    .catch(error => {
        alert("Something went wrong. Please try again.")
        throw error;
    });

}

function createOrderObject(){
    // //Creating the Order object
    const customerId = 1;
    const items = [];

    //Create all goods Item based on the DTO in the back end
    quantityMapOfGoods.forEach( (quantityPriceObject, id) => {
        const goodsId = id;
        const quantity = quantityPriceObject.quantity;
        const price = quantityPriceObject.price;
        const goodsItem = {goodsId, quantity, price};
        items.push(goodsItem);
    });
    //Create full OrderCreationDTO based on the java DTO to write to DB
    return {customerId, items};
}

window.onload = async function(){ 
    /*Test to make sure we get objects from API*/
    // const goods = await getGoods();
    // goods.forEach(good => {
    //     console.log(good);
    // });

    loadGoodsToDivs();

    document.getElementById("cartButton").addEventListener("click", event => createOrder(event));
}