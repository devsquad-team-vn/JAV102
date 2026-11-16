package com.polycoffee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Lop tien ich ket noi va thuc thi truy van JDBC cho he thong PolyCoffee.
 * Cai dat theo chuan Slide 3 & Lab 3 - JAV102.
 *
 * @author Quang (Nhóm PolyCoffee - JAV102)
 */
public class JdbcUtil {
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String dburl = "jdbc:sqlserver://localhost:1433;database=polycoffee;encrypt=true;trustServerCertificate=true;";
    static String username = "sa";
    static String password = "kdiuoilaiu1601";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mo ket noi moi toi CSDL SQL Server polycoffee.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, username, password);
    }

    /**
     * Tao PreparedStatement (hoac CallableStatement neu bat dau bang '{') va gan tham so.
     */
    public static PreparedStatement createPreStmt(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        if (sql.trim().startsWith("{")) {
            stmt = connection.prepareCall(sql);
        } else {
            stmt = connection.prepareStatement(sql);
        }
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
        return stmt;
    }

    /**
     * Thuc thi cac cau lenh INSERT, UPDATE, DELETE.
     */
    public static int executeUpdate(String sql, Object... values) throws SQLException {
        PreparedStatement stmt = JdbcUtil.createPreStmt(sql, values);
        try (Connection conn = stmt.getConnection(); PreparedStatement ps = stmt) {
            return ps.executeUpdate();
        }
    }

    /**
     * Thuc thi cau lenh SELECT hoac Stored Procedure tra ve ResultSet.
     */
    public static ResultSet executeQuery(String sql, Object... values) throws SQLException {
        PreparedStatement stmt = JdbcUtil.createPreStmt(sql, values);
        return stmt.executeQuery();
    }
}
