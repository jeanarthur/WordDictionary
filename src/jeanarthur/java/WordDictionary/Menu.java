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

    private void composeActionDisplayNames(){
        int i = 1;
        for (Action action : this.actions){
            this.actionsDisplayNames.add(String.format("%d. %s", i++, action.getDisplayName()));
        }
    }

    private void composeView(){
        int width = getLargerStringLength();
        String delimiter = String.format("+%s+\n", "=".repeat(width+2));
        view = delimiter;
        for (String actionDisplayName : this.actionsDisplayNames){
            String format = "| %-" + width + " |\n";
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
        System.out.println(this.view);
    }

}
