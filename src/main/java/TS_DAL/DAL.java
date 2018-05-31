package TS_DAL;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class DAL {
	//TINYINT used for boolean, 0-false;1-true

	private Connection conn = null;
	private Statement stmt = null;

	private final static int emptyPolicyTypeCode = 0, andPolicyTypeCode = 1, orPolicyTypeCode = 2,
			notPolicyTypeCode = 3, maxPolicyTypeCode = 4, minPolicyTypeCode = 5;
	
	
	private static DAL dal = new DAL();
	private DAL(){

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql;
			try{
				sql = "CREATE DATABASE TradingSystem";
				stmt.executeUpdate(sql);
			}
			catch (SQLException sqlException) {
				if (sqlException.getErrorCode() == 1007) {
					// Database already exists error
					sql = "DROP DATABASE TradingSystem";
					stmt.executeUpdate(sql);
				}
			}
			sql = "USE TradingSystem";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Subscribers(username VARCHAR(50),"
					+ " password VARCHAR(50),"
					+ " fullname VARCHAR(50),"
					+ " address VARCHAR(50),"
					+ " phone VARCHAR(50),"
					+ " credidCardNumber VARCHAR(50),"
					+ " PRIMARY KEY (username));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Admins(username VARCHAR(50),"
					+ " PRIMARY KEY (username));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE StoreOwners(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " storeId INTEGER REFERENCES Stores(storeId) ,"
					+ "PRIMARY KEY (username, storeId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Stores(storeId INTEGER,"
					+ " name VARCHAR(50),"
					+ " address VARCHAR(50),"
					+ " phone VARCHAR (50), "
					+ "grading INTEGER, "
					+ "isOpen TINYINT(1), "
					+ "moneyEarned INTEGER,"
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (storeId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInStores(storeId INTEGER REFERENCES Stores(storeId),"
					+ "productId INTEGER REFERENCES Products(productId),"
					+ "amount INTEGER, "
					+ "PRIMARY KEY (storeId, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Purchased(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " storeId INTEGER REFERENCES Stores(storeId),"
					+ " whenPurchased DATE,"
					+ " price INTEGER,"
					+ " amount INTEGER,"
					+ "PRIMARY KEY (username, productId, whenPurchased));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE Policies("
					+ "policyId INTEGER,"
					+ " policyType INTEGER,"
					+ " IValue INTEGER,"//save int value such max,min...
					+ " SValue VARCHAR(50),"//save string value such username,address...
					+ "PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE SubPolicies("
					+ "fatherPolicyId INTEGER REFERENCES Policies(policyId),"
					+ "sonPolicyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (fatherPolicyId, sonPolicyId));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE CategoryDiscont("
					+ "storeId INTEGER REFERENCES Stores(storeId),"
					+ "categoryName INTEGER,"
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (storeId, categoryName));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Products(productId INTEGER,"
					+ " name VARCHAR(50),"
					+ " price INTEGER,"
					+ " grading INTEGER ,"
					+ " category VARCHAR(50),"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ImmediatelyPurchases("
					+ "productId INTEGER REFERENCES Products(productId),"
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE LottaryPurchases("
					+ "productId INTEGER REFERENCES Products(productId),"
					+ "lottaryId INTEGER UNIQUE,"
					+ "actualEndDate DATE,"
					+ "lottaryEndDate DATE,"
					+ "winnerUserName VARCHAR(50),"
					+ "hasEnd TINYINT(1),"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE StoreManagers(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "storeId INTEGER REFERENCES Stores(storeId) ,"
					+ " permission INTEGER,"
					+ "PRIMARY KEY (username, storeId, permission));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Carts(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "productId INTEGER REFERENCES Products(productId),"
					+ "amount INTEGER,"
					+ "code INTEGER,"
					+ "PRIMARY KEY (username, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInCategory(categoryName VARCHAR(50),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ "PRIMARY KEY (categoryName, productId));";
			stmt.executeUpdate(sql); 
			sql = "CREATE TABLE HiddenDiscount("
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ "code INTEGER,"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE OvertDiscount("
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE SubscribersInLottery("
					+ "lotaryId INTEGER REFERENCES LottaryPurchases(lottaryId),"
					+ "username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "moneyPayed INTEGER,"
					+ "PRIMARY KEY (lotaryId, username));";
			stmt.executeUpdate(sql);

			System.out.println("Created Tables");
		} catch (ClassNotFoundException e) {
			System.out.println("error: failed to load MySQL driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("error: failed to create a connection object.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("other error:");
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static DAL getInstance(){
		return dal;
	}
	//or
	public List<Subscriber> allSubscribers() throws Exception
	{
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Subscribers";
		ResultSet rs = statement.executeQuery(query);
		List<Subscriber>ans = new LinkedList<Subscriber>();
		while(rs.next())
		{
			List<Purchase> myPurchase=getMyPurchase(rs.getString("username"));
			List<StoreManager>managers=getSubscriberManagers(rs.getString("username"));
			List<StoreOwner>owners=getStoreOwners(rs.getString("username"));
			Subscriber s=new Subscriber(
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("fullname"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getString("credidCardNumber"),
					myPurchase,
					managers,
					owners);
			ans.add(s);
		}
		return ans;

	}

	public List<StoreOwner> getStoreOwners(String username) throws Exception {
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement=c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM StoreOwners WHERE username="+username+";";
		ResultSet rs=statement.executeQuery(query);
		List<StoreOwner>ans=new LinkedList<StoreOwner>();
		while(rs.next())
		{
			StoreOwner so=new StoreOwner(getStoreByStoreId(rs.getString("storeId")));
			ans.add(so);
		}
		return ans;
	}

	public Store getStoreByStoreId(String storeId) {
		// TODO return Store by store id
		return null;
	}

	public  List<StoreManager> getSubscriberManagers(String username) {
		// TODO return all StoreManagers of Subscriber with given username
		return null;
	}

	public  List<Purchase> getMyPurchase(String username) {
		// TODO return all purchase of Subscriber with given username
		return null;
	}

	public boolean isSubscriberExist(String username) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT username "
				+ "FROM Subscribers "
				+ "WHERE username = " + username+ ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next())
			return true;
		return false;
	}

	public boolean isAdmin(String username) throws Exception 
	{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT *  "
				+ "FROM Admins "
				+ "WHERE username = " + username+ ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next())
			return true;
		return false;
	}

	public void removeSubscriber(Subscriber sub) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Subscribers " +
                "WHERE username = " + sub.getUsername();
		statement.executeUpdate(query);
	}

	public List<Purchase> getStorePurchase(Store store){
		return null;
	}

	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Purchased " +
                "VALUES (" + sub.getUsername() + " , " + p.getPurchased().getMyProduct().getId() 
                + " , " + p.getPurchased().getMyProduct().getStore().getStoreId() + 
                " , " + p.getWhenPurchased() + " , " + p.getPurchased().getDiscountOrPrice() + 
                " , " + p.getPurchased().getAmount() + ")";
		statement.executeUpdate(query);
	}

	//removes owner named "username" from store with storeId
	public void removeStoreOwner(String username, int storeId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM StoreOwners " +
				"WHERE username = " + username +
				"AND storeId = " + storeId;
		statement.executeUpdate(query);
	}

	//removes manager named "username" from store with storeId
	public void deleteStoreManager(String username, int storeId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM StoreManagers " +
				"WHERE username = " + username +
				"AND storeId = " + storeId;
		statement.executeUpdate(query);
	}

	//checks if there is at least "amount" of this product with productId in store
	public boolean checkInStock(int productId, int amount) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT ProductsInStore.amount, ProductsInStore.productId  "
				+ "FROM ProductsInStores "
				+ "INNER JOIN Carts ON ProductsInStores.productId = Carts.productId"
				+ "WHERE ProductsInStores.productId = " + productId+ ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		while(res.next()){
			if(res.getInt("amount") >= amount)
				return true;
		}
		return false;
	}


	public Store getProductStore(Product p) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT Stores.* "
				+ " FROM ProductsInStores "
				+ "INNER JOIN Stores ON ProductsInStores.storeId=Stores.storeId"
				+ "WHERE ProductsInStores.productId = " + p.getId() + ";";
		stmt.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		Store s = new Store(res.getString("name"), res.getString("address"), res.getString("phone"), res.getInt("grading"), null, null, false);
		if(res.getInt("isOpen") == 1)
			s.setIsOpen(true);
		s.setMoneyEarned(res.getInt("moneyEarned"));
		return s;
	}

	public Map<Store,Product> getStoreProduct(Store store){
		return null;
	}

	public void stockUpdate(Product p, int amount,Store s) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO ProductsInStores " +
				"SET name = "+ s.getStoreName()+
				" , productId = " + p.getId() +
				" , amount = " + amount +
				" WHERE storeId = "+ s.getStoreId();
		statement.executeUpdate(query);
	}

	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Stores " +
				"SET moneyEarned = " + newMoneyEarend +
				" WHERE storeId = "+ s.getStoreId();
		statement.executeUpdate(query);
	}

	public List<Category>getAllCategory(){
		return null;

	}

	public Category getCategory(String categoryName){
		return null;

	}

	public void addProductToStore(Store s, Product product, int amount,String category) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO ProductsInStores " +
				"VALUES (" + s.getStoreId() + product.getId() + amount + ")";
		statement.executeUpdate(query);
		query = "INSERT INTO ProductsInCategory" +
				"VALUES (" + category + product.getId() + ")";
		statement.executeUpdate(query);
	}

	public void deleteProductFromStore(Store s, Product product){
	}

	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory)
	{

	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES (" + owner.getUsername() + " , " + s.getStoreId() + ")";
		statement.executeUpdate(query);
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES (" + newMan.getUsername() + " , " + s.getStoreId() + " , " + permission + ")";
		statement.executeUpdate(query);
	}

	//if isOpen insert 1 to store to the isOpen(TinyInt) field, else insert 0
	public void updateStore(Store s) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		int open = 0;
		if(s.getIsOpen())
			open = 1;
		query = "UPDATE Stores " +
				"SET name = "+ s.getStoreName()+
				" , address = " + s.getAddress() +
				" , phone = " + s.getPhone() + 
				" , grading = " + s.getGradeing() + 
				" , isOpen = " + open + 
				" , moneyEarned = " + s.getMoneyEarned() + 
				" WHERE storeId = "+ s.getStoreId();
		statement.executeUpdate(query);

	}

	public void addSubscriber(Subscriber s) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Subscribers " +
				"VALUES (" + s.getUsername() + s.getPassword() + s.getFullName() 
				+ s.getAddress() + s.getPhone() + s.getCreditCardNumber()+ ")";
		statement.executeUpdate(query);
		if(s.isAdmin()){
			query = "INSERT INTO Admins " +
					"VALUES (" + s.getUsername() + ")";
			statement.executeUpdate(query);
		}
	}

	public Subscriber getSubscriber(String username, String password) throws Exception{
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//add product to cart
	public void addImeddiatleyProductToCart(String username, int productId, int amount, int code){

	}
	public void removeProductFromCart(String username, int productId){

	}
	public void editProductAmount(String username, int productId, int amount){

	}
	public void editProductCode(String username, int productId, int code){

	}
	public void editProductPrice(int productId, int price){

	}
	//isOpen = false
	public void closeStore(int StoreId){

	}
	//is
	public void openStore(int StoreId){

	}

	public void addStore(Store s){

	}

	

	protected static Connection getConnection() throws Exception {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "12345";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
	public static void main(String[] args){
		
	}


}
