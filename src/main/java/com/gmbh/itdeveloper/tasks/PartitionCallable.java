package com.gmbh.itdeveloper.tasks;

import com.gmbh.itdeveloper.utils.PartitionConsumer;

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

    @Override
    public Boolean call() throws Exception {
        partitionConsumer.accept(index,offset);
        return true;
    }
}
