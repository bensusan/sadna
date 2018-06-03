var user = ""
function login(username, passsword) {
    cy.get('#loginMBtn').click()
    cy.get('#userName').type(username)
    cy.get('#password').type(passsword)

    cy.get('#loginBtn').click()
    cy.wait(3000)
}

function signUp (){
    user = 'newUserForRemove'.concat((Math.round(Math.random()*1000000)).toString());
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
        login(user, 'correctPassword')
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
        // cy.get('#subscribersInSystemTable').should('include', 'newUserForRemove')
        cy.get('#subscribersInSystemTable').contains(user).click()
        cy.wait(3000)
        cy.url().should('include', '/mainPage.html')

        //check that the subscriber deleted
        cy.get('#removeSubscriberBtn').click()
        cy.get('#subscribersInSystemTable').contains(user).should('not.exist')
        cy.wait(3000)
        //TODO - change to later !!!!
        //check that itzik can not delete himself
        // cy.get('#subscribersInSystemTableBody').should('include', 'itzik')
        cy.get('#subscribersInSystemTable').contains('itzik').click()
        cy.wait(3000)
        cy.url().should('include', '/subscribersPage.html')

        cy.get('#goToMainPageBtn').click()

        //Now lets connect and check that newUserForRemove can not log in to system
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            login(user, 'correctPassword')
            cy.url().should('include', '/loginPage.html')
        })
    })
})