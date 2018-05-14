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
                    case "myStoresPage":
                    	recieveMyStoresMsg(body.functionName, obj);
                    	break;
                    default:
                        break;
                }
            }
        });
    });
}
// localStorage.setItem('mapForTable', JSON.stringify({}));
// var mapForTable = null;
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

function recieveLoginPageMsg(funcName, obj) {
    switch (funcName){
        case "signIn":
            localStorage.setItem('currentUser', JSON.stringify(obj));
            localStorage.setItem('isSubscriber', JSON.stringify(true));
            if(window.confirm("shit2222"))
        		window.alert("shit");
            loadMainPage();
            break;
        default:
            break;
    }
}

function recieveSignUpMsg(funcName, obj) {
	window.alert("YOU");
	if(window.confirm("shit3333"))
        		window.alert("shit");
    switch (funcName){
        case "signUp":
        if(window.confirm("shit3333"))
        		window.alert("shit");
            loadMainPage();
            break;
        default:
            break;
    }
}

function recieveOpenStoreMsg(funcName, obj) {
    switch (funcName){
        case "openStore":
        	//localStorage.currentUser.owner = obj.owner;
        	if(window.confirm("shit4444"))
        		window.alert("shit");
            loadMainPage();
            break;
        default:
            break;
    }
}

function recieveMyStoresMsg(funcName, obj) {
    switch (funcName){
        case "getAllSubscriberStores":
            localStorage.setItem('stores', JSON.stringify(true));
             window.alert("shit111");
              window.alert(JSON.stringify(JSON.parse(localStorage.getItem('stores'))));
            loadMainPage();
            break;
        default:
            break;
    }
}


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
	
	 var sub = JSON.parse(localStorage.getItem('currentUser'));
	 if(sub == null){
	      window.alert("shit");
	 	}
	
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
    //assume current user is subscriber
    getMyStores();
    stompClient.disconnect();
    stompClient = null;
    window.location.href = "myStoresPage.html";
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
	
	loadMainPage();
}

function openStore(){
    stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "openStorePage",
				    	'functionName': "openStore",
				    	'paramsAsJSON': [	localStorage.getItem('currentUser'),	//will be store owner
				    						$("#storeName").val(),			//store name
				    						$("#grading").val(),				//number of policy
				    						Boolean(true)
				    					]
				    }
	));
	
	loadMainPage();
}

function getMyStores(){
    stompClient.send("/app/hello", {},
    JSON.stringify(
				    {	'pageName': "myStoresPage",
				    	'functionName': "getAllSubscriberStores",
				    	'paramsAsJSON': [	localStorage.getItem('currentUser') 	//will be store owner
				    					]
				    }
	));
	
}


$(function () {
    connect();
    if(window.location.href.includes("mainPage.html"))
        makeMainPage();
    else{
	  
   	}
});
