function login(username, passsword) {
    cy.get('#loginMBtn').click()
    cy.get('#userName').type(username)
    cy.get('#password').type(passsword)

    cy.get('#loginBtn').click()
}

function signUp (){
    typeUserName('newUserForRemove')
    typePassword('correctPassword')
    typeFullName('FullName')
    typeAddress('Address')
    typePhone('0501234567')
    cy.get('#signUpBtn').click().then(() => {
        cy.url().should('include', '/mainPage.html')
    })
}

describe('Remove Subscriber Tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            cy.get('#signUpMBtn').click()
            signUp()
        })
    })

    it('Remove The subscriber', function () {
        //first lets try to login to see that the subscriber is exist
        login('newUserForRemove', 'correctPassword')
        cy.url().should('include', '/mainPage.html')

        //Now lets connect again as itzik
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            login('itzik', '11111111')
            cy.url().should('include', '/mainPage.html')
        })

        //remove the subscriber
        cy.get('#removeSubscriberBtn').click()
        cy.get('#subscribersInSystemTableBody').should('include', 'newUserForRemove')
        cy.get('#subscribersInSystemTableBody').contains('newUserForRemove').click()
        cy.url().should('include', '/mainPage.html')

        //check that the subscriber deleted
        cy.get('#removeSubscriberBtn').click()
        cy.get('#subscribersInSystemTableBody').should('not.include', 'newUserForRemove')

        //TODO - change to later !!!!
        //check that itzik can not delete himself
        cy.get('#subscribersInSystemTableBody').should('include', 'itzik')
        cy.get('#subscribersInSystemTableBody').contains('itzik').click()
        cy.url().should('include', '/subscribersPage.html')

        cy.get('#goToMainPageBtn').click()

        //Now lets connect and check that newUserForRemove can not log in to system
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            login('newUserForRemove', 'correctPassword')
            cy.url().should('include', '/loginPage.html')
        })
    })
})