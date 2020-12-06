/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.persistence.JoinColumn;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import com.example.orbix_web.database.Audit;

/**
 * @author GODFREY
 *
 */
@Component
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role extends Audit<String>{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	/*
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = {
        @JoinColumn(name = "role_id", nullable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "user_id",
            nullable = false, updatable = false) 
        })
    private Set<User> users;
	*/
	@NotBlank
    private String roleName;
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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
}
