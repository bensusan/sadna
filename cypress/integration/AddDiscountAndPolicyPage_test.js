var pName = "";
var storeName = "";

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

function backToStorePage(){
    cy.get('#goToMainPageBtn').click()
    cy.get('#myStores').click()
    cy.wait(3000)
    cy.get('#storeIOwnTable').contains(storeName).click()
    cy.wait(3000)
}

function addProduct(){
    cy.get('#0').click()
    cy.wait(3000)
    pName = 'NewProduct'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newProductName').type(pName).should('have.value', pName)
    var price = (Math.round(Math.random()*1000000)).toString()
    cy.get('#newProductPrice').type(price).should('have.value', price)
    var cat = 'toys'
    cy.get('#newProductCategory').type(cat).should('have.value', cat)
    var amount = '1'
    cy.get('#newProductAmount').type(amount).should('have.value', amount)
    cy.get('#addProductBtn').click()
    cy.wait(3000)
}

function loginAsItzik(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type('itzik')
    cy.get('#password').type('11111111')
    cy.get('#loginBtn').click()
    cy.wait(3000)
}

function openStore(){
    cy.get('#openStoreMBtn').click()
    storeName = 'randomStoreNum'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newStoreName').type(storeName)
    cy.get('#openStoreBtn').click()
    cy.wait(3000)
}

function checkPolicyVisibility(){
    cy.get('#discountSection').should('not.exist')
    cy.get('#amountsSection').should('not.exist')
    cy.get('#addDiscountToProductButton').should('not.exist')
    // cy.get('#changeStorePurchasePolicyButton').should('not.exist')
    cy.get('#addDiscountToCategoryStoreButton').should('not.exist')

    cy.get('#emptyPolicy').should('be.visible')
    cy.get('#minPolicy').should('be.visible')
    cy.get('#maxPolicy').should('be.visible')
    cy.get('#orPolicy').should('be.visible')
    cy.get('#andPolicy').should('be.visible')
    // cy.get('#addPolicyToProductButton').should('be.visible')
}

function checkDiscountVisibility(){
    cy.get('#amountsSection').should('not.exist')
    // cy.get('#addDiscountToProductButton').should('not.exist')
    cy.get('#changeStorePurchasePolicyButton').should('not.exist')
    // cy.get('#addDiscountToCategoryStoreButton').should('not.exist')
    cy.get('#addPolicyToProductButton').should('not.exist')


    cy.get('#discountSection').should('not.visible')
    cy.get('#emptyPolicy').should('be.visible')
    cy.get('#minPolicy').should('be.visible')
    cy.get('#maxPolicy').should('be.visible')
    cy.get('#orPolicy').should('be.visible')
    cy.get('#andPolicy').should('be.visible')
}

function policiesCheck(){
    cy.get('#minPolicy').check()
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#maxPolicy').check()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('not.exist')
    cy.get('#minAmountVal').should('not.exist')

    cy.get('#orPolicy').check()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#andPolicy').check()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#emptyPolicy').check()
    cy.get('#maxAmount').should('not.exist')
    cy.get('#maxAmountVal').should('not.exist')
    cy.get('#minAmount').should('not.exist')
    cy.get('#minAmountVal').should('not.exist')
}

function emptyCheck(btnID){
    cy.get('#emptyPolicy').check()
    cy.get(btnID).click()
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function minCheck(btnID){
    cy.get('#minPolicy').check()
    cy.get('#minAmountVal').type('1')
    cy.get(btnID).click()
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function maxCheck(btnID){
    cy.get('#maxPolicy').check()
    cy.get('#maxAmountVal').type('3')
    cy.get(btnID).click()
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function orCheck(btnID){
    cy.get('#orPolicy').check()
    cy.get('#maxAmountVal').type('1')
    cy.get('#minAmountVal').type('5')
    cy.get(btnID).click()
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function andCheck(btnID){
    cy.get('#andPolicy').check()
    cy.get('#maxAmountVal').type('5')
    cy.get('#minAmountVal').type('1')
    cy.get(btnID).click()
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function goInToPolicyPage(numBtnId, isForProduct){
    cy.get(numBtnId).click()
    cy.wait(3000)
    if(isForProduct) {
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
    }
}

function typeCategory(category){
    if(category !== false)
        cy.get('#categoryNameForDiscount').type(category)
}

function typeDiscount(discount) {
    if(discount !== false){
        cy.get('#precentageOfDiscount').type('10')
        //TODO - check it

        cy.get('#endDate').type(tomorrowDate())
        if(discount === 'hidden'){
            cy.get('#hiddenDiscountButton').check()
            cy.get('#codeOfDiscount').type('1')
        }
        else
            cy.get('#overtDiscountButton').check()
    }
}

function beforeChecks(numBtnId, isForProduct, discount, category){
    goInToPolicyPage(numBtnId, isForProduct)
    typeCategory(category)
    typeDiscount(discount)
}

function functions(numBtnId, btnId, isForProduct, discount, category){
    //empty policy test
    beforeChecks(numBtnId, isForProduct, discount, category)
    emptyCheck(btnId)

    //minPolicy test
    beforeChecks(numBtnId, isForProduct, discount, category)
    minCheck(btnId)

    //maxPolicy test
    beforeChecks(numBtnId, isForProduct, discount, category)
    maxCheck(btnId)

    //orPolicy test
    beforeChecks(numBtnId, isForProduct, discount, category)
    orCheck(btnId)

    //andPolicy test
    beforeChecks(numBtnId, isForProduct, discount, category)
    andCheck(btnId)
}


describe('Add Policy or Discount Tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click()
        cy.wait(3000)
        loginAsItzik()
        openStore()
        cy.get('#myStores').click()
        cy.wait(3000)
        cy.get('#storeIOwnTable').contains(storeName).click() //should be there
        cy.wait(3000)
        addProduct()
    })

    it('Add Policy (all the types) to Product Test', function () {
        cy.get('#3').click()
        cy.wait(3000)
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        //checkPolicyVisibility()
        // cy.get('#categoryNameForDiscount').should('not.exist')
        // cy.get('#changeStorePurchasePolicyButton').should('not.exist')
        // cy.get('#addPolicyToProductButton').should('be.visible')
        // policiesCheck()
        backToStorePage()
        functions('#3', '#addPolicyToProductButton', true, false, false)
    })

    it('Add Discount (all the types) to Product Tests', function () {
        cy.get('#4').click()
        cy.wait(3000)
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        //checkDiscountVisibility()
        // cy.get('#categoryNameForDiscount').should('not.exist')
        // cy.get('#addDiscountToCategoryStoreButton').should('not.exist')
        // cy.get('#addDiscountToProductButton').should('be.visible')
        // policiesCheck()
        backToStorePage()
        functions('#4', '#addDiscountToProductButton', true, 'overt', false)
        functions('#4', '#addDiscountToProductButton', true, 'hidden', false)
    })


    //TODO!!!!!!!!!!!!!!!!!!!!!!!!!! for next version!!!!!! now the implementation does not support in policy to category
    // it('Add Policy (all the types) to Store Tests', function () {
    //     cy.get('#11').click()
    //     cy.wait(3000)
    //     cy.url().should('include', '/addDiscountAndPolicyPage.html')
    //     //checkPolicyVisibility()
    //     // cy.get('#categoryNameForDiscount').should('be.visible')
    //     // cy.get('#changeStorePurchasePolicyButton').should('be.visible')
    //     // cy.get('#addPolicyToProductButton').should('not.exist')
    //     // policiesCheck()
    //     backToStorePage()
    //     functions('#11', '#changeStorePurchasePolicyButton', false, false, 'a')
    // })

    it('Add Discount (all the types) to Store Tests', function () {
        cy.get('#12').click()
        cy.wait(3000)
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        //checkPolicyVisibility()
        // cy.get('#categoryNameForDiscount').should('be.visible')
        // cy.get('#addDiscountToProductButton').should('not.exist')
        // cy.get('#addDiscountToCategoryStoreButton').should('be.visible')
        // policiesCheck()
        backToStorePage()
        functions('#12', '#addDiscountToCategoryStoreButton', false, 'overt', 'a')
        functions('#12', '#addDiscountToCategoryStoreButton', false, 'hidden', 'a')
    })
})

//TODO !!!!!! - uncomment all the comments and fix it