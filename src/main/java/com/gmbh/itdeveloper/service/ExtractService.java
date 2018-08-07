package com.gmbh.itdeveloper.service;

public interface ExtractService {
    void beginForkJoinProcess();

//    void partitionBigTable();
//
//    void partitionBigTableDrop();

    void addNewColumnAndIndexing();

    void vacuumBigTable();
}
