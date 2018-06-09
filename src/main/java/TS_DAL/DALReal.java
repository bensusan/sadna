package TS_DAL;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import TS_SharedClasses.*;

public class DALReal implements DAL {
	private Connection conn = null;
	private Statement stmt = null;

	private String DataBaseName="TradingSystem";
	
	private final static int emptyPolicyTypeCode = 0, andPolicyTypeCode = 1, orPolicyTypeCode = 2,
			notPolicyTypeCode = 3, maxPolicyTypeCode = 4, minPolicyTypeCode = 5,addressPolicyTypeCode=6;


	private static DALReal dal = new DALReal(1);
	private static DALReal Testdal = new DALReal(2);
	
	private DALReal(int Type){
		if(Type==1){
			DataBaseName="TradingSystem";
		}
		else{
			DataBaseName="TradingSystemTest";
		}
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql;
			try{
				sql = "CREATE DATABASE "+DataBaseName;
				stmt.executeUpdate(sql);
			}
			catch (SQLException sqlException) {
				if (sqlException.getErrorCode() == 1007) {
					// Database already exists error
					sql = "DROP DATABASE "+DataBaseName;
					stmt.executeUpdate(sql);
				}
			}
			sql = "USE "+DataBaseName;
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
			sql = "CREATE TABLE LotteryPurchases("
					+ "productId INTEGER REFERENCES Products(productId),"
					+ "lotteryId INTEGER UNIQUE,"
					+ "actualEndDate DATE,"
					+ "lotteryEndDate DATE,"
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
					+ "lotteryId INTEGER REFERENCES LotteryPurchases(lottaryId),"
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
	public static DALReal getInstance(int type){
		if(type==1){
			return dal;
		}
		return Testdal;
		
	}
	//or
	public List<Subscriber> allSubscribers() throws Exception
	{
		String query = "USE "+DataBaseName;
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Subscribers";
		ResultSet rs = statement.executeQuery(query);
		List<Subscriber>ans = new LinkedList<Subscriber>();
		while(rs.next())
		{
			List<Purchase> myPurchase = getMyPurchase(rs.getString("username"));
			List<StoreManager>managers = getSubscriberManagers(rs.getString("username"));
			List<StoreOwner>owners = getStoreOwners(rs.getString("username"));
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Stores WHERE storeId = " + storeId;
		ResultSet rs = statement.executeQuery(query);
		if(rs.next())
		{
			boolean isOpen = false;
			if(rs.getInt("isOpen") == 1)
				isOpen = true;
			Store s = new Store(rs.getString("name"), rs.getString("address"), rs.getString("phone"), rs.getInt("grading"), getProductAmount(storeId), getStorePurchase(storeId), isOpen);
			return s;
		}
		return null;
	}



	public  List<StoreManager> getSubscriberManagers(String username) throws Exception {
		String query = "USE "+DataBaseName;
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM StoreManagers WHERE username = " + username;
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
		String query = "USE "+DataBaseName;
		Connection c=getConnection();
		Statement statement = c.createStatement();
		statement.executeQuery(query);
		query ="SELECT * FROM Purchased "
				+ "INNER JOIN Products ON Purchased.productId = Products.productId"
				+ "INNER JOIN Policies ON Purchased.policyId = Policies.policyId"
				+ "WHERE Purchased.username = " + username;
		ResultSet rs = statement.executeQuery(query);
		List <Purchase> ret = new LinkedList<Purchase>();
		while(rs.next())
		{
			int policyId = rs.getInt("policyId");
			int type = rs.getInt("policyType");
			int maxOrMin = rs.getInt("IValue");
			String address = rs.getString("SValue");
			PurchasePolicy pp = null;
			switch(rs.getInt("policyType")){
			case 0:{
				pp = getPurchasePolicyAtomic(policyId, type, maxOrMin, address);
			}
			case 1:{
				List<PurchasePolicy> sub = getPurchasePolicyNotAtomic(policyId, type, maxOrMin, address);
				pp = new AndPolicy(getDiscountPolicy(policyId), sub);
			}
			case 2:{
				List<PurchasePolicy> sub = getPurchasePolicyNotAtomic(policyId, type, maxOrMin, address);
				pp = new OrPolicy(getDiscountPolicy(policyId), sub);
			}
			case3:{
				List<PurchasePolicy> sub = getPurchasePolicyNotAtomic(policyId, type, maxOrMin, address);
				pp = new NotPolicy(getDiscountPolicy(policyId), sub.get(0));
			}
			case 4:{
				pp = getPurchasePolicyAtomic(policyId, type, maxOrMin, address);
			}
			case 5:{
				pp = getPurchasePolicyAtomic(policyId, type, maxOrMin, address);
			}
			case 6:{
				pp = getPurchasePolicyAtomic(policyId, type, maxOrMin, address);
			}
			}
			//PurchaseType pt = new PurchaseType();
			Product p = new Product(rs.getString("name"), rs.getInt("price"), rs.getInt("grading"), pp, null);
			ProductInCart pic = new ProductInCart(p, rs.getInt("price"), rs.getInt("amount"));
			Purchase purchase = new Purchase(rs.getDate("whenPurchased"), pic);
			ret.add(purchase);
		}
		return null;//TODO fix this function
		//	return ret;
	}

	public boolean isSubscriberExist(String username) throws Exception{
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Subscribers " +
				"WHERE username = " + sub.getUsername();
		statement.executeUpdate(query);
	}


	///problem with policies...
	public List<Purchase> getStorePurchase(Store store){
		return null;
	}

	public void addPurchaseToHistory(Subscriber sub, Purchase p) throws Exception{
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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

	//policies.....
	public List<Product> getAllProductsOfStore(Store store) throws Exception{
		//		String query = "USE "+DataBaseName;
		//		Connection c=getConnection();
		//		Statement statement=c.createStatement();
		//		statement.executeQuery(query);
		//		query ="SELECT * FROM ProductsInStores WHERE storeId = " +store.getStoreId()+ ";";
		//		ResultSet rs=statement.executeQuery(query);
		//		List<Product>ans=new LinkedList<Product>();
		//		while(rs.next())
		//		{
		//			Product prod = new Product(rs.getString("name"), rs.getInt("price"), rs.getInt("grading"), );
		//			ans.add(prod);
		//		}
		//		return ans;
		return null;
	}

	public void stockUpdate(Product p, int amount,Store s) throws Exception{
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Stores " +
				"SET moneyEarned = " + newMoneyEarend +
				" WHERE storeId = "+ s.getStoreId();
		statement.executeUpdate(query);
	}

	//policies....
	public List<Category>getAllCategory(){
		return null;

	}
	//policies...
	public Category getCategory(String categoryName){
		return null;

	}

	public void addProductToStore(Store s, Product product, int amount,String category) throws Exception{
		String query = "USE "+DataBaseName;
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

	public void deleteProductFromStore(Store s, Product product) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM ProductsInStores " +
				"WHERE productId = " + product.getId() +
				"AND storeId = " + s.getStoreId();
		statement.executeUpdate(query);
	}

	//polices....
	public void updateProductDetails(Store s, Product oldProduct, Product newProduct, int amount,String newProductCategory)
	{
	}

	public void addNewStoreOwner(Store s, Subscriber owner) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES (" + owner.getUsername() + " , " + s.getStoreId() + ")";
		statement.executeUpdate(query);
	}

	public void addNewStoreManager(Store s, Subscriber newMan, int permission) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO StoreOwners " +
				"VALUES (" + newMan.getUsername() + " , " + s.getStoreId() + " , " + permission + ")";
		statement.executeUpdate(query);
	}

	//if isOpen insert 1 to store to the isOpen(TinyInt) field, else insert 0
	public void updateStore(Store s) throws Exception{
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
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
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "SELECT * "
				+ "FROM Subscribers "
				+ "WHERE username = " + username
				+ "AND password = "+ password + ";";
		statement.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		if(res.next()){
			List<Purchase> myPurchase=getMyPurchase(res.getString("username"));
			List<StoreManager>managers=getSubscriberManagers(res.getString("username"));
			List<StoreOwner>owners=getStoreOwners(res.getString("username"));
			Subscriber s = new Subscriber(res.getString("username"),
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
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Carts " +
				"VALUES (" + username + " , " + productId + " , " + amount + " , " + code + ")";
		statement.executeUpdate(query);
	}


	public void removeProductFromCart(String username, int productId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Carts " +
				"WHERE productId = " + productId +
				"AND username = " + username;
		statement.executeUpdate(query);
	}


	public void editProductAmount(String username, int productId, int amount) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Carts " +
				"SET amount = "+ amount +
				" WHERE productId = "+ productId +
				"AND username = " + username;
		statement.executeUpdate(query);
	}


	public void editProductCode(String username, int productId, int code) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Carts " +
				"SET code = "+ code +
				" WHERE productId = "+ productId +
				"AND username = " + username;
		statement.executeUpdate(query);
	}


	public void editProductPrice(int productId, int price) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Products " +
				"SET price = "+ price +
				" WHERE productId = "+ productId;
		statement.executeUpdate(query);
	}
	//isOpen = false
	public void closeStore(int storeId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Store " +
				"SET isOpen = "+ 0 +
				" WHERE storeId = "+ storeId;
		statement.executeUpdate(query);
	}
	//is
	public void openStore(int storeId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "UPDATE INTO Store " +
				"SET isOpen = "+ 1 +
				" WHERE storeId = "+ storeId;
		statement.executeUpdate(query);
	}

	public void addStore(Store s) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "INSERT INTO Stores " +
				"VALUES (" + s.getStoreId() + " , " + s.getStoreName() 
				+ " , " + s.getAddress() + 
				" , " + s.getPhone() + " , " + s.getGradeing() + 
				" , " + s.getIsOpen() + 
				" , " + s.getMoneyEarned() + 
				" , " + s.getStorePolicy()+ ")"; //policy id , need to check last param!!!!!!!!!
		statement.executeUpdate(query);
	}

	//policies
	public  Map<Product,Integer> getProductAmount(int storeId) throws Exception {
		String query = "USE "+DataBaseName;
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
		}
		return prodAmount;
	}

	///policies.......
	public  List<Purchase> getStorePurchase(int storeId) {
		// TODO return all purchase of Subscriber with given username
		return null;
	}


	//########### or new function ###########
	public void removePurchase(Subscriber s, Purchase p) {
		// TODO remove purchase from purchase table
	}

	public DiscountPolicy getDiscountPolicy(int policyId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT * "
				+ "FROM HiddenDiscount "
				+ "FULL OUTER JOIN OvertDiscount ON HiddenDiscount.policyId = OvertDiscount.policyId"
				+ "INNER JOIN Policies " 
				+ "WHERE OvertDiscount.policyId = Policies.policyId"
				+ "OR HiddenDiscount.policyId = Poilcies.policyId ;" ;
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

	public PurchasePolicy getPurchasePolicyAtomic(int policyId, int type, int maxOrMin, String address) throws Exception{
		PurchasePolicy pp = null;
		switch(type){
		case 0: {
			pp = new EmptyPolicy(getDiscountPolicy(policyId)); 
		}
		case 4: {
			pp = new MaxPolicy(getDiscountPolicy(policyId), maxOrMin); 
		}
		case 5: {
			pp = new MinPolicy(getDiscountPolicy(policyId), maxOrMin); 
		}
		case 6: {
			pp = new AddressPolicy(getDiscountPolicy(policyId), address); 
		}
		}
		return pp;
	}
	
	public List<Integer> getNumbersOfSubPolicies(int policyId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		stmt.executeUpdate(query);
		query = "SELECT * "
				+ "FROM SubPolicies "
				+ "WHERE SubPolicies.fatherPolicyId = " + policyId + ";" ;
		stmt.executeUpdate(query);
		ResultSet res=statement.executeQuery(query);
		List<Integer> lst = new LinkedList<Integer>();
		while(res.next()){
			lst.add(res.getInt("sonPolicyId"));
		}
		return lst;
	}
	
	public List<PurchasePolicy> getPurchasePolicyNotAtomic(int policyId, int type, int maxOrMin, String address) throws Exception{
		List<PurchasePolicy> ret = new LinkedList<PurchasePolicy>();
		List<Integer> lst = getNumbersOfSubPolicies(policyId);
		switch(policyId){
		case 1:{
			while(!lst.isEmpty()){
				PurchasePolicy pp = getPurchasePolicyAtomic(lst.get(0), type, maxOrMin, address);
				ret.add(pp);
				lst.remove(0);
			}
		}
		case 2:{
			while(!lst.isEmpty()){
				PurchasePolicy pp = getPurchasePolicyAtomic(lst.get(0), type, maxOrMin, address);
				ret.add(pp);
				lst.remove(0);
			}
		}
		case 3:{
				PurchasePolicy pp = getPurchasePolicyAtomic(lst.get(0), type, maxOrMin, address);
				ret.add(pp);
				lst.remove(0);
		}
		}
		return ret;
	}
	
	public void deleteStore(int storeId) throws Exception{
		String query = "USE "+DataBaseName;
		Connection c = getConnection();
		Statement statement=c.createStatement();
		statement.executeUpdate(query);
		query = "DELETE FROM Stores " +
				"WHERE storeId = " + storeId;
		statement.executeUpdate(query);
		query = "DELETE FROM ProductsInStores " +
				"WHERE storeId = " + storeId;
		statement.executeUpdate(query);
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
	


}
