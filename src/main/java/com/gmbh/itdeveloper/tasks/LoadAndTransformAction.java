package com.gmbh.itdeveloper.tasks;

import com.gmbh.itdeveloper.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class LoadAndTransformAction extends RecursiveAction {

    private int offset;
    private Consumer<Integer> consumer;

    public LoadAndTransformAction(int offset,Consumer<Integer> consumer) {
        this.offset = offset;
        this.consumer = consumer;
    }

    @Override
    protected void compute() {
        consumer.accept(offset);
        App.OFFSET.addAndGet(App.LIMIT);
        if (App.OFFSET.get() < App._MAX.get()) {
            List<LoadAndTransformAction> subTasks = new ArrayList<>();
            subTasks.add(new LoadAndTransformAction(App.OFFSET.get(), consumer));

            App.OFFSET.addAndGet(App.LIMIT);
            if (App.OFFSET.get() < App._MAX.get()) {
                subTasks.add(new LoadAndTransformAction(App.OFFSET.get(), consumer));
                invokeAll(subTasks);
            } else {
                invokeAll(subTasks);
            }

        }
        System.out.println("I am shutdown! GoodBy");
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
