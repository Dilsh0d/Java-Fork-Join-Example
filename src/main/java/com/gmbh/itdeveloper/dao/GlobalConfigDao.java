package com.gmbh.itdeveloper.dao;

import com.gmbh.itdeveloper.entities.GlobalConfigEntity;
import com.gmbh.itdeveloper.entities.StatusEnum;

public interface GlobalConfigDao {
    void changeConfigFile(StatusEnum satus);

    GlobalConfigEntity getConfigEntity();
}
