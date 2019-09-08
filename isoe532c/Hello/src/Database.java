import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Database {

    static Scanner  sc = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String driverName = "com.mysql.jdbc.Driver";
        Class.forName(driverName); // here is the ClassNotFoundException

        String serverName = "localhost";
        String mydatabase = "Library";
        String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

        String username = "zerefwayne";
        String password = "bane123";
        Connection connection = DriverManager.getConnection(url, username, password);

        System.out.println("Are you an admin or a user?");
        System.out.println(("Press 1 for admin"));
        System.out.println("Press 2 for user");

        int user_choice = sc.nextInt();

        if(user_choice == 1){
            System.out.println("You have entered as an admin\n");

            while(true){

                System.out.println("Press 1 to Edit Book Information");
                System.out.println("Press 2 to Issue a book");
                System.out.println("Press 3 to Return a book");
                System.out.println("Press 4 to View book issued to a student");
                System.out.println("Press 5 to Add a student");
                System.out.println("Press 6 to View all books");

                int choice = sc.nextInt();

                if(choice == 1){
                    new EditBook(connection);
                }
                else if(choice == 2){
                    new IssueBook(connection);
                }
                else if(choice == 3){
                    new ReturnBook(connection);
                }
                else if(choice == 4){
                    new ViewIssuedBook(connection);
                }
                else if(choice == 5){
                    new AddStudent(connection);
                }
                else if(choice == 6){
                    new ViewAllBooks(connection);
                }
                else{
                    System.out.println("Enter a valid choice");
                }

                System.out.println("Press 1 to continue");
                System.out.println("Press 0 to exit");

                int out = sc.nextInt();
                if(out == 0) break;

            }
        }

        else{
            System.out.println("Enter your Student Id");
            int student_id = sc.nextInt();
            System.out.println("You have entered as a user/student");


            while(true){

                System.out.println("Press 1 to View Book information");
                System.out.println("Press 2 to View status of your issued books");

                int choice = sc.nextInt();
                if(choice == 1){
                    new SearchBook(connection);
                }
                else if(choice == 2){
                    new BookStatus(student_id, connection);
                }
                else{
                    System.out.println("Enter a valid choice");
                }

                System.out.println("Press 1 to continue");
                System.out.println("Press 0 to exit");

                int out = sc.nextInt();
                if(out == 0) break;

            }

        }

    }
}
