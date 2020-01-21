package sample;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://dbta.1ez.xyz/9_FoodHallDB?autoReconnect=true&useSSL=false";
    static String USER = "root";
    static String PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void getCredentials(){
        String usrnm;
        String pass;

        Scanner sc = new Scanner(System.in);

        System.out.println("enter username");
        usrnm = sc.nextLine();
        System.out.println("enter pass");
        pass = sc.nextLine();

        USER = usrnm;
        PASS = pass;
    }

    public static void connect(){
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (CommunicationsException e){
            System.out.println("connection failed, check database status");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText("Could not connect to database");
            alert.setContentText("Make sure the database is running");

            alert.showAndWait();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void close(){
        try{
            conn.close();
            System.out.println("Connection successfully closed");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Closing connection attempt failed");
        }
    }

    public static boolean executeSQL(String sql){
        try {
//            conn = connect();
            stmt = conn.createStatement();
            boolean result = stmt.execute(sql);
//            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    login function
    public static int login(String username, String password){
        if(!username.isEmpty() && !password.isEmpty()) {
            String sql = "SELECT * FROM cashier WHERE cashierName = '%s' AND password = MD5('%s')";

            try {
//                conn = connect();
                sql = String.format(sql, username, password);
                rs = conn.createStatement().executeQuery(sql);

                if (rs.next()) {
                    if(rs.getInt("admin") == 0){
                        return 1;
                    } else if(rs.getInt("admin") == 1){
                        return 2;
                    }

                } else {
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        } else{
            return 0;
        }
        return 0;
    }

//    bill related functions
    public static void newBill(int cashierID, int storeID, int paymentTypeID){

        String sql = "INSERT INTO bill (cashierID, storeID, paymentTypeID) values ('%d', '%d', '%d')";

        try {
//            conn = connect();
            stmt = conn.createStatement();
            sql = String.format(sql, cashierID, storeID, paymentTypeID);
            stmt.execute(sql);
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean lastBillHaveNulls(int currbillID) {
        String sql = " SELECT cashierID FROM bill WHERE billID = '%d'";
        sql = String.format(sql, currbillID);

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
//            int lastCashier =;
            System.out.println("cashierID"+ rs.getInt("cashierID"));
            if (rs.getInt("cashierID") == 0){
                return true;
            }
            return false;

//            return records;
        } catch (SQLException e) {
//            e.printStackTrace();
            return true;
        }
    }

    public static void addInitBill() {
        String sql = "INSERT INTO bill (cashierID, storeID, paymentTypeID) values (null,null,null)";
//        System.out.println("cashierID"+cashierID);
//        System.out.println("storeID"+storeID);
//        System.out.println("paymentTypeID"+paymentTypeID);d
        try {
//            conn = connect();
            stmt = conn.createStatement();

//            sql = String.format(sql,NULL,);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getBillNumber() {
//        String sql = " SELECT COUNT(*) AS total FROM bill ";
        String sql = " SELECT MAX(billID) AS total FROM bill  ";

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
            return rs.getInt("total");

//            return records;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void updateBill(int billID, int cashierID, int storeID, int paymentTypeID){

        String sql = "UPDATE bill set cashierID = '%d', storeID = '%d', paymentTypeID = '%d' where billID = '%d'";
        sql = String.format(sql, cashierID, storeID, paymentTypeID, billID);

        executeSQL(sql);

    }

    public static ActualBill getABill(int billID){
        String sql = "SELECT * FROM bill WHERE billID = " + billID;
        ActualBill bill;

        try{
//            conn = connect();
            rs = conn.createStatement().executeQuery(sql);

            rs.next();

            bill = new ActualBill(rs.getInt("billID"), rs.getString("transactionTime"),
                    rs.getInt("cashierID"), rs.getInt("storeID"), rs.getInt("paymentTypeID"));

            return bill;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ResultSet selectAllBill(String startDate, String endDate){

        String sql = "SELECT b.billID, b.transactionTime, c.cashierName, s.storeName, p.paymentName FROM bill b " +
                "INNER JOIN cashier c on b.cashierID = c.cashierID " +
                "INNER JOIN store s on b.storeID = s.StoreID " +
                "INNER JOIN paymenttype p ON b.paymentTypeID = p.paymentTypeID " +
                "WHERE b.transactionTime BETWEEN \'" + startDate + "\' and \'" + endDate +"\'" +
                "ORDER BY b.billID ASC";

        try{
//            conn = connect();
            rs = conn.createStatement().executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return rs;

    }

    public static ResultSet selectBillFromStore(String startDate, String endDate, String storeName){

        String sql = "SELECT b.billID, b.transactionTime, c.cashierName, s.storeName, p.paymentName FROM bill b " +
                "INNER JOIN cashier c on b.cashierID = c.cashierID " +
                "INNER JOIN store s on b.storeID = s.StoreID " +
                "INNER JOIN paymenttype p ON b.paymentTypeID = p.paymentTypeID " +
                "WHERE b.transactionTime BETWEEN \'" + startDate + "\' and \'" + endDate +"\' AND s.StoreName = \'" + storeName + "\'";

        try{
//            conn = connect();
            rs = conn.createStatement().executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return rs;
    }

    public static void deleteBill(int billID){
        String sql = "DELETE FROM bill WHERE billID = '%d'";
        sql = String.format(sql, billID);

        executeSQL(sql);

    }

//    itemTransaction related functions
    public static void addItemTransaction(int billID, int productID , int qty){

        String sql = "INSERT INTO itemTransaction (billID, productID, qty) VALUES ('%d','%d','%d')";
        sql = String.format(sql, billID, productID, qty);

        executeSQL(sql);

    }

    public static int itemTransactionExist(int billID, int productID){

        String sql = " SELECT qty FROM itemtransaction " +
                "WHERE billID = '%d' AND productID = '%d'";
        sql = String.format(sql, billID, productID);

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
            int qtyprev = rs.getInt("qty");
            System.out.println("qtyprevdb "+qtyprev);
            return qtyprev;

//            return records;
        } catch (SQLException e) {
//            e.printStackTrace();
            return 0;
        }
    }

    public static void updateItemTransaction(int billID, int productID, int newQty){

        String sql = "UPDATE itemTransaction set qty = '%d' WHERE billID = '%d' AND productID = '%d'";
        sql = String.format(sql, newQty, billID,productID);

        executeSQL(sql);
    }

    public static void deleteItemTransaction(int selectedProductID, int billID){
        String sql = "DELETE FROM itemtransaction WHERE productID = '%d' AND billID = '%d'";
        sql = String.format(sql, selectedProductID, billID);

        executeSQL(sql);

    }

    public static ResultSet getAllItemTransactions(int currBillID) {

        String sql = "SELECT i.productID, p.productName, p.productPrice, i.qty FROM itemtransaction i\n" +
                "     INNER JOIN products p on i.productID = p.productID \n" +
                "     WHERE i.billID = '%d'";
        sql = String.format(sql, currBillID);

        try {
            rs = conn.createStatement().executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return rs;

    }

//    store related fucntions
    public static ArrayList<String> getAllStores() throws NullPointerException {
        ArrayList<String> listofTypes = new ArrayList<>();
        try {
//            conn = connect();
            String sql = "SELECT * FROM store";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                listofTypes.add(rs.getString("StoreName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listofTypes;
    }

    public static int getStoreID(String storeName){

        String sql = "SELECT storeID FROM store WHERE storeName = '%s'";
        sql = String.format(sql, storeName);

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
            return rs.getInt("storeID");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


//    cashier related functions
    public static ArrayList<Cashier> getAllCashier(){
        ArrayList<Cashier> cashierList = new ArrayList<>();
        try {
//            conn = connect();
            String sql = "SELECT cashierID, cashierName, password, admin, CASE WHEN admin = '0' THEN \"NO\" ELSE \"YES\" END AS adminStatus From cashier";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                cashierList.add(new Cashier(rs.getInt("cashierID"), rs.getString("cashierName"),
                        rs.getString("password"), rs.getString("adminStatus")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashierList;
    }

    public static int getCashierID(String cashierName){

        String sql = "SELECT cashierID FROM cashier WHERE cashierName = '%s'";
        sql = String.format(sql, cashierName);

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
            return rs.getInt("cashierID");

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ArrayList<String> getCashierNames(){
        ArrayList<String> cashierNames = new ArrayList<>();
        try {
//            conn = connect();
            String sql = "SELECT cashierID, cashierName FROM cashier";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                cashierNames.add(rs.getString("cashierID") + " " + rs.getString("cashierName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashierNames;

    }

    public static void addCashier(String name, String pass, int admin){

        String sql = "INSERT INTO cashier (cashierName, password, admin) VALUES ('%s', MD5('%s'),'%d')";
        sql = String.format(sql, name, pass, admin);

        executeSQL(sql);

    }


    public static void deleteCashier(int cashierID){

        String sql = "DELETE FROM cashier WHERE cashierID = '%d'";
        sql = String.format(sql, cashierID);

        executeSQL(sql);

    }

    public static void updateCashier(int cashierID, String cashierName, String cashierPass, int admin){

        String sql = "UPDATE cashier SET cashierName = '%s', password = MD5('%s'), admin = '%d' WHERE cashierID = '%d'";
        sql = String.format(sql, cashierName, cashierPass, admin, cashierID);

        executeSQL(sql);

    }

    public static boolean checkPassword(int cashierID, String password){

        String sql = "SELECT * from cashier WHERE cashierID = '%d' AND password = MD5('%s')";
        sql = String.format(sql, cashierID, password);

        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);

            if (rs.next()){
                return true ;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;

    }

//    product related function
    public static ArrayList<Product> getAllProducts(){
        ArrayList<Product> productList = new ArrayList<>();
        try {
//            conn = connect();
            String sql = "SELECT * From products";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                productList.add(new Product(rs.getInt("productID"), rs.getString("productName"),
                        rs.getInt("productPrice")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
    public static void addProduct(String productName, int productPrice){

        String sql = "INSERT INTO products (productName, productPrice) VALUES ('%s','%d')";
        sql = String.format(sql, productName, productPrice);

        executeSQL(sql);

    }

    public static void updateProduct(int productID, String productName, int productPrice){

        String sql = "UPDATE products SET productName = '%s', productPrice = '%d' WHERE productID = '%d'";
        sql = String.format(sql, productName, productPrice, productID);

        executeSQL(sql);

    }

    public static void deleteProduct(int productID){

        String sql = "DELETE FROM products WHERE productID = '%d'";
        sql = String.format(sql, productID);

        executeSQL(sql);

    }
//    payment related functions

    public static ArrayList<String> getPaymentMethods(){
        ArrayList<String> paymentNames = new ArrayList<>();

        try {
//            conn = connect();
            String sql = "SELECT * FROM paymentType";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                paymentNames.add(rs.getString("paymentTypeID") + " " + rs.getString("paymentName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentNames;

    }

    public static int getPaymentID(String paymentName){

        String sql = "SELECT paymentTypeID FROM paymenttype WHERE paymentName = '%s'";
        sql = String.format(sql, paymentName);

        try {
            rs = conn.createStatement().executeQuery(sql);
            rs.next();
            return rs.getInt("paymentTypeID");
//            return records;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static ArrayList<String> getAllPaymentTypes() throws NullPointerException {
        ArrayList<String> listofTypes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM paymenttype";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                listofTypes.add(rs.getString("paymentName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listofTypes;

    }


    public static void testconnect(){

        String sql = "Select * from itemTransaction";

        try {
//            conn = connect();
            rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("transactionID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    }