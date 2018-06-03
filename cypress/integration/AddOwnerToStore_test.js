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
        cy.wait(3000)
    })
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
    storeName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newStoreName').type(storeName)
    cy.get('#openStoreBtn').click()
    cy.wait(3000)
}

function checkVisibility() {
    cy.get('#0').should('be.visible')
    cy.get('#1').should('be.visible')
    cy.get('#2').should('be.visible')
    cy.get('#3').should('be.visible')
    cy.get('#4').should('be.visible')
    cy.get('#5').should('be.visible')
    cy.get('#6').should('be.visible')
    cy.get('#9').should('be.visible')
    cy.get('#11').should('be.visible')
    cy.get('#12').should('be.visible')
    cy.get('#13').should('be.visible')
}

function login(){
    cy.get('#loginMBtn').click()
    cy.wait(100)
    cy.get('#userName').type(user)
    cy.get('#password').type('correctPassword')
    cy.get('#loginBtn').click()
    cy.wait(3000)
}

describe('Add Store Owner Tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click()
        cy.wait(2000)
        signUp()
        loginAsItzik()
        openStore()
        cy.get('#myStores').click()
        cy.wait(3000)
        cy.get('#storeIOwnTable').contains(storeName).click() //should be there
        cy.wait(3000)
    })

    it('Main Test', function () {
        cy.get('#5').click()
        cy.wait(3000)
        cy.get('#subscribersInSystemTable').contains(user).click() //should be there
        cy.wait(3000)
        cy.url().should('include', '/storePage.html')

        //check that the new owner can access to the store page
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click()
        cy.wait(2000)
        login()
        cy.get('#myStores').click()
        cy.wait(3000)
        cy.get('#storeIOwnTable').contains(storeName).click() //should be there
        cy.wait(3000)
        cy.url().should('include', '/storePage.html')

        // checkVisibility()
    })
})

//TODO!!!!!! uncomment visibility