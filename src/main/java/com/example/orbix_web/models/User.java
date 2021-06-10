/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

/**
 * @author GODFREY
 *
 */
@Entity
@Component
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends Audit<String> {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	/*
	@ManyToMany(cascade = CascadeType.ALL,mappedBy = "users")
    private Set<Role> roles;
	*/
	
	@NotBlank
	@Column(unique = true)
    private String username;
	@NotBlank
	@Column(unique = true)
    private String payRollNo;
    private String firstName;
    private String secondName;
    private String lastName;
    private String password;
    private String fingerPrint;
    private String accessToken;
    private String status;
    
    @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER,  optional = true)
    @JoinColumn(name = "role_id", nullable = true , updatable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
	@Autowired
	@Embedded
    private Role role;

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the payRollNo
	 */
	public String getPayRollNo() {
		return payRollNo;
	}

	/**
	 * @param payRollNo the payRollNo to set
	 */
	public void setPayRollNo(String payRollNo) {
		this.payRollNo = payRollNo;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the fingerPrint
	 */
	public String getFingerPrint() {
		return fingerPrint;
	}

	/**
	 * @param fingerPrint the fingerPrint to set
	 */
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
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
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
