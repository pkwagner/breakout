package de.tudarmstadt.informatik.fop.breakout.controllers;

import java.util.function.Consumer;

public class Timeout {
    private float wakeupTime = 0;
    private final float timeoutTime;
    private final Consumer<Integer> callback;

    // TODO Change Consumer to Void
    public Timeout(float timeoutTime, Consumer<Integer> callback) {
        this.timeoutTime = timeoutTime;
        this.callback = callback;
    }

    Consumer<Integer> getCallback() {
        return callback;
    }

    float getWakeupTime() {
        return wakeupTime;
    }

    void setStartedAt(float startedAt) {
        wakeupTime = startedAt + timeoutTime;
    }
}