describe('Login Tests', function() {
    beforeEach( ()=> {
        cy.visit('/Login')
        })

    it('Enter Login page', function() {
        cy.contains('title', 'Login')
    })

    it('Blank username', function () {
        cy.contains('Login').click()
        cy.get('.error-messages')
            .should('contains', 'User name can\'t be blank')
    })

    it('Blank password', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.contains('Login').click()

        cy.get('.error-messages')
            .should('contains', 'Password can\'t be blank')
    })

    it('Subscriber does not exists', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.contains('Login').click()

        cy.get('.error-messages')
            .should('contains', 'Incorrect username or password')
    })

    it('Subscriber exists', function () {

        cy.get('.action-username')
            .type('itzik')
            .should('have.value', 'itzik')

        cy.get('.action-password')
            .type('11111111')
            .should('have.value', '11111111')

        cy.contains('Login').click()

        //TODO - need to understand whats happen now. maybe go to home page.

    });
})