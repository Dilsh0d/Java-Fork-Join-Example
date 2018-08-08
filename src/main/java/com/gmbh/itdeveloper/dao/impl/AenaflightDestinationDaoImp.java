package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.AenaflightDestinationDao;
import com.gmbh.itdeveloper.entities.AenaflightDestinationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AenaflightDestinationDaoImp implements AenaflightDestinationDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void batchInserts(List<AenaflightDestinationEntity> insertedResult) {
       for(AenaflightDestinationEntity entity:insertedResult) {
           em.merge(entity);
       }
    }
}
