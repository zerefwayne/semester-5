import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try {
            String host = "jdbc:mysql://localhost:3306/library2";
            String uName = "zerefwayne";
            String uPass = "bane123";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stat = con.createStatement();

            Scanner sc=new Scanner(System.in);

            String welcome_string="Hello! Welcome to Library Management System.";
            System.out.println(welcome_string);
            //choose opening mode;
            Boolean flag=false;
            while(flag==false) {
                int opening_mode;
                String choose_mode="Press: \n 1. Admin \n 2. Student \n 3. Exit";
                System.out.println(choose_mode);
                opening_mode=sc.nextInt();
                //Admin Mode
                if(opening_mode==3){
                    flag=true;
                    break;
                }
                if (opening_mode == 1) {

                    //admin confirmation
                    String password_confimation = "Enter the password Admin";
                    System.out.println(password_confimation);
                    String password = sc.next();
                    if (!password.equals("admin")) {
                        System.out.println("Enter the correct Password");
                        return;
                    }

                    //admin operations

                    String starting_msg = "Hello! Admin";
                    System.out.println(starting_msg);

                    String choose_option = "Choose operation to Perform: ";
                    System.out.println(choose_option);

                    String options_to_admin = " 1. View Books \n 2. Add Book \n 3. Delete Book \n 4. Update book Details \n 5. Edit book Information \n 6. Books issued by A Student";
                    System.out.println(options_to_admin);

                    int option_admin_choosed = sc.nextInt();

                    switch (option_admin_choosed) {
                        case 1:
                            String sql = "Select * from books;";
                            ResultSet res = stat.executeQuery(sql);
                            while (res.next()) {
                                System.out.println(res.getInt("book_id") + " " + res.getString("author_name") + " " +
                                        res.getString("book_name") + " " + res.getDate("issue_date") + " " +
                                        res.getDate("due_date") + " " + res.getString("student_id"));
                            }
                            break;
                        case 2:
                            System.out.println("Add details of books in this order: ");
                            System.out.println("Book ID: ");
                            String id = sc.next();
                            System.out.println("Book Name: ");
                            String book_name = sc.next();
                            System.out.println("Author Name: ");
                            String author_name = sc.next();
                            String add_book_sql = "insert into books(book_id,author_name,book_name) values('" + id + "','" + author_name + "','" + book_name + "')";
                            int res_add_book = stat.executeUpdate(add_book_sql);
                            break;
                        case 3:
                            System.out.println("Add details of book int order to delete it: ");
                            System.out.println("Book ID: ");
                            String delete_id = sc.next();
                            String delete_sql = "delete from books where books.book_id='" + delete_id + "'";
                            int res_delete_book = stat.executeUpdate(delete_sql);
                            break;
                        case 4:
                            System.out.println("Choose which section");
                            System.out.println(" a. Book ID \n b. Author Name \n c. Book Name ");
                            String update_choice = sc.next();
                            if (update_choice.equals("a")) {
                                System.out.println("Enter The Old  ID: ");
                                String old_book_id = sc.next();
                                System.out.println("Enter The New  ID: ");
                                String new_book_id = sc.next();
                                String update_book_id = "update books set book_id='" + new_book_id + "' where book_id='" + old_book_id + "';";
                                int res_update_book_id = stat.executeUpdate(update_book_id);
                            } else if (update_choice.equals("b")) {
                                System.out.println("Enter The New Book ID: ");
                                String new_book_id = sc.next();
                                System.out.println("Enter The New Author Name: ");
                                String new_author_name = sc.next();
                                String update_author_name = "update books set book_id='" + new_author_name + "' where book_id='" + new_book_id + "';";
                                int res_update_author_name = stat.executeUpdate(update_author_name);
                            } else if (update_choice.equals("c")) {
                                System.out.println("Enter The New Book ID: ");
                                String new_book_id = sc.next();
                                System.out.println("Enter The New Book Name: ");
                                String new_book_name = sc.next();
                                String update_book_name = "update books set book_id='" + new_book_name + "' where book_id='" + new_book_id + "';";
                                int res_update_book_name = stat.executeUpdate(update_book_name);
                            } else {
                                System.out.println("Enter Valid Choice.");
                            }
                            break;
                        case 5:
                            System.out.println("Choose Options to edit: ");
                            System.out.println(" a. Issue Date \n b. Due_Date");
                            String edit_choice = sc.next();
                            if (edit_choice.equals("a")) {
                                System.out.println("Enter Date in this format (YYYY/MM/DD): ");
                                String new_issue_date = sc.next();
                                String update_issue_date = "update books set books.issue_date='" + new_issue_date + "';";//pause
                                int res_update_issue_date = stat.executeUpdate(update_issue_date);
                            } else if (edit_choice.equals("b")) {
                                System.out.println("Enter Date in this format (YYYY/MM/DD): ");
                                String new_return_date = sc.next();
                                String update_return_date = "update books set books.issue_date='" + new_return_date + "';";//pause
                                int res_update_issue_date = stat.executeUpdate(update_return_date);
                            } else {
                                System.out.println("Enter a Valid Choice.");
                            }
                            break;
                        case 6:
                            System.out.println("Add details to see status of books issued by a Student: ");
                            System.out.println("Enter Student ID: ");
                            String s_id = sc.next();
                            String view_student = "Select * from books where books.student_id='" + s_id + "';";
                            ResultSet res_view_student = stat.executeQuery(view_student);
                            while (res_view_student.next()) {
                                System.out.println(res_view_student.getString("book_id") + " " + res_view_student.getString("book_name"));
                            }
                            break;
                        default:
                            System.out.println("Choose a valid Option");
                            break;
                    }

                }

                //Student Mode
                else if (opening_mode == 2) {
                    System.out.println("Hello! Student ");
                    System.out.println("Choose the operation: ");
                    System.out.println(" 1. Search a Book \n 2. Books in your Account \n 3. Issue Book \n 4. Return Book");
                    String student_choice = sc.next();
                    if (student_choice.equals("1")) {
                        System.out.println("Enter the book unique Identification Number: ");
                        String id_number = sc.next();
                        String search_book = "select * from books where books.book_id='" + id_number + "';";
                        ResultSet res_search_book = stat.executeQuery(search_book);
                        res_search_book.next();
                        System.out.println(res_search_book.getInt("book_id") + " " + res_search_book.getString("author_name") + " " +
                                res_search_book.getString("book_name") + " " + res_search_book.getDate("issue_date") + " " +
                                res_search_book.getDate("due_date") + " " + res_search_book.getString("student_id"));

                    } else if (student_choice.equals("2")) {
                        System.out.println("Enter Your ID Number: ");
                        String s_id = sc.next();
                        String student_account_info = "select * from books where books.student_id='" + s_id + "';";
                        ResultSet res_student_account_info = stat.executeQuery(student_account_info);
                        while (res_student_account_info.next()) {
                            System.out.println(res_student_account_info.getInt("book_id") + " " + res_student_account_info.getString("author_name") + " " +
                                    res_student_account_info.getString("book_name") + " " + res_student_account_info.getDate("issue_date") + " " +
                                    res_student_account_info.getDate("due_date") + " " + res_student_account_info.getString("student_id"));
                        }

                    } else if (student_choice.equals("3")) {


                        //check: Already have more than 4 books
                        System.out.println("Enter Your Student ID: ");
                        String s_id = sc.next();
                        String check_cond_1 = "select COUNT(*) from books where student_id='" + s_id + "';";
                        ResultSet res1 = stat.executeQuery(check_cond_1);
                        res1.next();
                        if (res1.getInt(1) >= 4) {
                            System.out.println("You Already have more than 4 books");
                            return;
                        }


                        //check: book is available or not


                        System.out.println("Enter The Book ID: ");
                        String b_id = sc.next();
                        String check_cond_2 = "select * from books where books.book_id = '" + b_id + "';";
                        ResultSet res2 = stat.executeQuery(check_cond_2);
                        res2.next();
                        if (res2.getInt("flag") == 1) {
                            System.out.println("Book is Already issued by SOMEONE ELSE");
                            return;
                        }


                        // if both of above conditions are false then student can issue book
                        LocalDateTime today = LocalDateTime.now();
                        LocalDateTime returnDate = today.plusDays(15);

                        String update_issue_status = "update books set books.student_id = '" + s_id + "',books.issue_date = '" + today + "',books.due_date = '" + returnDate + "',books.flag=1 where book_id='" + b_id + "';";
                        int res_update_issue_status = stat.executeUpdate(update_issue_status);


                    } else if (student_choice.equals("4")) {
                        System.out.println("Enter The Book ID: ");
                        String b_id = sc.next();
                        String find_dates = "select issue_date,due_date from books where book_id='" + b_id + "'";
                        ResultSet res_find_dates = stat.executeQuery(find_dates);
                        res_find_dates.next();
                        LocalDateTime today = LocalDateTime.now();
                        String duedate = res_find_dates.getString(2);
                        System.out.println(today);
                        System.out.println(duedate);
                        String calculate_dueDays = "select DATEDIFF('" + today + "','" + duedate + "')";
                        int days = 0;
                        ResultSet res_diff = stat.executeQuery(calculate_dueDays);
                        res_diff.next();
                        days = res_diff.getInt(1);
                        System.out.println(days);
                        if (days <= 0) {
                            //store
                            String str = "select book_id,author_name,book_name from books where books.book_id='" + b_id + "';";
                            ResultSet res_str = stat.executeQuery(str);
                            res_str.next();
                            String bid = res_str.getString(1);
                            String an = res_str.getString(2);
                            String bn = res_str.getString(3);
                            String del_id = "delete from books where books.book_id='" + b_id + "'";
                            int del_str = stat.executeUpdate(del_id);

                            // return to database table again

                            String ret_str = "insert into books(book_id,author_name,book_name,flag)values('" + bid + "','" + an + "','" + bn + "',0)";
                            int res_ret = stat.executeUpdate(ret_str);
                            System.out.println("Successfully Returned");
                            return;
                        } else {
                            int cost = days * 5;
                            System.out.print("Your Fine on this Book: ");
                            System.out.println(cost);
                        }
                    } else {
                        System.out.println("Enter a Valid choice.");
                    }
                } else {
                    String error_msg = "Choose correct Option!";
                    System.out.println(error_msg);
                }
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}