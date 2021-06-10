/**
 * 
 */
package com.example.orbix_web.models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;

/**
 * @author GODFREY
 *
 */
@Entity
@Component
@Table(name = "days")
@EntityListeners(AuditingEntityListener.class)
public class Day extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate systemDate;
	//@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startedAt;
	//@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closedAt;
    private String status;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the systemDate
	 */
	public LocalDate getSystemDate() {
		return systemDate;
	}
	/**
	 * @param systemDate the systemDate to set
	 */
	public void setSystemDate(LocalDate systemDate) {
		this.systemDate = systemDate;
	}
	/**
	 * @return the startedAt
	 */
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	/**
	 * @param startedAt the startedAt to set
	 */
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}
	/**
	 * @return the closedAt
	 */
	public LocalDateTime getClosedAt() {
		return closedAt;
	}
	/**
	 * @param closedAt the closedAt to set
	 */
	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}
	
	
}
