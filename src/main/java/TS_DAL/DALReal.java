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
					+ " credidCardNumber VARCHAR(50),"
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
			sql = "CREATE TABLE Purchases(purchaseID INTEGER UNIQUE"
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
					+ "	PRIMARY KEY (lotaryId, username));";
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
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query = "SELECT * FROM Subscribers";
		ResultSet rs = statement.executeQuery(query);
		List<Subscriber>ans = new LinkedList<Subscriber>();
		while(rs.next())
		{
			List<Purchase> myPurchase = getMyPurchase(rs.getString("username"));
			List<StoreManager>managers = getSubscriberManagers(rs.getString("username"));
			List<StoreOwner>owners = getStoreOwners(rs.getString("username"));
			
			if(isAdmin(rs.getString("username")))
			{
				SystemAdministrator sa = new SystemAdministrator(rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("address"), rs.getString("phone"), rs.getString("credidCardNumber"), myPurchase, managers, owners);
				ans.add(sa);
				continue;
			}
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
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeQuery(query);
		query = "SELECT * FROM StoreOwners WHERE username = " + username + ";";
		ResultSet rs = statement.executeQuery(query);
		List<StoreOwner>ans = new LinkedList<StoreOwner>();
		while(rs.next())
		{
			StoreOwner so = new StoreOwner(getStoreByStoreId(rs.getInt("storeId")));
			ans.add(so);
		}
		return ans;
	}

	public Store getStoreByStoreId(int storeId) throws Exception {
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Stores WHERE storeId = " + storeId + ";";
		ResultSet rs = statement.executeQuery(query);
		if(rs.next())
		{
			int id = rs.getInt("storeId");
			boolean isOpen = false;
			if(rs.getInt("isOpen") == 1)
				isOpen = true;
			Store s = new Store(id, rs.getString("name"), rs.getString("address"), rs.getString("phone"),
					rs.getInt("grading"), getProductAmount(storeId), getStorePurchase(storeId), isOpen,
					getStoreOwnersFromStore(id), getStoreManagersFromStore(id), rs.getInt("moneyEarned"),
					getStorePolicy(id), getStoreCategoryDiscount(id),getStoreCategoryPolicy(id));
			return s;
		}
		return null;
	}
	
	

	
	public  List<StoreManager> getSubscriberManagers(String username) throws Exception {
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM StoreManagers WHERE username = " + username + ";";
		ResultSet rs = statement.executeQuery(query);
		List <StoreManager> ret = new LinkedList<StoreManager>();
		while(rs.next())
		{
			StoreManager sm = new StoreManager(getStoreByStoreId(rs.getInt("storeId")));
			ret.add(sm);
		}
		return ret;
	}

	
	public  List<Purchase> getMyPurchase(String username) throws Exception {
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Purchases "
				+ "WHERE Purchases.username = " + username + ";";
		ResultSet rs = statement.executeQuery(query);
		List <Purchase> ret = new LinkedList<Purchase>();
		while(rs.next())
		{
			Product p = getProductById(rs.getInt("productId"));
			ProductInCart pic = new ProductInCart(p, rs.getInt("price"), rs.getInt("amount"));
			Purchase purchase = new Purchase(rs.getDate("whenPurchased"), rs.getInt("purchaseID"), pic);
			ret.add(purchase);
		}
		return ret;
	}

	public boolean isSubscriberExist(String username) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
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
				+ "FROM SystemAdministrators "
				+ "WHERE username = " + username+ ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next())
			return true;
		return false;
	}

	public void removeSubscriber(String username) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Subscribers " +
                "WHERE username = " + username + ";";
		statement.executeUpdate(query);
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
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM StoreOwners " +
				"WHERE username = " + username +
				"AND storeId = " + storeId + ";";
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
				"AND storeId = " + storeId + ";";
		statement.executeUpdate(query);
	}

	//checks if there is at least "amount" of this product with productId in store
	public boolean checkInStock(int productId, int amount) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT ProductsInStore.amount, ProductsInStore.productId "
				+ "FROM ProductsInStores "
				+ "INNER JOIN Carts ON ProductsInStores.productId = Carts.productId "
				+ "WHERE ProductsInStores.productId = " + productId + ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		while(res.next()){
			if(res.getInt("amount") >= amount)
				return true;
		}
		return false;
	}


	public Store getProductStore(int productId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT Stores.storeId "
				+ "FROM ProductsInStores "
				+ "INNER JOIN Stores ON ProductsInStores.storeId=Stores.storeId "
				+ "WHERE ProductsInStores.productId = " + productId + ";";
		stmt.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		Store s = null;
		while(res.next()){
			s = getStoreByStoreId(res.getInt("storeId"));
		}
		return s;
	}

	public List<Product> getAllProductsOfStore(int storeId) throws Exception{
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement=c.createStatement();
		statement.executeQuery(query);
		query = "SELECT productId FROM ProductsInStores"
				+ " WHERE storeId = " + storeId + ";";
		ResultSet rs=statement.executeQuery(query);
		List<Product>ans = new LinkedList<Product>();
		while(rs.next())
		{
			ans.add(getProductById(rs.getInt("productId")));
		}
		return ans;
	}

	public void stockUpdate(Product p, int amount,Store s) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO ProductsInStores " +
				"SET amount = " + amount +
				" WHERE storeId = " + s.getStoreId() +
				" AND productId = " + p.getId() + ";";
		statement.executeUpdate(query);
	}

	public void updateMoneyEarned(Store s, int newMoneyEarend) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Stores " +
				"SET moneyEarned = " + newMoneyEarend +
				" WHERE storeId = " + s.getStoreId() + ";";
		statement.executeUpdate(query);
	}

	public void addProductToStore(Store s, Product product, int amount,String category) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO ProductsInStores " +
				"VALUES (" + s.getStoreId() + ", " + product.getId() + ", " + amount + ");";
		statement.executeUpdate(query);
		query = "INSERT INTO ProductsInCategory " +
				"VALUES (" + category + ", " + product.getId() + ");";
		statement.executeUpdate(query);
	}

	public void deleteProductFromStore(int storeId, int productId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE * FROM ProductsInStores " +
				"WHERE productId = " + productId +
				" AND storeId = " + storeId + ";";
		statement.executeUpdate(query);
	}

	//polices....
	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory) throws Exception{
		String query = "UPDATE ProductsInStores " + 
				"SET amount = " + amount +
				" WHERE productId = " + oldProduct.getId() +
				" AND storeId = " + s.getStoreId() + ";";
		otherQuery(query);
		
		query = "UPDATE ProductsInCategory " +
				"SET categoryName = " + newProductCategory + 
				" WHERE productId = " + oldProduct.getId() + ";";
		otherQuery(query);
		
		query = "UPDATE Products " +
				"SET productId = " + newProduct.getId() +	
				", name = " + newProduct.getName() +
				", price = " + newProduct.getPrice() +
				", grading = " + newProduct.getGrading() + 
				", category = " + newProduct.getCategory() +  
				" WHERE productId = " + oldProduct.getId() + ";";
		otherQuery(query);
	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES (" + owner.getUsername() + ", " + s.getStoreId() + ");";
		statement.executeUpdate(query);
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreManagers " +
				"VALUES (" + newMan.getUsername() + ", " + s.getStoreId() + ", " + permission + ");";
		statement.executeUpdate(query);
	}

	
	
	//TODO - Missing updates... policies for example
	//if isOpen insert 1 to store to the isOpen(TinyInt) field, else insert 0
	public void updateStore(Store s) throws Exception{
		
		updateStorePolicy(s.getStoreId(), s.getStorePolicy());
		//TODO - think if necessary to change purchase history
		//TODO same to StoreOwners and to StoreManagers and to store's products
		//ALL of them have add/remove functions..
		updateStoreCategoryDiscount(s.getStoreId(), s.getCategoryDiscounts());
		
		int open = 0;
		if(s.getIsOpen())
			open = 1;
		String query = "UPDATE Stores " +
				"SET name = " + s.getStoreName() +
				", address = " + s.getAddress() +
				", phone = " + s.getPhone() + 
				", grading = " + s.getGradeing() + 
				", isOpen = " + open + 
				", moneyEarned = " + s.getMoneyEarned() + 
				" WHERE storeId = " + s.getStoreId() + ";";
		otherQuery(query);
	}

	public void addSubscriber(Subscriber s) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Subscribers " +
				"VALUES (" + s.getUsername() + ", " + s.getPassword() + ", " + s.getFullName() 
				+ ", " + s.getAddress() + ", " + s.getPhone() + ", " + s.getCreditCardNumber() + ");";
		statement.executeUpdate(query);
		if(s.isAdmin()){
			query = "INSERT INTO SystemAdministrators " +
					"VALUES (" + s.getUsername() + ");";
			statement.executeUpdate(query);
		}
	}

	public Subscriber getSubscriber(String username, String password) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT * "
				+ "FROM Subscribers "
				+ "WHERE username = " + username
				+ " AND password = "+ password + ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next()){
			List<Purchase> myPurchase = getMyPurchase(res.getString("username"));
			List<StoreManager>managers = getSubscriberManagers(res.getString("username"));
			List<StoreOwner>owners = getStoreOwners(res.getString("username"));
			return new Subscriber(res.getString("username"),
					res.getString("password"),
					res.getString("fullname"),
					res.getString("address"),
					res.getString("phone"), 
					res.getString("creditCardNumber"),
					myPurchase, managers, owners);
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//add product to cart
	public void addImeddiatleyProductToCart(String username, int productId, int amount, int code) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Carts " +
				"VALUES (" + username + ", " + productId + ", " + amount + ", " + code + ");";
		statement.executeUpdate(query);
	}
	
	
	public void removeProductFromCart(String username, int productId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Carts " +
				"WHERE productId = " + productId +
				" AND username = " + username + ";";
		statement.executeUpdate(query);
	}
	
	
	public void editProductAmount(String username, int productId, int amount) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Carts " +
				"SET amount = " + amount +
				" WHERE productId = "+ productId +
				" AND username = " + username + ";";
		statement.executeUpdate(query);
	}
	
	
	public void editProductCode(String username, int productId, int code) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Carts " +
				"SET code = "+ code +
				" WHERE productId = "+ productId +
				" AND username = " + username +";";
		statement.executeUpdate(query);
	}
	
	
	public void editProductPrice(int productId, int price) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Products " +
				"SET price = " + price +
				" WHERE productId = " + productId + ";";
		statement.executeUpdate(query);
	}
	//isOpen = false
	public void closeStore(int storeId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Store " +
				"SET isOpen = " + 0 +
				" WHERE storeId = " + storeId + ";";
		statement.executeUpdate(query);
	}
	//is
	public void openStore(int storeId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Store " +
				"SET isOpen = " + 1 +
				" WHERE storeId = " + storeId + ";";
		statement.executeUpdate(query);
	}

	//should be primitive store - just create one to the dal 
	public void addStore(Store s) throws Exception{
		String query = "INSERT INTO Stores " +
                "VALUES (" + s.getStoreId() + 
                ", " + s.getStoreName() + 
                ", " + s.getAddress() + 
                ", " + s.getPhone() + 
                ", " + s.getGradeing() + 
                ", " + s.getIsOpen() + 
                ", " + s.getMoneyEarned() + ");";
		otherQuery(query);
		
		//should not have store policy here.
		
		//store owner is added outside from open store in BL
		
	}

	public  Map<Product,Integer> getProductAmount(int storeId) throws Exception {
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT * "
				+ "FROM ProductsInStores "
				+ "WHERE ProductsInStores.storeId = " + storeId + ";";
		stmt.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		Map <Product, Integer> prodAmount = new HashMap<Product, Integer>();
		while(res.next()){
			prodAmount.put(getProductById(res.getInt("productId")), res.getInt("amount"));
		}
		return prodAmount;
	}

	public  List<Purchase> getStorePurchase(int storeId) throws Exception{
		String query = "SELECT * FROM Purchases "
				+ "WHERE storeId = " + storeId + ";" ;
		ResultSet res = selectQuery(query);
		List<Purchase> ans = new LinkedList<Purchase>();
		while(res.next()){
			ans.add(new Purchase(res.getDate("whenPurchased"), res.getInt("purchaseID"), new ProductInCart(getProductById(res.getInt("productId")), res.getInt("price"), res.getInt("amount"))));
		}
		return ans;
	}

	
	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT * "
				+ "FROM HiddenDiscount "
				+ "FULL OUTER JOIN OvertDiscount ON HiddenDiscount.policyId = OvertDiscount.policyId "
				+ "INNER JOIN Policies " 
				+ "WHERE OvertDiscount.policyId = Policies.policyId "
				+ "OR HiddenDiscount.policyId = Poilcies.policyId;" ;
		stmt.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next()){
			int code = res.getInt("code");
			if (res.wasNull()){
				OvertDiscount od = new OvertDiscount(res.getDate("discountEndDate"), res.getInt("discountPrecentage"));
				return od;
			}
			HiddenDiscount hd = new HiddenDiscount(code, res.getDate("discountEndDate"), res.getInt("discountPrecentage"));
			return hd;
		}
		return null;
	}
	
	private ImmediatelyPurchase tryImmediatelyPurchase(int productId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT *  "
				+ "FROM ImmediatelyPurchases "
				+ "WHERE productId = " + productId + ";";
		statement.executeUpdate(query);
		ResultSet res = statement.executeQuery(query);
		if(res.next())
			return new ImmediatelyPurchase(getPurchasePolicy(res.getInt("policyId")));
		return null;
	}
	
	private LotteryPurchase tryLotteryPurchase(int productId) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT *  "
				+ "FROM LotteryPurchases "
				+ "WHERE productId = " + productId + ";";
		statement.executeUpdate(query);
		ResultSet res = statement.executeQuery(query);
		if(res.next()){
			boolean hasEnded = false;
			if(res.getInt("hasEnded") == 1) //TODO Need to check that 1 is true
				hasEnded = true;
			return new LotteryPurchase(res.getDate("actualEndDate"), res.getDate("lotteryEndDate"), getLotteryParticipants(res.getInt("lotteryId")), null, hasEnded);
			//TODO ????What about guests winner
		}
		return null;
	}
	
	public PurchaseType getPurchaseType(int productId) throws Exception{
		PurchaseType ans = tryImmediatelyPurchase(productId);  
		if(ans == null)
			ans = tryLotteryPurchase(productId); 
		return ans;
	}
	
	public Subscriber getSubscriberIfExists(String username) throws Exception{
		String query = "USE TradingSystem";
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT username "
				+ "FROM Subscribers "
				+ "WHERE username = " + username + ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next()){
			List<Purchase> myPurchase = getMyPurchase(res.getString("username"));
			List<StoreManager> managers = getSubscriberManagers(res.getString("username"));
			List<StoreOwner> owners = getStoreOwners(res.getString("username"));
			return new Subscriber(res.getString("username"),
					res.getString("password"),
					res.getString("fullname"),
					res.getString("address"),
					res.getString("phone"), 
					res.getString("creditCardNumber"),
					myPurchase, managers, owners);
		}
		return null;
	}
	
	public List<Store> getStores() throws Exception {
		String query = "USE TradingSystem";
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query = "SELECT * FROM Stores";
		ResultSet rs = statement.executeQuery(query);
		List <Store> ret = new LinkedList<Store>();
		while(rs.next())
		{
			boolean isOpen = false;
			if(rs.getInt("isOpen") == 1)
				isOpen = true;
			int storeId = rs.getInt("storeId");
			Store s = new Store(storeId, rs.getString("name"), rs.getString("address"), rs.getString("phone"),
					rs.getInt("grading"),getProductAmount(storeId), getStorePurchase(storeId), isOpen,
					getStoreOwnersFromStore(storeId), getStoreManagersFromStore(storeId),
					rs.getInt("moneyEarned"), getStorePolicy(storeId), getStoreCategoryDiscount(storeId),getStoreCategoryPolicy(storeId));	
			ret.add(s);
		}
		return ret;
	}
	
	public void addImeddiatleyProductToCart(String username, Product p, int amount, int code) throws Exception {
		String query = "INSERT INTO Carts VALUES(" + username +", " + p.getId() + ", " + amount + ", " + code + ");";
		otherQuery(query);
	}
	public void editProductAmount(String username, Product p, int amount) throws Exception {
		String query = "UPDATE Carts SET amount = " + amount + " WHERE username = " + username + " AND productId = " + p.getId() + ";"; 
		otherQuery(query);
		
	}
	public void editProductCode(String username, Product p, int code) throws Exception {
		String query = "UPDATE Carts SET code = " + code + " WHERE username = " + username + " AND productId = " + p.getId() + ";"; 
		otherQuery(query);
	}
	
	public int getAmountOfProduct(int storeId, int productId) throws Exception{
		String query = "SELECT * FROM ProductsInStores WHERE storeId = " + storeId + " AND productId = " + productId + ";";
		ResultSet res = selectQuery(query);
		if(res.next())
			return res.getInt("amount");
		return 0;
	}
	
	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception {
		String query = "INSERT INTO Purchases VALUES(" + p.getPurchaseID() + ", " + sub.getUsername() + ", " + p.getPurchased().getMyProduct().getId() + ", " + p.getPurchased().getMyProduct().getStore().getStoreId() + ", " + p.getWhenPurchased() + ", " + p.getPurchased().getDiscountOrPrice() + ", " + p.getPurchased().getAmount() + ");";
		otherQuery(query);
	}
	public List<StoreOwner> getStoreOwnersFromStore(int storeId) throws Exception{
		String query = "SELECT * FROM StoreOwners WHERE storeId = " + storeId + ";" ;
		ResultSet res = selectQuery(query);
		List<StoreOwner> ans = new LinkedList<StoreOwner>();
		while(res.next()){
			ans.add(new StoreOwner(getStoreByStoreId(storeId))); //TODO - very strange need to check...
		}
		return ans;
	}
	
	public List<StoreManager> getStoreManagersFromStore(int storeId) throws Exception{
		String query = "SELECT * FROM StoreManagers WHERE storeId = " + storeId + ";" ;
		ResultSet res = selectQuery(query);
		Map<String,StoreManager>map=new HashMap<String, StoreManager>();
		while(res.next()){
			String username=res.getString("username");
			if(map.containsKey(username)){
				map.get(username).setSpecificPermission(res.getInt("permission"), true);
			}
			else{
				StoreManager sm=new StoreManager(getStoreByStoreId(storeId));
				sm.setSpecificPermission(res.getInt("permission"), true);
				map.put(username,sm);
			}
		}
		return new LinkedList<StoreManager>(map.values());
	}
	
	
	public Product getProductById(int productId) throws Exception{
		String query = "SELECT * FROM Products WHERE ptoductId = " + productId + ";";
		ResultSet res = selectQuery(query);
		if(res.next())
			return new Product(productId, res.getString("name"), res.getInt("price"),res.getInt("grading"), getPurchasePolicy(res.getInt("policyId")), getProductStore(productId), getCategory(res.getString("category")),getPurchaseType(productId));
		return null;
	}
	
	public int getNextProductId() throws Exception {
		String query="SELECT MAX(productId) FROM Products";
		ResultSet res = selectQuery(query);
		if(res.next()){
			return res.getInt(1)+1;
		}
		return 155;
	}
	public int getNextStoreId() throws Exception {
		String query="SELECT MAX(storeId) FROM Stores";
		ResultSet res = selectQuery(query);
		if(res.next()){
			return res.getInt(1)+1;
		}
		return 144;
	}
	public int getNextPolicyId() throws Exception {
		String query="SELECT MAX(policyId) FROM Policies";
		ResultSet res = selectQuery(query);
		if(res.next()){
			return res.getInt(1)+1;
		}
		return 133;
	}

	public int getNextPurchaseId() throws Exception {
		String query="SELECT MAX(purchaseID) FROM Purchases";
		ResultSet res = selectQuery(query);
		if(res.next()){
			return res.getInt(1)+1;
		}
		return 122;
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
	
	private static ResultSet selectQuery(String selectQuery) throws Exception{
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate("USE TradingSystem");
		statement.executeUpdate(selectQuery);
		return statement.executeQuery(selectQuery);
	}
	
	private static void otherQuery(String otherQuery) throws Exception{
		Connection c = getConnection();
		Statement statement = c.createStatement();
		statement.executeUpdate("USE TradingSystem");
		statement.executeUpdate(otherQuery);
	}
	
	////////////////////////////////////////////////////////////////////////////
	public PurchasePolicy getPurchasePolicy(int policyId) throws Exception {
		String query = "SELECT * FROM Policies WHERE policyId = " + policyId + ";";
		ResultSet res = selectQuery(query);
		if(res.next()){
			DiscountPolicy discount=getDiscountPolicy(policyId);
			switch(res.getInt("policyType"))
			{
				case emptyPolicyTypeCode: 
					return new EmptyPolicy(discount);
				case andPolicyTypeCode:
					return new AndPolicy(discount, getSubPolicies(policyId));
				case orPolicyTypeCode:
					return new OrPolicy(discount, getSubPolicies(policyId));
				case notPolicyTypeCode:
					List<PurchasePolicy>lst=getSubPolicies(policyId);
					if(lst.size()>0)
						return new NotPolicy(discount, lst.get(0));
					else
						return new NotPolicy(discount, null);
				case maxPolicyTypeCode:
					return new MaxPolicy(discount, res.getInt("IValue"));
				case minPolicyTypeCode:
					return new MinPolicy(discount, res.getInt("IValue"));
				case addressPolicyTypeCode:
					return new AddressPolicy(discount, res.getString("SValue"));
				default: throw new Exception("Error in policy type");
			}
		}
		return null;
	}
	
	public List<PurchasePolicy> getSubPolicies(int policyId) throws Exception{
		String query = "SELECT * FROM SubPolicies WHERE fatherPolicyId = " + policyId + ";";
		ResultSet res = selectQuery(query);
		List<PurchasePolicy> ans=new ArrayList<PurchasePolicy>();
		while(res.next()){
			ans.add(getPurchasePolicy(res.getInt("sonPolicyId")));
		}
		return ans;
	}
	
	public PurchasePolicy getStorePolicy(int storeId) throws Exception{
		String query = "SELECT * FROM Stores WHERE storeId = " + storeId + ";";
		ResultSet res = selectQuery(query);
		if(res.next())
		{
			return getPurchasePolicy(res.getInt("policyId"));
		}
		return null;
	}

	public Map<Category, PurchasePolicy> getStoreCategoryDiscount(int storeId) throws Exception{
		String query = "SELECT * FROM CategoryDiscount WHERE storeId = " + storeId + ";";
		ResultSet res = selectQuery(query);
		Map<Category, PurchasePolicy> ans=new HashMap<Category, PurchasePolicy>();
		while(res.next())
		{
			ans.put(getCategory(res.getString("categoryName")), getPurchasePolicy(res.getInt("policyId")));
		}
		return ans;
	}
	
	private Map<Category, PurchasePolicy> getStoreCategoryPolicy(int storeId) throws Exception {
		String query = "SELECT * FROM CategoryPolicy WHERE storeId = " + storeId + ";";
		ResultSet res = selectQuery(query);
		Map<Category, PurchasePolicy> ans=new HashMap<Category, PurchasePolicy>();
		while(res.next())
		{
			ans.put(getCategory(res.getString("categoryName")), getPurchasePolicy(res.getInt("policyId")));
		}
		return ans;
	}

	private void updateStorePolicy(int storeId, PurchasePolicy newPolicy) throws Exception{
		int prevPolicyId=-1;
		String query = "SELECT * FROM Stores WHERE storeId = " + storeId + ";";
		ResultSet res = selectQuery(query);
		if(res.next())
		{
			prevPolicyId= res.getInt("policyId");
		}
		int PolicyId=getNextPolicyId();
		AddPolicy(newPolicy,PolicyId); 
		
		if(prevPolicyId!=-1){
			query="UPDATE Stores "
					+ " SET policyId = " +PolicyId
					+ " WHERE storeId = "+ storeId +";";
			otherQuery(query);
			deletePolicy(prevPolicyId);
		}
	}

	private void deletePolicy(int policyId) throws Exception {
		String query = "SELECT * FROM SubPolicies WHERE fatherPolicyId = " + policyId + ";";
		ResultSet res = selectQuery(query);
		while(res.next()){
			query = "DELETE * FROM SubPolicies WHERE fatherPolicyId = " + policyId +" AND sonPolicyId = "+res.getInt("sonPolicyId") +";";
			otherQuery(query);
			deletePolicy(res.getInt("sonPolicyId"));
		}
		query = "DELETE * FROM Policies WHERE policyId = " + policyId + " ;";
		otherQuery(query);
		
	}

	private void AddPolicy(PurchasePolicy newPolicy,int policyId) throws Exception{
		if(newPolicy instanceof AndPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + andPolicyTypeCode + ", "
					+ "" + ", " + "" + ");";
			otherQuery(query);
			for(PurchasePolicy policy:((AndPolicy)newPolicy).getSubPolicy()){
				int sonPolicyId=getNextPolicyId();
				AddPolicy(policy,sonPolicyId);
				query = "INSERT INTO SubPolicies VALUES(" + policyId + ", " + sonPolicyId+ ");";
				otherQuery(query);
			}
		}
		if(newPolicy instanceof NotPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + notPolicyTypeCode + ", "
					+ "" + ", " + "" + ");";
			otherQuery(query);
			PurchasePolicy policy=((NotPolicy)newPolicy).getSubPolicy();
			int sonPolicyId=getNextPolicyId();
			AddPolicy(policy,sonPolicyId);
			query = "INSERT INTO SubPolicies VALUES(" + policyId + ", " + sonPolicyId+ ");";
			otherQuery(query);
		}
		if(newPolicy instanceof OrPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + orPolicyTypeCode + ", "
					+ "" + ", " + "" + ");";
			otherQuery(query);
			for(PurchasePolicy policy:((OrPolicy)newPolicy).getSubPolicy()){
				int sonPolicyId=getNextPolicyId();
				AddPolicy(policy,sonPolicyId);
				query = "INSERT INTO SubPolicies VALUES(" + policyId + ", " + sonPolicyId+ ");";
				otherQuery(query);
			}
		}
		if(newPolicy instanceof EmptyPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + emptyPolicyTypeCode + ", "
					+ "" + ", " + "" + ");";
			otherQuery(query);
		}
		if(newPolicy instanceof MaxPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + maxPolicyTypeCode + ", "
					+ ((MaxPolicy)newPolicy).getMax() + ", " + "" + ");";
			otherQuery(query);
		}
		if(newPolicy instanceof MinPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + minPolicyTypeCode + ", "
					+ ((MinPolicy)newPolicy).getMin() + ", " + "" + ");";
			otherQuery(query);
		}
		if(newPolicy instanceof AddressPolicy){
			String query = "INSERT INTO Policies VALUES(" + policyId + ", " + addressPolicyTypeCode + ", "
								+ "" + ", " + ((AddressPolicy)newPolicy).getAddress() + ");";
			otherQuery(query);
		}
	}

	private void updateStoreCategoryDiscount(int storeId, Map<Category, PurchasePolicy> categorys) throws Exception{
		for (Entry<Category, PurchasePolicy> cat:categorys.entrySet()){
			int prevPolicyId=-1;
			String query = "SELECT * FROM CategoryPolicy WHERE storeId = " + storeId + " AND categoryName = " 
							+ cat.getKey().getName()+" ;";
			ResultSet res = selectQuery(query);
			if(res.next())
			{
				prevPolicyId= res.getInt("policyId");
			}
			int PolicyId=getNextPolicyId();
			AddPolicy(cat.getValue(),PolicyId); 
			
			
			if(prevPolicyId!=-1){
				query="UPDATE CategoryPolicy "
						+ " SET policyId = " +PolicyId
						+ " WHERE storeId = "+ storeId +" AND categoryName = "+ cat.getKey().getName() +";";
				otherQuery(query);
				deletePolicy(prevPolicyId);
			}
			else{
				query="INSERT INTO CategoryPolicy VALUES( "
						+ storeId+" , "+cat.getKey().getName()+" , "+PolicyId+" );";
				otherQuery(query);
			}
		}
	}
	
	public boolean isCategoryExists(String category) {
		//TODO not sure about categories
		return false;
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
		String query = "DELETE * FROM Purchases WHERE purchaseID = " + purchaseId + " ;";
		otherQuery(query);
		
	}
	
	
	
}
