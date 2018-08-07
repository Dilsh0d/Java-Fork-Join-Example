package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dao.GlobalConfigDao;
import com.gmbh.itdeveloper.entities.StatusEnum;
import com.gmbh.itdeveloper.service.LoadService;
import com.gmbh.itdeveloper.service.TransientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransientServiceImpl implements TransientService {

    @Autowired
    private LoadService loadService;

//    @Autowired
//    private PartitionService partitionService;

    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Autowired
    private AenaflightSource2017Dao aenaflightSourceDao;

//    @Override
//    @Transactional(propagation =  Propagation.NEVER)
//    public void partitionBigTable(int index, int offset) {
//        partitionService.partitionBigTable(index,offset);
//    }

    @Override
    @Transactional(propagation =  Propagation.NEVER)
    public void readAndWriteTable(int index, int offset, int limit) {
        loadService.readAndWriteTable(index,offset,limit);
        aenaflightSourceDao.flushAndClear();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGlobalConfig() {
        globalConfigDao.changeConfigFile(StatusEnum.DONE);
    }

//    @Override
//    @Transactional
//    public void createPartitionFunc() {
//        aenaflightSourceDao.createPartitionFunc();
//    }
}
