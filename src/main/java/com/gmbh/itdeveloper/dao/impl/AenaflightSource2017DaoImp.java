package com.gmbh.itdeveloper.dao.impl;

import com.gmbh.itdeveloper.dao.AenaflightSource2017Dao;
import com.gmbh.itdeveloper.entities.AenaflightSource2017Entity;
import org.hibernate.CacheMode;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AenaflightSource2017DaoImp implements AenaflightSource2017Dao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Long getTableCount() {
        Query query = em.createQuery("SELECT COUNT(id) FROM AenaflightSource2017Entity",Long.class);
        List<Long> counts = query.getResultList();
        if(counts.isEmpty()){
            return 0L;
        }
        return counts.get(0);
    }

    @Override
    public List<AenaflightSource2017Entity> getListByPagenation(int offset, int limit) {
        List<AenaflightSource2017Entity> resultList = em.createQuery("FROM AenaflightSource2017Entity WHERE offset_id>:offset  ORDER BY offset_id")
                .setParameter("offset",offset)
                .setMaxResults(limit)
                .setHint(QueryHints.HINT_FETCH_SIZE,500)
                .setHint(QueryHints.HINT_CACHE_MODE,CacheMode.IGNORE)
                .setFlushMode(FlushModeType.COMMIT)
                .getResultList();
        return resultList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void flushAndClear() {
        em.flush();
        em.clear();
    }

//    @Override
//    public List<AenaflightDto> getListStringByPagenation(int index, int offset, int limit) {
//        List<AenaflightDto> resultList = em.createNativeQuery("select " +
////                "CONCAT_WS('@'," +
//                "act_arr_date_time_lt, " +
//                "aircraft_name_scheduled," +
//                "arr_apt_name_es, " +
//                "arr_apt_code_iata, " +
//                "baggage_info, " +
//                "carrier_airline_name_en, " +
//                "carrier_icao_code, " +
//                "carrier_number, " +
//                "counter, " +
//                "dep_apt_name_es, " +
//                "dep_apt_code_iata, " +
//                "est_arr_date_time_lt, " +
//                "est_dep_date_time_lt, " +
//                "flight_airline_name_en, " +
//                "flight_airline_name, " +
//                "flight_icao_code, " +
//                "flight_number, " +
//                "flt_leg_seq_no, " +
//                "gate_info, " +
//                "lounge_info, " +
//                "schd_arr_only_date_lt, " +
//                "schd_arr_only_time_lt, " +
//                "source_data, status_info, " +
//                "terminal_info, " +
//                "arr_terminal_info, " +
//                "created_at, " +
//                "act_dep_date_time_lt, " +
//                "schd_dep_only_date_lt, " +
//                "schd_dep_only_time_lt " +
////                ") " +
//                "from part_aenaflight_"+index+"  ORDER BY id")
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .setHint(QueryHints.HINT_FETCH_SIZE,500)
//                .setHint(QueryHints.HINT_CACHE_MODE,CacheMode.IGNORE)
//                .unwrap( org.hibernate.query.Query.class )
//                .setResultTransformer( Transformers.aliasToBean(AenaflightDto.class ) )
//                .getResultList();
//        return resultList;
//    }
//
//    @Override
//    public void partitionBigTable(int index, int offset, int limit) {
//        StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("create_partition_table");
//        // set parameters
//        storedProcedure.registerStoredProcedureParameter("index", Integer.class, ParameterMode.IN);
//        storedProcedure.registerStoredProcedureParameter("offset", Integer.class, ParameterMode.IN);
//        storedProcedure.registerStoredProcedureParameter("limit", Integer.class, ParameterMode.IN);
//        storedProcedure.registerStoredProcedureParameter("output", String.class, ParameterMode.OUT);
//        storedProcedure.setParameter("index",index);
//        storedProcedure.setParameter("offset",offset);
//        storedProcedure.setParameter("limit",limit);
//        // execute SP
//        storedProcedure.execute();
//        storedProcedure.getOutputParameterValue("output");
//    }
//
//
//    @Override
//    @Transactional
//    public void dropPartitionTables(int index) {
//        em.createNativeQuery("DROP table part_aenaflight_"+index).executeUpdate();
//    }
//
//    @Override
//    public void createPartitionFunc() {
//        em.createNativeQuery("CREATE OR REPLACE FUNCTION create_partition_table(index1 integer,offset1 integer,limit1 integer) RETURNS varchar(10) AS $$\n" +
//                "        BEGIN\n" +
//                "                EXECUTE 'CREATE TABLE part_aenaflight_'||index1||'() INHERITS (aenaflight_2017_01)';\n" +
//                "                EXECUTE 'ALTER TABLE part_aenaflight_'||index1||' NO INHERIT aenaflight_2017_01';\n"+
//                "                EXECUTE 'INSERT INTO part_aenaflight_'||index1||\n" +
//                "                ' select * from aenaflight_2017_01 OFFSET '||offset1||' LIMIT '||limit1;\n" +
//                "                EXECUTE 'CREATE INDEX \"part_aenaflight_id_index'||index1||'\" ON part_aenaflight_'||index1||' USING btree(id)';\n" +
//                "                RETURN 'OK';\n"+
//                "        END;\n" +
//                "$$ LANGUAGE plpgsql;").executeUpdate();
//    }

    @Override
    public void addNewColumn() {
        em.createNativeQuery("ALTER TABLE aenaflight_2017_01 ADD COLUMN offset_id BIGSERIAL").executeUpdate();
        em.createNativeQuery("CREATE INDEX aenaflight_2017_01_offset_id_index ON aenaflight_2017_01 USING btree(offset_id)").executeUpdate();
    }

    @Override
    public void vacuumTable() throws SQLException {
        org.hibernate.Session session = em.unwrap(org.hibernate.Session.class);
        org.hibernate.internal.SessionImpl sessionImpl = (SessionImpl) session;  // required because Session doesn't provide connection()
        java.sql.Connection connection = sessionImpl.connection();
        connection.prepareStatement("VACUUM (VERBOSE) aenaflight_2017_01").execute();
    }
}
