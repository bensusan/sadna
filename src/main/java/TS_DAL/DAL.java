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
			notPolicyTypeCode = 3, maxPolicyTypeCode = 4, minPolicyTypeCode = 5,addressPolicyTypeCode=6;
	
	
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
					+ " password VARCHAR(50), fullname VARCHAR(50),"
					+ " address VARCHAR(50), phone VARCHAR(50),"
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
					+ " name VARCHAR(50), address VARCHAR(50),"
					+ " phone VARCHAR (50), "
					+ "grading INTEGER, "
					+ "isOpen TINYINT(1), "
					+ "moneyEarned INTEGER,"
					+ "policyId INTEGER REFERENCES Policies(policyId),"
					+ "PRIMARY KEY (storeId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInStores(storeId INTEGER REFERENCES Stores(storeId),"
					+ " productId INTEGER REFERENCES Products(productId),amount INTEGER, "
					+ "PRIMARY KEY (storeId, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Purchased(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " storeId INTEGER REFERENCES Stores(storeId),"
					+ " whenPurchased DATE, price INTEGER,"
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
					+ " productId INTEGER REFERENCES Products(productId),"
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

			System.out.println("CreateEmployeeTableMySQL: main(): table created.");
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
	public List<Subscriber> allSubscribers() throws Exception
	{
		String query ="SELECT * FROM Subscribers";
		Connection c=getConnection();
		Statement statement=c.createStatement();
		ResultSet rs=statement.executeQuery(query);
		List<Subscriber>ans=new LinkedList<Subscriber>();
		while(rs.next())
		{
			List<Purchase> myPurchase=getMyPurchase(rs.getString("username"));
			List<StoreManager>managers=getSubscriberManagers(rs.getString("username"));
			List<StoreOwner>owners=getSubscriberOwners(rs.getString("username"));
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

	public List<StoreOwner> getSubscriberOwners(String username) throws Exception {
		String query ="SELECT * FROM storeowners WHERE username="+username+";";
		Connection c=getConnection();
		Statement statement=c.createStatement();
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
	
	public boolean isSubscriberExist(String username){
		// TODO return all purchase of Subscriber with given username
		return false;
	}
	
	public boolean isAdmin(String username)
	{
		// TODO return all purchase of Subscriber with given username
		return false;
	}

	public void removeSubscriber(){
		// TODO return all purchase of Subscriber with given username
	}

	public List<Purchase> getStorePurchase(Store store){
		return null;
	}

	public void addStoreOwner(Subscriber s,StoreOwner so){	
	}

	public void addPurchaseToHistory(Subscriber sub, Purchase p){
		
	}
	
	public void addOwner(Subscriber sub, StoreOwner owner){
		
	}
	
	public void addManager(Subscriber sub, StoreManager manager){
		
	}

	public void deleteOwner(Subscriber sub, StoreOwner owner){
		
	}

	public void deleteManager(Subscriber sub, StoreManager manager){
		
	}
	
	public boolean checkInStock(Product p, int amount){
		return false;
		}

	public Store getProductStore(Product p){
		return null;
	}

	public Map<Store,Integer> getStoreProduct(Store store){
		return null;
	}

	public void stockUpdate(Product p, int amount,Store store){
		
	}

	public void updategetMoneyEarned(Store s, int newMoneyEarend){
		
	}

	public List<Category>getAllCategory(){
		return null;
		
	}
	
	public Category getCategory(String categoryName){
		return null;
		
	}

	public void addProductToStore(Store s, Product product, int amount,String category){
		
	}

	public void deleteProductFromStore(Store s, Product product){
		
	}

	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory)
	{
		
	}

	public void addNewStoreOwner(Store s, Subscriber owner){
		
	}

	public void addNewManager(Store s, Subscriber newMan){
		
	}

	public void updateCloseStore(Store s){
		
	}

	public void upadteOpenStore(Store s){
		
	}
	
	public void addSubscriber(Subscriber s){
		
	}

	public Subscriber getSubscriber(String username, String password){
		return null;
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
