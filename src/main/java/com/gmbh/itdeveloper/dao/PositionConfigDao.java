package com.gmbh.itdeveloper.dao;

import java.util.List;

public interface PositionConfigDao {
    void insertOffset(int offset);
    List<Integer> getNotIndexOffsets();
    Integer getMaxIndexOffset();
}
