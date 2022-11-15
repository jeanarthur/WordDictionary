package jeanarthur.java.WordDictionary;

import java.util.Objects;

public class Setting {

    private String representation;
    private int value = 0;

    private String[] states;

    public Setting(String... states){
        this.states = Objects.requireNonNullElseGet(states, () -> new String[]{"true", "false"});
        this.setRepresentation();
    }

    public Setting(int startStateValue, String... states){
        this.states = Objects.requireNonNullElseGet(states, () -> new String[]{"true", "false"});
        this.value = startStateValue;
        this.setRepresentation();
    }

    private void setRepresentation(){
        for (int i = 0; i < this.states.length; i++){
            this.representation += (i == this.value) ? "[X]" + this.states[i] : "[ ]" + this.states[i];
        }
    }

    public Runnable changeState = () -> {
        this.value = (this.value + 1 > this.states.length - 1) ? 0 : this.value + 1;
        this.setRepresentation();
    };

    public String getRepresentation() {
        return representation;
    }
}
