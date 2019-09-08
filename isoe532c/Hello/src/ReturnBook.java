import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReturnBook {
    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public ReturnBook(Connection conn) throws SQLException {
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

        query = "SELECT due_date FROM Issued WHERE student_id = ? and book_id = ?";
        preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, student_id);
        preparedStmt.setInt (2, book_id);

        rs = preparedStmt.executeQuery();

        long due_date = 0;
        int size = 0;
        while (rs.next())
        {
            due_date = rs.getLong("due_date");
            size += 1;
        }

        int fine = 0;

        query = "SELECT total_fine FROM Student WHERE student_id = ?";
        preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, student_id);
        rs = preparedStmt.executeQuery();

        while (rs.next())
        {
            fine = rs.getInt("total_fine");
        }

//        long t0 = 0;
//        query = "select due_date from Issued where student_id = ? and book_id = ?";
//        preparedStmt = conn.prepareStatement(query);
//        preparedStmt.setInt (1, student_id);
//        preparedStmt.setInt(2, book_id);
//        rs = preparedStmt.executeQuery();
//
//        while (rs.next())
//        {
//            t0 = rs.getLong("due_date");
//        }

        if(size == 0){
            System.out.println("Cannot issue book with entered book Id to entered student id");
        }
        else if(issue_status.compareTo("Not_Issued") == 0){
            System.out.println("The book is not issued");
        }
        else {
            String Not_Issued = "Not_Issued";
            query = "update Book set issue_status = ? where book_id =?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, Not_Issued);
            preparedStmt.setInt (2, book_id);
            preparedStmt.execute();

            query = "delete from Issued where book_id = ? and student_id = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, book_id);
            preparedStmt.setInt(2, student_id);
            preparedStmt.execute();

            query = "update Student set total_fine = ? where student_id = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, (int) (fine + (Math.max((System.currentTimeMillis()/1000 - due_date)/(24*3600), 0) )*5));
            preparedStmt.setInt(2, student_id);
            preparedStmt.execute();

            query = "update Student set num_books = ? where student_id = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, num_books-1);
            preparedStmt.setInt(2, student_id);
            preparedStmt.execute();

            int temp = (int) (fine + (Math.max((System.currentTimeMillis()/1000 - due_date)/(24*3600), 0) )*5);
            if(temp > 0 ){
                System.out.println("Fine of Rs. "+Integer.toString(temp)+" is added");
            }
        }

    }
}
