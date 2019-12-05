import java.util.ArrayList;

public class Node {
    private String name;
    private Integer duration;
    private ArrayList<String> children;
    private ArrayList<String> predeccessor;

    private Integer startTime;
    private Integer completionTime;

    Node(String name, Integer duration) {
        this.name = name;
        this.duration = duration;
        this.children = new ArrayList<>();
        this.predeccessor = new ArrayList<>();

        startTime = 0;
        completionTime = 0;
    }

    void addChild(String name) {
        children.add(name);
    }

    void addPredeccessor(String name) {
        predeccessor.add(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ArrayList<String> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<String> children) {
        this.children = children;
    }

    public ArrayList<String> getPredeccessor() {
        return predeccessor;
    }

    public void setPredeccessor(ArrayList<String> predeccessor) {
        this.predeccessor = predeccessor;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
    }
}
