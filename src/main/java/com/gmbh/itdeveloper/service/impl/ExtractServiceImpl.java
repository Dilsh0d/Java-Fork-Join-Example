package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dao.GlobalConfigDao;
import com.gmbh.itdeveloper.dto.ConfigDto;
import com.gmbh.itdeveloper.entities.GlobalConfigEntity;
import com.gmbh.itdeveloper.entities.StatusEnum;
import com.gmbh.itdeveloper.service.ExtractService;
import com.gmbh.itdeveloper.service.TransientService;
import com.gmbh.itdeveloper.tasks.LoadAndTransformAction;
import com.gmbh.itdeveloper.tasks.LosingOffsetCallable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

@Service
public class ExtractServiceImpl implements ExtractService{

    @Autowired
    private ForkJoinPool forkJoinPool;

    @Autowired
    private TransientService transientService;

    @Autowired
    private GlobalConfigDao aenaflightConfigDao;

    @Autowired
    private AenaflightSource2017Dao aenaflightSource2017Dao;


    @Override
    public void beginForkJoinProcess() {
        long startTime = System.currentTimeMillis();
        Consumer<Integer> consumer = offset -> {
            try {
                transientService.readAndWriteTable(offset, App.LIMIT);
            } catch (Exception e){
                // error
            }
        };
        App.OFFSET.set(transientService.getMaxIndexOffset()*App.LIMIT);
        if(App.OFFSET.get()>0){
            System.out.println("___________________________START WITH OFFSET = " +App.OFFSET.get()+"_____________________________");
        }
        do {
            if(forkJoinPool.getQueuedTaskCount()==0 && forkJoinPool.getActiveThreadCount() == 0) {
                App.proccesRun.set(true);
                App._MAX.addAndGet(1_00_000);
                forkJoinPool.invoke(new LoadAndTransformAction(App.OFFSET.get(),consumer));
                System.gc();
            }
        } while (App._MAX.get()<=App.BIG_TABLE_MAX_COUNT);
        forkJoinPool.shutdown();

        List<Integer>  notIndexOffsets = transientService.getNotIndexOffsets();
        if(notIndexOffsets.isEmpty()) {
            transientService.updateGlobalConfig();
            System.out.println("All offsets good indexing without error");
        } else {
            Integer maxIndexOffset = transientService.getMaxIndexOffset();
            System.out.println("Current position Offset = "+maxIndexOffset+
                    " and losing  offsets between [1 .."+maxIndexOffset+"] ="+notIndexOffsets.toString());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Fork/Join " + (endTime - startTime) + " milliseconds.");

    }


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

    @Override
    public void losingOffsetsANewIndexing() {
        Consumer<Integer> consumer = offset -> {
            try {
                transientService.readAndWriteTable(offset, App.LIMIT);
            } catch (Exception e){
                // error
            }
        };

        List<LosingOffsetCallable> losingOffsetCallables = new ArrayList<>();

        List<Integer> lostNotIndexOffsets = transientService.getNotIndexOffsets();
        lostNotIndexOffsets.forEach(offset ->
                losingOffsetCallables.add(new LosingOffsetCallable(offset*App.LIMIT,consumer)
        ));

        if(!losingOffsetCallables.isEmpty()) {
            System.out.println("Start old stoped process positions = "+lostNotIndexOffsets.toString());
            forkJoinPool.invokeAll(losingOffsetCallables);
            System.out.println("Successly indexing old stoped process positions = "+lostNotIndexOffsets.toString());
        }
    }
}
