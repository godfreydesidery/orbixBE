/**
 * 
 */
package com.example.orbix_web.models;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author GODFREY
 *
 */
@Entity
@Component
@Table(name = "clases")
@EntityListeners(AuditingEntityListener.class)
public class Clas extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(unique = true)
    private String clasName;
	@NotBlank
	@Column(unique = true)
    private String clasCode;
	
	
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "department_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Department department;

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
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}
	/**
	 * @return the clasName
	 */
	public String getClasName() {
		return clasName;
	}
	/**
	 * @param clasName the clasName to set
	 */
	public void setClasName(String clasName) {
		this.clasName = clasName;
	}
	/**
	 * @return the clasCode
	 */
	public String getClasCode() {
		return clasCode;
	}
	/**
	 * @param clasCode the clasCode to set
	 */
	public void setClasCode(String clasCode) {
		this.clasCode = clasCode;
	}
}
