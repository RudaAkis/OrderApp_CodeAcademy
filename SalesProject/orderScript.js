let savedOrdersCache = null;
let orderPromise = null;

function loadOrders(){
    //If data is already fetched return the resolved promise data
    if (savedOrdersCache){
        return Promise.resolve(savedOrdersCache);
    }
    //If fetch is still going - Order promise != null return the promise
    if(orderPromise){
        return orderPromise;
    }

    orderPromise = fetch("http://localhost:8080/orders/all")
    .then( response => validateResponse(response))
    .then( jsonData => handleResponseJsonData(jsonData))
    .catch( error => handleJsonOrderError(error))

    return orderPromise;
}

function validateResponse(response){
    if(!response.ok){
        throw new Error("Failed to retrieve order data from backend");
    }
    return response.json();
}

function handleResponseJsonData(jsonData){
    savedOrdersCache = jsonData;
    orderPromise = null;//Reset promise to null - meaning its no longer in progress
    console.log(jsonData);
    return jsonData;
}

function handleJsonOrderError(error) {
    orderPromise = null; //Reset progress to allow to fetch again
    throw error;
}


window.onload = async function(){
    const orders = await loadOrders();

    orders.forEach(order => {
        console.log(order);
    });
}