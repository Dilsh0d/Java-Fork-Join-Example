package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.ForkJoinApp;
import com.gmbh.itdeveloper.dao.PositionConfigDao;
import com.gmbh.itdeveloper.entities.PositionConfigEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PositionConfigDaoImp implements PositionConfigDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insertOffset(int offset) {
        PositionConfigEntity positionConfigEntity = new PositionConfigEntity();
        positionConfigEntity.setpOffset(offset);
        em.merge(positionConfigEntity);
    }

    @Override
    public List<Integer> getNotIndexOffsets(){
        return em.createNativeQuery("select (my_order*"+ ForkJoinApp.LIMIT+")-"+ ForkJoinApp.LIMIT+" from generate_series(1,(select max(poffset) from position_config)) my_order " +
                                        "where my_order not in (select poffset from position_config) " +
                                        "order by my_order ").getResultList();
    }

    @Override
    public Integer getMaxIndexOffset(){
        List<Integer> maxList =
                em.createQuery("SELECT (max(pOffset)*"+ ForkJoinApp.LIMIT+")-"+ ForkJoinApp.LIMIT+" from PositionConfigEntity ",Integer.class).getResultList();
        if(maxList.isEmpty()){
            return 0;
        }
        return maxList.get(0)!=null ? maxList.get(0) : 0;
    }
}
