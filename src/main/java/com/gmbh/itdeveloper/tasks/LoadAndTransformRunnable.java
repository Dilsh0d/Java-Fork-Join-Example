package com.gmbh.itdeveloper.tasks;

import java.util.function.Consumer;

public class LoadAndTransformRunnable implements Runnable {

    private int offset;
    private Consumer<Integer> consumer;

    public LoadAndTransformRunnable(int offset,Consumer<Integer> consumer) {
        this.offset = offset;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        consumer.accept(offset);
    }
}
