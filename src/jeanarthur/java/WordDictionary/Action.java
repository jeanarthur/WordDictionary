package jeanarthur.java.WordDictionary;

public class Action {

    private String displayName;
    private Runnable action;
    private String group = "standard";

    public Action(String displayName, String group, Runnable action){
        this.displayName = displayName;
        this.group = group;
        this.action = action;
    }

    public Action(String displayName, Runnable action){
        this.displayName = displayName;
        this.action = action;
    }

    public void run(){
        this.action.run();
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public String getGroup(){
        return this.group;
    }

}
