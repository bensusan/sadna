var stompClient = null;

var currentUser = {'cart': {'products': []}};

function setUPOnce(){
    localStorage.setItem('currentUser', JSON.stringify({'cart': {'products': []}})); //New Guest for start
    localStorage.setItem('isSubscriber', JSON.stringify(false));
    localStorage.setItem('specificProduct', JSON.stringify(null));
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
					case "addPurchaseTypeToProductPage":
						recieveAddPurchaseTypeToProductPage(body.functionName, obj);
						break;
						
                    default:
                        break;
                }
            }
        });
    });
}


//****RECIEVE MESSAGE FROM SERVER****//
function recieveMainPageMsg(funcName, obj) {
    switch (funcName){
        case "getAllStoresWithThierProductsAndAmounts":
            localStorage.setItem('mapForTable', JSON.stringify(obj));
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "mainPage.html";
            break;
        default:
            break;
    }
}

function recieveAddPurchaseTypeToProductPage(funcName, obj) {
	switch (funcName){
        case "changeProductType":
            window.alert("Product new purchase type added succesfully!");
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
            window.alert("Product Update succesfully");
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
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
			window.alert("Product was deleted succesfully!");
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
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
			window.alert("New Store Owner added succesfully!");
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
		case "addNewManager":
			window.alert("New Store Manager added succesfully!");
			stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
			break;
        default:
            break;
    }
}

function recieveAddProduct(funcName, obj) {
    switch (funcName){
        case "addProductToStore":
			window.alert("New Product was added succesfully!");
            stompClient.disconnect();
            stompClient = null;
            window.location.href = "storePage.html";
            break;
        default:
            break;
    }
}

function recieveLoginPageMsg(funcName, obj) {
    switch (funcName){
        case "signIn":
            localStorage.setItem('currentUser', JSON.stringify(obj));
            localStorage.setItem('isSubscriber', JSON.stringify(true));
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
			window.alert("Store " + JSON.stringify(obj['name']) + " opened succesfuly!");
			updateCurrentSubscriber();
			setTimeout(function(){
			loadMainPage();
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
				    						$("#newStoreName").val()			//store name
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
				    						$("#newProductName").val(),			//store name
											$("#newProductPrice").val(),
											$("#newProductCategory").val(),
											$("#newProductAmount").val(),
											localStorage.getItem('isOwner')
				    					]
				    }
	));
}

/***LOAD FUNCTIONS***/
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
    // obj = {s:{p:1}}; //example for the loop
    var found = false;
    Object.entries(obj).map(([s, pAndA]) => {
        Object.entries(pAndA).map(([p, amount]) => {
            if (amount > 0) {
                var toShow = "Store - id: " + s.storeId + " name: " + s.name + " Product - id: " + p.id + " name: " + p.name;
                var pAsJson = JSON.stringify(p);
                $('#myTable').append('<button onclick="loadProductPage("+ pAsJson + ")">toShow</button>');
                found = true;
            }
        });
    });

    if(!found)
        window.alert("No Products");
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

function addNewStoreManager(usernameToAdd){
	localStorage.setItem('usernameToAddAsManager', usernameToAdd);
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "permissionsToManager.html";
	

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
							$("#editnewProductName").val(),
							$("#editnewProductPrice").val(),
							$("#editnewProductCategory").val(),
							$("#editnewProductAmount").val()
							]
							
        }));
}



function addPolicyToProduct(id){
	window.alert("Need to Imple add policy to product in store");
}

function addDiscountToProduct(id){
	window.alert("Need to Imple add discount to product in store");
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

function loadAllUsersPage(action){
	localStorage.setItem('actionOnSubscriber', action);
	stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "subscribersPage",
            'functionName': "getAllSubscribersWithPotential",
            'paramsAsJSON': []
        }));
}

function loadEditOwnStore(store){
	localStorage.setItem('currentStore', JSON.stringify(store));
	localStorage.setItem('isOwner', JSON.stringify(true));
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "storePage.html";
}

function loadEditManageStore(store){
	localStorage.setItem('currentStore', JSON.stringify(store));
	localStorage.setItem('isOwner', JSON.stringify(false));
	stompClient.disconnect();
    stompClient = null;
    window.location.href = "storePage.html";
}

function loadMainPage() {
    stompClient.send("/app/hello", {},
    JSON.stringify(
        {	'pageName': "mainPage",
            'functionName': "getAllStoresWithThierProductsAndAmounts",
            'paramsAsJSON': []
        }));
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
    window.location.href = "cartPage.html";
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
    }
    else
    {
        $('#loginMBtn').hide();
        $('#signUpMBtn').hide();
        $('#openStoreMBtn').show();
        $('#myStores').show();
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
