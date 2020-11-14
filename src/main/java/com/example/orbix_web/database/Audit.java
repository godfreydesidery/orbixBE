package com.example.orbix_web.database;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Audit<T> {
    @CreatedBy
    @Column(name="created_by")
    protected T createdBy;
    @LastModifiedBy
    @Column(name="modified_by")
    protected T modifiedBy;
    @CreatedDate
    @Column(name="created_date")
    protected LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name="modified_date")
    protected LocalDateTime modifiedDate;
    public T getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(T createdBy) {
        this.createdBy = createdBy;
    }
    public T getModifiedBy() {
        return modifiedBy;
    }
    public void setModifiedBy(T modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }    
}
