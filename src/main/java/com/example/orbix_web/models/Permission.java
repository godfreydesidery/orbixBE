/**
 * 
 */
package com.example.orbix_web.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
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
@Table(name = "permissions")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Audit<String> {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "roles_permissions", joinColumns = {
        @JoinColumn(name = "permission_id", nullable = false, updatable = false) },
        inverseJoinColumns = { @JoinColumn(name = "role_id",
            nullable = false, updatable = false) 
        })
    private Set<Role> roles;
	
	@NotBlank
    private String permissionName;
	@NotBlank
    private String status;
}
