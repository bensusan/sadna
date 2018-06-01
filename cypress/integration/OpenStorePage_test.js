describe('Open Store tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            cy.get('#loginMBtn').click().then(()=>{
                cy.wait(2000)
                cy.get('#userName').type('itzik')
                cy.get('#password').type('11111111')
                cy.get('#loginBtn').click().then(()=>{
                    cy.get('#openStoreMBtn').click()
                })
            })
        })
    })

    it('Bad open', function () {
        cy.get('#newStoreName').type('!##@$\'')
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#openStoreBtn').click()
        cy.url().should('include', '/openStorePage.html')
    })

    it('Success', function () {
        var sName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
        cy.get('#newStoreName').type(sName)
        const stub = cy.stub()
        cy.on('window:alert', stub)
        cy.get('#openStoreBtn').click()
        // cy.wait(2000)
        // expect(stub.getCall(0)).to.be.calledWith('Store \"'.concat(sName).concat('\" opened succesfuly!'))
        cy.url().should('include', '/mainPage.html')
    })

    it('Reset', function () {
        cy.get('#newStoreName').type('Something').should('have.value', 'Something')
        cy.get('#openStoreReset').click().then(()=>{
            cy.get('#newStoreName')
                .should('have.value', '')
        })
    })

    it('Back', function () {
        cy.get('#openStoreBackBtn').click().then(()=>{
            cy.url().should('include', '/mainPage.html')
        })
    })
})