package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.LoadService;
import com.gmbh.itdeveloper.tasks.LoadAndTransformAction;
import com.gmbh.itdeveloper.tasks.LoadAndTransformRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Service
public class ExtractServiceImpl implements ExtractService{

    @Autowired
    private ForkJoinPool forkJoinPool;

//    @Autowired
//    private ExecutorService executorService;

    @Autowired
    private LoadService loadService;

    @Autowired
    private AenaflightSourceDao aenaflightSourceDao;


    @Override
    public void beginForkJoinProcess() {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = offset -> {
            loadService.readAndWriteTable(offset, App.LIMIT);
            aenaflightSourceDao.flushAndClear();
        };

        do {
            if(forkJoinPool.getQueuedTaskCount()==0 && forkJoinPool.getActiveThreadCount() == 0) {
                App._MAX.addAndGet(50_000);
                forkJoinPool.invoke(new LoadAndTransformAction(App.OFFSET.get(), consumer));
//                aenaflightSourceDao.flushAndClear();
            }
        } while (App._MAX.get()<1_000_000);
        forkJoinPool.shutdown();
        long endTime = System.currentTimeMillis();

        System.out.println("Fork/Join " + (endTime - startTime) +
                " milliseconds.");

    }

    @Override
    public void sampleForEachBegin() {
        long startTime = System.currentTimeMillis();
        while (App.OFFSET.get()<App._MAX.get()){
            loadService.readAndWriteTable(App.OFFSET.addAndGet(App.LIMIT), App.LIMIT);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Simple ForEach " + (endTime - startTime) +
                " milliseconds.");
    }

    @Override
    public void beginConcurrenceProcess() {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = offset -> {
            loadService.readAndWriteTable(offset, App.LIMIT);
        };
        while (App.OFFSET.get()<App._MAX.get()){
//            executorService.execute(new LoadAndTransformRunnable(App.OFFSET.get(),consumer));
            App.OFFSET.addAndGet(App.LIMIT);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("ExecutorService " + (endTime - startTime) +
                " milliseconds.");
    }
}
