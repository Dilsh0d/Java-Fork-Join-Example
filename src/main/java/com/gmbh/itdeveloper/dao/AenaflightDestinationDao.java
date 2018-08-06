package com.gmbh.itdeveloper.dao;

import com.gmbh.itdeveloper.entities.AenaflightDestinationEntity;

import java.util.List;

public interface AenaflightDestinationDao {
    void batchUpdates(List<AenaflightDestinationEntity> insertedResult);
}
