package TS_DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import TS_SharedClasses.*;

public class DALReal implements DAL {
	//TINYINT used for boolean, 0-false;1-true

	private Connection conn = null;
	private Statement stmt = null;

	private final static int emptyPolicyTypeCode = 0, andPolicyTypeCode = 1, orPolicyTypeCode = 2,
			notPolicyTypeCode = 3, maxPolicyTypeCode = 4, minPolicyTypeCode = 5,addressPolicyTypeCode=6;
	
	
	private static DALReal dal = new DALReal();
	private DALReal(){

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
					+ " creditCardNumber VARCHAR(50),"
					+ " PRIMARY KEY (username));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE SystemAdministrators(username VARCHAR(50),"
					+ " PRIMARY KEY (username));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE StoreOwners(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " storeId INTEGER REFERENCES Stores(storeId) ,"
					+ "PRIMARY KEY (username, storeId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Stores(storeId INTEGER,"
					+ " name VARCHAR(50),"
					+ " address VARCHAR(50),"
					+ " phone VARCHAR (50),"
					+ " grading INTEGER,"
					+ " isOpen TINYINT(1),"
					+ " moneyEarned INTEGER,"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (storeId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInStores(storeId INTEGER REFERENCES Stores(storeId),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " amount INTEGER, "
					+ " PRIMARY KEY (storeId, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Purchases(purchaseID INTEGER UNIQUE ,"
					+ " username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " storeId INTEGER REFERENCES Stores(storeId),"
					+ " whenPurchased DATE,"
					+ " price INTEGER,"
					+ " amount INTEGER,"
					+ " PRIMARY KEY (purchaseID));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE Policies("
					+ " policyId INTEGER,"
					+ " policyType INTEGER,"
					+ " IValue INTEGER,"//save int value such max,min...
					+ " SValue VARCHAR(50),"//save string value such username,address...
					+ " PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE SubPolicies("
					+ " fatherPolicyId INTEGER REFERENCES Policies(policyId),"
					+ " sonPolicyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (fatherPolicyId, sonPolicyId));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE CategoryDiscount("
					+ " storeId INTEGER REFERENCES Stores(storeId),"
					+ " categoryName INTEGER,"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (storeId, categoryName));";
			stmt.executeUpdate(sql);
			sql="CREATE TABLE CategoryPolicy("
					+ " storeId INTEGER REFERENCES Stores(storeId),"
					+ " categoryName INTEGER,"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (storeId, categoryName));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Products(productId INTEGER,"
					+ " name VARCHAR(50),"
					+ " price INTEGER,"
					+ " grading INTEGER ,"
					+ " category VARCHAR(50),"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ImmediatelyPurchases("
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " policyId INTEGER REFERENCES Policies(policyId),"
					+ " PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE LotteryPurchases("
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " lotteryId INTEGER UNIQUE,"
					+ " actualEndDate DATE,"
					+ " lotteryEndDate DATE,"
					+ " winnerUserName VARCHAR(50)," //TODO Not so true.. what about guests...
					+ " hasEnd TINYINT(1),"
					+ " PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE StoreManagers(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " storeId INTEGER REFERENCES Stores(storeId) ,"
					+ " permission INTEGER,"
					+ " PRIMARY KEY (username, storeId, permission));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Carts(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ " amount INTEGER,"
					+ " code INTEGER,"
					+ " PRIMARY KEY (username, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInCategory(categoryName VARCHAR(50) REFERENCES CategoryDiscount(categoryName),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ "	PRIMARY KEY (categoryName, productId));";
			stmt.executeUpdate(sql); 
			sql = "CREATE TABLE HiddenDiscount("
					+ "	policyId INTEGER REFERENCES Policies(policyId),"
					+ "	code INTEGER,"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "	PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE OvertDiscount("
					+ "	policyId INTEGER REFERENCES Policies(policyId),"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "	PRIMARY KEY (policyId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE SubscribersInLottery("
					+ "	lotteryId INTEGER REFERENCES LotteryPurchases(lotteryId),"
					+ "	username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "	moneyPayed INTEGER,"
					+ "	PRIMARY KEY (lotteryId, username));";
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
	public static DALReal getInstance(){
		return dal;
	}
	//or
	public List<Subscriber> allSubscribers() throws Exception
	{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Subscribers";
		ResultSet rs = stmt.executeQuery(query);
		List<Subscriber>ans = new LinkedList<Subscriber>();
		while(rs.next())
		{
			List<Purchase> myPurchase = getMyPurchase(rs.getString("username"));
			List<StoreManager>managers = getSubscriberManagers(rs.getString("username"));
			List<StoreOwner>owners = getStoreOwners(rs.getString("username"));
			
			if(isAdmin(rs.getString("username")))
			{
				SystemAdministrator sa = new SystemAdministrator(rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("address"), rs.getString("phone"), rs.getString("creditCardNumber"), myPurchase, managers, owners);
				ans.add(sa);
				continue;
			}
			Subscriber s=new Subscriber(
					rs.getString("username"),
					rs.getString("password"),
					rs.getString("fullname"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getString("creditCardNumber"),
					myPurchase,
					managers,
					owners);
			ans.add(s);
		}
		stmt.close();
		conn.close();
		return ans;
	}

	public List<StoreOwner> getStoreOwners(String username) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM StoreOwners WHERE username = '" + username + "';";
		ResultSet rs = stmt.executeQuery(query);
		List<StoreOwner>ans = new LinkedList<StoreOwner>();
		while(rs.next())
		{
			StoreOwner so = new StoreOwner(getStoreByStoreId(rs.getInt("storeId")));
			ans.add(so);
		}
		stmt.close();
		conn.close();
		return ans;
	}

	public Store getStoreByStoreId(int storeId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query ="SELECT * FROM Stores WHERE storeId = '" + storeId + "';";
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next())
		{
			int id = rs.getInt("storeId");
			boolean isOpen = false;
			if(rs.getInt("isOpen") == 1)
				isOpen = true;
			List<StoreOwner>so=new LinkedList<StoreOwner>();
			List<StoreManager>sm=new LinkedList<StoreManager>();
			//getStoreOwnersFromStore(id), getStoreManagersFromStore(id)
			Store s = new Store(id, rs.getString("name"), rs.getString("address"), rs.getString("phone"),
					rs.getInt("grading"), getProductAmount(storeId), getStorePurchase(storeId), isOpen,
					so,sm, rs.getInt("moneyEarned"),getStorePolicy(id), getStoreCategoryDiscount(id),getStoreCategoryPolicy(id));
			s.setMyOwners(getStoreOwnersFromStore(s));
			s.setMyManagers(getStoreManagersFromStore(s));
			for(Product p:s.getProducts().keySet()){
				p.setStore(s);
			}
			stmt.close();
			conn.close();
			return s;
		}
		stmt.close();
		conn.close();
		return null;
	}
	
	

	
	public  List<StoreManager> getSubscriberManagers(String username) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query ="SELECT * FROM StoreManagers WHERE username = '" + username + "';";
		ResultSet rs = stmt.executeQuery(query);
		List <StoreManager> ret = new LinkedList<StoreManager>();
		while(rs.next())
		{
			StoreManager sm = new StoreManager(getStoreByStoreId(rs.getInt("storeId")));
			ret.add(sm);
		}
		stmt.close();
		conn.close();
		return ret;
	}

	
	public  List<Purchase> getMyPurchase(String username) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query ="SELECT * FROM Purchases "
				+ "WHERE Purchases.username = '" + username + "';";
		ResultSet rs = stmt.executeQuery(query);
		List <Purchase> ret = new LinkedList<Purchase>();
		while(rs.next())
		{
			Product p = getProductById(rs.getInt("productId"));
			ProductInCart pic = new ProductInCart(p, rs.getInt("price"), rs.getInt("amount"));
			Purchase purchase = new Purchase(rs.getDate("whenPurchased"), rs.getInt("purchaseID"), pic);
			ret.add(purchase);
		}
		stmt.close();
		conn.close();
		return ret;
	}

	public boolean isSubscriberExist(String username) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT username "
				+ "FROM Subscribers "
				+ "WHERE username = '" + username+ "';";
		ResultSet res = stmt.executeQuery(query);
		if(res.next())
		{
			stmt.close();
			conn.close();
			return true;
		}
		stmt.close();
		conn.close();
		return false;
	}

	public boolean isAdmin(String username) throws Exception 
	{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT *  "
				+ "FROM SystemAdministrators "
				+ "WHERE username = '" + username+ "';";
		ResultSet res = stmt.executeQuery(query);
		if(res.next())
		{
			stmt.close();
			conn.close();
			return true;
		}
		stmt.close();
		conn.close();
		return false;
	}

	public void removeSubscriber(String username) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Subscribers " +
                "WHERE username = '" + username + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	
//	///problem with policies...
//	public List<Purchase> getStorePurchase(Store store){
//		return null;
//	}
//
//	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception{
//		String query = "USE TradingSystem";
//		Connection c = getConnection();
//		Statement statement=c.createStatement();
//		statement.executeUpdate(query);
//		query = "INSERT INTO Purchased " +
//                "VALUES (" + sub.getUsername() + " , " + p.getPurchased().getMyProduct().getId() 
//                + " , " + p.getPurchased().getMyProduct().getStore().getStoreId() + 
//                " , " + p.getWhenPurchased() + " , " + p.getPurchased().getDiscountOrPrice() + 
//                " , " + p.getPurchased().getAmount() + ")";
//		statement.executeUpdate(query);
//	}

	//removes owner named "username" from store with storeId
	public void removeStoreOwner(String username, int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM StoreOwners " +
				"WHERE username = '" + username +
				"' AND storeId = '" + storeId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	//removes manager named "username" from store with storeId
	public void deleteStoreManager(String username, int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM StoreManagers " +
				"WHERE username = '" + username +
				"' AND storeId = '" + storeId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	//checks if there is at least "amount" of this product with productId in store
	public boolean checkInStock(int productId, int amount) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT ProductsInStore.amount, ProductsInStore.productId "
				+ "FROM ProductsInStores "
				+ "INNER JOIN Carts ON ProductsInStores.productId = Carts.productId "
				+ "WHERE ProductsInStores.productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		while(res.next()){
			if(res.getInt("amount") >= amount)
			{
				stmt.close();
				conn.close();
				return true;
			}
		}
		stmt.close();
		conn.close();
		return false;
	}


	public Store getProductStore(int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT Stores.storeId "
				+ "FROM ProductsInStores "
				+ "INNER JOIN Stores ON ProductsInStores.storeId=Stores.storeId "
				+ "WHERE ProductsInStores.productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		Store s = null;
		while(res.next()){
			s = getStoreByStoreId(res.getInt("storeId"));
		}
		stmt.close();
		conn.close();
		return s;
	}

	public List<Product> getAllProductsOfStore(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT productId FROM ProductsInStores"
				+ " WHERE storeId = '" + storeId + "';";
		ResultSet rs = stmt.executeQuery(query);
		List<Product>ans = new LinkedList<Product>();
		while(rs.next())
		{
			ans.add(getProductById(rs.getInt("productId")));
		}
		stmt.close();
		conn.close();
		return ans;
	}

	public void stockUpdate(Product p, int amount,Store s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE ProductsInStores " +
				" SET amount = '" + amount +
				"' WHERE storeId = '" + s.getStoreId() +
				"' AND productId = '" + p.getId() + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Stores " +
				"SET moneyEarned = '" + newMoneyEarend +
				"' WHERE storeId = '" + s.getStoreId() + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	public void addProductToStore(Store s, Product product, int amount,String category) throws Exception{
		addProduct(product);
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		
		String query = "INSERT INTO ProductsInStores " +
				"VALUES ('" + s.getStoreId() + "', '" + product.getId() + "', '" + amount + "');";
		stmt.executeUpdate(query);
		query = "INSERT INTO ProductsInCategory " +
				"VALUES ('" + category + "', '" + product.getId() + "');";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}

//	"CREATE TABLE Products(productId INTEGER,"
//	+ " name VARCHAR(50),"
//	+ " price INTEGER,"
//	+ " grading INTEGER ,"
//	+ " category VARCHAR(50),"
//	+ " policyId INTEGER REFERENCES Policies(policyId),"
//	+ " PRIMARY KEY (productId));";
	private void addProduct(Product product) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		int policyId=getNextPolicyId();
		AddPolicy(product.getPurchasePolicy(), policyId);
		String query = "INSERT INTO Products " +
				"VALUES ('" + product.getId() + "', '" + product.getName() + "', '" + product.getPrice() 
				+ "', '" + product.getGrading() +"', '" + product.getCategory()+"', '" + policyId +"');";
		stmt.executeUpdate(query);
		
		stmt.close();
		conn.close();
	}
	public void deleteProductFromStore(int storeId, int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM ProductsInStores " +
				"WHERE productId = '" + productId +
				"' AND storeId = '" + storeId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	//polices....
	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "UPDATE ProductsInStores " + 
				"SET amount = '" + amount +
				"' WHERE productId = '" + oldProduct.getId() +
				"' AND storeId = '" + s.getStoreId() + "';";
		stmt.executeUpdate(query);
		
		query = "UPDATE ProductsInCategory " +
				"SET categoryName = '" + newProductCategory + 
				"' WHERE productId = '" + oldProduct.getId() + "';";
		stmt.executeUpdate(query);
		
		query = "UPDATE Products " +
				"SET productId = '" + newProduct.getId() +	
				"', name = '" + newProduct.getName() +
				"', price = '" + newProduct.getPrice() +
				"', grading = '" + newProduct.getGrading() + 
				"', category = '" + newProduct.getCategory() +  
				"' WHERE productId = '" + oldProduct.getId() + "';";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES ('" + owner.getUsername() + "', '" + s.getStoreId() + "');";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreManagers " +
				"VALUES ('" + newMan.getUsername() + "', '" + s.getStoreId() + "', '" + permission + "');";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	
	
	//TODO - Missing updates... policies for example
	//if isOpen insert 1 to store to the isOpen(TinyInt) field, else insert 0
	public void updateStore(Store s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
			
		updateStorePolicy(s.getStoreId(), s.getStorePolicy());
		//TODO - think if necessary to change purchase history
		//TODO same to StoreOwners and to StoreManagers and to store's products
		//ALL of them have add/remove functions..
		updateStoreCategoryDiscount(s.getStoreId(), s.getCategoryDiscounts());
		
		int open = 0;
		if(s.getIsOpen())
			open = 1;
		String query = "UPDATE Stores " +
				"SET name = '" + s.getStoreName() +
				"', address = '" + s.getAddress() +
				"', phone = '" + s.getPhone() + 
				"', grading = '" + s.getGradeing() + 
				"', isOpen = '" + open + 
				"', moneyEarned = '" + s.getMoneyEarned() + 
				"' WHERE storeId = '" + s.getStoreId() + "';";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	public void addSubscriber(Subscriber s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Subscribers " +
				"VALUES ('" + s.getUsername() + "', '" + s.getPassword() + "', '" + s.getFullName() 
				+ "', '" + s.getAddress() + "', '" + s.getPhone() + "', '" + s.getCreditCardNumber() + "');";
		statement.executeUpdate(query);
		if(s.isAdmin()){
			query = "INSERT INTO SystemAdministrators " +
					"VALUES ('" + s.getUsername() + "');";
			statement.executeUpdate(query);
		}
		stmt.close();
		conn.close();
	}

	public Subscriber getSubscriber(String username, String password) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * "
				+ "FROM Subscribers "
				+ "WHERE username = '" + username
				+ "' AND password = '"+ password + "';";
		ResultSet res = stmt.executeQuery(query);
		Subscriber ans=null;
		if(res.next()){
			List<Purchase> myPurchase = getMyPurchase(res.getString("username"));
			List<StoreManager>managers = getSubscriberManagers(res.getString("username"));
			List<StoreOwner>owners = getStoreOwners(res.getString("username"));
			ans= new Subscriber(res.getString("username"),
					res.getString("password"),
					res.getString("fullname"),
					res.getString("address"),
					res.getString("phone"), 
					res.getString("creditCardNumber"),
					myPurchase, managers, owners);
		}
		stmt.close();
		conn.close();
		return ans;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//add product to cart
	public void addImeddiatleyProductToCart(String username, int productId, int amount, int code) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Carts " +
				"VALUES ('" + username + "', '" + productId + "', '" + amount + "', '" + code + "');";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	
	
	public void removeProductFromCart(String username, int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Carts " +
				"WHERE productId = '" + productId +
				"' AND username = '" + username + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	
	
	public void editProductAmount(String username, int productId, int amount) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Carts " +
				"SET amount = '" + amount +
				"' WHERE productId = '"+ productId +
				"' AND username = '" + username + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	
	
	public void editProductCode(String username, int productId, int code) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Carts " +
				"SET code = '"+ code +
				"' WHERE productId = '"+ productId +
				"' AND username = '" + username +"';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	
	
	public void editProductPrice(int productId, int price) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Products " +
				"SET price = '" + price +
				"' WHERE productId = '" + productId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	//isOpen = false
	public void closeStore(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Stores " +
				"SET isOpen = '" + 0 +
				"' WHERE storeId = '" + storeId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	//is
	public void openStore(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE Stores " +
				"SET isOpen = '" + 1 +
				"' WHERE storeId = '" + storeId + "';";
		statement.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	//should be primitive store - just create one to the dal 
	public void addStore(Store s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		AddPolicy(s.getStorePolicy(), s.getStorePolicy().getPolicyId());
		String query = "INSERT INTO Stores " +
                "VALUES ( '" + s.getStoreId() + 
                "', '" + s.getStoreName() + 
                "', '" + s.getAddress() + 
                "', '" + s.getPhone() + 
                "', '" + s.getGradeing() + 
                "', '" + (s.getIsOpen() ? 1 : 0) + 
                "', '" + s.getMoneyEarned() + 
                "', '"+ s.getStorePolicy().getPolicyId() + "');";
		
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
		//should not have store policy here.
		
		//store owner is added outside from open store in BL
		
	}

	public  Map<Product,Integer> getProductAmount(int storeId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * "
				+ "FROM ProductsInStores "
				+ "WHERE ProductsInStores.storeId = '" + storeId + "';";
		ResultSet res = stmt.executeQuery(query);
		Map <Product, Integer> prodAmount = new HashMap<Product, Integer>();
		while(res.next()){
			Product p=getProductById(res.getInt("productId"));
			if(p!=null)
				prodAmount.put(p, res.getInt("amount"));
		}
		stmt.close();
		conn.close();
		return prodAmount;
	}

	public  List<Purchase> getStorePurchase(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Purchases "
				+ "WHERE storeId = '" + storeId + "';" ;
		ResultSet res = stmt.executeQuery(query);
		List<Purchase> ans = new LinkedList<Purchase>();
		while(res.next()){
			ans.add(new Purchase(res.getDate("whenPurchased"), res.getInt("purchaseID"), new ProductInCart(getProductById(res.getInt("productId")), res.getInt("price"), res.getInt("amount"))));
		}
		stmt.close();
		conn.close();
		return ans;
	}

	
	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query="SELECT * FROM HiddenDiscount "
				+" WHERE policyId = '" + policyId + "';";
		ResultSet res = stmt.executeQuery(query);
		if(res.next()){
			int code = res.getInt("code");
			HiddenDiscount hd = new HiddenDiscount(code, res.getDate("discountEndDate"), res.getInt("discountPrecentage"));
			stmt.close();
			conn.close();
			return hd;
		}
		else{
			query="SELECT * FROM OvertDiscount "
					+" WHERE policyId = '" + policyId + "';";
			res = stmt.executeQuery(query);
			if (res.next()){
				OvertDiscount od = new OvertDiscount(res.getDate("discountEndDate"), res.getInt("discountPrecentage"));
				stmt.close();
				conn.close();
				return od;
			}
		}
		stmt.close();
		conn.close();
		return null;
	}
	
	private ImmediatelyPurchase tryImmediatelyPurchase(int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT *  "
				+ "FROM ImmediatelyPurchases "
				+ "WHERE productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		ImmediatelyPurchase ans=null;
		if(res.next()){
			ans= new ImmediatelyPurchase(getPurchasePolicy(res.getInt("policyId")));
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	private LotteryPurchase tryLotteryPurchase(int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT *  "
				+ "FROM LotteryPurchases "
				+ "WHERE productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		LotteryPurchase ans=null;
		if(res.next()){
			boolean hasEnded = false;
			if(res.getInt("hasEnded") == 1) //TODO Need to check that 1 is true
				hasEnded = true;
			stmt.close();
			conn.close();
			ans= new LotteryPurchase(res.getDate("actualEndDate"), res.getDate("lotteryEndDate"), getLotteryParticipants(res.getInt("lotteryId")), null, hasEnded);
			//TODO ????What about guests winner
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public PurchaseType getPurchaseType(int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		PurchaseType ans = tryImmediatelyPurchase(productId);  
		if(ans == null)
			ans = tryLotteryPurchase(productId);
		stmt.close();
		conn.close();
		return ans;
	}
	
	public Subscriber getSubscriberIfExists(String username) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT username "
				+ "FROM Subscribers "
				+ "WHERE username = '" + username + "';";
		ResultSet res = stmt.executeQuery(query);
		Subscriber ans=null;
		if(res.next()){
			List<Purchase> myPurchase = getMyPurchase(res.getString("username"));
			List<StoreManager> managers = getSubscriberManagers(res.getString("username"));
			List<StoreOwner> owners = getStoreOwners(res.getString("username"));
			
			ans= new Subscriber(res.getString("username"),
					res.getString("password"),
					res.getString("fullname"),
					res.getString("address"),
					res.getString("phone"), 
					res.getString("creditCardNumber"),
					myPurchase, managers, owners);
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public List<Store> getStores() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Stores";
		ResultSet rs = stmt.executeQuery(query);
		List <Store> ret = new LinkedList<Store>();
		while(rs.next())
		{
			boolean isOpen = false;
			if(rs.getInt("isOpen") == 1)
				isOpen = true;
			int storeId = rs.getInt("storeId");
			Store s = new Store(storeId, rs.getString("name"), rs.getString("address"), rs.getString("phone"),
					rs.getInt("grading"),getProductAmount(storeId), getStorePurchase(storeId), isOpen,
					null, null,rs.getInt("moneyEarned"), getStorePolicy(storeId),
					getStoreCategoryDiscount(storeId),getStoreCategoryPolicy(storeId));	
			s.setMyOwners(getStoreOwnersFromStore(s));
			s.setMyManagers(getStoreManagersFromStore(s));
			ret.add(s);
		}
		stmt.close();
		conn.close();
		return ret;
	}
	
	public void addImeddiatleyProductToCart(String username, Product p, int amount, int code) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "INSERT INTO Carts VALUES('" + username +"', '" + p.getId() + "', '" + amount + "', '" + code + "');";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	public void editProductAmount(String username, Product p, int amount) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "UPDATE Carts SET amount = '" + amount + "' WHERE username = '"
						+ username + "' AND productId = '" + p.getId() + "';"; 
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
		
	}
	public void editProductCode(String username, Product p, int code) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "UPDATE Carts SET code = '" + code + "' WHERE username = '"
						+ username + "' AND productId = '" + p.getId() + "';"; 
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	
	public int getAmountOfProduct(int storeId, int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM ProductsInStores WHERE storeId = '" + storeId + "' AND productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		int ans=0;
		if(res.next()){
			ans= res.getInt("amount");
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "INSERT INTO Purchases VALUES('"
						+ p.getPurchaseID() + "', '" + sub.getUsername() + "', '"
						+ p.getPurchased().getMyProduct().getId() + "', '" 
						+ p.getPurchased().getMyProduct().getStore().getStoreId() + "', '" 
						+ p.getWhenPurchased() + "', '" + p.getPurchased().getDiscountOrPrice() + "', '" 
						+ p.getPurchased().getAmount() + "');";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}
	public List<StoreOwner> getStoreOwnersFromStore(Store s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM StoreOwners WHERE storeId = '" + s.getStoreId() + "';" ;
		ResultSet res = stmt.executeQuery(query);
		List<StoreOwner> ans = new LinkedList<StoreOwner>();
		while(res.next()){
			ans.add(new StoreOwner(s)); //TODO - very strange need to check...
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public List<StoreManager> getStoreManagersFromStore(Store s) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM StoreManagers WHERE storeId = '" + s.getStoreId() + "';" ;
		ResultSet res = stmt.executeQuery(query);
		Map<String,StoreManager>map=new HashMap<String, StoreManager>();
		while(res.next()){
			String username=res.getString("username");
			if(map.containsKey(username)){
				map.get(username).setSpecificPermission(res.getInt("permission"), true);
			}
			else{
				StoreManager sm=new StoreManager(s);
				if(res.getInt("permission")>=0)
					sm.setSpecificPermission(res.getInt("permission"), true);
				map.put(username,sm);
			}
		}
		stmt.close();
		conn.close();
		return new LinkedList<StoreManager>(map.values());
	}
	
	
	public Product getProductById(int productId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Products WHERE productId = '" + productId + "';";
		ResultSet res = stmt.executeQuery(query);
		Product ans=null;
		if(res.next()){
			ans= new Product(productId, res.getString("name"), res.getInt("price"),res.getInt("grading"), getPurchasePolicy(res.getInt("policyId")), null, getCategory(res.getString("category")),getPurchaseType(productId));
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public int getNextProductId() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT MAX(productId) AS maxId FROM Products;";
		ResultSet res = stmt.executeQuery(query);
		int ans=155;
		if(res.next()){
			if(res.getObject("maxId")!=null){
				ans=res.getInt("maxId") + 1;
			}
		}
		stmt.close();
		conn.close();
		return ans;
	}
	public int getNextStoreId() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT MAX(storeId) AS maxId FROM Stores;";
		ResultSet res = stmt.executeQuery(query);
		int ans=144;
		if(res.next()){
			ans= res.getInt("maxId") + 1;
		}
		stmt.close();
		conn.close();
		return ans;
	}
	public int getNextPolicyId() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT MAX(policyId) AS maxId FROM Policies;";
		ResultSet res = stmt.executeQuery(query);
		int ans=133;
		if(res.next()){
			ans= res.getInt("maxId") + 1;
		}
		stmt.close();
		conn.close();
		return ans;
	}

	public int getNextPurchaseId() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT MAX(purchaseID) AS maxId FROM Purchases;";
		ResultSet res = stmt.executeQuery(query);
		int ans=122;
		if(res.next()){
			ans= res.getInt("maxId") + 1;
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public List<StoreManager> getStoreManagers(String username) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM StoreManagers WHERE username = '" + username + "';";
		ResultSet rs = stmt.executeQuery(query);
		List<StoreManager>ans = new LinkedList<StoreManager>();
		while(rs.next())
		{
			StoreManager sm = new StoreManager(getStoreByStoreId(rs.getInt("storeId")));
			ans.add(sm);
		}
		stmt.close();
		conn.close();
		return ans;
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
	
	
	
	////////////////////////////////////////////////////////////////////////////
	public PurchasePolicy getPurchasePolicy(int policyId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Policies WHERE policyId = '" + policyId + "';";
		ResultSet res = stmt.executeQuery(query);
		PurchasePolicy ans=null;
		if(res.next()){
			DiscountPolicy discount=getDiscountPolicy(policyId);
			switch(res.getInt("policyType"))
			{
				case emptyPolicyTypeCode:{
					ans=new EmptyPolicy(discount);
				}
				case andPolicyTypeCode:
				{
					ans=new AndPolicy(discount, getSubPolicies(policyId));
				}
				case orPolicyTypeCode:{
					ans=new OrPolicy(discount, getSubPolicies(policyId));
				}
				case notPolicyTypeCode:
					List<PurchasePolicy>lst=getSubPolicies(policyId);
					if(lst.size()>0)
					{
						ans= new NotPolicy(discount, lst.get(0));
					}
					else
					{
						ans=new NotPolicy(discount, null);
					}
				case maxPolicyTypeCode:{
					ans= new MaxPolicy(discount, res.getInt("IValue"));
					}
				case minPolicyTypeCode:
				{
					ans=new MinPolicy(discount, res.getInt("IValue"));
				}
				case addressPolicyTypeCode:{
					ans=new AddressPolicy(discount, res.getString("SValue"));
				}
				default:
				{
					stmt.close();
					conn.close();
					//throw new Exception("Error in policy type");
				}
			}
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public List<PurchasePolicy> getSubPolicies(int policyId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM SubPolicies WHERE fatherPolicyId = '" + policyId + "';";
		ResultSet res = stmt.executeQuery(query);
		List<PurchasePolicy> ans=new ArrayList<PurchasePolicy>();
		while(res.next()){
			ans.add(getPurchasePolicy(res.getInt("sonPolicyId")));
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	public PurchasePolicy getStorePolicy(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM Stores WHERE storeId = '" + storeId + "';";
		ResultSet res = stmt.executeQuery(query);
		PurchasePolicy ans=null;
		if(res.next())
		{
			ans=getPurchasePolicy(res.getInt("policyId"));
		}
		stmt.close();
		conn.close();
		return ans;
	}

	public Map<Category, PurchasePolicy> getStoreCategoryDiscount(int storeId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM CategoryDiscount WHERE storeId = '" + storeId + "';";
		ResultSet res = stmt.executeQuery(query);
		Map<Category, PurchasePolicy> ans=new HashMap<Category, PurchasePolicy>();
		while(res.next())
		{
			ans.put(getCategory(res.getString("categoryName")), getPurchasePolicy(res.getInt("policyId")));
		}
		stmt.close();
		conn.close();
		return ans;
	}
	
	private Map<Category, PurchasePolicy> getStoreCategoryPolicy(int storeId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		String query = "SELECT * FROM CategoryPolicy WHERE storeId = '" + storeId + "';";
		ResultSet res = stmt.executeQuery(query);
		Map<Category, PurchasePolicy> ans=new HashMap<Category, PurchasePolicy>();
		while(res.next())
		{
			ans.put(getCategory(res.getString("categoryName")), getPurchasePolicy(res.getInt("policyId")));
		}
		stmt.close();
		conn.close();
		return ans;
	}

	private void updateStorePolicy(int storeId, PurchasePolicy newPolicy) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		int prevPolicyId=-1;
		String query = "SELECT * FROM Stores WHERE storeId = '" + storeId + "';";
		ResultSet res = stmt.executeQuery(query);
		if(res.next())
		{
			prevPolicyId= res.getInt("policyId");
		}
		int PolicyId=getNextPolicyId();
		AddPolicy(newPolicy,PolicyId); 
		
		if(prevPolicyId!=-1){
			query="UPDATE Stores "
					+ " SET policyId = '" +PolicyId
					+ "' WHERE storeId = '"+ storeId +"';";
			stmt.executeUpdate(query);
			deletePolicy(prevPolicyId);
		}
		stmt.close();
		conn.close();
	}

	private void deletePolicy(int policyId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		
		String query = "SELECT * FROM SubPolicies WHERE fatherPolicyId = '" + policyId + "';";
		ResultSet res = stmt.executeQuery(query);
		while(res.next()){
			query = "DELETE FROM SubPolicies WHERE fatherPolicyId = '" + policyId 
					+"' AND sonPolicyId = '"+res.getInt("sonPolicyId") +"';";
			stmt.executeUpdate(query);
			deletePolicy(res.getInt("sonPolicyId"));
		}
		query = "DELETE FROM Policies WHERE policyId = '" + policyId + "' ;";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
	}

	private void AddPolicy(PurchasePolicy newPolicy,int policyId) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		
		if(newPolicy instanceof AndPolicy){
			String query = "INSERT INTO Policies (policyId, policyType) VALUES('"
						+ policyId + "', '" + andPolicyTypeCode + "');";
			stmt.executeUpdate(query);
			for(PurchasePolicy policy:((AndPolicy)newPolicy).getSubPolicy()){
				int sonPolicyId=getNextPolicyId();
				AddPolicy(policy,sonPolicyId);
				query = "INSERT INTO SubPolicies VALUES('" + policyId + "', '" + sonPolicyId+ "');";
				stmt.executeUpdate(query);
			}
		}
		if(newPolicy instanceof NotPolicy){
			String query = "INSERT INTO Policies (policyId, policyType) VALUES('"
					+ policyId + "', '" + notPolicyTypeCode + "');";
			stmt.executeUpdate(query);
			PurchasePolicy policy=((NotPolicy)newPolicy).getSubPolicy();
			int sonPolicyId=getNextPolicyId();
			AddPolicy(policy,sonPolicyId);
			query = "INSERT INTO SubPolicies VALUES('" + policyId + "', '" + sonPolicyId+ "');";
			stmt.executeUpdate(query);
		}
		if(newPolicy instanceof OrPolicy){
			String query = "INSERT INTO Policies (policyId, policyType) VALUES('"
					+ policyId + "', '" + orPolicyTypeCode + "');";
			stmt.executeUpdate(query);
			for(PurchasePolicy policy:((OrPolicy)newPolicy).getSubPolicy()){
				int sonPolicyId=getNextPolicyId();
				AddPolicy(policy,sonPolicyId);
				query = "INSERT INTO SubPolicies VALUES('" + policyId + "', '" + sonPolicyId+ "');";
				stmt.executeUpdate(query);
			}
		}
		if(newPolicy instanceof EmptyPolicy){
			String query = "INSERT INTO Policies (policyId, policyType) VALUES('"
					+ policyId + "', '" + emptyPolicyTypeCode + "');";
			stmt.executeUpdate(query);
		}
		if(newPolicy instanceof MaxPolicy){
			String query = "INSERT INTO Policies (policyId, policyType,IValue) VALUES('"
					+ policyId + "', '" + maxPolicyTypeCode + "', '"
					+ ((MaxPolicy)newPolicy).getMax() + "');";
			stmt.executeUpdate(query);
		}
		if(newPolicy instanceof MinPolicy){
			String query = "INSERT INTO Policies (policyId, policyType,IValue) VALUES('" 
					+ policyId + "', '" + minPolicyTypeCode + "', '"
					+ ((MinPolicy)newPolicy).getMin() + "');";
			stmt.executeUpdate(query);
		}
		if(newPolicy instanceof AddressPolicy){
			String query = "INSERT INTO Policies (policyId, policyType,SValue) VALUES('"
							+ policyId + "', '" + addressPolicyTypeCode + "', '"
							+ ((AddressPolicy)newPolicy).getAddress() + "');";
			stmt.executeUpdate(query);
		}
		stmt.close();
		conn.close();
	}

	private void updateStoreCategoryDiscount(int storeId, Map<Category, PurchasePolicy> categorys) throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		for (Entry<Category, PurchasePolicy> cat:categorys.entrySet()){
			int prevPolicyId=-1;
			String query = "SELECT * FROM CategoryPolicy WHERE storeId = '" + storeId + "' AND categoryName = '" 
							+ cat.getKey().getName()+"' ;";
			ResultSet res = stmt.executeQuery(query);
			if(res.next())
			{
				prevPolicyId= res.getInt("policyId");
			}
			int PolicyId=getNextPolicyId();
			AddPolicy(cat.getValue(),PolicyId); 
			
			
			if(prevPolicyId!=-1){
				query="UPDATE CategoryPolicy "
						+ " SET policyId = '" +PolicyId
						+ "' WHERE storeId = '"+ storeId +"' AND categoryName = '"+ cat.getKey().getName() +"';";
				stmt.executeUpdate(query);
				deletePolicy(prevPolicyId);
			}
			else{
				query="INSERT INTO CategoryPolicy VALUES( '"
						+ storeId+"' , '"+cat.getKey().getName()+"' , '"+PolicyId+"' );";
				stmt.executeUpdate(query);
			}
		}
		stmt.close();
		conn.close();
	}
	
	public boolean isCategoryExists(String category) {
		//TODO not sure about categories
		return true;
	}
	
	public void addLotteryProductToCart(String username, Product p, int money) {
		// TODO Auto-generated method stub
	}
	
	//policies....
	//TODO ?????????????
	public List<Category> getAllCategory(){
		return null;
	}
		
	//policies...
	//TODO ????????????
	public Category getCategory(String categoryName){
		return null;
	}
	

	public void deleteStore(int storeId) {
		// TODO Yadani
	}
	private Map<Guest, Integer> getLotteryParticipants(int int1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removePurchase(int purchaseId) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("USE TradingSystem");
		
		String query = "DELETE FROM Purchases WHERE purchaseID = '" + purchaseId + "' ;";
		stmt.executeUpdate(query);
		stmt.close();
		conn.close();
		
	}
	
//	private ResultSet selectQuery(String selectQuery) throws Exception{
//		conn = getConnection();
//		stmt = conn.createStatement();
//		stmt.executeUpdate("USE TradingSystem");
//		ResultSet ans = stmt.executeQuery(selectQuery);
//		stmt.close();
//		conn.close();
//		
//		return ans;
//	}
	
//	private void otherQuery(String otherQuery) throws Exception{
//		Connection conn = getConnection();
//		Statement stmt = conn.createStatement();
//		stmt.executeUpdate("USE TradingSystem");
//		stmt.executeUpdate(otherQuery);
//		stmt.close();
//		conn.close();
//		
//	}
	
	
	
}
