var user = "";
var storeName = ""
function signUp(){
    cy.get('#signUpMBtn').click().then(()=> {
        user = 'newUser'.concat((Math.round(Math.random()*1000000)).toString());
        cy.get('#newuserName')
            .type(user)

        cy.get('#newpassword')
            .type('correctPassword')

        cy.get('#fullname')
            .type('FullName')

        cy.get('#address')
            .type('Address')

        cy.get('#phonenum')
            .type('0501234567')

        cy.get('#signUpBtn').click()
    })
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

function checkVisibility() {
    cy.get('#1').should('not.exist')
    cy.get('#3').should('not.exist')
    cy.get('#5').should('not.exist')
    cy.get('#9').should('not.exist')
    cy.get('#11').should('not.exist')
    cy.get('#12').should('not.exist')
    cy.get('#13').should('not.exist')

    cy.get('#0').should('be.visible')
    cy.get('#2').should('be.visible')
    cy.get('#4').should('be.visible')
    cy.get('#6').should('be.visible')
}

function login(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type(user)
    cy.get('#password').type('correctPassword')
    cy.get('#loginBtn').click()
}

describe('Add Store Manager Tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click()
        cy.wait(2000)
        signUp()
        loginAsItzik()
        openStore()
        cy.get('#myStores').click()
        cy.get('#storeIOwnTableBody').contains(storeName).click() //should be there
    })

    it('Main Test', function () {
        cy.get('#6').click()
        cy.get('#subscribersInSystemTableBody').contains(user).click() //should be there
        cy.url().should('include', '/permissionsToManager.html')
        cy.get('#0').checked()
        cy.get('#2').checked()
        cy.get('#4').checked()
        cy.get('#6').checked()
        cy.get('#addManagerBtn').click()
        cy.url().should('include', '/storePage.html')

        //check that the new owner can access to the store page
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click()
        cy.wait(2000)
        login()
        cy.get('#myStores').click()
        cy.get('#storeIOwnTableBody').contains(storeName).click() //should be there
        cy.url().should('include', '/storePage.html')

        checkVisibility()
    })
})