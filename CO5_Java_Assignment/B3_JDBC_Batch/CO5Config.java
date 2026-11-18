import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CO5Config {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/campus_transport";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = ""; // Set your local MySQL root password.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
