import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Helper {
    static String removeMultilineComments(String inputData) {
        String pattern = "/\\*.*?\\*/";
        Pattern p  = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }

    static String removeSinglelineComments(String inputData) {
        String pattern = "//.*?\\n";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("\n");
        return inputData;
    }

    static String removeQuotes(String inputData) {
        String pattern = "\".*?\"";
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(inputData);
        inputData = m.replaceAll("");
        return inputData;
    }

    static String getInputFromFile(String filename) {
        String inputData = "";
        try {
            FileReader in = new FileReader(filename);
            BufferedReader br = new BufferedReader(in);
            String input;
            while ((input = br.readLine()) != null) {
                inputData += input + "\n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return inputData;
    }

    static int findMatching(String inputData, int openingIndex) {
        int i = openingIndex;
        int count = 1;
        boolean flag = true;
        int matchingIndex = -1;
        while (true) {
            if (inputData.charAt(i) == '{') {
                count++;
                if (count == 1) {
                    flag = true;
                }
            } else if (inputData.charAt(i) == '}') {
                count--;
            }
            if (count == 0 && flag == true) {
                matchingIndex = i;
                break;
            }
            i++;
        }
        return matchingIndex;
    }
}

class Activity {
    String name;
    int startTime;
    ArrayList<Activity> predecessor;
    int duration;
    int completionTime;
    boolean isCritical;

    Activity(String activityName, ArrayList<Activity> pre, int dur) {
        name = activityName;
        predecessor = pre;
        duration = dur;
    }
}

public class Main{
    static ArrayList<Activity> table = new ArrayList<>();
    static ArrayList<String> criticalPath = new ArrayList<>();

    void run() {
        String inputData = Helper.getInputFromFile("/home/zerefwayne/semester-5/isoe532c/endsem/src/input.txt");
        System.out.println(inputData);


        addComponentsToPane();

    }

    void calculateTime() {
        for (Activity activity : table) {
            if (activity.predecessor.size() == 0) {
                activity.startTime = 0;
                activity.completionTime = activity.duration;
            } else {
                activity.startTime = findMax(activity.predecessor);
                activity.completionTime = activity.startTime + activity.duration;
            }
        }
    }
    void findCritical(ArrayList<Activity> activities) {
        ArrayList<Activity> crit = new ArrayList<Activity>();
        int maxTime = findMax(activities);
        for (Activity activity : activities) {
            if (activity.completionTime == maxTime) {
                markCritical(activity);
            }
        }
    }

    void markCritical(Activity activity) {
        activity.isCritical = true;
        criticalPath.add(activity.name);
        findCritical(activity.predecessor);
    }

    void createTable(String data) {
        String[] rows = data.split("\n");
        for (int i = 1; i < rows.length; i++) {
            table.add(createActivity(rows[i]));
        }
    }

    HashMap<Integer, String> test = new HashMap<>();
    String A = "";
    String B = "";
    boolean toBePrinted = false;

    Activity createActivity(String line) {
        String[] details = line.split("\\s\\s+");
        String activityName = details[0];
        ArrayList<Activity> predecessor = findPredecessor(details[1]);
        int duration = Integer.parseInt(details[2]);
        if (test.containsKey(duration)) {
            A = activityName;
            B = test.get(duration);
            toBePrinted = true;
        } else {
            test.put(duration,activityName);
        }
        return new Activity(activityName, predecessor, duration);
    }

    ArrayList<Activity> findPredecessor(String data) {
        System.out.println("Data : "+data);
        ArrayList<Activity> predecessor = new ArrayList<Activity>();
        if (!data.equals("-")) {
            String[] names = data.split(",");
            for (String name : names) {
                predecessor.add(search(name));
            }
        }
        return predecessor;
    }

    Activity search(String name) {
        name = name.trim();
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).name.equals(name)) {
                return table.get(i);
            }
        }
        return null;
    }

    int findMax(ArrayList<Activity> activities) {
        int max = 0;
        for (Activity activity : activities) {
            if (activity.completionTime > max) {
                max = activity.completionTime;
            }
        }
        return max;
    }

    void printTable() {
        HashSet<String> done = new HashSet<>();

        System.out.println("Critical Path is : ");
        StringBuilder output = new StringBuilder();
        for (int i = criticalPath.size() - 1; i >= 0; i--) {

            if(!done.contains(criticalPath.get(i) + " "))
                output.append(criticalPath.get(i)).append(" ");

            done.add(criticalPath.get(i) + " ");
        }
        if (toBePrinted) {
            String tt = output.toString().replace(A+" ", "");
            System.out.println(tt);
            System.out.println(tt.replace(B,A));

            System.out.println("");
            System.out.println("");
        } else {
            System.out.println(output+"\n");
        }
        for (Activity activity : table) {
            if(activity.isCritical){
                System.out.println(activity.name + " " + activity.startTime + " " + activity.completionTime + " " + "*");
            }
            else
                System.out.println(activity.name + " " + activity.startTime + " " + activity.completionTime + " " + "-");

        }
    }

    private void addComponentsToPane() {

        JFrame container = new JFrame("Critical Path Method");
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton buttonAdd;
        buttonAdd = new JButton("Add new Activity");
        buttonAdd.setForeground(Color.GREEN);
        c.insets = new Insets(0,20,20,0);
        c.gridx = 0;
        c.gridy = 0;
        container.add(buttonAdd, c);
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                judgeRegister();
                container.setVisible(false);
            }
        });

        JButton buttonRemove = new JButton("Remove Selected Activity");
        c.insets = new Insets(0,20,20,0);
        buttonRemove.setForeground(Color.RED);
        c.gridx = 1;
        c.gridy = 0;
        container.add(buttonRemove,c);

        buttonRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeAnEntry();
                container.setVisible(false);
            }
        });

        JButton buttonEvaluate = new JButton("Evaluate Result");
        buttonEvaluate.setBackground(Color.GREEN);
        buttonEvaluate.setForeground(Color.WHITE);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,0);
        container.add(buttonEvaluate,c);

        buttonEvaluate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//                container.setVisible(false);
                displayActivityTable();
            }
        });

        JButton buttonReset = new JButton("Reset");
        buttonReset.setBackground(Color.RED);
        buttonReset.setForeground(Color.WHITE);
        c.gridx = 3;
        c.gridy = 0;
        c.insets = new Insets(0,20,20,0);
        container.add(buttonReset,c);

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                container.setVisible(false);
                int a = table.size();
                while (a>0) {
                    table.remove(a-1);
                    a--;
                }
                addComponentsToPane();
            }
        });

        JLabel textTotal = new JLabel("Total activities right now : "+table.size());
        c.gridy = 1;
        c.gridx = 1;
        c.insets = new Insets(0,20,20,0);
        container.add(textTotal,c);

        container.setVisible(true);

        container.setSize(500,200);


    }

    private void removeAnEntry() {

        System.out.println(table.size());
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Remove an Activity");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        JLabel uname = new JLabel("Enter the Index (between 0 and "+table.size()+" )");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jNameID = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 3;
        frame.getContentPane().add(jNameID,c);

        JButton button = new JButton("Delete");
        c.gridx = 1;
        c.gridy = 6;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int id = Integer.parseInt(jNameID.getText());
                System.out.println("Id : "+id+" "+table.size());
                if (id < table.size()) {
                    System.out.println("aaya");
                    table.remove(id);
                    frame.setVisible(false);
                    addComponentsToPane();
                }
            }
        });

        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);

    }

    private void judgeRegister() {
        JButton button;
        GridBagConstraints c = new GridBagConstraints();
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Add new Activity");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.getContentPane().setLayout(new GridBagLayout());

        JLabel uname = new JLabel("Activity Name : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 2;
        frame.getContentPane().add(uname,c);

        JTextField jNameID = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 2;
        frame.getContentPane().add(jNameID,c);


        //Set up the content pane.
        uname = new JLabel("Intermediate predecessor : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 3;
        frame.getContentPane().add(uname,c);

        JTextField jInt = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 3;
        frame.getContentPane().add(jInt,c);

        uname = new JLabel("Duration : ");
        c.ipady = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20,0,0,10);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 1;
//        c.insets = new Insets(0,60,0,0);
        c.gridy = 4;
        frame.getContentPane().add(uname,c);

        JTextField jDuration = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
//        c.ipady = 20;
        c.insets = new Insets(20,0,0,20);
//        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;
        c.gridy = 4;
        frame.getContentPane().add(jDuration,c);

        button = new JButton("Submit");
        c.gridx = 1;
        c.gridy = 6;
        frame.getContentPane().add(button, c);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Activity a = new Activity(jNameID.getText(),findPredecessor(jInt.getText()),Integer.parseInt(jDuration.getText()));
                if (test.containsKey(Integer.parseInt(jDuration.getText()))) {
                    A = jNameID.getText();
                    B = test.get(Integer.parseInt(jDuration.getText()));
                    toBePrinted = true;
                } else {
                    test.put(Integer.parseInt(jDuration.getText()),jNameID.getText());
                }
                table.add(a);
                frame.setVisible(false);
                addComponentsToPane();
            }
        });


        frame.pack();
        frame.setVisible(true);

    }

    private void displayActivityTable() {
        calculateTime();
        findCritical(table);

        JFrame tableFrame = new JFrame("Evaluated Result");
        tableFrame.setSize(1200,800);

        String[] columns = {"Activity", "Precd.", "Duration", "Start Time", "End Time", "Critical Path"};
        String[][] data = new String[table.size()][6];
        for (int i=0;i<table.size();i++) {
            data[i][0]  = table.get(i).name;
            data[i][1] = String.valueOf(table.get(i).startTime);
            data[i][2] = String.valueOf(table.get(i).duration);
            data[i][3] = String.valueOf(table.get(i).startTime);
            data[i][4] = String.valueOf(table.get(i).completionTime);
            if (table.get(i).isCritical)
                data[i][5] = "*";
            else
                data[i][5] = "-";
        }
        JTable j = new JTable(data,columns);
        j.setBounds(30, 40, 200, 300);
        JScrollPane sp = new JScrollPane(j);
        tableFrame.add(sp);
        tableFrame.setSize(800, 800);
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}


























