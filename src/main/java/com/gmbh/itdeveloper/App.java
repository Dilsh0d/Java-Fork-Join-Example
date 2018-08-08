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
public class App
{
//    public static AtomicInteger PARTITION_INDEX = new AtomicInteger(0);
//    public static AtomicInteger PARTITION_OFFSET = new AtomicInteger(0);
//    public static int PARTITION_LIMIT = 100_000;
    public static int BIG_TABLE_MAX_COUNT = 9_760_785; // 9_809_285

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
            extractService.beginForkJoinProcess();
        } else {
            System.out.println("All data tranformed!!!");
        }
    }

    public static synchronized int addOffsetAndCheckMax(){
        App.OFFSET.addAndGet(App.LIMIT);
        if(App.proccesRun.get() && App.OFFSET.get() < App._MAX.get() && App.OFFSET.get() < App.BIG_TABLE_MAX_COUNT) {
            return App.OFFSET.get();
        } else {
            App.proccesRun.set(false);
            App.OFFSET.addAndGet(-App.LIMIT);
        }
        return 0;
    }
}
