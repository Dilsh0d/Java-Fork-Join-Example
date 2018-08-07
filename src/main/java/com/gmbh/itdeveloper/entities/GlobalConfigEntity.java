package com.gmbh.itdeveloper.entities;

import javax.persistence.*;

@Entity
@Table(name = "aenaflight_config")
public class GlobalConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private Boolean isCreatedNewColumn = Boolean.FALSE;

    @Basic
    @Enumerated(EnumType.STRING)
    private StatusEnum status;//IN_PROGRESS, DONE

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
