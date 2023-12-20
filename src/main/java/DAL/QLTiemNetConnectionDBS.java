package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.DriverManager;

public class QLTiemNetConnectionDBS {
//    private static final SQLServerDataSource ds = new SQLServerDataSource();
//    static {
//        ds.setUser("sa");
//        ds.setPassword("123");
//        ds.setServerName("LAPTOP-R5G6HICH");
//        ds.setPortNumber(1433);
//        ds.setDatabaseName("QuanLyTiemNet");
//        ds.setEncrypt(false);
//    }
//
//    public Connection getConnection() {
//        try {
//            return ds.getConnection();
//        } catch (SQLServerException e) {
//            throw new RuntimeException("Kết nối không thành công!");
//        }
//    }

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyTiemNet;encrypt=false;";
        String username = "sa";
        String password = "123";
        Connection con = DriverManager.getConnection(connectionURL, username, password);
        return con;
    }
}
