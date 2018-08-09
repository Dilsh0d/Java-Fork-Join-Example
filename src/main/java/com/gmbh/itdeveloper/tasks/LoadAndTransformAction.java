package com.gmbh.itdeveloper.tasks;

import com.gmbh.itdeveloper.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class LoadAndTransformAction extends RecursiveAction {

    private int offset;
    private Consumer<Integer> consumer;

    public LoadAndTransformAction(int offset, Consumer<Integer> consumer) {
        this.offset = offset;
        this.consumer = consumer;
    }

    @Override
    protected void compute() {
        consumer.accept(offset);

        int offset = 0;
        if ((offset = App.nextStepAndCheckMax())>0) {
            List<LoadAndTransformAction> subTasks = new ArrayList<>();
            subTasks.add(new LoadAndTransformAction(offset, consumer));

            if ((offset=App.nextStepAndCheckMax())>0) {
                subTasks.add(new LoadAndTransformAction(offset, consumer));
            }
            invokeAll(subTasks);
        }

        System.out.println("I am shutdown! GoodBy");
        quietlyComplete();
    }
}
