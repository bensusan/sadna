var sub = ""
//Describe 1
function openMainPage() {
    cy.visit('http://localhost:8080')
    cy.wait(500)
    cy.contains('Connect to Trading System').click()
    cy.wait(2000)
}

function titleTest () {
    cy.get('head').title().should('include', 'Trading system')
}

function loginTest(){
    cy.get('#loginMBtn').click().then(()=>{
        cy.url().should('include', '/loginPage.html')
    })
}

function signUpTest(){
    cy.get('#signUpMBtn').click().then(()=>{
        cy.url().should('include', '/signUpPage.html')
    })
}

function cartTest(){
    cy.contains('My Cart').click().then(()=>{
        cy.url().should('include', '/myCart.html')
    })
}

function guestVisibility(){
    //hidden
    cy.get('#systemAdminSection').should('not.exist')
    cy.get('#removeSubscriberBtn').should('not.exist')
    cy.get('#viewSubscriberHistoryBtn').should('not.exist')
    cy.get('#viewStoreHistoryBtn').should('not.exist')
    cy.get('#openStoreMBtn').should('not.exist')
    cy.get('#myStores').should('not.exist')

    //visible
    cy.get('#loginMBtn').should('be.visible')
    cy.get('#signUpMBtn').should('be.visible')
    cy.contains('My Cart').should('be.visible')         //TODO Change to get when they create id for that button
    cy.get('#loadProducts').should('be.visible')
}

//Describe 2
function signUp(){
    cy.get('#signUpMBtn').click().then(()=> {
        sub = 'newUser'.concat((Math.round(Math.random()*1000000)).toString());
        cy.get('#newuserName')
            .type(sub)

        cy.get('#newpassword')
            .type('correctPassword')

        cy.get('#fullname')
            .type('FullName')

        cy.get('#address')
            .type('Address')

        cy.get('#phonenum')
            .type('0501234567')

        cy.get('#signUpBtn').click().then(() => {
            cy.wait(2000)
        })
    })
}

function openStoreTest() {
    cy.get('#openStoreMBtn').click().then(()=>{
        cy.url().should('include', '/openStorePage.html')
    })
}

function myStoresTest() {
    cy.get('#myStores').click().then(()=>{
        cy.url().should('include', '/myStoresPage.html')
    })
}

function myStoresEmptyTest() {
    const stub = cy.stub()
    cy.on('window:alert', stub)
    cy.get('#myStores').click().then(() => {
        expect(stub.getCall(0)).to.be.calledWith('No Stores')
        cy.url().should('include', '/mainPage.html')
    })
}

function subscriberVisibility(){
    //hidden
    cy.get('#systemAdminSection').should('not.exist')
    cy.get('#removeSubscriberBtn').should('not.exist')
    cy.get('#viewSubscriberHistoryBtn').should('not.exist')
    cy.get('#viewStoreHistoryBtn').should('not.exist')
    cy.get('#loginMBtn').should('not.exist')
    cy.get('#signUpMBtn').should('not.exist')

    //visible
    cy.get('#openStoreMBtn').should('be.visible')
    cy.get('#myStores').should('be.visible')
    cy.contains('My Cart').should('be.visible')         //TODO Change to get when they create id for that button
    cy.get('#loadProducts').should('be.visible')   //TODO Change to get when they create id for that button
}

//Describe 3
function loginAsSubscriber(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type(sub)
    cy.get('#password').type('correctPassword')
    cy.get('#loginBtn').click()
}

//Describe 4

function openNewStore() {
    cy.get('#openStoreMBtn').click()
    var sName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newStoreName').type(sName)
    cy.get('#openStoreBtn').click()
}
function loginAsStoreOwnerManager(){
    loginAsSubscriber()
    openNewStore()
}

//Describe 6
function loginAsSystemAdministrator(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type('itzik')
    cy.get('#password').type('11111111')
    cy.get('#loginBtn').click()
}

describe('MainPage Tests Initial', function() {
    beforeEach( ()=> {
        openMainPage()
    })

    it('Title', function() {
        titleTest()
    })

    it('Visibility', function () {
        guestVisibility()
    })

    it('Cart', function () {
        cartTest()
    })

    it('Login', function () {
        loginTest()
    })

    it('Sign up', function () {
        signUpTest()
    })

})


describe('MainPage Tests After Sign up', function() {
    beforeEach( ()=> {
        openMainPage()
        signUp()
    })

    it('Title', function() {
        titleTest()
    })

    it('Visibility', function () {
        guestVisibility()
    })

    it('Cart', function () {
        cartTest()
    })

    it('Login', function () {
        loginTest()
    })

    it('Sign up', function () {
        signUpTest()
    })
})

// //TODO LATER
describe('MainPage Tests After Login as subscriber', function() {
    beforeEach( ()=> {
        openMainPage()
        loginAsSubscriber()
    })

    it('Title', function() {
        titleTest()
    })

    it('Visibility', function () {
        subscriberVisibility()
    })

    it('Cart', function () {
        cartTest()
    })

    it('My stores', function () {
        //assume does not have stores
        myStoresEmptyTest()
    })

    it('Open Store', function () {
        openStoreTest()
    })

})

describe('MainPage Tests After Login as Store Owner/Manager', function() {
    beforeEach( ()=> {
        openMainPage()
        loginAsStoreOwnerManager()
    })

    it('Title', function() {
        titleTest()
    })

    it('Visibility', function () {
        subscriberVisibility()
    })

    it('Cart', function () {
        cartTest()
    })

    it('My stores', function () {
        myStoresTest()
    })

    it('Open Store', function () {
        openStoreTest()
    })

})

describe('MainPage Tests After Login as System Administrator', function() {
    beforeEach( ()=> {
        openMainPage()
        loginAsSystemAdministrator()
    })

    it('Title', function() {
        titleTest()
    })

    it('Visibility', function () {
        //hidden
        cy.get('#systemAdminSection').should('not.exist')
        cy.get('#removeSubscriberBtn').should('not.exist')
        cy.get('#viewSubscriberHistoryBtn').should('not.exist')
        cy.get('#viewStoreHistoryBtn').should('not.exist')
        cy.get('#loginMBtn').should('not.exist')
        cy.get('#signUpMBtn').should('not.exist')

        //visible
        cy.get('#openStoreMBtn').should('be.visible')
        cy.get('#myStores').should('be.visible')
        cy.contains('My Cart').should('be.visible')         //TODO Change to get when they create id for that button
        cy.contains('Load products').should('be.visible')   //TODO Change to get when they create id for that button
    })

    it('Cart', function () {
        cartTest()
    })

    it('Open Store', function () {
        openStoreTest()
    })

    it('Remove Subscriber', function () {
        cy.get('#removeSubscriberBtn').click().then(()=>{
            cy.url().should('include', '/subscribersPage.html')
        })
    })

    it('Watch Subscriber Purchase History', function () {
        cy.get('#viewSubscriberHistoryBtn').click().then(()=>{
            cy.url().should('include', '/subscribersPage.html')
        })
    })

    it('Watch Store Purchase History', function () {
        cy.get('#viewStoreHistoryBt').click().then(()=>{
            cy.url().should('include', '/storePage.html')
        })
    })
})