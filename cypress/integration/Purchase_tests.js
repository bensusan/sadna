var sName1 = "";
var pName1 = "";
var pName2 = "";

var MAX_PRODUCTS = 10;
var products = [];
var MAX_STORES = 5;
var stores = [];

function tomorrowDate(){
    var currentDate = new Date();
    currentDate.setDate(currentDate.getDate() + 1);
    var day = currentDate.getDate();
    var month = currentDate.getMonth()+1; //January is 0!
    var year = currentDate.getFullYear();
    if(month<10)
        month = '0'+month;
    else
        month = ''+month;
    if(day<10)
        day = '0'+day;
    else
        day = ''+day;
    year = ''+year;
    return year.concat('-').concat(month).concat('-').concat(day);
}

function openMainPage() {
    cy.visit('http://localhost:8080')
    cy.wait(500)
    cy.contains('Connect to Trading System').click()
    cy.wait(3000)
}

function loginAsSystemAdministrator(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type('itzik')
    cy.get('#password').type('11111111')
    cy.get('#loginBtn').click()
    cy.wait(3000)
}

function openStore(sName) {
    cy.get('#openStoreMBtn').click()
    cy.get('#newStoreName').type(sName)
    cy.get('#openStoreBtn').click()
    cy.wait(3000)
}
function addProduct(pName, sName) {
    cy.get('#myStores').click()
    cy.wait(3000)
    cy.get('#storeIOwnTable').contains(sName).click()
    cy.wait(3000)
    cy.get('#0').click()
    cy.get('#newProductName').type(pName)
    var price = (Math.round(Math.random() * 1000000)).toString()
    cy.get('#newProductPrice').type(price)
    var cat = 'toys'
    cy.get('#newProductCategory').type(cat)
    var amount = '10'
    cy.get('#newProductAmount').type(amount)
    cy.get('#addProductBtn').click()
    cy.wait(3000)
}

function typeDiscount(discount) {
    if(discount !== false){
        cy.get('#precentageOfDiscount').type('10')
        cy.get('#endDate').type(tomorrowDate())
        if(discount === 'hidden'){
            cy.get('#hiddenDiscountButton').check()
            cy.get('#codeOfDiscount').clear()
            cy.get('#codeOfDiscount').type('1')
        }
        else
            cy.get('#overtDiscountButton').check()
    }
}

function typePolicy(policy, val1, val2) {
    cy.get('#'.concat(policy).concat('Policy')).check()
    if(policy !== 'empty'){
        if(policy !== 'max' && val1 !== '-1')
            cy.get('#minAmountVal').type(val1)
        if(policy !== 'min' && val2 !== '-1')
            cy.get('#maxAmountVal').type(val2)
    }
}

function addDiscountToProduct(pName, discount, policy, val1, val2){
    cy.get('#4').click()
    cy.wait(3000)
    cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
    cy.wait(3000)
    typeDiscount(discount)
    typePolicy(policy, val1, val2)
    cy.get('#addDiscountToProductButton').click()
    cy.wait(3000)
}

function addToCart(pName, amount, code) {
    cy.get('#loadProducts').click()
    cy.wait(3000)
    cy.get('#mainProductsTable').contains(pName).click()
    cy.wait(3000)
    cy.get('#productAmount').clear()
    cy.get('#productAmount').type(amount)
    cy.get('#discountCode').clear()
    cy.get('#discountCode').type(code)
    cy.get('#addToCartBtn').click()
    cy.wait(3000)
}

function buyCart() {
    cy.get('#myCartBtn').click()
    cy.wait(3000)
    cy.url().should('include', '/myCart.html')

    cy.get('#creditCardInput').type('012345678901234') //valid credit card
    cy.get('#addressInput').type('TestAddress') //valid address
    cy.get('#purchaseCartbtn').click()
    cy.wait(3000)
    cy.url().should('include', '/mainPage.html')
}

function checkSubscriberHistory(pName) {
    cy.get('#viewSubscriberHistoryBtn').click()
    cy.wait(3000)
    cy.get('#subscribersInSystemTable').contains('itzik').click()
    cy.wait(3000)
    cy.get('#purchaseHistoryTable').should('include', pName)
    cy.contains('Main Menu').click()
}

function checkStoreHistory(sName, pName) {
    cy.get('#viewStoreHistoryBtn').click()
    cy.wait(3000)
    cy.get('#storesInSystemTable').contains(sName).click()
    cy.wait(3000)
    cy.get('#purchaseHistoryTable').should('include', pName)
    cy.contains('Main Menu').click()
}

function purchaseItemFromStore(pName, amount, code, sName){
    addToCart(pName,amount,code)
    buyCart()
    checkSubscriberHistory(pName)
    checkStoreHistory(sName, pName)
}

function addDiscountToStore(discount, policy, val1, val2){
    cy.get('#12').click()
    cy.wait(3000)
    cy.get('#categoryNameForDiscount').type('a')
    typeDiscount(discount)
    typePolicy(policy, val1, val2)
    cy.get('#addDiscountToCategoryStoreButton').click()
    cy.wait(3000)
}

//GOOD TESTS
describe('Regular - Purchase Tests', function() {
    beforeEach(() => {
        openMainPage()
        loginAsSystemAdministrator()
        sName1 = 'randStore'.concat((Math.round(Math.random()*1000000)).toString())
        openStore(sName1)
        pName1 = 'pro'.concat((Math.round(Math.random()*1000000)).toString())
        addProduct(pName1, sName1)
        cy.contains('Main Menu').click()
    })

    it('Make the purchase', function () {
        purchaseItemFromStore(pName1, '1', '-1', sName1)
    })
})

describe('Discount to Products - Purchase Tests', function() {
    beforeEach(() => {
        openMainPage()
        loginAsSystemAdministrator()
        sName1 = 'randStore'.concat((Math.round(Math.random()*1000000)).toString())
        openStore(sName1)
        pName1 = 'pro'.concat((Math.round(Math.random()*1000000)).toString())
        addProduct(pName1, sName1)
        addDiscountToProduct(pName1, 'overt', 'empty', '-1', '-1')
        cy.contains('Main Menu').click()
        pName2 = 'pro'.concat((Math.round(Math.random()*1000000)).toString())
        addProduct(pName2, sName1)
        addDiscountToProduct(pName2, 'hidden', 'and', '1', '5')
    })

    it('Overt Discount', function () {
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName1, '1', '-1', sName1)
    })

    it('Hidden Discount', function () {
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName2, '2', '1', sName1)
    })

    it('Add also Overt Discount To Store', function () {
        addDiscountToStore('overt', 'empty','-1', '-1')
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName1, '1', '-1', sName1)
        purchaseItemFromStore(pName2, '2', '1', sName1)
    })

    it('Add also Hidden Discount To Store', function () {
        addDiscountToStore('hidden', 'and', '1', '5')
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName1, '1', '-1', sName1)
        purchaseItemFromStore(pName2, '2', '1', sName1)
    })
})

describe('Discount to Store - Purchase Tests', function() {
    beforeEach(() => {
        openMainPage()
        loginAsSystemAdministrator()
        sName1 = 'randStore'.concat((Math.round(Math.random()*1000000)).toString())
        openStore(sName1)
        pName1 = 'pro'.concat((Math.round(Math.random()*1000000)).toString())
        addProduct(pName1, sName1)
    })

    it('Overt Discount', function () {
        addDiscountToStore('overt', 'empty','-1', '-1')
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName1, '1', '-1', sName1)
    })

    it('Hidden Discount', function () {
        addDiscountToStore('hidden', 'and', '1', '5')
        cy.contains('Main Menu').click()
        purchaseItemFromStore(pName1, '2', '1', sName1)
    })
})

describe('Buy from all the kinds 4 Different Stores - Purchase Tests', function() {
    beforeEach(() => {
        products = []
        for (i = 0; i < MAX_PRODUCTS; i++) {
            products.push('pro'.concat((Math.round(Math.random()*1000000)).toString()))
        }
        stores = []
        for (i = 0; i < MAX_STORES; i++) {
            stores.push('randStore'.concat((Math.round(Math.random()*1000000)).toString()))
        }
        openMainPage()
        loginAsSystemAdministrator()

        openStore(stores[0]) //Regular store
        addProduct(products[0], stores[0])
        cy.contains('Main Menu').click()
        addProduct(products[1], stores[0])
        addDiscountToProduct(products[1], 'overt', 'empty', '-1', '-1')
        cy.contains('Main Menu').click()
        addProduct(products[2], stores[0])
        addDiscountToProduct(products[2], 'hidden', 'and', '1', '5')
        cy.contains('Main Menu').click()

        openStore(stores[1]) //Overt Discount to 'a' category store
        addProduct(products[3], stores[1])
        cy.contains('Main Menu').click()
        addProduct(products[4], stores[1])
        addDiscountToProduct(products[4], 'overt', 'empty', '-1', '-1')
        cy.contains('Main Menu').click()
        addProduct(products[5], stores[1])
        addDiscountToProduct(products[5], 'hidden', 'and', '1', '5')
        addDiscountToStore('overt', 'empty','-1', '-1')
        cy.contains('Main Menu').click()

        openStore(stores[2]) //Hidden Discount to 'a' category store
        addProduct(products[6], stores[2])
        cy.contains('Main Menu').click()
        addProduct(products[7], stores[2])
        addDiscountToProduct(products[7], 'overt', 'empty', '-1', '-1')
        cy.contains('Main Menu').click()
        addProduct(products[8], stores[2])
        addDiscountToProduct(products[8], 'hidden', 'and', '1', '5')
        addDiscountToStore('hidden', 'and', '1', '5')
        cy.contains('Main Menu').click()
    })

    it('Purchase all the products separately', function () {
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                var code = '-1';
                if(j == 2)
                    code = '1'
                purchaseItemFromStore(products[i*3 + j], '1', code, stores[i]);
            }
        }
    });

    it('Purchase all the products together', function () {
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                var code = '-1';
                if(j == 2)
                    code = '1'
                addToCart(products[i*3 + j], '1', code)
            }
        }
        buyCart()
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                checkSubscriberHistory(products[i*3 + j])
                checkStoreHistory(stores[i], products[i*3 + j])
            }
        }
    });
})


//TODO - Next version
// //BAD TESTS


//TODO - Should add it!!!!!!
describe('Regular - Purchase Tests', function() {
    it('Buy empty cart', function () {
        cy.get('#myCartBtn').click()
        cy.wait(3000)
        cy.url().should('include', '/mainPage.html')
    });
})

// describe('Amount out of Product\'s Range - Purchase Tests', function() {
//     beforeEach(() => {
//         cy.visit('http://localhost:8080')
//     })
// })
//
// describe('Amount out of Store\'s Category Range - Purchase Tests', function() {
//     beforeEach(() => {
//         cy.visit('http://localhost:8080')
//     })
// })
