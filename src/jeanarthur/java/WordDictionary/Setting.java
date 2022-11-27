package jeanarthur.java.WordDictionary;

import java.util.Objects;

public class Setting {

    private String displayName;
    private String representation;
    private int stateValue = 0;
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

    private void setRepresentation(){
        this.representation = this.displayName + ": ";
        if (this.states.length == 1){
            this.representation += this.states[0];
        } else {
            for (int i = 0; i < this.states.length; i++){
                this.representation += (i == this.stateValue) ? "[X]" + this.states[i] : "[ ]" + this.states[i];
                if (i + 1 < this.states.length) { this.representation += " "; }
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
        this.states[this.stateValue] = Main.requestInput("Alterar '" + this.states[0] + "' para: ");
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getStateValue(){
        return this.stateValue;
    }

    public String getDisplayName(){ return this.displayName; }

    public String getCurrentState(){
        return this.states[this.stateValue];
    }

}
