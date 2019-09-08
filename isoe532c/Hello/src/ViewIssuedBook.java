import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewIssuedBook {
    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public ViewIssuedBook(Connection conn) throws SQLException{
        this.conn = conn;

        System.out.println("Enter Student Id");
        int student_id = sc.nextInt();

        String query = "select book_id from Issued where student_id = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, student_id);

        ResultSet rs = preparedStmt.executeQuery();

        ArrayList<Integer> book_ids = new ArrayList<Integer>();

        while (rs.next()){
            book_ids.add(rs.getInt("book_id"));
        }

        System.out.println("BOOK NAMES");
        System.out.println("-----------\n");

        for(int i=0;i<book_ids.size();i++){
            query = "select book_name from Book where book_id = ?";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt (1, book_ids.get(i));

            rs = preparedStmt.executeQuery();

            while(rs.next()){
                System.out.println(rs.getString("book_name"));
            }
        }

        System.out.println('\n');
    }
}
