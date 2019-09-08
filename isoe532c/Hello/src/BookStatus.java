import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookStatus {
    private Connection conn;
    private int student_id;
    Scanner sc = new Scanner(System.in);

    public BookStatus(int student_id, Connection conn) throws SQLException {
        this.conn = conn;
        this.student_id = student_id;

        System.out.println("Enter Id of the book");
        int book_id = sc.nextInt();

        String query = "select due_date from Issued where book_id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, book_id);
        ResultSet rs = preparedStmt.executeQuery();

        int size = 0;
        long due_date = 0;
        while(rs.next()){
            due_date = rs.getLong("due_date");
            size+=1;
        }

        query = "select total_fine from Student where student_id = ?";
        preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, student_id);
        rs = preparedStmt.executeQuery();

        int fine = 0;
        while (rs.next()){
            fine = rs.getInt("total_fine");
        }

        int days_left = (int)(due_date - System.currentTimeMillis()/1000 )/(24*3600) + 1;

        if(size == 0){
            System.out.println("The Book with this book Id is not issued");
        }
        else if(days_left >= 0){
            System.out.println("Please return the book in "+Integer.toString(days_left)+" days");
        }
        else {
            System.out.println("You have over-kept the book for "+Integer.toString(-1*days_left)+" days");
            System.out.println("Your current fine is Rs: "+Integer.toString(-1*days_left*5));
        }
    }
}
