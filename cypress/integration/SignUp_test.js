describe('Sign Up Tests', function() {
    beforeEach( ()=> {
        cy.visit('/SignUp')
    })

    it('Enter Sign Up page', function() {
        cy.contains('title', 'Sign Up')
    })

    it('Blank username', function () {
        cy.contains('Sign Up').click()
        cy.get('.error-messages')
            .should('contains', 'User name can\'t be blank')
    })

    it('Blank password', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'Password can\'t be blank')
    })

    it('Blank fullName', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'fullName can\'t be blank')
    })

    it('Blank address', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'address can\'t be blank')
    })

    it('Blank phone', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'phone can\'t be blank')
    })

    it('Blank creditCardNumber', function () {
        cy.get('.action-username')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'creditCardNumber can\'t be blank')
    })

    it('Existing username', function () {
        cy.get('.action-username')
            .type('itzik')
            .should('have.value', 'itzik')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'username exists')
    })

    it('Existing username', function () {
        cy.get('.action-username')
            .type('itzik')
            .should('have.value', 'itzik')

        cy.get('.action-password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'the username is already taken, try again')
    })

    it('Illegal password', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('fake!!')
            .should('have.value', 'fake!!')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'illegal password')
    })

    it('Illegal username', function () {
        cy.get('.action-username')
            .type('newUser!')
            .should('have.value', 'newUser!')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('fakeFullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'problem with one of the fields,check spelling try again')
    })

    it('Illegal fullname', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('fakeFullName!')
            .should('have.value', 'fakeFullName!')

        cy.get('.action-address')
            .type('fakeAddress')
            .should('have.value', 'fakeAddress')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'problem with one of the fields,check spelling try again')
    })

    it('Illegal address', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('FullName')
            .should('have.value', 'FullName')

        cy.get('.action-address')
            .type('fakeAddress!')
            .should('have.value', 'fakeAddress!')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'problem with one of the fields,check spelling try again')
    })

    it('Illegal phone', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('FullName')
            .should('have.value', 'fakeFullName')

        cy.get('.action-address')
            .type('Address')
            .should('have.value', 'Address')

        cy.get('.action-phone')
            .type('fakePhone')
            .should('have.value', 'fakePhone')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'problem with one of the fields,check spelling try again')
    })

    it('Illegal creditCardNumber', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('FullName')
            .should('have.value', 'FullName')

        cy.get('.action-address')
            .type('Address')
            .should('have.value', 'Address')

        cy.get('.action-phone')
            .type('0501234567')
            .should('have.value', '0501234567')

        cy.get('.action-creditCardNumber')
            .type('fakeCreditCardNumber')
            .should('have.value', 'fakeCreditCardNumber')

        cy.contains('Sign Up').click()

        cy.get('.error-messages')
            .should('contains', 'problem with one of the fields,check spelling try again')
    })

    it('Success Sign Up', function () {
        cy.get('.action-username')
            .type('newUser')
            .should('have.value', 'newUser')

        cy.get('.action-password')
            .type('correctPassword')
            .should('have.value', 'correctPassword')

        cy.get('.action-fullName')
            .type('FullName')
            .should('have.value', 'FullName')

        cy.get('.action-address')
            .type('Address')
            .should('have.value', 'Address')

        cy.get('.action-phone')
            .type('0501234567')
            .should('have.value', '0501234567')

        cy.get('.action-creditCardNumber')
            .type('0123456789')
            .should('have.value', '0123456789')

        cy.contains('Sign Up').click()

        //TODO - need to understand whats happen now. maybe go to home page.
    })
})