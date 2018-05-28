package TS_DAL;

import java.sql.*;

public class DAL {
	//TINYINT used for boolean, 0-false;1-true

	private Connection conn = null;
	private Statement stmt = null;

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
					+ "moneyEarned INTEGER,PRIMARY KEY (storeId));";
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
			sql = "CREATE TABLE Products(productId INTEGER,"
					+ " name VARCHAR(50),"
					+ " price INTEGER,"
					+ " grading INTEGER ,"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE StoreManagers(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "storeId INTEGER REFERENCES Stores(storeId) ,"
					+ " permission INTEGER"
					+ "PRIMARY KEY (username, storeId, permission));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE Carts(username VARCHAR(50) REFERENCES Subscribers(username),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ "amount INTEGER,"
					+ "code INTEGER"
					+ "PRIMARY KEY (username, productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE ProductsInCategory(categoryName VARCHAR(50),"
					+ " productId INTEGER REFERENCES Products(productId),"
					+ "PRIMARY KEY (categoryName, productId));";
			stmt.executeUpdate(sql); 
			sql = "CREATE TABLE HiddenDiscount(productId INTEGER REFERENCES Products(productId),"
					+ "code INTEGER,"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE OvertDiscount(productId INTEGER REFERENCES Products(productId),"
					+ " discountEndDate DATE,"
					+ " discountPercentage DATE,"
					+ "PRIMARY KEY (productId));";
			stmt.executeUpdate(sql);
			sql = "CREATE TABLE SubscribersInLottery(productId INTEGER REFERENCES Products(productId),"
					+ " username VARCHAR(50) REFERENCES Subscribers(username),"
					+ "PRIMARY KEY (productId, username));";
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

	protected static Connection getConnection() throws Exception {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "root";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
