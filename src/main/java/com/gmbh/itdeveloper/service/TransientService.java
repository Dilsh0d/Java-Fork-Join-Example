package com.gmbh.itdeveloper.service;

public interface TransientService {
//    void partitionBigTable(int index, int offset);

    void readAndWriteTable(int offset, int limit);

    void updateGlobalConfig();

//    void createPartitionFunc();
}
