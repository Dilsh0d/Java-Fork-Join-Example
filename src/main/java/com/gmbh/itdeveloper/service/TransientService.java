package com.gmbh.itdeveloper.service;

import java.util.List;

public interface TransientService {

    void readAndWriteTable(int offset, int limit);

    void updateGlobalConfig();

    List<Integer> getNotIndexOffsets();

    Integer getMaxIndexOffset();

}
