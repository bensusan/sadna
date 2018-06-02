var pName = "";
function addProduct(){
    cy.get('#0').click()
    pName = 'NewProduct'.concat((Math.round(Math.random()*1000000)).toString())
    cy.get('#newProductName').type(pName).should('have.value', pName)
    var price = (Math.round(Math.random()*1000000)).toString()
    cy.get('#newProductPrice').type(price).should('have.value', price)
    var cat = 'toys'
    cy.get('#newProductCategory').type(cat).should('have.value', cat)
    var amount = '1'
    cy.get('#newProductAmount').type(amount).should('have.value', amount)
    cy.get('#addProductBtn').click()
    cy.wait(2000)
}

describe('Add Purchase Type To Product Tests', function() {
    beforeEach( ()=> {
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
                    cy.get('#myStores').click().then(()=>{
                        cy.wait(300)
                        cy.get('#storeIOwnTableBody').contains(sName).click().then(()=>{
                            cy.wait(300)
                            addProduct()
                        })
                    })

                })
            })
        })
    })

    it('Immediately Purchase Test', function () {
        cy.get('#13').click()
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
        //already checked - therefore
        cy.get('#addPurchaseTypeBtn').click()
        cy.url().should('include', '/storePage.html')
    })

    it('Lottery Purchase Bad Test', function () {
        cy.get('#13').click()
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
        cy.get('#lotteryButton').checked()
        cy.get('#addPurchaseTypeBtn').click()
        cy.url().should('include', '/addPurchaseTypeToProductPage.html')
    })

    it('Lottery Purchase Good Test', function () {
        cy.get('#13').click()
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should be there
        cy.get('#lotteryButton').checked()
        cy.get('#endDate').type(moment().add(1, 'days').calendar()) //TODO - check it
        cy.get('#addPurchaseTypeBtn').click()
        cy.url().should('include', '/storePage.html')
    })
})