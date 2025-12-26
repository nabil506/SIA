package app.App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sekolah_db";
    private static final String DB_USER = "nabil07835";
    private static final String DB_PASS = "nabil12345";

    protected  static Connection conn;

    public static void connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            connect();
        }
        return conn;
    }

    public static PreparedStatement prepare(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }
    
    public static void setAutoCommit(boolean autoCommit) throws SQLException {
        getConnection().setAutoCommit(autoCommit);
    }
    
    public static void commit() throws SQLException {
        getConnection().commit();
    }
    
    public static void rollback() throws SQLException {
        getConnection().rollback();
    }

    public static void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
