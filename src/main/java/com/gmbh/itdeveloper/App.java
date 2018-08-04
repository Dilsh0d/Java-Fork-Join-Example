package com.gmbh.itdeveloper;

import com.gmbh.itdeveloper.configs.AppConfig;
import com.gmbh.itdeveloper.configs.PersistenceConfig;
import com.gmbh.itdeveloper.service.ExtractService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dilsh0d!
 *
 */
public class App
{
    public static final int LIMIT = 500;
    public static AtomicInteger _MAX =new AtomicInteger(0);
    public static AtomicInteger OFFSET = new AtomicInteger(0);
    public static AnnotationConfigApplicationContext ctx;

    public static void main( String[] args )
    {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.register(PersistenceConfig.class);
        ctx.refresh();
        ExtractService extractService = ctx.getBean(ExtractService.class);
//        extractService.beginForkJoinProcess();

//        extractService.beginConcurrenceProcess();
        extractService.sampleForEachBegin();
    }
}
