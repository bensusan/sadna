function signUpButton(msg){
    const stub = cy.stub()
    cy.on('window:alert', stub)
    cy.get('#signUpBtn').click().then(() => {
        expect(stub.getCall(0)).to.be.calledWith(msg)
    })
}

function typeUserName(username){
    cy.get('#newuserName')
        .type(username)
        .should('have.value', username)
}

function typeFakeUserName(){
    typeUserName('fakeUserName')
}

function typePassword(password){
    cy.get('#newpassword')
        .type(password)
        .should('have.value', password)
}

function typeFakePassword() {
    typePassword('fakePassword')
}

function typeFullName(name){
    cy.get('#fullname')
        .type(name)
        .should('have.value', name)
}

function typeFakeFullName(){
    typeFullName('fakeFullName')
}

function typeAddress(address){
    cy.get('#address')
        .type(address)
        .should('have.value', address)
}

function typeFakeAddress() {
    typeAddress('fakeAddress')
}

function typePhone(phonenumber){
    cy.get('#phonenum')
        .type(phonenumber)
        .should('have.value', phonenumber)
}

function typeFakePhone(){
    typePhone('fakePhone')
}

describe('Sign Up Tests', function() {
    beforeEach( ()=> {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            cy.get('#signUpMBtn').click()
            // cy.wait(1000)
        })
    })

    it('Enter Sign Up page', function() {
        cy.get('head').title().should('include', 'Sign Up')
    })

    it('Blank username', function () {
        signUpButton('User name can\'t be blank')
    })

    it('Blank password', function () {
        typeFakeUserName()
        signUpButton('Password can\'t be blank')
    })

    it('Blank fullName', function () {
        typeFakeUserName()
        typeFakePassword()
        signUpButton('fullName can\'t be blank')
    })

    it('Blank address', function () {
        typeFakeUserName()
        typeFakePassword()
        typeFakeFullName()
        signUpButton('address can\'t be blank')
    })

    it('Blank phone', function () {
        typeFakeUserName()
        typeFakePassword()
        typeFakeFullName()
        typeFakeAddress()
        signUpButton('phone can\'t be blank')
    })

    it('Existing username', function () {
        typeUserName('itzik')
        typeFakePassword()
        typeFakeFullName()
        typeFakeAddress()
        typeFakePhone()
        signUpButton('the username is already taken, try again')
    })

    it('Illegal password', function () {
        typeUserName('newUser')
        typePassword('fake!!')
        typeFakeFullName()
        typeFakeAddress()
        typeFakePhone()
        signUpButton('illegal password')
    })

    it('Illegal username', function () {
        typeUserName('newUser!')
        typePassword('correctPassword')
        typeFakeFullName()
        typeFakeAddress()
        typeFakePhone()
        signUpButton('problem with one of the fields,check spelling try again')
    })

    it('Illegal fullname', function () {
        typeUserName('newUser')
        typePassword('correctPassword')
        typeFakeFullName()
        typeFakeAddress()
        typeFakePhone()
        signUpButton('problem with one of the fields,check spelling try again')
    })

    it('Illegal address', function () {
        typeUserName('newUser')
        typePassword('correctPassword')
        typeFullName('FullName')
        typeFakeAddress()
        typeFakePhone()
        signUpButton('problem with one of the fields,check spelling try again')
    })

    it('Illegal phone', function () {
        typeUserName('newUser')
        typePassword('correctPassword')
        typeFakeFullName()
        typeAddress('Address')
        typeFakePhone()
        signUpButton('problem with one of the fields,check spelling try again')
    })

    it('Success Sign Up', function () {
        typeUserName('newUser')
        typePassword('correctPassword')
        typeFullName('FullName')
        typeAddress('Address')
        typePhone('0501234567')

        cy.get('#signUpBtn').click().then(() => {
            cy.url().should('include', '/mainPage.html')
        })
    })

    it('Reset', function () {
        typeFakeUserName()
        typeFakePassword()
        typeFakeFullName()
        typeFakeAddress()
        typeFakePhone()
        cy.get('#signUpBackResetBtn').click().then(()=>{
            cy.get('#newuserName')
                .should('have.value', '')

            cy.get('#newpassword')
                .should('have.value', '')

            cy.get('#fullname')
                .should('have.value', '')

            cy.get('#address')
                .should('have.value', '')

            cy.get('#phonenum')
                .should('have.value', '')
        })
    })

    it('Back', function () {
        cy.get('#signUpBack').click().then(()=>{
            cy.url().should('include', '/mainPage.html')
        })
    })
})