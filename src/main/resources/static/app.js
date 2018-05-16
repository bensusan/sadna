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
						window.alert("Entered line 39");
						recieveUpdateCurrentSubscriber(body.functionName, obj);
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

//signIn(Guest g, String userName, String password) : Subscriber
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
	window.alert("enter OS");
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

function loadMyStoresPage() {
	window.alert(localStorage.getItem('currentUser'));
    //assume current user is subscriber
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
