package com.gmbh.itdeveloper;

import com.gmbh.itdeveloper.configs.AppConfig;
import com.gmbh.itdeveloper.configs.PersistenceConfig;
import com.gmbh.itdeveloper.dto.ConfigDto;
import com.gmbh.itdeveloper.entities.StatusEnum;
import com.gmbh.itdeveloper.service.ExtractService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Dilsh0d!
 *
 */
public class ForkJoinApp
{
    public static int BIG_TABLE_MAX_COUNT = 9_760_785; // 9_760_785

    public static AtomicBoolean proccesRun = new AtomicBoolean(false);
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

        ConfigDto configDto = extractService.getAenaflightConfig();
        if(configDto.getStatus().equals(StatusEnum.INPROGRESS)) {
            if(!configDto.getCreatedNewColumn()) {
                try {
                    extractService.addNewColumnAndIndexing();
                } catch (Exception e) {
                    // already exist offset_id in big table
                }
            }
            try {
                extractService.vacuumBigTable();
            } catch (Exception e) {
                // ohter exception
            }
            extractService.losingOffsetsANewIndexing();
            extractService.beginForkJoinProcess();
        } else {
            System.out.println("All data tranformed!!!");
        }
    }

    public static synchronized int nextStepAndCheckMax(){
        ForkJoinApp.OFFSET.addAndGet(ForkJoinApp.LIMIT);
        if(ForkJoinApp.proccesRun.get() && ForkJoinApp.OFFSET.get() < ForkJoinApp._MAX.get() && ForkJoinApp.OFFSET.get() < ForkJoinApp.BIG_TABLE_MAX_COUNT) {
            return ForkJoinApp.OFFSET.get();
        } else {
            ForkJoinApp.proccesRun.set(false);
            ForkJoinApp.OFFSET.addAndGet(-ForkJoinApp.LIMIT);
        }
        return 0;
    }
}
