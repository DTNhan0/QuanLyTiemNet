package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.DriverManager;

public class QLTiemNetConnectionDBS {
    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyTiemNet;encrypt=false;";
        String username = "sa";
        String password = "12345";
        Connection con = DriverManager.getConnection(connectionURL, username, password);
        return con;
    }
}
