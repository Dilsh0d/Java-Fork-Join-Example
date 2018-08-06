package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoadServiceImpl implements LoadService {

    @Autowired
    private AenaflightSourceDao aenaflightSourceDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void readAndWriteTable(int index, int offset, int limit) {
        long b = System.currentTimeMillis();
        List<String> resultList = aenaflightSourceDao.getListStringByPagenation(index,offset,limit);
        resultList.forEach(row -> {
           String tt = row;
        });
        resultList.clear();
        long e = System.currentTimeMillis();
        System.out.println("Offset="+offset+ " Time = "+((e-b)/1000d));
    }
}
