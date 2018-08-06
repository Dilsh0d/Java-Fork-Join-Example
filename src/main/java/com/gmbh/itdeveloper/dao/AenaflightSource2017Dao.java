package com.gmbh.itdeveloper.dao;

import com.gmbh.itdeveloper.dto.AenaflightDto;
import com.gmbh.itdeveloper.entities.AenaflightSource2017Entity;

import java.util.List;

public interface AenaflightSource2017Dao {

    Long getTableCount();

    List<AenaflightSource2017Entity> getListByPagenation(int offset, int limit);

    void flushAndClear();

    List<AenaflightDto> getListStringByPagenation(int index, int offset, int limit);

    void partitionBigTable(int index, int offset, int limit);

    void dropPartitionTables(int index);

    void createPartitionFunc();
}
