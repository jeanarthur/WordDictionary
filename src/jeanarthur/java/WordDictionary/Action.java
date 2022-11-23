package jeanarthur.java.WordDictionary;

import java.util.function.Consumer;
import java.util.function.Function;

public class Action {

    private String displayName;
    private Runnable runnable;
    private Consumer<String> consumer;
    private String consumerParam;

    public Action(String displayName, Runnable action){
        this.displayName = displayName;
        this.runnable = action;
    }

    public Action(String displayName, Consumer<String> action, String actionParam){
        this.displayName = displayName;
        this.consumer = action;
        this.consumerParam = actionParam;
    }

    public void run(){
        if (this.runnable != null) {
            this.runnable.run();
        } else {
            this.consumer.accept(this.consumerParam);
        }
    }

    public String getDisplayName(){
        return this.displayName;
    }

}
