package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.ForkJoinApp;
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
                transientService.readAndWriteTable(offset, ForkJoinApp.LIMIT);
            } catch (Exception e){
                e.printStackTrace();
            }
        };
        int maxOffset = transientService.getMaxIndexOffset();
        if(maxOffset>0) {
            ForkJoinApp.OFFSET.set(transientService.getMaxIndexOffset() + ForkJoinApp.LIMIT);
            System.out.println("___________________________START WITH OFFSET = " + ForkJoinApp.OFFSET.get()+"_____________________________");
            ForkJoinApp._MAX.set((ForkJoinApp.OFFSET.get()/1_00_000)*1_00_000);
        }
        boolean isFirstLoop = false;
        do {
            if(forkJoinPool.getQueuedTaskCount()==0 && forkJoinPool.getActiveThreadCount() == 0) {
                ForkJoinApp.proccesRun.set(true);
                ForkJoinApp._MAX.addAndGet(1_00_000);
                if(isFirstLoop){
                    ForkJoinApp.nextStepAndCheckMax();
                }
                System.out.println("----------------------"+ ForkJoinApp.OFFSET.get()+"--------------------------");
                forkJoinPool.invoke(new LoadAndTransformAction(ForkJoinApp.OFFSET.get(),consumer));
                System.gc();
            }
            isFirstLoop = true;
        } while (ForkJoinApp._MAX.get()<= ForkJoinApp.BIG_TABLE_MAX_COUNT);
        forkJoinPool.shutdown();

        List<Integer>  notIndexOffsets = transientService.getNotIndexOffsets();
        if(notIndexOffsets.isEmpty()) {
            transientService.updateGlobalConfig();
            System.out.println("All offsets good indexing without error");
        } else {
            Integer maxIndexOffset = transientService.getMaxIndexOffset();
            System.out.println("Current position Offset = "+maxIndexOffset+
                    " and losing  offsets between = "+notIndexOffsets.toString());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Fork/Join " + (endTime - startTime) + " milliseconds.");

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewColumnAndIndexing() {
        System.out.println("___________Begin process add new ordered column with type bigserial___________");
        System.out.println("___________Please wait going process add new ordered column___________________");
        try {
            aenaflightSource2017Dao.addNewColumn();
            aenaflightConfigDao.changeConfigFile(StatusEnum.INPROGRESS);
        } catch (Exception e) {
            // already exist error
        }
        System.out.println("___________End process add new column_________________________________________");
    }

    @Override
    public void vacuumBigTable() {
        System.out.println("___________Begin process VACUUM big table_____________________________________");
        try {
            aenaflightSource2017Dao.vacuumTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("___________End process VACUUM_________________________________________________");
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
                transientService.readAndWriteTable(offset, ForkJoinApp.LIMIT);
            } catch (Exception e){
                // error
            }
        };

        List<LosingOffsetCallable> losingOffsetCallables = new ArrayList<>();

        List<Integer> lostNotIndexOffsets = transientService.getNotIndexOffsets();
        lostNotIndexOffsets.forEach(offset ->
                losingOffsetCallables.add(new LosingOffsetCallable(offset,consumer)
        ));

        if(!losingOffsetCallables.isEmpty()) {
            System.out.println("+++++++++++++++++ Start old stop process positions = "+lostNotIndexOffsets.toString());
            forkJoinPool.invokeAll(losingOffsetCallables);
            System.out.println("+++++++++++++++++ Successfully finish indexing old stop process ++++++++++++++++++++");
        }
    }
}
