var pName = "";
var storeName = "";
function addProduct(){
    cy.get('#0').click()
    pName = 'NewProduct'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newProductName').type(pName).should('have.value', pName)
    var price = (Math.round(Math.random()*1000000)).toString()
    cy.get('#newProductPrice').type(price).should('have.value', price)
    var cat = 'toys'
    cy.get('#newProductCategory').type(cat).should('have.value', cat)
    var amount = '1'
    cy.get('#newProductAmount').type(amount).should('have.value', amount)
    cy.get('#addProductBtn').click()
    cy.wait(2000)
}

function loginAsItzik(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type('itzik')
    cy.get('#password').type('11111111')
    cy.get('#loginBtn').click()
}

function openStore(){
    storeName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newStoreName').type(storeName)
    cy.get('#openStoreBtn').click()
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
    cy.get('#minPolicy').checked()
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#maxPolicy').checked()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('not.exist')
    cy.get('#minAmountVal').should('not.exist')

    cy.get('#orPolicy').checked()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#andPolicy').checked()
    cy.get('#maxAmount').should('be.visible')
    cy.get('#maxAmountVal').should('be.visible')
    cy.get('#minAmount').should('be.visible')
    cy.get('#minAmountVal').should('be.visible')

    cy.get('#emptyPolicy').checked()
    cy.get('#maxAmount').should('not.exist')
    cy.get('#maxAmountVal').should('not.exist')
    cy.get('#minAmount').should('not.exist')
    cy.get('#minAmountVal').should('not.exist')
}

function emptyCheck(btnID){
    cy.get(btnID).click()
    cy.url().should('include', '/storePage.html')
}

function minCheck(btnID){
    cy.get('#minPolicy').checked()
    cy.get('#minAmountVal').type('1')
    cy.get(btnID).click()
    cy.url().should('include', '/storePage.html')
}

function maxCheck(btnID){
    cy.get('#maxPolicy').checked()
    cy.get('#maxAmountVal').type('3')
    cy.get(btnID).click()
    cy.url().should('include', '/storePage.html
}

function orCheck(btnID){
    cy.get('#orPolicy').checked()
    cy.get('#maxAmountVal').type('1')
    cy.get('#minAmountVal').type('5')
    cy.get(btnID).click()
    cy.url().should('include', '/storePage.html')
}

function andCheck(btnID){
    cy.get('#andPolicy').checked()
    cy.get('#maxAmountVal').type('5')
    cy.get('#minAmountVal').type('1')
    cy.get(btnID).click()
    cy.url().should('include', '/storePage.html')
}

function goInToPolicyPage(numBtnId, isForProduct){
    cy.get(numBtnId).click()
    if(isForProduct)
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
}

function typeCategory(category){
    if(category !== false)
        cy.get('#categoryNameForDiscount').type(category)
}

function typeDiscount(discount) {
    if(discount !== false){
        cy.get('#precentageOfDiscount').type('10')
        //TODO - check it
        cy.get('#endDate').type(moment().add(1, 'days').calendar())
        if(discount === 'hidden'){
            cy.get('#hiddenDiscountButton').checked()
            cy.get('#codeOfDiscount').type('1')
        }
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
        cy.wait(2000)
        loginAsItzik()
        openStore()
        cy.get('#myStores').click()
        cy.get('#storeIOwnTableBody').contains(storeName).click() //should be there
        addProduct()
    })

    it('Add Policy (all the types) to Product Test', function () {
        cy.get('#3').click()
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        checkPolicyVisibility()
        cy.get('#categoryNameForDiscount').should('not.exist')
        cy.get('#changeStorePurchasePolicyButton').should('not.exist')
        cy.get('#addPolicyToProductButton').should('be.visible')
        policiesCheck()
        functions('#3', '#addPolicyToProductButton', true, false, false)
    })

    it('Add Discount (all the types) to Product Tests', function () {
        cy.get('#4').click()
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        checkDiscountVisibility()
        cy.get('#categoryNameForDiscount').should('not.exist')
        cy.get('#addDiscountToCategoryStoreButton').should('not.exist')
        cy.get('#addDiscountToProductButton').should('be.visible')
        policiesCheck()
        functions('#4', '#addDiscountToProduct', true, 'overt', false)
        functions('#4', '#addDiscountToProduct', true, 'hidden', false)
    })

    it('Add Policy (all the types) to Store Tests', function () {
        cy.get('#11').click()
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        checkPolicyVisibility()
        cy.get('#categoryNameForDiscount').should('be.visible')
        cy.get('#changeStorePurchasePolicyButton').should('be.visible')
        cy.get('#addPolicyToProductButton').should('not.exist')
        policiesCheck()
        functions('#11', '#changeStorePurchasePolicyButton', false, false, 'a')
    })

    it('Add Discount (all the types) to Store Tests', function () {
        cy.get('#12').click()
        cy.url().should('include', '/addDiscountAndPolicyPage.html')
        checkPolicyVisibility()
        cy.get('#categoryNameForDiscount').should('be.visible')
        cy.get('#addDiscountToProductButton').should('not.exist')
        cy.get('#addDiscountToCategoryStoreButton').should('be.visible')
        policiesCheck()
        functions('#12', '#changeStorePurchasePolicyButton', false, 'overt', 'a')
        functions('#12', '#changeStorePurchasePolicyButton', false, 'hidden', 'a')
    })
})