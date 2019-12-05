import javax.swing.*;
import java.sql.*;

public class Event {

    private String name;
    private String performanceType;
    private Integer numberOfParticipants;
    private Integer batch;
    private Integer judge1ID;
    private Integer Judge2ID;
    private Integer Judge3ID;

    private static int findmarks(Connection finalConn, int jID, String s) throws SQLException {
        PreparedStatement ret = finalConn.prepareStatement("SELECT marks from assignment3.jmarks where jID = ? AND perfID = ?");
        ret.setInt(1,jID);
        ret.setString(2,s);

        ResultSet retb = ret.executeQuery();
        if(retb.next()){
            return retb.getInt("marks");
        }
        else{
            return -1;
        }
    }

    public static String[][] displayAll(Connection finalConn, int colnum) throws SQLException {
        String[][] data = new String[1000][colnum];
        String query = "select* from assignment3.events";
        Statement stmt = finalConn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int count = 0;
        while (rs.next()) {
            data[count][0] = rs.getString("eventsID");
            data[count][1] = rs.getString("name");
            data[count][2] = rs.getString("pType");
            data[count][3] = rs.getString("num");
            data[count][4] = rs.getString("batchT");
            int j1ID = rs.getInt("j1s");
            int j2ID = rs.getInt("j2s");
            int j3ID = rs.getInt("j3s");
            int marks = findmarks(finalConn,j1ID,data[count][0]);
            if (marks != -1) {
                data[count][5] = String.valueOf(marks);
            }
            else{
                data[count][5] = "YetToFill";
            }

            marks = findmarks(finalConn,j2ID,data[count][0]);
            if (marks != -1) {
                data[count][6] = String.valueOf(marks);
            }
            else{
                data[count][6] = "YetToFill";
            }

            marks = findmarks(finalConn,j3ID,data[count][0]);
            if (marks != -1) {
                data[count][7] = String.valueOf(marks);
            }
            else{
                data[count][7] = "YetToFill";
            }
            count++;


        }
        return data;
    }

    public static void addEventDatabase(Connection finalConn, String evID, String name, String perftype, String num, String batch, int j1id, int j2id, int j3id) throws SQLException {
        PreparedStatement insertCata = finalConn.prepareStatement
                ("INSERT INTO assignment3.events (eventsID, name,pType , num, batchT,j1s,j2s,j3s) VALUES (?, ?, ?, ?, ?,?,?,?)");

        insertCata.setString(1, evID);
        insertCata.setString(2,name);
        insertCata.setString(3, perftype);
        insertCata.setInt(4, Integer.parseInt(num));
        insertCata.setInt(5, Integer.parseInt(batch));
        insertCata.setInt(6, j1id);
        insertCata.setInt(7, j2id);
        insertCata.setInt(8, j3id);
        insertCata.executeUpdate();
    }

    public static void  showEvents(Connection finalConn) throws SQLException {
        JFrame f = new JFrame();
        // Frame Title
        f.setTitle("Event Summary");
        int colnum = 8;
        String[][] data = displayAll(finalConn,colnum);

        // Column Names
        String[] columnNames = { "EventID", "Name", "Performance Type","Number of Members","Batch Year", "Judge1 Marks","Judge2 Marks","Judge3 Marks" };

        // Initializing the JTable
        JTable j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }


}
