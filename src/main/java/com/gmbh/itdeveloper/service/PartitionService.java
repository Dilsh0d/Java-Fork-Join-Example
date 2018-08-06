package com.gmbh.itdeveloper.service;

public interface PartitionService {

    void partitionBigTable(int index, int offset);

    void partitionBigTableDrop();
}
