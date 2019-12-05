import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static ArrayList<Node> nodes = new ArrayList<>();
    public static Map<String, Node> nameNodeMap = new HashMap<>();

    public static ArrayList<String> ansList = new ArrayList<>();
    public static HashMap<String, Boolean> visited = new HashMap<>();

    public static void main(String[] args) {
        File inputFile = new File("src/input.txt");
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            Node newNode;
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(" ");
                newNode = new Node(row[0], Integer.parseInt(row[2]));
                if (!row[1].contains("-")) {
                    String[] children = row[1].split(",");
                    for (String child : children) {
                        newNode.addChild(child);
                        nameNodeMap.get(child).addPredeccessor(newNode.getName());
                    }
                }
                nameNodeMap.put(row[0], newNode);
                nodes.add(newNode);
            }

            for (Map.Entry<String, Node> entry : nameNodeMap.entrySet()) {
                if (!visited.containsKey(entry.getKey())) {
                    topologicalSort(entry.getKey());
                }
            }

            for (String name : ansList) {
                int waitTill = 0;
                Node currNode = nameNodeMap.get(name);
                for (String child : currNode.getChildren()) {
                    waitTill = Math.max(waitTill, nameNodeMap.get(child).getCompletionTime());
                }
                currNode.setStartTime(waitTill);
                currNode.setCompletionTime(waitTill + currNode.getDuration());
            }

            String cpNodeName = "";
            int maxCompletionTime = Integer.MIN_VALUE;
            ArrayList<String> criticalPath = new ArrayList<>();

            for (Node node : nodes) {
                if (node.getChildren().size() == 0) {
                    if (maxCompletionTime < node.getCompletionTime()) {
                        maxCompletionTime = node.getCompletionTime();
                        cpNodeName = node.getName();
                    }
                } else break;
            }

            boolean pathGenerated = false;
            while (!pathGenerated) {
                criticalPath.add(cpNodeName);
                maxCompletionTime = Integer.MIN_VALUE;
                for (String parentName : nameNodeMap.get(cpNodeName).getPredeccessor()) {
                    Node parentNode = nameNodeMap.get(parentName);
                    if (maxCompletionTime < parentNode.getCompletionTime()) {
                        maxCompletionTime = parentNode.getCompletionTime();
                        cpNodeName = parentName;
                    }
                }
                if (maxCompletionTime == Integer.MIN_VALUE) pathGenerated = true;
            }

            for (Node node : nodes) {
                String ansLine = node.getName() + "\t" + node.getStartTime() + "\t" + node.getCompletionTime();
                if (criticalPath.contains(node.getName())) ansLine += "\t*";
                System.out.println(ansLine);
            }

            System.out.println("The Critical Path is: ");
            for (int i = 0; i < criticalPath.size(); ++i) {
                if (i != 0) {
                    System.out.print("->" + criticalPath.get(i));
                } else System.out.print(criticalPath.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sb.toString());
    }

    private static void topologicalSort(String currNodeName) {
        Node currentNode = nameNodeMap.get(currNodeName);
        visited.put(currentNode.getName(), true);
        for (String child : currentNode.getChildren()) {
            if (!visited.containsKey(child)) {
                topologicalSort(child);
            }
        }
        ansList.add(currNodeName);
    }
}
