import java.sql.*;
import java.util.Scanner;

public class IssueBook {
    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public IssueBook(Connection conn) throws SQLException {
        this.conn = conn;

        System.out.println("Enter Student Id");
        System.out.println("Enter Book Id");

        int student_id = sc.nextInt();
        int book_id = sc.nextInt();

        String query = "SELECT issue_status FROM Book WHERE book_id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, book_id);

        ResultSet rs = preparedStmt.executeQuery();
        String issue_status = "";

        while (rs.next())
        {
            issue_status = rs.getString("issue_status");
        }

        query = "SELECT num_books FROM Student WHERE student_id = ?";
        preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, student_id);

        rs = preparedStmt.executeQuery();
        int num_books = 0;

        while (rs.next())
        {
            num_books = rs.getInt("num_books");
        }

        if(issue_status.compareTo("Issued") == 0){
            System.out.println("The book is already issued by someone");
        }
        else if(num_books >= 4){
            System.out.println("The User has already issued 4 books");
        }
        else {
            String Issued = "Issued";
            query = "update Book set issue_status = ? where book_id =?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, Issued);
            preparedStmt.setInt(2, book_id);
            preparedStmt.execute();

            query = "insert into Issued values(?, ?, ?)";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, student_id);
            preparedStmt.setInt(2, book_id);
            preparedStmt.setLong(3, System.currentTimeMillis()/1000+(15*24*3600));
            preparedStmt.execute();

            query = "update Student set num_books = ? where student_id = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, num_books+1);
            preparedStmt.setInt(2, student_id);
            preparedStmt.execute();
        }

    }
}
