import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchBook {
    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public SearchBook(Connection conn) throws SQLException {
        this.conn = conn;

        System.out.println("Enter Book Id");
        int book_id = sc.nextInt();

        String query = "select book_name, author_name, issue_status from Book where book_id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, book_id);

        ResultSet rs = preparedStmt.executeQuery();
        String res = "";
        while (rs.next()){
            res+= "BOOK NAME: ";
            for(int i=1;i<=3;i++){
                if(i==1){
                    res += rs.getString(i)+"    AUTHOR NAME: ";
                }
                else if(i==2){
                    res += rs.getString(i)+"    ISSUE STATUS: ";
                }
                else{
                    res += rs.getString(i);
                }
            }
            res+='\n';
        }
        System.out.println(res);
    }
}
