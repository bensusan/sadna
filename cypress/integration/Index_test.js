describe('Index Tests', function() {
    beforeEach( ()=> {
        cy.visit('http://localhost:8080')
    })

    it('Title', function() {
        cy.get('head').contains('title', 'Sadna')
    })

    it('Button', function() {
        cy.contains('Connect to Trading System').click()
        cy.url().should('include', '/mainPage.html')
    })
})