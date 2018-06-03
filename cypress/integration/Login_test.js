describe('Login Tests', function() {
    beforeEach( ()=> {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(3000)
            cy.get('#loginMBtn').click()
        })

    })

    it('Enter Login page', function() {
        cy.get('head').title().should('include', 'Login')
    })

    it('Blank username', function () {
        // const stub = cy.stub()
        // cy.on('window:alert', stub)
        cy.get('#loginBtn').click()
        //     .then(() => {
        //     expect(stub.getCall(0)).to.be.calledWith('User name can\'t be blank')
        // })
        cy.url().should('include', '/loginPage.html')
    })

    it('Blank password', function () {
        cy.get('#userName')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#loginBtn').click()
        //     .then(() => {
        //     expect(stub.getCall(0)).to.be.calledWith('Password can\'t be blank')
        // })
        cy.url().should('include', '/loginPage.html')
    })

    it('Subscriber does not exists', function () {
        cy.get('#userName')
            .type('fakeUserName')
            .should('have.value', 'fakeUserName')

        cy.get('#password')
            .type('fakePassword')
            .should('have.value', 'fakePassword')

        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#loginBtn').click()
        // .then(() => {
        //     expect(stub.getCall(0)).to.be.calledWith('Incorrect username or password')
        // })
        cy.url().should('include', '/loginPage.html')
    })

    it('Subscriber exists', function () {

        cy.get('#userName')
            .type('itzik')
            .should('have.value', 'itzik')

        cy.get('#password')
            .type('11111111')
            .should('have.value', '11111111')

        cy.get('#loginBtn').click()
            // .then(() => {
        cy.wait(3000)
        cy.url().should('include', '/mainPage.html')
        // })
    })
})