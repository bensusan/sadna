var pName = "";
var storeName = "";
function backToStorePage(){
    cy.get('#goToMainPageBtn').click()
    cy.get('#myStores').click()
    cy.wait(3000)
    cy.get('#storeIOwnTable').contains(storeName).click()
    cy.wait(3000)
}

function addProduct() {
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
    cy.wait(3000)
    cy.url().should('include', '/storePage.html')
}

function goToStoresPage() {
    cy.visit('http://localhost:8080')
    cy.wait(500)
    cy.contains('Connect to Trading System').click().then(()=>{
        cy.wait(3000)
        cy.get('#loginMBtn').click().then(()=>{
            cy.get('#userName').type('itzik')
            cy.get('#password').type('11111111')
            cy.get('#loginBtn').click()
                // .then(()=>{
            cy.wait(3000)
            cy.get('#openStoreMBtn').click()
            storeName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
            cy.get('#newStoreName').type(storeName)
            cy.get('#openStoreBtn').click()
            cy.wait(3000)
            cy.get('#myStores').click()
            cy.wait(3000)
            cy.get('#storeIOwnTable').contains(storeName).click()
            cy.wait(3000)
            // })
        })
    })
}

describe('ProductPage tests', function() {
    beforeEach(() => {
        goToStoresPage()
    })

    it('Add Product', function () {
        //take the number of products in store before

        cy.get('#2').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        // cy.get('#ProductsInStoreTable').contains(pName).should('not.exist')
        backToStorePage()

        //Add product
        addProduct()

        //check that the number of products is numberOfProducts + 1
        cy.get('#2').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTable').contains(pName) //should exist because of the last test
    })

    it('Edit Product', function () {
        addProduct()

        var beforeProductName = pName;
        cy.get('#2').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTable').contains(pName).click() //should exist because of the last test
        cy.wait(3000)
        pName = 'NewProduct'.concat((Math.round(Math.random()*1000000)).toString())
        cy.get('#editnewProductName').type(pName)
        cy.get('#editnewProductPrice').type(100)
        cy.get('#editnewProductCategory').type('a')
        cy.get('#editnewProductAmount').type('111')
        cy.get('#editBtn').click()
        cy.wait(3000)
        cy.url().should('include', '/storePage.html')

        //check that indeed the product edited
        cy.get('#2').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTable').contains(pName) //should exist because of the last test
        cy.get('#ProductsInStoreTable').contains(beforeProductName).should('not.exist')
    })

    it('Delete Product', function () {
        addProduct()

        cy.get('#1').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTable').contains(pName).click() //should exist because of the last test
        cy.wait(3000)
        cy.url().should('include', '/storePage.html')

        //check that indeed the product delete - number of products should be numberOfProducts-1
        cy.get('#1').click()
        cy.wait(3000)
        cy.url().should('include', '/storeProductsPage.html')
        cy.get('#ProductsInStoreTable').contains(pName).should('not.exist')
    })
})