package jeanarthur.java.WordDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    public static Menu currentMenu;
    private final String title;
    private Map<Integer, Action> actions = new HashMap<>();
    private Map<Character, Setting> settings = new HashMap<>();
    private String view;
    private boolean isShowed = false;

    public Menu(String title){
        this.title = title;
    }

    public void add(Action action){
        this.actions.put(this.actions.size() + 1, action);
    }
    public void add(Setting setting){
        this.settings.put(convertIntegerInChar(this.settings.size() + 1), setting);
    }

    private Character convertIntegerInChar(int i){
        return i > 0 && i < 27 ? (char)(i + 'a' - 1) : null;
    }

    private List<String> composeActionsRepresentations(){
        List<String> actionsRepresentations = new ArrayList<>();
        int i = 1;
        for (Action action : this.actions.values()){
            actionsRepresentations.add(String.format("%d. %s", i++, action.getDisplayName()));
        }
        return actionsRepresentations;
    }
    
    private List<String> composeSettingsRepresentations(){
        List<String> settingsRepresentations = new ArrayList<>();
        char i = 'a';
        for (Setting setting : this.settings.values()){
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

    public void start(){
        this.isShowed = true;

        do {
            this.composeView();
            this.print();

            String actionCode = Main.requestInput("| Executar ação nº: ");
            if (Main.isNumeric(actionCode)) {
                this.actions.get(Integer.parseInt(actionCode)).run();
            } else {
                this.settings.get(actionCode.toCharArray()[0]).change();
            }
            currentMenu = this;
        } while (this.isShowed);

    }

    public void stop(){
        this.isShowed = false;
    }

    private void print(){
        System.out.println(this.view);
    }

}
