package jeanarthur.java.WordDictionary;

import java.util.*;

public class Menu {

    public static Menu currentMenu;
    private final String title;
    private Map<String, Action> actions = new HashMap<>();
    private Map<String, Setting> settings = new HashMap<>();
    private Map<String, String> texts = new HashMap<>();
    private List<String> componentKeys = new ArrayList<>();
    private List<Integer> separatorIndexes = new ArrayList<>();
    private String view;
    private boolean isShowed = false;

    public Menu(String title){
        this.title = title;
    }

    public void add(Action action){
        String key = Integer.toString(this.actions.size() + 1);
        this.actions.put(key, action);
        this.componentKeys.add(key);
    }
    public void add(Action action, String actionCode){
        this.actions.put(actionCode, action);
        this.componentKeys.add(actionCode);
    }

    public void add(Setting setting){
        String key = convertIntInAlphabetLetter(this.settings.size() + 1);
        this.settings.put(key, setting);
        this.componentKeys.add(key);
    }

    public void add(String text){
        String key = "menu.text_" + (this.texts.size() + 1);
        this.texts.put(key, text);
        this.componentKeys.add(key);
    }

    public void add(Setting setting, String actionCode){
        this.settings.put(actionCode, setting);
        this.componentKeys.add(actionCode);
    }

    public void addSeparator(){
        this.separatorIndexes.add(this.componentKeys.size() - 1);
    }

    public void clear() {
        this.actions.clear();
        this.settings.clear();
        this.componentKeys.clear();
        this.separatorIndexes.clear();
    }

    private String convertIntInAlphabetLetter(int i){

        return i > 0 && i < 27 ? Character.toString((char)(i + 'a' - 1)) : null;
    }

    private List<List<String>> composeRepresentations(){
        List<String> actionsRepresentations = new ArrayList<>();
        List<String> settingsRepresentations = new ArrayList<>();
        List<String> textRepresentations = new ArrayList<>();
        Set<String> actionKeys = this.actions.keySet();
        Set<String> settingKeys = this.settings.keySet();
        for (String key : this.componentKeys){
            if (actionKeys.contains(key)){
                actionsRepresentations.add(String.format("%s. %s", key, this.actions.get(key).getDisplayName()));
            } else if (settingKeys.contains(key)){
                settingsRepresentations.add(String.format("%s. %s", key, this.settings.get(key).getRepresentation()));
            } else {
                //textRepresentations.addAll(Arrays.asList(this.texts.get(key).split("\\n")));
                textRepresentations.add(this.texts.get(key));
            }
        }
        List<List<String>> representations = new ArrayList<>();
        representations.add(actionsRepresentations);
        representations.add(settingsRepresentations);
        representations.add(textRepresentations);

        return representations;
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
        List<List<String>> representations = composeRepresentations();
        List<String> actionsRepresentations = representations.get(0);
        List<String> settingsRepresentations = representations.get(1);
        List<String> textsRepresentations = representations.get(2);
        List<String> textsLines = new ArrayList<>();
        for (String text : textsRepresentations){
            textsLines.addAll(Arrays.asList(text.split("\\n")));
        }
        int width = getLargerStringLength(actionsRepresentations, settingsRepresentations, textsLines);
        int actionsAmount = actionsRepresentations.size();
        int settingsAmount = settingsRepresentations.size();
        int textsAmount = textsRepresentations.size();
        String delimiter = String.format("+%s+\n", "=".repeat(width+2));
        String separator = String.format("+%s+\n", "-".repeat(width+2));
        String format = "| %-" + width + "s |\n";

        view = delimiter;
        view += String.format(format, this.title);
        view += separator;
        Set<String> actionKeys = this.actions.keySet();
        Set<String> settingKeys = this.settings.keySet();
        int separatorIndex = 0, actionIndex = 0, settingIndex = 0, textIndex = 0;
        for (String key : this.componentKeys){
            if (actionsAmount > 0 && actionKeys.contains(key)){
                view += String.format(format, actionsRepresentations.get(actionIndex++));
            } else if (settingsAmount > 0 && settingKeys.contains(key)){
                view += String.format(format, settingsRepresentations.get(settingIndex++));
            } else if (textsAmount > 0) {
                for (String line : textsRepresentations.get(textIndex++).split("\\n")){
                    view += String.format(format, line);
                }
            } else {
                view += "ERROR!";
            }
            if (this.separatorIndexes.contains(separatorIndex++)){ view += separator; }
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
        String actionCode = Main.requestInput("| Executar ação: ");
        doOperation(actionCode);
    }

    private void doOperation(String actionCode){
        try {
            Set<String> actionKeys = this.actions.keySet();
            if (actionKeys.contains(actionCode)) { this.actions.get(actionCode).run(); }
            else { this.settings.get(actionCode).change(); }
        } catch (NullPointerException nullPointerException){
            System.out.println(Exception.invalidOperationCode.getMessage());
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
