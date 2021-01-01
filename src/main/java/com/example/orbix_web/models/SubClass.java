/**
 * 
 */
package com.example.orbix_web.models;

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
@Table(name = "sub_classes")
@EntityListeners(AuditingEntityListener.class)
public class SubClass extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
    private String subClassName;
	@NotBlank
    private String subClassCode;
	
	
	
	@ManyToOne(targetEntity = Clas.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "class_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Clas clas;
	
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
	 * @return the subClassName
	 */
	public String getSubClassName() {
		return subClassName;
	}

	/**
	 * @param subClassName the subClassName to set
	 */
	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}

	/**
	 * @return the subClassCode
	 */
	public String getSubClassCode() {
		return subClassCode;
	}

	/**
	 * @param subClassCode the subClassCode to set
	 */
	public void setSubClassCode(String subClassCode) {
		this.subClassCode = subClassCode;
	}

	/**
	 * @return the clas
	 */
	public Clas getClas() {
		return clas;
	}

	/**
	 * @param clas the clas to set
	 */
	public void setClas(Clas clas) {
		this.clas = clas;
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

	
}
