package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.dao.GlobalConfigDao;
import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dto.ConfigDto;
import com.gmbh.itdeveloper.entities.GlobalConfigEntity;
import com.gmbh.itdeveloper.entities.StatusEnum;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.TransientService;
import com.gmbh.itdeveloper.tasks.LoadAndTransformAction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

@Service
public class ExtractServiceImpl implements ExtractService{

    @Autowired
    private ForkJoinPool forkJoinPool;

//    @Autowired
//    private ExecutorService cexecutorService;

    @Autowired
    private TransientService transientService;

//    @Autowired
//    private PartitionService partitionService;

    @Autowired
    private GlobalConfigDao aenaflightConfigDao;

    @Autowired
    private AenaflightSource2017Dao aenaflightSource2017Dao;


    @Override
    public void beginForkJoinProcess() {
        long startTime = System.currentTimeMillis();

        Consumer<Integer[]> consumer = params -> {
            transientService.readAndWriteTable(params[0],params[1], App.LIMIT);
        };
        int index = 0;
        do {
            if(forkJoinPool.getQueuedTaskCount()==0 && forkJoinPool.getActiveThreadCount() == 0) {
                App._MAX.addAndGet(1_00_000);
                forkJoinPool.invoke(new LoadAndTransformAction(index++,App.OFFSET.get(),consumer));
                System.gc();
            }
        } while (App._MAX.get()<=App.BIG_TABLE_MAX_COUNT);
        forkJoinPool.shutdown();
        long endTime = System.currentTimeMillis();
        transientService.updateGlobalConfig();
        System.out.println("Fork/Join " + (endTime - startTime) +
                " milliseconds.");

    }

//    @Override
//    public void partitionBigTable() {
//        System.out.println("BEGIN CREATE PARTITION TABLES");
//
//        transientService.createPartitionFunc();
//
//        PartitionConsumer<Integer,Integer>  partitionConsumer = (index,offset)->{
//            transientService.partitionBigTable(index,offset);
//        };
//
//        List<PartitionCallable> partitionCallables = new ArrayList<>();
//        do{
//            partitionCallables.add(new PartitionCallable(App.PARTITION_INDEX.get(),App.PARTITION_OFFSET.get(),partitionConsumer));
//            App.PARTITION_OFFSET.addAndGet(App.PARTITION_LIMIT);
//            App.PARTITION_INDEX.incrementAndGet();
//        }while (App.PARTITION_OFFSET.get() < App.BIG_TABLE_MAX_COUNT);
//
//        try {
//            List<Future<Boolean>> partitionResult = cexecutorService.invokeAll(partitionCallables);
//            cexecutorService.shutdown();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.gc();
//        System.out.println("END CREATE PARTITION TABLES");
//    }
//
//    @Override
//    public void partitionBigTableDrop() {
//        partitionService.partitionBigTableDrop();
//    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewColumnAndIndexing() {
        System.out.println("Begin process add new ordered column with type bigserial");
        System.out.println("Please wait going process add new ordered column");
        try {
            aenaflightSource2017Dao.addNewColumn();
            aenaflightConfigDao.changeConfigFile(StatusEnum.INPROGRESS);
        } catch (Exception e) {
            // already exist error
        }
        System.out.println("End process add new column");
    }

    @Override
    public void vacuumBigTable() {
        System.out.println("Begin process VACUUM big table");
        try {
            aenaflightSource2017Dao.vacuumTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("End process VACUUM");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ConfigDto getAenaflightConfig() {
        ConfigDto configDto = new ConfigDto();
        GlobalConfigEntity aenaflightConfigEntity = aenaflightConfigDao.getConfigEntity();
        if(aenaflightConfigEntity!=null) {
            BeanUtils.copyProperties(aenaflightConfigEntity, configDto);
        }
        return configDto;
    }
}
