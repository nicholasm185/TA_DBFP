package sample;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://dbta.1ez.xyz/9_FoodHallDB";
    static final String USER = "NIC8761";
    static final String PASS = "tdda2nit";
    static Connection conn;

    public static Connection connect(){
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void testconnect(){

        String sql = "Select * from Products";

        try {
            conn = connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("ProductName"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    }