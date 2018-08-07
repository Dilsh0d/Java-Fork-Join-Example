package com.gmbh.itdeveloper.dto;

import javax.persistence.Basic;

public class ConfigDto {
    private Boolean isCreatedNewColumn = Boolean.FALSE;
    private String status;//InProgress, DONE

    public Boolean getCreatedNewColumn() {
        return isCreatedNewColumn;
    }

    public void setCreatedNewColumn(Boolean createdNewColumn) {
        isCreatedNewColumn = createdNewColumn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
