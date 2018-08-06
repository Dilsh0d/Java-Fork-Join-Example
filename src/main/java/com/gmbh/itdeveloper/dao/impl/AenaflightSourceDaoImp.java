package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.AenaflightSourceDao;
import com.gmbh.itdeveloper.entities.AenaflightSourceEntity;
import org.hibernate.CacheMode;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void flushAndClear() {
        em.flush();
        em.clear();
    }

    @Override
    public List<String> getListStringByPagenation(int index, int offset, int limit) {
        List<String> resultList = em.createNativeQuery("select " +
                "CONCAT_WS('@'," +
                "act_arr_date_time_lt, " +
                "aircraft_name_scheduled," +
                "arr_apt_name_es, " +
                "arr_apt_code_iata, " +
                "baggage_info, " +
                "carrier_airline_name_en, " +
                "carrier_icao_code, " +
                "carrier_number, " +
                "counter, " +
                "dep_apt_name_es, " +
                "dep_apt_code_iata, " +
                "est_arr_date_time_lt, " +
                "est_dep_date_time_lt, " +
                "flight_airline_name_en, " +
                "flight_airline_name, " +
                "flight_icao_code, " +
                "flight_number, " +
                "flt_leg_seq_no, " +
                "gate_info, " +
                "lounge_info, " +
                "schd_arr_only_date_lt, " +
                "schd_arr_only_time_lt, " +
                "source_data, status_info, " +
                "terminal_info, " +
                "arr_terminal_info, " +
                "created_at, " +
                "act_dep_date_time_lt, " +
                "schd_dep_only_date_lt, " +
                "schd_dep_only_time_lt" +
                ") " +
                "from part_aenaflight_100  ORDER BY id")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setHint(QueryHints.HINT_FETCH_SIZE,500)
                .setHint(QueryHints.HINT_CACHE_MODE,CacheMode.IGNORE)
                .getResultList();
        return resultList;
    }

    @Override
    public void partitionBigTable(int index, int offset, int limit) {
        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("create_partition_table");
        // set parameters
        storedProcedure.registerStoredProcedureParameter("index", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("offset", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("limit", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("output", String.class, ParameterMode.OUT);
        storedProcedure.setParameter("index",index);
        storedProcedure.setParameter("offset",offset);
        storedProcedure.setParameter("limit",limit);
        // execute SP
        storedProcedure.execute();
        storedProcedure.getOutputParameterValue("output");
    }


    @Override
    @Transactional
    public void dropPartitionTables(int index) {
        em.createNativeQuery("DROP table part_aenaflight_"+index).executeUpdate();
    }

    @Override
    public void createPartitionFunc() {
        em.createNativeQuery("CREATE OR REPLACE FUNCTION create_partition_table(index1 integer,offset1 integer,limit1 integer) RETURNS varchar(10) AS $$\n" +
                "        BEGIN\n" +
                "                EXECUTE 'CREATE TABLE part_aenaflight_'||index1||'() INHERITS (aenaflight_2017_01)';\n" +
                "                EXECUTE 'INSERT INTO part_aenaflight_'||index1||\n" +
                "                ' select * from aenaflight_2017_01 OFFSET '||offset1||' LIMIT '||limit1;\n" +
                "                EXECUTE 'CREATE INDEX \"part_aenaflight_id_index'||index1||'\" ON part_aenaflight_'||index1||' USING btree(id)';\n" +
                "                RETURN 'OK';\n"+
                "        END;\n" +
                "$$ LANGUAGE plpgsql;").executeUpdate();
    }


}
