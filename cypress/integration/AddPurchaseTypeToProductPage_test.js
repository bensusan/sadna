var pName = "";
var sName = "";
function tomorrowDate(){
    var currentDate = new Date();
    currentDate.setDate(currentDate.getDate() + 1);
    var day = currentDate.getDate();
    var month = currentDate.getMonth()+1; //January is 0!
    var year = currentDate.getFullYear();
    if(month<10)
        month = '0'+month;
    else
        month = ''+month;
    if(day<10)
        day = '0'+day;
    else
        day = ''+day;
    year = ''+year;
    return year.concat('-').concat(month).concat('-').concat(day);
}

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
    cy.wait(3000)
}

describe('Add Purchase Type To Product Tests', function() {
    beforeEach( ()=> {
        cy.visit('http://localhost:8080')
        cy.wait(500)
        cy.contains('Connect to Trading System').click().then(()=>{
            cy.wait(3000)
            cy.get('#loginMBtn').click().then(()=>{
                cy.get('#userName').type('itzik')
                cy.get('#password').type('11111111')
                cy.get('#loginBtn').click().then(()=>{
                    cy.wait(3000)
                    cy.get('#openStoreMBtn').click()
                    sName = 'randomStore'.concat((Math.round(Math.random()*1000000)).toString())
                    cy.get('#newStoreName').type(sName)
                    cy.get('#openStoreBtn').click()
                    cy.wait(3000)
                    cy.get('#myStores').click()
                    cy.wait(3000)
                    cy.get('#storeIOwnTable').contains(sName).click()
                    cy.wait(3000)
                    addProduct()
                })
            })
        })
    })

    it('Immediately Purchase Test', function () {
        cy.get('#13').click()
        cy.wait(3000)
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
        //already check - therefore
        cy.get('#addPurchaseTypeBtn').click()
        cy.wait(3000)
        cy.url().should('include', '/storePage.html')
    })

    it('Lottery Purchase Bad Test', function () {
        cy.get('#13').click()
        cy.wait(3000)
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
        cy.get('#lotteryButton').check()
        cy.get('#addPurchaseTypeBtn').click()
        cy.wait(3000)
        cy.url().should('include', '/addPurchaseTypeToProductPage.html')
    })

    it('Lottery Purchase Good Test', function () {
        cy.get('#13').click()
        cy.wait(3000)
        cy.get('#ProductsInStoreTable').contains(pName).click() //should be there
        cy.wait(3000)
        cy.get('#lotteryButton').check()
        cy.get('#endDate').type(tomorrowDate()) //TODO - check it
        cy.get('#addPurchaseTypeBtn').click()
        cy.url().should('include', '/storePage.html')
    })
})