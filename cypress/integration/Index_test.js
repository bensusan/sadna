describe('Index Tests', function() {
    beforeEach( ()=> {
        cy.visit('http://localhost:8080')
        cy.wait(3000)
    })

    it('Title', function() {
        cy.get('head').contains('title', 'Sadna')
    })

    it('Button', function() {
        cy.contains('Connect to Trading System').click()
        cy.wait(3000)
        cy.url().should('include', '/mainPage.html')
    })
})