import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddStudent {
    Scanner sc = new Scanner(System.in);
    private Connection conn;

    public AddStudent(Connection conn) throws SQLException {
        this.conn = conn;

        System.out.println("Enter Id of the student");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Name of the student");
        String name = sc.nextLine();

        String query = "insert into Student values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, 0);
        preparedStatement.setInt(4, 0);

        preparedStatement.execute();

        System.out.println("Student Added!\n");
    }
}
