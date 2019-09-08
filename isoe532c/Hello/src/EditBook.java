import java.util.*;
import java.sql.*;

public class EditBook {
    private Connection conn;
    Scanner sc = new Scanner(System.in);

    public EditBook(Connection conn) throws SQLException {

        this.conn = conn;

        System.out.println("Press 1 to Add Book");
        System.out.println("Press 2 to Delete Book");
        System.out.println("Press 3 to Update Book Info");

        int choice = sc.nextInt();

        if(choice == 1) AddBook();
        else if(choice == 2) DeleteBook();
        else if(choice == 3) UpdateBook();
        else System.out.println("Enter a Valid Choice");

    }

    public void AddBook() throws SQLException {
        System.out.println("Enter Id of the book");
        int book_id = sc.nextInt();

        sc.nextLine();

        System.out.println("Enter Name of the book");
        String book_name = sc.nextLine();

        System.out.println("Enter Name of the author");
        String author_name = sc.nextLine();

        String query = "insert into Book values (?, ?, ?, ?)";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt (1, book_id);
        preparedStmt.setString (2, book_name);
        preparedStmt.setString (3, author_name);
        preparedStmt.setString(4, "Not_Issued");

        preparedStmt.execute();
    }
    public void DeleteBook() throws SQLException {
        System.out.println("Enter Id of the book");
        int book_id = sc.nextInt();

        String query = "select issue_status from Book where book_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, book_id);

        String issue_status = "";
        ResultSet rs = preparedStatement.executeQuery();


        int size = 0;
        while(rs.next()){
            issue_status = rs.getString("issue_status");
            size += 1;
        }

        if(size == 0){
            System.out.println("Book with this book id doesn't exists");
        }
        else if(issue_status.compareTo("Issued") == 0){
            System.out.println("Cannot Delete the book because it is issued");
        }
        else{
            query = "delete from Book where book_id = ?;";

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt (1, book_id);

            preparedStatement.execute();
        }

    }
    public void UpdateBook() throws SQLException {
        System.out.println("Enter Id of the book");
        int book_id = sc.nextInt();

        String query = "select issue_status from Book where book_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, book_id);

        String issue_status = "";
        ResultSet rs = preparedStatement.executeQuery();

        int size = 0;
        while(rs.next()){
            issue_status = rs.getString("issue_status");
            size += 1;
        }

        if(size == 0){
            System.out.println("Book with this book id doesn't exists");
        }
        else if(issue_status.compareTo("Issued") == 0){
            System.out.println("Cannot Delete the book because it is issued");
        }
        else{
            System.out.println("Enter the field you want to edit");
            System.out.println("1 to update Book Id");
            System.out.println("2 to update Book Name");
            System.out.println("3 to update Author Name");

            int field = sc.nextInt();
            sc.nextLine();

            if(field == 1){
                System.out.println("Enter the new Book Id");
                int new_book_id = sc.nextInt();

                String Not_Issued = "Not_Issued";
                query = "UPDATE Book SET book_id = ? WHERE book_id = ? and issue_status = ?;";
                PreparedStatement preparedStmt = conn.prepareStatement(query);

                //preparedStmt.setString(1, "book_id");
                preparedStmt.setInt(1, new_book_id);
                preparedStmt.setInt (2, book_id);
                preparedStmt.setString(3, Not_Issued);

                preparedStmt.execute();
            }
            else if(field == 2){
                System.out.println("Enter the new Book Name");
                String new_book_name = sc.nextLine();

                query = "UPDATE Book SET book_name = ? WHERE book_id = ?;";
                PreparedStatement preparedStmt = conn.prepareStatement(query);

                //preparedStmt.setString(1, "book_name");
                preparedStmt.setString(1, new_book_name);
                preparedStmt.setInt (2, book_id);

                preparedStmt.execute();
            }
            else if(field == 3){
                System.out.println("Enter the new Author Name");
                String new_author_name = sc.nextLine();

                query = "UPDATE Book SET author_name = ? WHERE book_id = ?;";
                PreparedStatement preparedStmt = conn.prepareStatement(query);

                //preparedStmt.setString(1, "author_name");
                preparedStmt.setString(1, new_author_name);
                preparedStmt.setInt (2, book_id);

                preparedStmt.execute();
            }

            else{
                System.out.println("Enter a valid field");
            }
        }

    }

}
