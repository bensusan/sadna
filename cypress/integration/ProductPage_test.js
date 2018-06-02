var pName = "";
function gotoStoresPage() {
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
                var sName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
                cy.get('#newStoreName').type(sName)
                cy.get('#openStoreBtn').click()
                cy.wait(2000)
                cy.get('#myStores').click()
                cy.get('#storeIOwnTableBody').contains(sName).click()
            })
        })
    })
}

describe('ProductPage tests', function() {
    beforeEach(() => {
        gotoStoresPage()
    })

    it('Add Product', function () {
        //take the number of products in store before
        cy.get('#2').click()
        cy.url().should('include', '/storeProductsPage.html')
        var numberOfProducts = document.getElementById("#ProductsInStoreTableBody").rows.length;
        cy.get('#backBtn').click()      //TODO change id when the button will create

        //Add product
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
        cy.url().should('include', '/storePage.html')

        //check that the number of products is numberOfProducts + 1
        cy.get('#2').click()
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTableBody').should('have.length', numberOfProducts+1)
    })

    it('Edit Product', function () {
        cy.get('#2').click()
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should exist because of the last test
        pName = 'NewProduct'.concat((Math.round(Math.random()*1000000)).toString())
        cy.get('#editnewProductName').type(pName)
        cy.get('#editnewProductPrice').type(100)
        cy.get('#editnewProductCategory').type('a')
        cy.get('#editnewProductAmount').type('111')
        cy.get('#editBtn').click()
        cy.wait(2000)
        cy.url().should('include', '/storePage.html')

        //check that indeed the product edited
        cy.get('#2').click()
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTableBody').should('contains', pName) //should exist because of the last test
    })

    it('Delete Product', function () {
        cy.get('#1').click()
        cy.url().should('include', '/storeProductsPage.html')
        var numberOfProducts = document.getElementById("#ProductsInStoreTableBody").rows.length;
        cy.get('#ProductsInStoreTableBody').contains(pName).click() //should exist because of the last test
        cy.url().should('include', '/storePage.html')

        //check that indeed the product delete - number of products should be numberOfProducts-1
        cy.get('#1').click()
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTableBody').should('have.length', numberOfProducts-1)
    })
})