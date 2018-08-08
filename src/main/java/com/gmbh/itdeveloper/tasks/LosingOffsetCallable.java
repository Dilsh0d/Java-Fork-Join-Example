package com.gmbh.itdeveloper.tasks;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class LosingOffsetCallable implements Callable<Boolean> {

    private int offset;
    private Consumer<Integer> consumer;

    public LosingOffsetCallable(int offset, Consumer<Integer> consumer){
        this.offset = offset;
        this.consumer = consumer;
    }

    @Override
    public Boolean call() throws Exception {
        consumer.accept(offset);
        return true;
    }
}
