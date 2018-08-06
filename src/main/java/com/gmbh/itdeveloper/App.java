package com.gmbh.itdeveloper;

import com.gmbh.itdeveloper.configs.AppConfig;
import com.gmbh.itdeveloper.configs.PersistenceConfig;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.PartitionService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dilsh0d!
 *
 */
public class App
{
    public static AtomicInteger PARTITION_INDEX = new AtomicInteger(0);
    public static AtomicInteger PARTITION_OFFSET = new AtomicInteger(0);
    public static int PARTITION_LIMIT = 100_000;
    public static int BIG_TABLE_MAX_COUNT = 9_760_785;

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
        PartitionService partitionService = ctx.getBean(PartitionService.class);
//        partitionService.partitionBigTableDrop();
//        extractService.partitionBigTable();
        extractService.beginForkJoinProcess();
//
//        extractService.beginConcurrenceProcess();
//        extractService.sampleForEachBegin();
    }
}
