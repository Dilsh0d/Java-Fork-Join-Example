package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.TransientService;
import com.gmbh.itdeveloper.tasks.LoadAndTransformAction;
import com.gmbh.itdeveloper.tasks.PartitionCallable;
import com.gmbh.itdeveloper.utils.PartitionConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Consumer;

@Service
public class ExtractServiceImpl implements ExtractService{

    @Autowired
    private ForkJoinPool forkJoinPool;

    @Autowired
    private ExecutorService cexecutorService;

    @Autowired
    private TransientService transientService;


    @Override
    public void beginForkJoinProcess() {
        long startTime = System.currentTimeMillis();

        Consumer<Integer[]> consumer = params -> {
            transientService.readAndWriteTable(params[0],params[1], App.LIMIT);
        };
        int index = 0;
        do {
            if(forkJoinPool.getQueuedTaskCount()==0 && forkJoinPool.getActiveThreadCount() == 0) {
                App._MAX.set(1_00_000);
                App.OFFSET.set(0);
                forkJoinPool.invoke(new LoadAndTransformAction(index++,App.OFFSET.get(),consumer));
                System.gc();
            }
        } while (App._MAX.get()<=App.BIG_TABLE_MAX_COUNT);
        forkJoinPool.shutdown();
        long endTime = System.currentTimeMillis();

        System.out.println("Fork/Join " + (endTime - startTime) +
                " milliseconds.");

    }

    @Override
    public void sampleForEachBegin() {
        long startTime = System.currentTimeMillis();
        while (App.OFFSET.get()<1_000_000){
//            loadService.readAndWriteTable(index, App.OFFSET.addAndGet(App.LIMIT), App.LIMIT);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Simple ForEach " + (endTime - startTime) +
                " milliseconds.");
    }

    @Override
    public void beginConcurrenceProcess() {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = offset -> {
//            loadService.readAndWriteTable(index, offset, App.LIMIT);
        };
        while (App.OFFSET.get()<App._MAX.get()){
//            executorService.execute(new LoadAndTransformRunnable(App.OFFSET.get(),consumer));
            App.OFFSET.addAndGet(App.LIMIT);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ExecutorService " + (endTime - startTime) +
                " milliseconds.");
    }

    @Override
    public void partitionBigTable() {
        System.out.println("BEGIN CREATE PARTITION TABLES");

        transientService.createPartitionFunc();

        PartitionConsumer<Integer,Integer>  partitionConsumer = (index,offset)->{
            transientService.partitionBigTable(index,offset);
        };

        List<PartitionCallable> partitionCallables = new ArrayList<>();
        do{
            partitionCallables.add(new PartitionCallable(App.PARTITION_INDEX.get(),App.PARTITION_OFFSET.get(),partitionConsumer));
            App.PARTITION_OFFSET.addAndGet(App.PARTITION_LIMIT);
            App.PARTITION_INDEX.incrementAndGet();
        }while (App.PARTITION_OFFSET.get() < App.BIG_TABLE_MAX_COUNT);

        try {
            List<Future<Boolean>> partitionResult = cexecutorService.invokeAll(partitionCallables);
            cexecutorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        System.out.println("END CREATE PARTITION TABLES");
    }
}
