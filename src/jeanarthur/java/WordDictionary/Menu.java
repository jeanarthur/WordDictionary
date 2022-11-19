package jeanarthur.java.WordDictionary;

import java.util.List;

public class Menu {

    private String title;
    private List<Action> actions;
    private List<String> actionsDisplayNames;
    private String view;

    public Menu(String title, List<Action> actions){
        this.title = title;
        this.actions = actions;
        this.composeActionDisplayNames();
        this.composeView();
    }

    public Menu(String title){
        this.title = title;
    }

    public void add(Action action){
        this.actions.add(action);
    }

    public List<Action> getActions(){
        return this.actions;
    }

    private void composeActionDisplayNames(){
        int i = 1;
        for (Action action : this.actions){
            this.actionsDisplayNames.add(String.format("%d. %s", i++, action.getDisplayName()));
        }
    }

    private void composeView(){
        int width = getLargerStringLength();
        String delimiter = String.format("+%s+\n", "=".repeat(width+2));
        String separator = String.format("+%s+\n", "-".repeat(width+2));
        String format = "| %-" + width + " |\n";

        view = delimiter;
        view += String.format(format, this.title);
        view += separator;
        for (String actionDisplayName : this.actionsDisplayNames){
            view += String.format(format, actionDisplayName);
        }
        view += delimiter;
    }

    private int getLargerStringLength(){
        int largerLength = this.title.length();
        for (String actionDisplayName : this.actionsDisplayNames){
            int displayNameLength = actionDisplayName.length();
            if (displayNameLength > largerLength){
                largerLength = displayNameLength;
            }
        }
        return largerLength;
    }

    public void print(){
        if (view == null){
            composeActionDisplayNames();
            composeView();
        }
        System.out.println(this.view);
    }

}
