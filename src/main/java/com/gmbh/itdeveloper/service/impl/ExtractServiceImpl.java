package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.LoadService;
import com.gmbh.itdeveloper.tasks.LoadAndTransformAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Service
public class ExtractServiceImpl implements ExtractService{

    @Autowired
    private ForkJoinPool forkJoinPool;

    @Autowired
    private LoadService loadService;


    @Override
    public void beginProcess() {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = offset -> {
            App.ctx.getBean(LoadService.class).readAndWriteTable(offset, App.LIMIT);
        };
        forkJoinPool.invoke(new LoadAndTransformAction(App.OFFSET.get(),consumer));
        long endTime = System.currentTimeMillis();

        System.out.println("Fork/Join " + (endTime - startTime) +
                " milliseconds.");

    }

    @Override
    public void sampleForEachBegin() {
        long startTime = System.currentTimeMillis();
        while (App.OFFSET.get()<1_000_000){
            loadService.readAndWriteTable(App.OFFSET.addAndGet(App.LIMIT), App.LIMIT);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Simple ForEach " + (endTime - startTime) +
                " milliseconds.");
    }
}
