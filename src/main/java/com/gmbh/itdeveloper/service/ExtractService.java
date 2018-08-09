package com.gmbh.itdeveloper.service;

import com.gmbh.itdeveloper.dto.ConfigDto;

public interface ExtractService {
    void beginForkJoinProcess();

    void addNewColumnAndIndexing();

    void vacuumBigTable();

    ConfigDto getAenaflightConfig();

    void losingOffsetsANewIndexing();
}
