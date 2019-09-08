import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAllBooks {
    private Connection conn;
    public ViewAllBooks(Connection conn) throws SQLException {
        this.conn = conn;

        String query = "select book_id, book_name, author_name, issue_status from Book";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet rs = preparedStatement.executeQuery();

        System.out.println("BOOK ID         BOOK NAME           AUTHOR NAME         ISSUE STATUS");
        System.out.println("--------        ---------           -----------         -------------");
        while(rs.next()){
            System.out.print(Integer.toString(rs.getInt(1))+"               ");
            for(int i=2;i<=4;i++){
                System.out.print(rs.getString(i)+"              1" +
                        "");
            }
            System.out.println('\n');
        }
    }
}
