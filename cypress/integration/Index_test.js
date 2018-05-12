describe('Index Tests', function() {
    beforeEach( ()=> {
        cy.visit('/')
    })

    it('Enter Welcome page', function() {
        cy.contains('title', 'Trading system')
    })


})