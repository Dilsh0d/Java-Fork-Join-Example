package com.gmbh.itdeveloper.dao;

import com.gmbh.itdeveloper.entities.AenaflightSourceEntity;

import java.util.List;

public interface AenaflightSourceDao {

    Long getTableCount();

    List<AenaflightSourceEntity> getListByPagenation(int offset, int limit);

    void flushAndClear();

    List<String> getListStringByPagenation(int index, int offset, int limit);

    void partitionBigTable(int index, int offset, int limit);

    void dropPartitionTables(int index);

    void createPartitionFunc();
}
