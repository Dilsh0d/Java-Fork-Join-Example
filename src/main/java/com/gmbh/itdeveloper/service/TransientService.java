package com.gmbh.itdeveloper.service;

public interface TransientService {
//    void partitionBigTable(int index, int offset);

    void readAndWriteTable(int index, int offset, int limit);

//    void createPartitionFunc();
}
