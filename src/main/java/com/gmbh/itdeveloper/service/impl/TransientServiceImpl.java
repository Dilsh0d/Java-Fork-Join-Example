package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.dao.GlobalConfigDao;
import com.gmbh.itdeveloper.dao.PositionConfigDao;
import com.gmbh.itdeveloper.entities.StatusEnum;
import com.gmbh.itdeveloper.service.LoadService;
import com.gmbh.itdeveloper.service.TransientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TransientServiceImpl implements TransientService {

    @Autowired
    private LoadService loadService;


    @Autowired
    private GlobalConfigDao globalConfigDao;

    @Autowired
    private PositionConfigDao positionConfigDao;

    @Autowired
    private AenaflightSource2017Dao aenaflightSourceDao;


    @Override
    @Transactional(propagation =  Propagation.NEVER)
    public void readAndWriteTable(int offset, int limit) {
        loadService.readAndWriteTable(offset,limit);
        aenaflightSourceDao.flushAndClear();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateGlobalConfig() {
        globalConfigDao.changeConfigFile(StatusEnum.DONE);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Integer> getNotIndexOffsets() {
        return positionConfigDao.getNotIndexOffsets();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer getMaxIndexOffset() {
        return positionConfigDao.getMaxIndexOffset();
    }

}
