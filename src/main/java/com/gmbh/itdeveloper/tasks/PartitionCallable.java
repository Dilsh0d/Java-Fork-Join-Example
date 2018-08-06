package com.gmbh.itdeveloper.tasks;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.utils.PartitionConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class PartitionCallable implements Callable<Boolean> {

    private int index;
    private int offset;
    private PartitionConsumer<Integer,Integer> partitionConsumer;

    public PartitionCallable(int index, int offset, PartitionConsumer<Integer,Integer> partitionConsumer){
        this.index = index;
        this.offset = offset;
        this.partitionConsumer = partitionConsumer;
    }

//    @Override
//    protected void compute() {
//        partitionConsumer.accept(index,offset);
//
//        App.PARTITION_OFFSET.addAndGet(App.PARTITION_LIMIT);
//        App.PARTITION_INDEX.incrementAndGet();
//        if (App.PARTITION_OFFSET.get() < App.BIG_TABLE_MAX_COUNT) {
//            List<PartitionCallable> subTasks = new ArrayList<>();
//            subTasks.add(new PartitionCallable(App.PARTITION_INDEX.get(),App.PARTITION_OFFSET.get(),partitionConsumer));
//
//            App.PARTITION_OFFSET.addAndGet(App.PARTITION_LIMIT);
//            if (App.PARTITION_OFFSET.get() < App.BIG_TABLE_MAX_COUNT) {
//                subTasks.add(new PartitionCallable(App.PARTITION_INDEX.get(),App.PARTITION_OFFSET.get(),partitionConsumer));
//            } else {
//                App.PARTITION_OFFSET.addAndGet(-App.PARTITION_LIMIT);
//                App.PARTITION_INDEX.decrementAndGet();
//            }
//            invokeAll(subTasks);
//        } else {
//            App.PARTITION_OFFSET.addAndGet(-App.PARTITION_LIMIT);
//            App.PARTITION_INDEX.decrementAndGet();
//        }
//        System.out.println("Partition is shutdown! GoodBy");
//        quietlyComplete();
//    }

    @Override
    public Boolean call() throws Exception {
        partitionConsumer.accept(index,offset);
        return true;
    }
}
