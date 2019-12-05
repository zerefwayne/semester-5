import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Judge {

    private Integer judgeID;
    private String uname;
    private String email;
    private String password;

    public static void judgeRegister(Connection finalConn) {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Judge Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        JLabel uname = new JLabel("JudgeID: ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jTextID = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jTextID,c);


        //Set up the content pane.
        uname = new JLabel("Username: ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 3;
        frame.getContentPane().add(uname,c);

        JTextField juser = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 3;
        frame.getContentPane().add(juser,c);

        uname = new JLabel("Email: ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 4;
        frame.getContentPane().add(uname,c);

        JTextField jemail = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 4;
        frame.getContentPane().add(jemail,c);
        String email = jemail.getText();

        JLabel upass = new JLabel("Password: ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 5;
        frame.getContentPane().add(upass,c);

        JTextField jpass = new JPasswordField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 5;
        frame.getContentPane().add(jpass,c);

        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 6;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String jID = jTextID.getText();
                String pass = jpass.getText();
                String email = jemail.getText();
                String un = juser.getText();


                //check through regex
                frame.dispose();
                try {
                    judgeRegDatabase(finalConn,jID,un,email,pass);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }

    private static void judgeRegDatabase(Connection finalConn, String jID, String un, String email, String pass) throws SQLException {
        PreparedStatement insertCata = finalConn.prepareStatement
                ("INSERT INTO assignment3.judges (judgeID, uname , email, password) VALUES (?, ?, ?, ?)");

        insertCata.setString(1, jID);
        insertCata.setString(2, un);
        insertCata.setString(3, email);
        insertCata.setString(4, pass);
        insertCata.executeUpdate();
    }

    private static boolean checklogin(Connection finalConn, String id, String pass) throws SQLException {

        PreparedStatement ret = finalConn.prepareStatement("SELECT judgeID from assignment3.judges where judgeID = ? AND password = ?");
        ret.setString(1,id);
        ret.setString(2,pass);

        ResultSet retb = ret.executeQuery();
        return retb.next();
    }

    public static void judgeLanding(Connection finalConn, String ID) {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Welcome Judge!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        //Set up the content pane.
        JLabel uname = new JLabel("Performance ID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,20,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jtext = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,20,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jtext,c);


        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 4;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String evID = jtext.getText();
                //check if this event belongs to judge or not

                frame.dispose();
                try {
                    if (checkjudge(finalConn,Integer.parseInt(ID),evID)) {
                        enterMarks(finalConn, ID, evID);
                    }
                    else{
                        System.out.println("Not your event to judge boss!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void judgeLogin(Connection finalConn) {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Judge Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        //Set up the content pane.
        JLabel uname = new JLabel("UserID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,20,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);


        JTextField jid = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,20,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jid,c);

        JLabel upass = new JLabel("Password : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,0,20,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 3;
        frame.getContentPane().add(upass,c);

        JTextField jpass = new JPasswordField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(0,0,20,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 3;
        frame.getContentPane().add(jpass,c);

        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 4;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ID = jid.getText();
                String pass = jpass.getText();
                //check through regex

                try {
                    if (checklogin(finalConn,ID,pass)){
                        frame.dispose();
                        judgeLanding(finalConn,ID);
                    }
                    else {
                        System.out.println("Wrong Creds!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static void updatemarks(Connection finalConn, String id, String evID, int marks) throws SQLException {
        PreparedStatement updfine = finalConn.prepareStatement
                ("UPDATE assignment3.jmarks SET marks = ? WHERE jID = ? AND perfID = ? ");

        updfine.setInt(1, marks);
        updfine.setString(2, id);
        updfine.setString(3, evID);
        updfine.executeUpdate();
    }

    private static void enterMarks(Connection finalConn, String ID, String evID) {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Enter Marks!");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        //Set up the content pane.
        JLabel uname = new JLabel("Enter Marks(0-10): ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,20,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jtext = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,20,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jtext,c);


        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 4;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int marks = Integer.parseInt(jtext.getText());
                //check through regex
                frame.dispose();
                try {
                    updatemarks(finalConn,ID,evID,marks);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static boolean checkjudge(Connection finalConn, int id, String evID) throws SQLException {
        PreparedStatement ret = finalConn.prepareStatement("SELECT marks from assignment3.jmarks where jID = ? AND perfID = ?");
        ret.setInt(1,id);
        ret.setString(2,evID);

        ResultSet retb = ret.executeQuery();
        return retb.next();
    }

}
