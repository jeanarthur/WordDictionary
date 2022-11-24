package jeanarthur.java.WordDictionary;

import java.util.*;

public class Menu {

    public static Menu currentMenu;
    private final String title;
    private Map<String, Action> actions = new HashMap<>();
    private Map<String, Setting> settings = new HashMap<>();
    private String view;
    private boolean isShowed = false;

    public Menu(String title){
        this.title = title;
    }

    public void add(Action action){
        this.actions.put(Integer.toString(this.actions.size() + 1), action);
    }
    public void add(Action action, String actionCode){
        this.actions.put(actionCode, action);
    }

    public void add(Setting setting){
        this.settings.put(convertIntInAlphabetLetter(this.settings.size() + 1), setting);
    }
    public void add(Setting setting, String actionCode){
        this.settings.put(actionCode, setting);
    }

    public void clear() {
        this.actions.clear();
        this.settings.clear();
    }

    private String convertIntInAlphabetLetter(int i){
        return i > 0 && i < 27 ? String.format("%c", (char)(i + 'a' - 1)) : null;
    }

    private List<String> composeActionsRepresentations(){
        List<String> actionsRepresentations = new ArrayList<>();
        Object[] keys = this.actions.keySet().toArray();
        int i = 0;
        for (Action action : this.actions.values()){
            actionsRepresentations.add(String.format("%s. %s", keys[i++], action.getDisplayName()));
        }
        return actionsRepresentations;
    }
    
    private List<String> composeSettingsRepresentations(){
        List<String> settingsRepresentations = new ArrayList<>();
        Object[] keys = this.settings.keySet().toArray();
        int i = 0;
        for (Setting setting : this.settings.values()){
            settingsRepresentations.add(String.format("%s. %s", keys[i++], setting.getRepresentation()));
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

    public void open(){
        this.isShowed = true;
        this.showUntilClose();
    }

    private void showUntilClose(){
        do {
            this.setCurrentMenu();
            this.composeView();
            this.print();
            this.doInputOutputOperation();
        } while (this.isShowed);
    }

    private void setCurrentMenu(){
        currentMenu = this;
    }

    private void doInputOutputOperation() {
        String actionCode = Main.requestInput("| Executar ação nº: ");
        doOperation(actionCode);
    }

    private void doOperation(String actionCode){
        try {
            Set<String> actionKeys = this.actions.keySet();
            if (actionKeys.contains(actionCode)) { this.actions.get(actionCode).run(); }
            else { this.settings.get(actionCode).change(); }
        } catch (NullPointerException nullPointerException){
            System.out.println("| Operação inválida!\n| Digite uma letra ou \n| número correspondente.");
        } catch (RuntimeException runtimeException){
            System.out.println(runtimeException.getMessage());
        }
    }

    public void close(){
        this.isShowed = false;
    }

    private void print(){
        System.out.println(this.view);
    }

}
