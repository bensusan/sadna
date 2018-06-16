var stompClient = null;

var currentUser = {'cart': {'products': []}};

function setUPOnce(){
    localStorage.setItem('currentUser', JSON.stringify({'cart': {'products': []}})); //New Guest for start
    localStorage.setItem('isSubscriber', JSON.stringify(false));
    localStorage.setItem('specificProduct', JSON.stringify(null));
    localStorage.setItem('init' , JSON.stringify(-1));
    localStorage.setItem('myCart' , JSON.stringify(null));
    localStorage.setItem('msgToPrint' , JSON.stringify(5));
}

function connect() {
    var socket = new SockJS('/gs-guide-websocket');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
        	var body = JSON.parse(greeting.body);
        	var obj = JSON.parse(body.contentAsJson);
            if(body.isException) {
                window.alert(obj);
            }
            else {
                //    no exception from now.
                switch (body.pageName) {
                    case "mainPage":
                        recieveMainPageMsg(body.functionName, obj);
                        break;
                    case "loginPage":
                        recieveLoginPageMsg(body.functionName, obj);
                        break;
                    case "signUpPage":
                    	recieveSignUpMsg(body.functionName, obj);
                    	break;
					case "openStorePage":
                    	recieveOpenStoreMsg(body.functionName, obj);
                    	break;
					case "updateCurrentSubscriber":
						recieveUpdateCurrentSubscriber(body.functionName, obj);
						break;
					case "addProductPage":
						recieveAddProduct(body.functionName, obj);
						break;
					case "storeProductsPage":
						recieveStoreProductsPage(body.functionName, obj);
						break;
					case "editProductPage":
						recieveEditProductsPage(body.functionName, obj);
						break;
					case "subscribersPage":
						recieveSubscribersPage(body.functionName, obj);
						break;
					case "storesPage":
						recieveStoresPage(body.functionName, obj);
						break;
					case "addPurchaseTypeToProductPage":
						recieveAddPurchaseTypeToProductPage(body.functionName, obj);
						break;
					case "addDiscountAndPolicyPage":
						recieveAddDiscountAndPolicyPage(body.functionName, obj);
						break;
					case "loadProductPage":
						recieveGetProductMsg(body.functionName, obj);
						break;
					case "productPage":
						recieveAddToSubCart(body.functionName, obj);
						break;
					case "cartPage":
						recieveRemCart(body.functionName, obj);
						break;
					case "editCartPage":
						recieveEditCart(body.functionName, obj);
						break;
					case "purchaseGuestCart":
						recieveCartAfterPurchase(body.functionName, obj);
						break;
					case "purchaseSubCart":
						recieveCartAfterPurchase(body.functionName, obj);
						break;
					case "storePage":
						recieveStorePage(body.functionName, obj);
						break;
                    default:
                        break;
                }
            }
        });
    });
}


/*********************************************************************************/
//**************************RECIEVE MESSAGE FROM SERVER*************************//
/********************************************************************************/
function recieveMainPageMsg(funcName, obj) {
    switch (funcName){
        case "getAllStoresWithThierProductsAndAmounts":
        	var data = JSON.parse(obj);
        	var data1=data[0], data2=data[1];       		
            localStorage.setItem('mapForTable', JSON.stringify(data2));
            localStorage.setItem('msgToPrint',JSON.stringify(data1));
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "mainPage.html";
            break;
		case "findProductByName":
		case "findProductByCategory":
            if(obj.length == 0){
				window.alert("No such Products in the System!");
			}else{
				localStorage.setItem('searchProductsResult', JSON.stringify(obj));				
				loadMainPage();
				
			}
            break;
        default:
            break;
    }
}

function recieveAddPurchaseTypeToProductPage(funcName, obj) {
	switch (funcName){
        case "changeProductType":
            window.alert("Product new purchase type added successfully!");
            loadStoreProducts();
            break;
        default:
            break;
    }
}

function recieveAddDiscountAndPolicyPage(funcName, obj) {
	switch (funcName){
        case "addDiscountToProduct":
            window.alert("New discount added successfully!");
            loadStoreProducts();
            break;
		case "addPolicyToProduct":
			window.alert("New Policy added successfully!");
            loadStoreProducts();
			break;
		case "changeStorePurchasePolicy":
			window.alert("New Policy to store added successfully!");
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
		case "addDiscountToCategoryStore":
			window.alert("New Discount to category in store added successfully!");
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
        default:
            break;
    }
}

function recieveEditProductsPage(funcName, obj) {
	switch (funcName){
        case "updateProductDetails":
            window.alert("Product Update successfully");
            loadStoreProducts();
            break;
        default:
            break;
    }
}
function recieveStoreProductsPage(funcName, obj) {
    switch (funcName){
        case "getProductAndAmountPerStoreId":
			localStorage.setItem('prodAmountToStoreMap', JSON.stringify(obj));
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storeProductsPage.html";
            break;
		case "deleteProductFromStore":
			window.alert("Product was deleted successfully!");
			loadStoreProducts();
            break;
        default:
            break;
    }
}

function recieveSubscribersPage (funcName, obj) {
    switch (funcName){
		
        case "getAllSubscribersWithPotential":
			localStorage.setItem('allSubscribers', JSON.stringify(obj));
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "subscribersPage.html";
            break;
		case "addNewStoreOwner":
			window.alert("New Store Owner added successfully!");
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
		case "addNewManager":
			window.alert("New Store Manager added successfully!");
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
		case "removeSubscriber":
			window.alert("Subscriber removed successfully!");
            loadMainPage();
			break;
		case "viewSubscriberHistory":
			if(obj.length == 0){
				window.alert("No Purchase made yes.");
			}else{
				localStorage.setItem('purchaseList', JSON.stringify(obj));
				stompClient.disconnect();
				stompClient = null;
				window.location.href = "purchaseListPage.html";
			}
			break;
        default:
            break;
    }
}

function recieveStorePage(funcName, obj){
	switch (funcName){
		case "getPurchaseHistory":
			if(obj.length == 0){
				window.alert("No Purchase made yes.");
			}else{
				localStorage.setItem('purchaseList', JSON.stringify(obj));
				stompClient.disconnect();
				stompClient = null;
				window.location.href = "purchaseListPage.html";
			}
			break;
		case "getProductAndAmountPerStoreId":
			localStorage.setItem('storeProducts', JSON.stringify(obj));
			stompClient.disconnect();
			stompClient = null;
			window.location.href = "storePage.html";
			break;
        default:
            break;
    }
}

function recieveStoresPage (funcName, obj) {
    switch (funcName){
		
        case "getAllStores":
			localStorage.setItem('allStores', JSON.stringify(obj));
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storesPage.html";
            break;
		case "viewStoreHistory":
			if(obj.length == 0){
				window.alert("No Purchase made yes.");
			}else{
				localStorage.setItem('purchaseList', JSON.stringify(obj));
				stompClient.disconnect();
				stompClient = null;
				window.location.href = "purchaseListPage.html";
			}
			break;
        default:
            break;
    }
}

function recieveAddProduct(funcName, obj) {
    switch (funcName){
        case "addProductToStore":
			window.alert("New Product was added successfully!");
			loadStoreProducts();
            break;
        default:
            break;
    }
}

function recieveLoginPageMsg(funcName, obj) {
    switch (funcName){
        case "signIn":
            localStorage.setItem('currentUser', JSON.stringify(obj));
            localStorage.setItem('mySubCart' , JSON.stringify(obj.cart));
            localStorage.setItem('isSubscriber', JSON.stringify(true));
			localStorage.setItem('isAdmin', JSON.stringify(false));
			
			if(obj.isAdmin == true){
				localStorage.setItem('isAdmin', JSON.stringify(true));
			}
            loadMainPage();
            break;
        default:
            break;
    }
}

function recieveSignUpMsg(funcName, obj) {
    switch (funcName){
        case "signUp":
			window.alert("Successfully signed up");
            loadMainPage();
            break;
        default:
            break;
    }
}

function recieveOpenStoreMsg(funcName, obj) {
    switch (funcName){
        case "openStore":
			window.alert("Store " + JSON.stringify(obj['name']) + " opened successfully!");
			updateCurrentSubscriber();
			setTimeout(function(){
			loadMyStoresPage();
			}, 2000);
            
            break;
        default:
            break;
    }
}

function recieveUpdateCurrentSubscriber(funcName, obj) {
    switch (funcName){
        case "getSubscriberFromUsername":
            localStorage.setItem('currentUser', JSON.stringify(obj));
            break;
        default:
            break;
    }
}

function recieveGetProductMsg(funcName, obj) {
    switch (funcName){
        case "getProduct":
        	localStorage.setItem('currentProduct' , JSON.stringify(obj));
			loadProductPage();
            break;
        default:
            break;
    }
}

function recieveAddToSubCart(funcName, obj){
	switch (funcName){
        case "addToSubCart":
        	localStorage.setItem('mySubCart' , JSON.stringify(obj));
        	window.alert('Product was added to cart!');
			setTimeout(function(){
			loadMainPage();
			}, 2000);
       		break;
       	case "addToGuestCart":
       		var data = JSON.parse(obj);
        	var data1=data[0], data2=data[1];
        	localStorage.setItem('myCartToPrint' , JSON.stringify(data2));
        	localStorage.setItem('myCart',data2);
        	localStorage.setItem('init' , JSON.stringify(data1));
        	window.alert('Product was added to cart!');
			setTimeout(function(){
			loadMainPage();
			}, 2000);   
       		break;   		
        default:
            break;
    }
}


/******************************************************************************/
/**********************************END RECIEVE*********************************/
/******************************************************************************/

//****SEND MESSAGE TO SERVER****//
function signIn() {
    stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "loginPage",
				    	'functionName': "signIn",
				    	'paramsAsJSON': [	localStorage.getItem('currentUser'),	//new Guest()
				    						$("#userName").val(),			//userName
				    						$("#password").val()
				    					]
				    }
	));
}

function signUp(){
    stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "signUpPage",
				    	'functionName': "signUp",
				    	'paramsAsJSON': [	localStorage.getItem('currentUser'),	//new Guest()
				    						$("#newuserName").val(),			//userName
				    						$("#newpassword").val(),
				    						$("#fullname").val(),
				    						$("#address").val(),
				    						$("#phonenum").val(),
				    						$("#creditCardNumber").val()
				    					]
				    }
	));
}


function openStore() {
    stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "openStorePage",
				    	'functionName': "openStore",
				    	'paramsAsJSON': [	localStorage.getItem('currentUser'),	//logged in sub turns to store owner
				    						JSON.stringify($("#newStoreName").val())			//store name
				    					]
				    }
	));
	
	
}

function updateCurrentSubscriber(){
	    stompClient.send("/app/hello", {},
		JSON.stringify(
				    {	'pageName': "updateCurrentSubscriber",
				    	'functionName': "getSubscriberFromUsername",
				    	'paramsAsJSON': [	JSON.parse(localStorage.getItem('currentUser'))['username']	//username as string
				    					]
				    }
	));
}

function addProductToStore(){
	stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "addProductPage",
				    	'functionName': "addProductToStore",
				    	'paramsAsJSON': [	JSON.parse(localStorage.getItem('currentUser'))['username'],	//logged in sub turns to store owner
											JSON.parse(localStorage.getItem('currentStore'))['storeId'],
				    						JSON.stringify($("#newProductName").val()),			//store name
											$("#newProductPrice").val(),
											JSON.stringify($("#newProductCategory").val()),
											$("#newProductAmount").val(),
											localStorage.getItem('isOwner')
				    					]
				    }
	));
}

function getProduct(productId){
	productId = Number(productId);
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "loadProductPage",
            'functionName': "getProduct",
            'paramsAsJSON': [productId]				
        }
    ));
}

function purchaseAllCart(){
	var creditCard = document.getElementById('creditCardInput').value;
	var address = document.getElementById('addressInput').value;
	
	if( creditCard === '' ){
	      window.alert("Please Insert Valid Credit Card Number");
	      return;
    }
    
    if( address === '' ){
	      window.alert("Please Insert Valid Address");
	      return;
    }
    
    if(JSON.parse(localStorage.getItem('isSubscriber')) === false){
 	
				  stompClient.send("/app/hello", {},
					JSON.stringify(
						{	'pageName': "purchaseGuestCart",
							'functionName': "purchaseCart",
							'paramsAsJSON': [
											 localStorage.getItem('init'),
											 creditCard,
											 address,
											 0
											 ]
					}));
    }
    
    else{
	  	stompClient.send("/app/hello", {},
				JSON.stringify(
					{	'pageName': "purchaseSubCart",
						'functionName': "purchaseCart",
						'paramsAsJSON': [
										 JSON.parse(localStorage.getItem('currentUser'))['username'],
										 creditCard,
										 address
										 ]
				}));
    }
	    
	
}

/******************************************************************************/
/********************************LOAD FUNCTIONS********************************/
/******************************************************************************/
function loadStoreDetails(){
	var store = JSON.parse(localStorage.getItem('currentStore'));
	var sid = document.getElementById('storeIdInStorePage');
	sid.innerHTML = store.storeId;
	var sn = document.getElementById('storeNameInStorePage');
	sn.innerHTML = store.name;
	var sa = document.getElementById('storeAddressInStorePage');
	sa.innerHTML = store.address;
	var sp = document.getElementById('storePhoneInStorePage');
	sp.innerHTML = store.phone;
	var sg = document.getElementById('storeGradingInStorePage');
	sg.innerHTML = store.gradeing;
}

function hideButtonsToManager(){
		if(localStorage.getItem('isOwner') == 'false'){
			var managers  = JSON.parse(localStorage.getItem('currentUser')).manager;
			for(var i = 0; i < managers.length; i++){
				var store = managers[i].store;
				if(store.storeId == JSON.parse(localStorage.getItem('currentStore'))['storeId']){
					var permits = managers[i].premisions;
					if(permits[0] == false){
						$('#0').hide();
					}
					if(permits[1] == false){
						$('#1').hide();
					}
					if(permits[2] == false){
						$('#2').hide();
					}
					if(permits[3] == false){
						$('#3').hide();
					}
					if(permits[4] == false){
						$('#4').hide();
					}
					if(permits[5] == false){
						$('#5').hide();
					}
					if(permits[6] == false){
						$('#6').hide();
					}
					if(permits[9] == false){
						$('#9').hide();
					}
					if(permits[11] == false){
						$('#11').hide();
					}
					if(permits[12] == false){
						$('#12').hide();
					}
					if(permits[13] == false){
						$('#13').hide();
					}
					
				}
			}
			
		}

}

function mainTableOnLoad() {
    var obj = JSON.parse(localStorage.getItem('mapForTable'));
    var tableRef = document.getElementById('mainProductsTable');
    var found = false;
    Object.entries(obj).map(([s, pAndA]) => {
		var newRow = tableRef.insertRow(-1);
		var newCell = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.innerHTML = "Store: " + s;
		newCell.appendChild(newElem);
        Object.entries(pAndA).map(([p, amount]) => {
            if (amount > 0) 
            {
            	var newRow = tableRef.insertRow(-1);
				var newCell = newRow.insertCell(0);
				var newElem = document.createElement( 'button' );
				newElem.setAttribute('class', 'btn');
				newElem.innerHTML = p + ", Amount:" + amount;
				var pID = p.substring(p.indexOf(" "), p.indexOf(","));
				newElem.setAttribute('onclick', 'getProduct(' + pID + ')');
				newCell.appendChild(newElem);
                found = true; 
            }
        });
    });

    if(!found)
        window.alert("No Products");
    else
    	$('#loadProducts').hide();
}

function loadProduct(){
	var product = JSON.parse(localStorage.getItem('currentProduct'));
	var pid = document.getElementById("productIdInProductPage");
	pid.innerHTML = product.id;
	var pn = document.getElementById('productNameInProductPage');
	pn.innerHTML = product.name;
	var pg = document.getElementById('productGradingInProductPage');
	pg.innerHTML = product.grading;
	var pc = document.getElementById('productCategoryInProductPage');
	pc.innerHTML = product.category.name;
	var pt = document.getElementById('productTypeInProductPage');
	if(JSON.stringify(product.type) == "{}"){
		pt.innerHTML = "Immediatly Purchase";
	}else{
		pt.innerHTML = "Lottery Purchase: End Date: " + product.type.lotteryEndDate + ", Has Ended: " + JSON.stringify(product.type.hasEnded);
	}
	var pp = document.getElementById('productPolicyInProductPage');
	if(JSON.stringify(product.purchasePolicy) == "{}"){
		pp.innerHTML = "Emplty Policy";
	}else{
		var toBeInner = "";
		if(typeof product.purchasePolicy["min"] != "undefined"){
			toBeInner = toBeInner + "Min amount: " + JSON.stringify(product.purchasePolicy["min"]) + ". ";
		}
		if(typeof product.purchasePolicy["max"] != "undefined"){
			toBeInner = toBeInner + "Max amount: " + JSON.stringify(product.purchasePolicy["max"]) + ". ";
		}
		pp.innerHTML = toBeInner;
	}
	
}

function loadProductsOfStore(){
	var obj = JSON.parse(localStorage.getItem('prodAmountToStoreMap'));
	var tableRef = document.getElementById('ProductsInStoreTable');
	for(var toPrint in obj){
		var newRow   = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.innerHTML = toPrint + ", Amount:" + obj[toPrint] ;
		var productIDasString = toPrint.substring(toPrint.indexOf(" "), toPrint.indexOf(","));
		newElem.setAttribute('onclick', localStorage.getItem('actionOnProduct')+'('+ productIDasString +');');
		newCell.appendChild(newElem);
		
	}
}

function loadStoresIOwn(){
	
	var storeOwners = JSON.parse(localStorage.getItem('currentUser')).owner;
	var tableRef = document.getElementById('storeIOwnTable');
	for(var i = 0; i < storeOwners.length; i++){
		
		var store = storeOwners[i].store;
		
		var newRow   = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.setAttribute('onclick', 'loadEditOwnStore('+ JSON.stringify(storeOwners[i].store) +');');
		newElem.innerHTML = "Store - id: " + store.storeId + ", Name: " + store.name + ", Grading: " + store.gradeing;
		newCell.appendChild(newElem);

	}
}

function loadSearchProducts(){
	var foundProds = JSON.parse(localStorage.getItem('searchProductsResult'));
	var tableRef = document.getElementById('searchProductResultTable');
	if(foundProds.length != 0){
		for(var i = 0; i < foundProds.length; i++){
			window.alert(JSON.stringify(foundProds[i]));
			var newRow   = tableRef.insertRow(-1);
			var newCell  = newRow.insertCell(0);
			var newElem = document.createElement( 'button' );
			newElem.setAttribute('class', 'btn');
			newElem.setAttribute('onclick', 'getProduct('+ JSON.stringify(foundProds[i].id) +');');
			newElem.innerHTML = "Product - id: " + foundProds[i].id + ", Name: " + foundProds[i].name + ", Price: " + foundProds[i].price +", Grading: " + foundProds[i].grading + ", Policy: " + JSON.stringify(foundProds[i].purchasePolicy) + ", Type: " + JSON.stringify(foundProds[i].type);
			newCell.appendChild(newElem);
		}
	}

}

function searchProducts(){
	if ($("input[type=radio]:checked").length == 0 ) {
		window.alert("Please choose Search Type.");
	}else if($("#searchValue").val() == ""){
		window.alert("Please enter Search Valllue.");
	}else{
		if($('#nameSearchButton').is(':checked')){
			stompClient.send("/app/hello", {},
			JSON.stringify(
				{	'pageName': "mainPage",
					'functionName': "findProductByName",
					'paramsAsJSON': [
									JSON.stringify($("#searchValue").val())
									]
									
			}));
		}else if($('#categorySearchButton').is(':checked')){
			stompClient.send("/app/hello", {},
			JSON.stringify(
				{	'pageName': "mainPage",
					'functionName': "findProductByCategory",
					'paramsAsJSON': [
									JSON.stringify($("#searchValue").val())
									]
									
			}));
		}
	}
}


function loadSubscribers(){
	var subs = JSON.parse(localStorage.getItem('allSubscribers'));
	var tableRef = document.getElementById('subscribersInSystemTable');
	for(var i = 0; i < subs.length; i++){

		
		
		var newRow   = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.innerHTML = JSON.stringify(subs[i].username);
		newElem.setAttribute('onclick', localStorage.getItem('actionOnSubscriber')+'('+ JSON.stringify(subs[i].username) +');');
		newCell.appendChild(newElem);

	}
}

function loadPurchaseList(){
	var purchaseList = JSON.parse(localStorage.getItem('purchaseList'));
	var stringToBeInner = "";
	for(var i = 0; i < purchaseList.length; i++){
		stringToBeInner = stringToBeInner + "<tr>" + 
						"<td>" + JSON.stringify(purchaseList[i].whenPurchased) + "</td>" +
						"<td>" + JSON.stringify(purchaseList[i].purchaseID) + "</td>" +
						"<td>" + JSON.stringify(purchaseList[i].purchased.myProduct.name) + "</td>" +
						"<td>" + JSON.stringify(purchaseList[i].purchased.myProduct.price) + "</td>" +
						"<td>" + JSON.stringify(purchaseList[i].purchased.amount) + "</td>" +
						"</tr>";
	}
	var tableRef = document.getElementById('purchaseHistoryTableBody');
	tableRef.innerHTML = stringToBeInner;
}

function loadStores(){
	var subs = JSON.parse(localStorage.getItem('allStores'));
	var tableRef = document.getElementById('storesInSystemTable');
	for(var i = 0; i < subs.length; i++){
		
		var newRow   = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.innerHTML = "Store - id: " + subs[i].storeId + ", Name: " + subs[i].name + ", Grading: " + subs[i].gradeing;
		newElem.setAttribute('onclick', localStorage.getItem('actionOnStore')+'('+ JSON.stringify(subs[i].storeId) +');');
		newCell.appendChild(newElem);

	}
}

/**********************************************************************************/
/********************************END LOAD FUNCTIONS********************************/
/**********************************************************************************/

function addNewStoreOwner(usernameToAdd){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "addNewStoreOwner",
            'paramsAsJSON': [localStorage.getItem('isOwner'),
							JSON.parse(localStorage.getItem('currentUser'))['username'],
							usernameToAdd,
							JSON.parse(localStorage.getItem('currentStore'))['storeId']
							]
							
        }));
}

function removeSubscriber(usernameToRemove){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "removeSubscriber",
            'paramsAsJSON': [JSON.parse(localStorage.getItem('currentUser'))['username'],
							usernameToRemove
							]
							
        }));
}

function viewSubscriberHistory(usernameToViewHistory){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "viewSubscriberHistory",
            'paramsAsJSON': [JSON.parse(localStorage.getItem('currentUser'))['username'],
							usernameToViewHistory
							]
							
        }));
}

function watchCurrentStorePurchaseHistory(){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storePage",
            'functionName': "getPurchaseHistory",
            'paramsAsJSON': [localStorage.getItem('isOwner'),
							JSON.parse(localStorage.getItem('currentUser'))['username'],
							JSON.parse(localStorage.getItem('currentStore'))['storeId']
							]
							
        }));
}

function viewStoreHistory(storeIdToWatch){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storesPage",
            'functionName': "viewStoreHistory",
            'paramsAsJSON': [JSON.parse(localStorage.getItem('currentUser'))['username'],
							storeIdToWatch
							]
							
        }));
}


function sendAddNewStoreManager(){
	var toSend = '';
	if (document.getElementById('0').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('1').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('2').checked)
			toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('3').checked)
				toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('4').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('5').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('6').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('9').checked)
		toSend = toSend + "0d0d1d";
	else
		toSend = toSend + "0d0d0d";
	if (document.getElementById('11').checked)
		toSend = toSend + "0d1d";
	else
		toSend = toSend + "0d0d";
	if (document.getElementById('12').checked)
		toSend = toSend + "1d";
	else
		toSend = toSend + "0d";
	if (document.getElementById('13').checked)
		toSend = toSend + "1";
	else
		toSend = toSend + "0";
	
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "addNewManager",
            'paramsAsJSON': [localStorage.getItem('isOwner'),
							JSON.parse(localStorage.getItem('currentUser'))['username'],
							localStorage.getItem('usernameToAddAsManager'),
							JSON.parse(localStorage.getItem('currentStore'))['storeId'],
							toSend
							]
							
        }));
}


function deleteProductFromStore(productId){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storeProductsPage",
            'functionName': "deleteProductFromStore",
            'paramsAsJSON': [localStorage.getItem('isOwner'),
							productId,
							JSON.parse(localStorage.getItem('currentStore'))['storeId'],
							JSON.parse(localStorage.getItem('currentUser'))['username']
							]
							
        }));
}

function editProductInStore(){
	if(isNaN($("#editnewProductPrice").val())){
		window.alert("wrong price");
		loadEditProductInStorePage(localStorage.getItem('currentProduct'));
	}
	if(isNaN($("#editnewProductAmount").val())){
		window.alert("wrong amount");
		loadEditProductInStorePage(localStorage.getItem('currentProduct'));
	}
	
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "editProductPage",
            'functionName': "updateProductDetails",
            'paramsAsJSON': [localStorage.getItem('isOwner'),
							JSON.parse(localStorage.getItem('currentProduct')),
							JSON.parse(localStorage.getItem('currentStore'))['storeId'],
							JSON.parse(localStorage.getItem('currentUser'))['username'],
							JSON.stringify($("#editnewProductName").val()),
							$("#editnewProductPrice").val(),
							JSON.stringify($("#editnewProductCategory").val()),
							$("#editnewProductAmount").val()
							]
							
        }));
}







function loadStoresIManage(){
	var storeManagers = JSON.parse(localStorage.getItem('currentUser')).manager;
	var tableRef = document.getElementById('storeIManageTable');
	for(var i = 0; i < storeManagers.length; i++){
		
		var store = storeManagers[i].store;
		
		var newRow   = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'button' );
		newElem.setAttribute('class', 'btn');
		newElem.setAttribute('onclick', 'loadEditManageStore('+ JSON.stringify(storeManagers[i].store) +');');
		newElem.innerHTML = "Store - id: " + store.storeId + ", Name: " + store.name + ", Grading: " + store.gradeing;
		newCell.appendChild(newElem);

	}
}	

function loadStoreProductsPage(action){
	localStorage.setItem('actionOnProduct', action);
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storeProductsPage",
            'functionName': "getProductAndAmountPerStoreId",
            'paramsAsJSON': [JSON.parse(localStorage.getItem('currentStore'))['storeId']]
        }));
}

function loadStoreProducts(){
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storePage",
            'functionName': "getProductAndAmountPerStoreId",
            'paramsAsJSON': [JSON.parse(localStorage.getItem('currentStore'))['storeId']]
        }));
}



function loadAllUsersPage(action){
	localStorage.setItem('actionOnSubscriber', action);
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "getAllSubscribersWithPotential",
            'paramsAsJSON': []
        }));
}
//TODO : continue to present all stores
function loadAllStoresPage(action){
	localStorage.setItem('actionOnStore', action);
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "storesPage",
            'functionName': "getAllStores",
            'paramsAsJSON': []
        }));
}

function addToCart(){
	 var product = JSON.parse(localStorage.getItem('currentProduct'));
	 var amount = document.getElementById("productAmount").value;
	 var discountCode = document.getElementById("discountCode").value;
	 
	 if(JSON.parse(localStorage.getItem('isSubscriber')) === false) {
		 stompClient.send("/app/hello", {},
			JSON.stringify(
				{	'pageName': "productPage",
					'functionName': "addToGuestCart",
					'paramsAsJSON': [JSON.parse(localStorage.getItem('init')),
									 product.id,
									 amount,
									 discountCode
									 ]				
			}));
	}
	else{
		stompClient.send("/app/hello", {},
			JSON.stringify(
				{	'pageName': "productPage",
					'functionName': "addToSubCart",
					'paramsAsJSON': [
									 JSON.parse(localStorage.getItem('currentUser'))['username'],
									 product.id,
									 amount,
									 discountCode
									 ]
			}));
		}	
}

function editProductInCart(pid){
    var newAmount = prompt("Please enter new amount:", "here");
    if (newAmount == null || newAmount == "") {
        window.alert("User cancelled the prompt.");
        setTimeout(function(){
				loadMainPage();
				}, 2000);
    } else {
        editCart(pid,newAmount);  
    }
}

function deleteFromCart(pid){
	if(JSON.parse(localStorage.getItem('isSubscriber')) === false) {
		stompClient.send("/app/hello", {},
				JSON.stringify(
					{	'pageName': "cartPage",
						'functionName': "removeProductFromCart",
						'paramsAsJSON': [
										 localStorage.getItem('init'),
										 pid,
										 0
										 ]
				}));
	}
	
	else{
		stompClient.send("/app/hello", {},
				JSON.stringify(
					{	'pageName': "cartPage",
						'functionName': "removeProductFromCart",
						'paramsAsJSON': [
										 JSON.parse(localStorage.getItem('currentUser'))['username'],
										 pid
										 ]
				}));
		}
}

function editCart(pid,newAmount){
	if(JSON.parse(localStorage.getItem('isSubscriber')) === false) {
		stompClient.send("/app/hello", {},
				JSON.stringify(
					{	'pageName': "editCartPage",
						'functionName': "editCart",
						'paramsAsJSON': [
										 localStorage.getItem('init'),
										 pid,
										 newAmount,
										 0
										 ]
				}));
	}
	
	else{
		stompClient.send("/app/hello", {},
				JSON.stringify(
					{	'pageName': "editCartPage",
						'functionName': "editCart",
						'paramsAsJSON': [
										 JSON.parse(localStorage.getItem('currentUser'))['username'],
										 pid,
										 newAmount
										 ]
				}));
		}
}

function recieveRemCart(funcName, obj){
   switch (funcName){ 
       	case "removeProductFromCart":
       			if(JSON.parse(localStorage.getItem('isSubscriber'))){
	       			localStorage.setItem('mySubCart' , JSON.stringify(obj));
	       		}
	       		else{
	       			localStorage.setItem('myCart' , obj);
	       			localStorage.setItem('myCartToPrint' , JSON.stringify(obj));
	       		}
	       		
	       		window.alert('Product was deleted !!!!');
				setTimeout(function(){
				loadMainPage();
				}, 2000);
			break;
		default:
			break;
		}
}

function recieveEditCart(funcName, obj){
   switch (funcName){ 
       	case "editCart":
       			if(JSON.parse(localStorage.getItem('isSubscriber'))){
	       			localStorage.setItem('mySubCart' , JSON.stringify(obj));
	       		}
	       		else{
	       			localStorage.setItem('myCart' , obj);
	       			localStorage.setItem('myCartToPrint' , JSON.stringify(obj));
	       		}
	       		
	       		window.alert('Product was edited !!!!');
				setTimeout(function(){
				loadMainPage();
				}, 2000);
			break;
		default:
			break;
		}
}

function recieveCartAfterPurchase(funcName, obj){
   switch (funcName){ 
       	case "purchaseCart":
       			if(JSON.parse(localStorage.getItem('isSubscriber'))){
	       			localStorage.setItem('mySubCart' , JSON.stringify(obj));
	       		}
	       		else{
	       			localStorage.setItem('myCart' , obj);
	       			localStorage.setItem('myCartToPrint' , JSON.stringify(obj));
	       		}
	       		
	       		window.alert('Cart was purchased!');
				setTimeout(function(){
				loadMainPage();
				}, 2000);
			break;
		default:
			break;
		}
}


/******************************************************************************/
/********************************LOAD PAGES************************************/
/******************************************************************************/

function loadEditOwnStore(store){
	localStorage.setItem('currentStore', JSON.stringify(store));
	localStorage.setItem('isOwner', JSON.stringify(true));
	loadStoreProducts();
	/*stompClient.disconnect();
    stompClient = null;
    window.location.href = "storePage.html";*/
}

function loadEditManageStore(store){
	localStorage.setItem('currentStore', JSON.stringify(store));
	localStorage.setItem('isOwner', JSON.stringify(false));
	loadStoreProducts();
	/*stompClient.disconnect();
    stompClient = null;
    window.location.href = "storePage.html";*/
}

function loadMainPage() {
	if(JSON.parse(localStorage.getItem('isSubscriber')) === false){
	    stompClient.send("/app/hello", {},
	    JSON.stringify(
	        {	'pageName': "mainPage",
	            'functionName': "getAllStoresWithThierProductsAndAmounts",
	            'paramsAsJSON': [
	            				localStorage.getItem('init'),
	            				0
	            				]
	        }));
	}
	else{
		stompClient.send("/app/hello", {},
	    JSON.stringify(
	        {	'pageName': "mainPage",
	            'functionName': "getAllStoresWithThierProductsAndAmounts",
	            'paramsAsJSON': [
	            				JSON.parse(localStorage.getItem('currentUser'))['username'],
	            				]
	        }));
	}
}

function loadLoginPage(){
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "loginPage.html";
}

function loadSignUpPage(){
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "signUpPage.html";
}

function loadOpenStorePage(){
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "openStorePage.html";
}

function loadCartPage(){
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "myCart.html";
}

function loadProductPage(product) {
    //product comes here as string
    localStorage.setItem('specificProduct', product);
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "productPage.html";
}

function loadAddProductPage(){
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "addProductPage.html";
}

function loadEditProductInStorePage(id){
	localStorage.setItem('currentProduct', id);
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "editProductPage.html";
}

function loadAddPurchaseTypeToProductPage(id){
	localStorage.setItem('currentProduct', id);
	stompClient.disconnect();
    stompClient = null;
	window.location.href = "addPurchaseTypeToProductPage.html";
}

function loadAddDiscountAndPolicyPage(id){
	localStorage.setItem('currentProduct', id);
	stompClient.disconnect();
    stompClient = null;
	window.location.href = "addDiscountAndPolicyPage.html";
}

function addNewStoreManager(usernameToAdd){
	localStorage.setItem('usernameToAddAsManager', usernameToAdd);
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "permissionsToManager.html";
}

function loadMyCart(){
	var cart;
	
	if(JSON.parse(localStorage.getItem('isSubscriber'))){
		cart = JSON.parse(localStorage.getItem('mySubCart'));
		}
	else{
		cart = JSON.parse(localStorage.getItem('myCartToPrint'));
	}	
	
	var productsInCart;
	var size = 0;
	if(cart != null){
		productsInCart = cart.products;
		size = productsInCart.length;
	}

	var tableRef = document.getElementById('myCartTable');
	
	if(size === 0){
		window.alert("empty cart!");
		setTimeout(function(){
			loadMainPage();
			}, 2000);
	}
	
	for(var i = 0; i < size; i++){
		var p = productsInCart[i].myProduct;
		
		var newRow = tableRef.insertRow(-1);
		var newCell  = newRow.insertCell(0);
		var newElem = document.createElement( 'div' );
		newElem.setAttribute('class', 'dropdown');

		newElem.innerHTML = "<button class='dropbtn'>" + "id: " + p.id + " ,name: " + p.name + " ,price: " + p.price + " ,grading: " + p.grading
							 + " ,category: " + p.category.name + " ,policy: " + p.policy + " ,type: " + p.type + 
							 + " ,amount: " +  productsInCart[i].amount
							 + " </button>"
							 + "<div class='dropdown-content'>"
							 + "<button onclick=deleteFromCart(" + p.id + ");> delete </button>"
							 + "<button onclick=editProductInCart(" + p.id + ");> edit </button>" 
							 + "</div>";
							 
		newCell.appendChild(newElem);
	}
	
}

/******************************************************************************/
/********************************END LOAD PAGES********************************/
/******************************************************************************/

function logOut(){
	localStorage.setItem('currentUser', JSON.stringify({'cart': {'products': []}}));
    localStorage.setItem('mySubCart' , JSON.stringify(null));
    localStorage.setItem('isSubscriber', JSON.stringify(false));
	localStorage.setItem('isAdmin', JSON.stringify(false));
	
	window.alert("Logged out!");
	
    loadMainPage();
}


/******************************************************************************/
/***********************************POLICY*************************************/
/******************************************************************************/

function changeStorePurchasePolicy(){
	if ($("input[type=radio]:checked").length == 0 ) {
		window.alert("Please choose Policy.");
	}else if($('#minPolicy').is(':checked') && (isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong min amount");
	else if($('#maxPolicy').is(':checked') && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == ""))
		window.alert("Wrong max amount");
	else if(($('#orPolicy').is(':checked') || ($('#andPolicy').is(':checked'))) && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == "" || isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong amounts");
	else{
		if($('#emptyPolicy').is(':checked')){
			var policyType = "empty";
		}else if($('#minPolicy').is(':checked')){
			var policyType = "min";
		}else if($('#maxPolicy').is(':checked')){
			var policyType = "max";
		}else if($('#orPolicy').is(':checked')){
			var policyType = "or";
		}else if($('#andPolicy').is(':checked')){
			var policyType = "and";
		}
		stompClient.send("/app/hello", {},
		JSON.stringify(
			{	'pageName': "addDiscountAndPolicyPage",
				'functionName': "changeStorePurchasePolicy",
				'paramsAsJSON': [localStorage.getItem('isOwner'),
								JSON.parse(localStorage.getItem('currentUser'))['username'],
								JSON.parse(localStorage.getItem('currentStore'))['storeId'],
								policyType,
								$("#minAmountVal").val(),
								$("#maxAmountVal").val()
								
								]
								
		}));
	}
}



function addPolicyToProduct(){
	if ($("input[type=radio]:checked").length == 0 ) {
		window.alert("Please choose Policy.");
	}else if($('#minPolicy').is(':checked') && (isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong min amount");
	else if($('#maxPolicy').is(':checked') && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == ""))
		window.alert("Wrong max amount");
	else if(($('#orPolicy').is(':checked') || ($('#andPolicy').is(':checked'))) && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == "" || isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong amounts");
	else{
		if($('#emptyPolicy').is(':checked')){
			var policyType = "empty";
		}else if($('#minPolicy').is(':checked')){
			var policyType = "min";
		}else if($('#maxPolicy').is(':checked')){
			var policyType = "max";
		}else if($('#orPolicy').is(':checked')){
			var policyType = "or";
		}else if($('#andPolicy').is(':checked')){
			var policyType = "and";
		}
		stompClient.send("/app/hello", {},
		JSON.stringify(
			{	'pageName': "addDiscountAndPolicyPage",
				'functionName': "addPolicyToProduct",
				'paramsAsJSON': [localStorage.getItem('isOwner'),
								JSON.parse(localStorage.getItem('currentProduct')),
								JSON.parse(localStorage.getItem('currentUser'))['username'],
								JSON.parse(localStorage.getItem('currentStore'))['storeId'],
								policyType,
								$("#minAmountVal").val(),
								$("#maxAmountVal").val()
								
								]
								
		}));
	}
}

function addDiscountToCategoryStore(){
	if($("#categoryNameForDiscount").val() == "")
		window.alert("Please choose category.");
	else if ($("input[type=radio]:checked").length == 0 || $("input[type=radio]:checked").length == 1) {
		window.alert("Please choose Discount and Policy.");
	}
	else if(document.getElementById("endDate").value == ""){
		window.alert("Please choose discount end date.");
	}
	else if(isNaN($("#precentageOfDiscount").val()) || $("#precentageOfDiscount").val() == "")
		window.alert("Wrong %.");
	else if($('#hiddenDiscountButton').is(':checked') && (document.getElementById("codeOfDiscount").value == ""))
		window.alert("Please enter discount code.");
	else if($('#minPolicy').is(':checked') && (isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong min amount");
	else if($('#maxPolicy').is(':checked') && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == ""))
		window.alert("Wrong max amount");
	else if(($('#orPolicy').is(':checked') || ($('#andPolicy').is(':checked'))) && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == "" || isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong amounts");
	else{
		if($('#overtDiscountButton').is(':checked')){
			var discountType = "overt";
		}else{
			var discountType = "hidden";
		}
		if($('#emptyPolicy').is(':checked')){
			var policyType = "empty";
		}else if($('#minPolicy').is(':checked')){
			var policyType = "min";
		}else if($('#maxPolicy').is(':checked')){
			var policyType = "max";
		}else if($('#orPolicy').is(':checked')){
			var policyType = "or";
		}else if($('#andPolicy').is(':checked')){
			var policyType = "and";
		}
		stompClient.send("/app/hello", {},
		JSON.stringify(
			{	'pageName': "addDiscountAndPolicyPage",
				'functionName': "addDiscountToCategoryStore",
				'paramsAsJSON': [localStorage.getItem('isOwner'),
								JSON.stringify($("#categoryNameForDiscount").val()),
								JSON.parse(localStorage.getItem('currentUser'))['username'],
								JSON.parse(localStorage.getItem('currentStore'))['storeId'],
								discountType,
								$("#precentageOfDiscount").val(),
								$("#endDate").val(),
								$("#codeOfDiscount").val(),
								policyType,
								$("#minAmountVal").val(),
								$("#maxAmountVal").val()
								
								]				
		}));
	}
}

function addDiscountToProduct(){
	if ($("input[type=radio]:checked").length == 0 || $("input[type=radio]:checked").length == 1) {
		window.alert("Please choose Discount and Policy.");
	}
	else if(document.getElementById("endDate").value == ""){
		window.alert("Please choose discount end date.");
	}
	else if(isNaN($("#precentageOfDiscount").val()) || $("#precentageOfDiscount").val() == "")
		window.alert("Wrong %.");
	else if($('#hiddenDiscountButton').is(':checked') && (document.getElementById("codeOfDiscount").value == ""))
		window.alert("Please enter discount code.");
	else if($('#minPolicy').is(':checked') && (isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong min amount");
	else if($('#maxPolicy').is(':checked') && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == ""))
		window.alert("Wrong max amount");
	else if(($('#orPolicy').is(':checked') || ($('#andPolicy').is(':checked'))) && (isNaN($("#maxAmountVal").val()) || $("#maxAmountVal").val() == "" || isNaN($("#minAmountVal").val()) || $("#minAmountVal").val() == ""))
		window.alert("Wrong amounts");
	else{
		if($('#overtDiscountButton').is(':checked')){
			var discountType = "overt";
		}else{
			var discountType = "hidden";
		}
		if($('#emptyPolicy').is(':checked')){
			var policyType = "empty";
		}else if($('#minPolicy').is(':checked')){
			var policyType = "min";
		}else if($('#maxPolicy').is(':checked')){
			var policyType = "max";
		}else if($('#orPolicy').is(':checked')){
			var policyType = "or";
		}else if($('#andPolicy').is(':checked')){
			var policyType = "and";
		}
		stompClient.send("/app/hello", {},
		JSON.stringify(
			{	'pageName': "addDiscountAndPolicyPage",
				'functionName': "addDiscountToProduct",
				'paramsAsJSON': [localStorage.getItem('isOwner'),
								JSON.parse(localStorage.getItem('currentProduct')),
								JSON.parse(localStorage.getItem('currentUser'))['username'],
								JSON.parse(localStorage.getItem('currentStore'))['storeId'],
								discountType,
								$("#precentageOfDiscount").val(),
								$("#endDate").val(),
								$("#codeOfDiscount").val(),
								policyType,
								$("#minAmountVal").val(),
								$("#maxAmountVal").val()
								
								]
								
		}));
	}
}

function addNewPurchaseTypeToProdcut(){
	if ($("input[type=radio]:checked").length == 0) {
		window.alert("Please choose purchase type.");
	}else if($('#lotteryButton').is(':checked') && document.getElementById("endDate").value == ""){
		window.alert("Please choose end date.");
	}else{
		if($('#lotteryButton').is(':checked')){
			var purType = "lottery";
			var dateToSend = document.getElementById("endDate").value;
		}
		else{
			var purType = "immedietly";
			var dateToSend = "";
		}
		stompClient.send("/app/hello", {},
		JSON.stringify(
			{	'pageName': "addPurchaseTypeToProductPage",
				'functionName': "changeProductType",
				'paramsAsJSON': [localStorage.getItem('isOwner'),
								JSON.parse(localStorage.getItem('currentProduct')),
								JSON.parse(localStorage.getItem('currentUser'))['username'],
								purType,
								dateToSend,
								JSON.parse(localStorage.getItem('currentStore'))['storeId']
								]
								
		}));
	}
}

/******************************************************************************/
/*******************************END POLICY*************************************/
/******************************************************************************/

function loadMyStoresPage() {
	updateCurrentSubscriber();
	setTimeout(function(){
		var storeManager = JSON.parse(localStorage.getItem('currentUser')).manager;
		var storeOwner = JSON.parse(localStorage.getItem('currentUser')).owner;
		
		if((storeManager !== null && storeManager.length > 0) || (storeOwner !== null && storeOwner.length > 0))
		{
			stompClient.disconnect();
			stompClient = null;
			window.location.href = "myStoresPage.html";
		}
		else{
			window.alert("You don\'t own or manage any stores");
		}
	}, 2000);
    //assume current user is subscriber
    
}

function makeMainPage(){
    if(JSON.parse(localStorage.getItem('isSubscriber')) === false) {
        $('#loginMBtn').show();
        $('#signUpMBtn').show();
        $('#openStoreMBtn').hide();
        $('#myStores').hide();
        $('#logOutBtn').hide();
    }
    else
    {
        $('#loginMBtn').hide();
        $('#signUpMBtn').hide();
        $('#openStoreMBtn').show();
        $('#myStores').show();
        $('#logOutBtn').show();
        document.getElementById("unameLbl").innerHTML ="username:\n" + JSON.parse(localStorage.getItem('currentUser'))['username'];
        
    }
}


$(function () {
    connect();
    if(window.location.href.includes("mainPage.html"))
        makeMainPage();
    else{
        //TODO maybe change somehow to switch.
    }
});
