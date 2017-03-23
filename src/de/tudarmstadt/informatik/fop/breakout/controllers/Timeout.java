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

    public Consumer<Integer> getCallback() {
        return callback;
    }

    public float getWakeupTime() {
        return wakeupTime;
    }

    public void setStartedAt(float startedAt) {
        wakeupTime = startedAt + timeoutTime;
    }
}
