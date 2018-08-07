package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.PositionConfigDao;
import com.gmbh.itdeveloper.entities.PositionConfigEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PositionConfigDaoImp implements PositionConfigDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insertOffset(int offset) {
        PositionConfigEntity positionConfigEntity = new PositionConfigEntity();
        positionConfigEntity.setpOffset(offset);
        em.merge(positionConfigEntity);
    }
}
