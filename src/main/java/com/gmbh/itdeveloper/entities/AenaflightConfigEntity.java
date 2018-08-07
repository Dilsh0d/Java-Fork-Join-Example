package com.gmbh.itdeveloper.entities;

import javax.persistence.*;

@Entity
@Table(name = "aenaflight_config")
public class AenaflightConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private Boolean isCreatedNewColumn = Boolean.FALSE;

    @Basic
    private String status;//IN_PROGRESS, DONE

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
