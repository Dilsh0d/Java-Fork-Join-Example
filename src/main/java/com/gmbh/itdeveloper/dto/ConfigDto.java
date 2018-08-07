package com.gmbh.itdeveloper.dto;

import com.gmbh.itdeveloper.entities.StatusEnum;

import javax.persistence.Basic;

public class ConfigDto {
    private Boolean isCreatedNewColumn = Boolean.FALSE;
    private StatusEnum status = StatusEnum.INPROGRESS;

    public Boolean getCreatedNewColumn() {
        return isCreatedNewColumn;
    }

    public void setCreatedNewColumn(Boolean createdNewColumn) {
        isCreatedNewColumn = createdNewColumn;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
