package com.gmbh.itdeveloper.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "position_config",indexes = {@Index(columnList = "pOffset", name = "pOffset_index")})
public class PositionConfigEntity implements Serializable{

    @Id
    @Column(name = "pOffset",nullable = false)
    public Integer pOffset;

    public Integer getpOffset() {
        return pOffset;
    }

    public void setpOffset(Integer pOffset) {
        this.pOffset = pOffset;
    }
}
