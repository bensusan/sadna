var sName = "";
describe('Storage Page tests', function() {
    beforeEach(() => {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(2000)
            cy.get('#loginMBtn').click().then(()=>{
                cy.get('#userName').type('itzik')
                cy.get('#password').type('11111111')
                cy.get('#loginBtn').click().then(()=>{
                    cy.wait(2000)
                    cy.get('#openStoreMBtn').click()
                    sName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
                    cy.get('#newStoreName').type(sName)
                    cy.get('#openStoreBtn').click()
                    cy.wait(2000)
                    cy.get('#myStores').click()
                    cy.get('#storeIOwnTableBody').contains(sName).click()
                })
            })
        })
    })

    it('Store Name correct', function () {
        cy.get('#storeNameInStorePage').should('include', sName)
    })

    it('Move between pages using buttons', function () {
        cy.get('#0').click().then(()=>{
            cy.url().should('include', '/addProductPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#1').click().then(()=>{
            cy.url().should('include', '/storeProductsPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#2').click().then(()=>{
            cy.url().should('include', '/storeProductsPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#3').click().then(()=>{
            cy.url().should('include', '/storeProductsPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#4').click().then(()=>{
            cy.url().should('include', '/storeProductsPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#5').click().then(()=>{
            cy.url().should('include', '/subscribersPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#6').click().then(()=>{
            cy.url().should('include', '/subscribersPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#9').click().then(()=>{
            cy.url().should('include', '/purchaseHistoryOfStorePage.html')  //TODO - change to the real name
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#11').click().then(()=>{
            cy.url().should('include', '/addDiscountAndPolicyPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#12').click().then(()=>{
            cy.url().should('include', '/addDiscountAndPolicyPage.html')
        })
        cy.get('backBtn').click() //TODO - change when will create
        cy.get('#13').click().then(()=>{
            cy.url().should('include', '/storeProductsPage.html')
        })
    })
})