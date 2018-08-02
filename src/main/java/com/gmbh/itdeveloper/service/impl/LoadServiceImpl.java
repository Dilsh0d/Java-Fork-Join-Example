package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.entities.AenaflightSourceEntity;
import com.gmbh.itdeveloper.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private AenaflightSourceDao aenaflightSourceDao;

    @Override
    @Transactional
    public void readAndWriteTable(int offset, int limit) {
        long b = System.currentTimeMillis();
        List<AenaflightSourceEntity> resultList = aenaflightSourceDao.getListByPagenation(offset,limit);
        resultList.forEach(aenaflightSourceEntity -> {
           Long tt = aenaflightSourceEntity.getId();
        });
        long e = System.currentTimeMillis();
        System.out.println("Offset="+offset+ " Time = "+((e-b)/1000d));
        aenaflightSourceDao.flushAndClear();
    }
}
