import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

    public static Connection conn;

    DBCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment3", "zerefwayne", "bane123");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}