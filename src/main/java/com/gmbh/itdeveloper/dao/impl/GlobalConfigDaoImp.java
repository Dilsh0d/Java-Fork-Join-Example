package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.GlobalConfigDao;
import com.gmbh.itdeveloper.entities.GlobalConfigEntity;
import com.gmbh.itdeveloper.entities.StatusEnum;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GlobalConfigDaoImp implements GlobalConfigDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void changeConfigFile(StatusEnum satus) {
        GlobalConfigEntity aenaflightConfigEntity= getConfigEntity();
        if(aenaflightConfigEntity==null){
            aenaflightConfigEntity = new GlobalConfigEntity();
        }
        aenaflightConfigEntity.setCreatedNewColumn(Boolean.TRUE);
        aenaflightConfigEntity.setStatus(satus);
        em.merge(aenaflightConfigEntity);
    }

    @Override
    public GlobalConfigEntity getConfigEntity() {
        return em.createQuery("from AenaflightConfigEntity",GlobalConfigEntity.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
