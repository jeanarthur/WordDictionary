package jeanarthur.java.WordDictionary;

import java.util.Objects;

public class Setting {

    private String displayName;
    private String representation;
    private int stateValue = 0;
    private String group = "standard";
    private String[] states;

    public Setting(String displayName, String... states){
        this.displayName = displayName;
        this.states = Objects.requireNonNullElseGet(states, () -> new String[]{"true", "false"});
        this.setRepresentation();
    }

    public Setting(String displayName, String state){
        this.displayName = displayName;
        this.states = new String[]{state};
        this.setRepresentation();
    }

    public Setting(String displayName, int startStateValue, String... states){
        this.displayName = displayName;
        this.states = Objects.requireNonNullElseGet(states, () -> new String[]{"true", "false"});
        this.stateValue = startStateValue;
        this.setRepresentation();
    }

    public Setting(String displayName, String group, int startStateValue, String... states){
        this.displayName = displayName;
        this.states = Objects.requireNonNullElseGet(states, () -> new String[]{"true", "false"});
        this.stateValue = startStateValue;
        this.group = group;
        this.setRepresentation();
    }

    public Setting(String displayName, String group, String state){
        this.displayName = displayName;
        this.states = new String[]{state};
        this.group = group;
        this.setRepresentation();
    }

    private void setRepresentation(){
        this.representation = this.displayName + ": ";
        if (this.states.length == 1){
            this.representation += this.states[0];
        } else {
            for (int i = 0; i < this.states.length; i++){
                this.representation += (i == this.stateValue) ? "[X]" + this.states[i] : "[ ]" + this.states[i];
            }
        }

    }

    public void change(){
        if (this.states.length == 1){
            changeState();
        } else {
            changeToNext();
        }
        this.setRepresentation();
    }

    private void changeToNext(){
        this.stateValue = (this.stateValue + 1 > this.states.length - 1) ? 0 : this.stateValue + 1;
    }

    private void changeState(){
        this.states[this.stateValue] = Main.requestInput("Alterar linguagem '" + this.states[0] + "' para: ");
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getStateValue(){
        return this.stateValue;
    }

    public String getGroup(){
        return this.group;
    }

    public String getDisplayName(){ return this.displayName; }

    public String getCurrentState(){
        return this.states[this.stateValue];
    }

}
