package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.entities.AenaflightSourceEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AenaflightSourceDaoImp implements AenaflightSourceDao{

    @PersistenceContext
    private EntityManager em;


    @Override
    public Long getTableCount() {
        Query query = em.createQuery("SELECT COUNT(id) FROM AenaflightSourceEntity",Long.class);
        List<Long> counts = query.getResultList();
        if(counts.isEmpty()){
            return 0L;
        }
        return counts.get(0);
    }

    @Override
    public List<AenaflightSourceEntity> getListByPagenation(int offset, int limit) {
        List<AenaflightSourceEntity> resultList = em.createQuery("FROM AenaflightSourceEntity ORDER BY ID")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return resultList;
    }

    @Override
    @Transactional
    public void flushAndClear() {
        em.flush();
        em.clear();
    }


}
