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

//    bill related functions
    public static void newBill(int cashierID, int storeID){

        String sql = "INSERT INTO bill (cashierID, storeID) values ('%d', '%d')";

        try {
            conn = connect();
            stmt = conn.createStatement();
            sql = String.format(sql, cashierID, storeID);
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    payment related functions
    public static void newPayment(int billID, int amount){

        String sql = "INSERT INTO payment (billID, amount) values ('%d', '%d')";

        try {
            conn = connect();
            stmt = conn.createStatement();
            sql = String.format(sql, billID, amount);
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    itemTransaction related functions
    public static void newItemTransaction(int billID, int productID, int qty){

        String sql = "INSERT INTO itemTransaction (billID, productID, qty) VALUES ( '%d', '%d', '%d')";

        try {
            conn = connect();
            stmt = conn.createStatement();
            sql = String.format(sql, billID, productID, qty);
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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