import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Test {

    final static boolean RIGHT_TO_LEFT = false;

    public static void addComponentsToPane(Container pane, Connection finalConn) {

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        button = new JButton("Login as Judge");
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,20,20,0);
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Judge.judgeLogin(finalConn);
            }
        });


        button = new JButton("Register as a judge");
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,20,20,0);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(button, c);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Judge.judgeRegister(finalConn);
            }
        });

        button = new JButton("Show all Events");
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,20,20,0);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(button, c);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Event.showEvents(finalConn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        button = new JButton("Add an event");
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0,20,20,0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(button, c);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    addEvent(finalConn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

//
    }

    private static void addEvent(Connection finalConn) throws SQLException {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Event Register");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        JLabel uname = new JLabel("EventID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jID = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jID,c);


        //Set up the content pane.
        uname = new JLabel("Name : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 3;
        frame.getContentPane().add(uname,c);

        JTextField jname = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 3;
        frame.getContentPane().add(jname,c);


        uname = new JLabel("Performance Type : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 4;
        frame.getContentPane().add(uname,c);

        JTextField jperf = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 4;
        frame.getContentPane().add(jperf,c);


        JLabel upass = new JLabel("Number of Members : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 5;
        frame.getContentPane().add(upass,c);

        JTextField jnum = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 5;
        frame.getContentPane().add(jnum,c);


        upass = new JLabel("Batch Year : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 6;
        frame.getContentPane().add(upass,c);

        JTextField jb = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 6;
        frame.getContentPane().add(jb,c);


        upass = new JLabel("Judge1 ID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 7;
        frame.getContentPane().add(upass,c);

//        JTextField j1 = new JTextField();
        ArrayList<String> jList = getJudgesList(finalConn);

        SpinnerListModel jModel = new SpinnerListModel(jList);
        JSpinner j1spinner = new JSpinner(jModel);
        String[] j1id = {jList.get(0)};
        j1spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                j1id[0] = (String) ((JSpinner)e.getSource()).getValue();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 7;
        frame.getContentPane().add(j1spinner,c);


        upass = new JLabel("Judge2 ID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 8;
        frame.getContentPane().add(upass,c);

//        JTextField j2 = new JTextField();
        jList = getJudgesList(finalConn);

        jModel = new SpinnerListModel(jList);
        JSpinner j2spinner = new JSpinner(jModel);
        String[] j2id = {jList.get(0)};
        j2spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                j2id[0] = (String) ((JSpinner)e.getSource()).getValue();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 8;
        frame.getContentPane().add(j2spinner,c);


        upass = new JLabel("Judge3 ID : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 9;
        frame.getContentPane().add(upass,c);

//        JTextField j3 = new JTextField();
        jList = getJudgesList(finalConn);
        jModel = new SpinnerListModel(jList);
        JSpinner j3spinner = new JSpinner(jModel);
        String[] j3id = {jList.get(0)};
        j3spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spinner = (JSpinner) e.getSource();
                j3id[0] = (String) spinner.getValue();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        /* c.ipady = 20; */
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 9;
        frame.getContentPane().add(j3spinner,c);


        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 10;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String evID = jID.getText();
//                String j3id = j3.getText();
//                String j2id = j2.getText();
//                String j1id = j1.getText();
                String batch = jb.getText();
                String perftype = jperf.getText();
                String name = jname.getText();
                String num = jnum.getText();

                try {
                    Event.addEventDatabase(finalConn,evID,name,perftype,num,batch,Integer.parseInt(j1id[0]),
                            Integer.parseInt(j2id[0]),Integer.parseInt(j3id[0]));
                    addjMarksDatabase(finalConn,evID,Integer.parseInt(j1id[0]));
                    addjMarksDatabase(finalConn,evID,Integer.parseInt(j2id[0]));
                    addjMarksDatabase(finalConn,evID, Integer.parseInt(j3id[0]));

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //check through regex
                frame.dispose();
            }
        });

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private static ArrayList<String> getJudgesList(Connection finalConn) throws SQLException {
        ArrayList<String> dlist = new ArrayList<>();
        String query = "select* from assignment3.judges";
        Statement stmt = finalConn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            dlist.add(rs.getString("judgeID"));
        }
        return dlist;
    }

    private static void addjMarksDatabase(Connection finalConn, String evID, int jid) throws SQLException {
        PreparedStatement insertCata = finalConn.prepareStatement
                ("INSERT INTO assignment3.jmarks (jID,perfID,marks) VALUES (?, ?,?)");

        insertCata.setInt(1, jid);
        insertCata.setString(2, evID);
        insertCata.setInt(3, -1);
        insertCata.executeUpdate();
    }

    private static void createAndShowGUI(Connection finalConn) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("assignment3 Day");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addComponentsToPane(frame.getContentPane(),finalConn);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
}