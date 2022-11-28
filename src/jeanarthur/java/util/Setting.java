package jeanarthur.java.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static jeanarthur.java.util.InputOperation.requestInput;

public class Setting {

    private static Map<String, Setting> settings = new HashMap<>();
    private final String displayName;
    private String representation;
    private int stateValue = 0;
    private final String[] states;

    public static Setting get(String settingName){
        return settings.get(settingName);
    }

    public static void put(String settingName, Setting setting){
        settings.put(settingName, setting);
    }

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

    @SuppressWarnings("StringConcatenationInLoop")
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
        this.states[this.stateValue] = requestInput("Alterar '" + this.states[0] + "' para: ");
    }

    public String getRepresentation() {
        return this.representation;
    }

    public int getStateValue(){
        return this.stateValue;
    }

    public String getCurrentState(){
        return this.states[this.stateValue];
    }

}