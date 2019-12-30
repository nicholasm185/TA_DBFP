package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/FoodHallDB";
    static final String USER = "root";
    static final String PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static Connection connect(){
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean executeSQL(String sql){
        try {
            conn = connect();
            stmt = conn.createStatement();
            boolean result = stmt.execute(sql);
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    login function
    public static int login(String username, String password){
        if(!username.isEmpty() && !password.isEmpty()) {
            String sql = "SELECT * FROM cashier WHERE cashierName = '%s' AND password = '%s'";

            try {
                conn = connect();
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
            conn = connect();
            stmt = conn.createStatement();
            sql = String.format(sql, cashierID, storeID, paymentTypeID);
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateBill(int billID, int cashierID, int storeID, int paymentTypeID){

        String sql = "UPDATE bill set cashierID = '%d', storeID = '%d', paymentTypeID = '%d' where billID = '%d'";
        sql = String.format(sql, cashierID, storeID, paymentTypeID, billID);

        executeSQL(sql);

    }

    public static ResultSet selectAllBill(String startDate, String endDate){

        String sql = "SELECT b.billID, b.transactionTime, c.cashierName, s.storeName, p.paymentName FROM bill b " +
                "INNER JOIN cashier c on b.cashierID = c.cashierID " +
                "INNER JOIN store s on b.storeID = s.StoreID " +
                "INNER JOIN paymenttype p ON b.paymentTypeID = p.paymentTypeID " +
                "WHERE b.transactionTime BETWEEN \'" + startDate + "\' and \'" + endDate +"\'";

        try{
            conn = connect();
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
            conn = connect();
            rs = conn.createStatement().executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return rs;
    }

//    itemTransaction related functions
    public static void newItemTransaction(int billID, int productID, int qty){

        String sql = "INSERT INTO itemTransaction (billID, productID, qty) VALUES ( '%d', '%d', '%d')";
        sql = String.format(sql, billID, productID, qty);

        executeSQL(sql);

    }

    public static void updateItemTransaction(int transactionID, int billID, int productID, int qty){

        String sql = "UPDATE itemTransaction set billID = '%d', productID = '%d', qty where transactionID = '%d'";
        sql = String.format(sql, billID, productID, qty, transactionID);

        executeSQL(sql);

    }

    public static ResultSet getItemTransaction(int billID){

        String sql = "SELECT t.transactionID, t.qty, p.productName, p.productPrice FROM itemtransaction t INNER JOIN products p ON t.productID = p.productID WHERE billID = '%d'";
        sql = String.format(sql, billID);

        try{
            conn = connect();
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
            conn = connect();
            String sql = "SELECT StoreName FROM store";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                listofTypes.add(rs.getString("StoreName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listofTypes;
    }

    public static void testconnect(){

        String sql = "Select * from itemTransaction";

        try {
            conn = connect();
            rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("transactionID"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    }