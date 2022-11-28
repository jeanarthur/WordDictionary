package jeanarthur.java.util;

import java.util.function.Consumer;

public class Action {

    private final String displayName;
    private Runnable runnable;
    private Consumer<String> stringConsumer;
    private Consumer<String[]> stringArrayConsumer;
    private String stringConsumerParam;
    private String[] stringArrayConsumerParam;

    public Action(String displayName, Runnable action){
        this.displayName = displayName;
        this.runnable = action;
    }

    public Action(String displayName, Consumer<String> action, String actionParam){
        this.displayName = displayName;
        this.stringConsumer = action;
        this.stringConsumerParam = actionParam;
    }

    public Action(String displayName, Consumer<String[]> action, String[] actionParam){
        this.displayName = displayName;
        this.stringArrayConsumer = action;
        this.stringArrayConsumerParam = actionParam;
    }

    public void run(){
        if (this.runnable != null) {
            this.runnable.run();
        } else if (this.stringConsumer != null) {
            this.stringConsumer.accept(this.stringConsumerParam);
        } else {
            this.stringArrayConsumer.accept(this.stringArrayConsumerParam);
        }
    }

    public String getDisplayName(){
        return this.displayName;
    }

}
