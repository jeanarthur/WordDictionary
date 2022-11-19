package jeanarthur.java.WordDictionary;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final String title;
    private List<Action> actions = new ArrayList<>();
    private String view;

    public Menu(String title, List<Action> actions){
        this.title = title;
        this.actions = actions;
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

    private List<String> composeActionDisplayNames(){
        List<String> actionsDisplayNames = new ArrayList<>();
        int i = 1;
        for (Action action : this.actions){
            actionsDisplayNames.add(String.format("%d. %s", i++, action.getDisplayName()));
        }
        return actionsDisplayNames;
    }

    private void composeView(){
        List<String> actionsDisplayNames = composeActionDisplayNames();
        int width = getLargerStringLength(actionsDisplayNames);
        String delimiter = String.format("+%s+\n", "=".repeat(width+2));
        String separator = String.format("+%s+\n", "-".repeat(width+2));
        String format = "| %-" + width + "s |\n";

        view = delimiter;
        view += String.format(format, this.title);
        view += separator;
        for (String actionDisplayName : actionsDisplayNames){
            view += String.format(format, actionDisplayName);
        }
        view += delimiter.substring(0, delimiter.length() - 1);
    }

    private int getLargerStringLength(List<String>... additionalStringLists){
        int largerLength = this.title.length();
        if (additionalStringLists != null) {
            for (List<String> additionalStringList : additionalStringLists) {
                for (String actionDisplayName : additionalStringList) {
                    int displayNameLength = actionDisplayName.length();
                    if (displayNameLength > largerLength) {
                        largerLength = displayNameLength;
                    }
                }
            }
        }
        return largerLength;
    }

    public void print(){
        if (view == null){
            composeView();
        }
        System.out.println(this.view);
    }

}
