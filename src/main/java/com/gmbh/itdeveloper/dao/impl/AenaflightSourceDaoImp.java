package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.entities.AenaflightSourceEntity;
import org.hibernate.CacheMode;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
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
//    @Transactional(readOnly = true,propagation = Propagation.NEVER)
    public List<AenaflightSourceEntity> getListByPagenation(int offset, int limit) {
        List<AenaflightSourceEntity> resultList = em.createQuery("FROM AenaflightSourceEntity ORDER BY ID")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setHint(QueryHints.HINT_FETCH_SIZE,500)
                .setHint(QueryHints.HINT_CACHE_MODE,CacheMode.IGNORE)
                .setFlushMode(FlushModeType.COMMIT)
                .getResultList();
//        StatelessSession session = ((Session) em.getDelegate()).getSessionFactory().openStatelessSession();

//        Transaction txn = session.getTransaction();
//        txn.begin();
//        ScrollableResults results = session
//                .createQuery("FROM AenaflightSourceEntity ORDER BY ID")
//                .setHint(QueryHints.HINT_FETCH_SIZE,Integer.valueOf(500))
//                .setHint(QueryHints.HINT_READONLY,true)
//                .setMaxResults(limit)
//                .setFirstResult(offset)
//                .scroll(ScrollMode.FORWARD_ONLY);
//
//
//        List<AenaflightSourceEntity> resultList = new ArrayList<>();
//        while (results.next()) {
//             resultList.add((AenaflightSourceEntity) results.get(0));
//        }
//        txn.commit();
//        results.close();
//        session.close();
        return resultList;
    }

    @Override
    @Transactional(readOnly = true)
    public void flushAndClear() {
        em.flush();
        em.clear();
    }


}
