package com.gmbh.itdeveloper.service.impl;

import com.gmbh.itdeveloper.App;
import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.service.PartitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartitionServiceImpl implements PartitionService {

    @Autowired
    private AenaflightSource2017Dao aenaflightSourceDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void partitionBigTable(int index, int offset) {
        aenaflightSourceDao.partitionBigTable(index,offset,App.PARTITION_LIMIT);
        System.out.println("Created partition table = "+index);
    }

    @Override
    public void partitionBigTableDrop() {
        int index = 0;
        do {
            try {
                aenaflightSourceDao.dropPartitionTables(index++);
            }catch(Exception e){
                e.printStackTrace();
            }
        }while(index<117);
    }

}
