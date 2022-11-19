package jeanarthur.java.WordDictionary;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private final String title;
    private List<Action> actions = new ArrayList<>();
    private List<Setting> settings = new ArrayList<>();
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
    public void add(Setting setting){
        this.settings.add(setting);
    }

    public List<Action> getActions(){
        return this.actions;
    }

    public List<Setting> getSettings(){
        return this.settings;
    }

    private List<String> composeActionsRepresentations(){
        List<String> actionsRepresentations = new ArrayList<>();
        int i = 1;
        for (Action action : this.actions){
            actionsRepresentations.add(String.format("%d. %s", i++, action.getDisplayName()));
        }
        return actionsRepresentations;
    }
    
    private List<String> composeSettingsRepresentations(){
        List<String> settingsRepresentations = new ArrayList<>();
        char i = 'a';
        for (Setting setting : this.settings){
            settingsRepresentations.add(String.format("%c. %s", i++, setting.getRepresentation()));
        }
        return settingsRepresentations;
    }

    private void composeView(){
        List<String> actionsRepresentations = composeActionsRepresentations();
        List<String> settingsRepresentations = composeSettingsRepresentations();
        int width = getLargerStringLength(actionsRepresentations, settingsRepresentations);
        int actionsAmount = actionsRepresentations.size();
        int settingsAmount = settingsRepresentations.size();
        String delimiter = String.format("+%s+\n", "=".repeat(width+2));
        String separator = String.format("+%s+\n", "-".repeat(width+2));
        String format = "| %-" + width + "s |\n";

        view = delimiter;
        view += String.format(format, this.title);
        view += separator;
        if (actionsAmount > 0) {
            for (String actionRepresentation : actionsRepresentations) {
                view += String.format(format, actionRepresentation);
            }
        }
        if (actionsAmount > 0 && settingsAmount > 0){
            view += separator;
        }
        if (settingsAmount > 0){
            for (String settingRepresentation : settingsRepresentations) {
                view += String.format(format, settingRepresentation);
            }
        }
        view += delimiter.substring(0, delimiter.length() - 1);
    }

    @SafeVarargs
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
        if (this.view == null || this.settings.size() > 0){
            composeView();
        }
        System.out.println(this.view);
    }

}
