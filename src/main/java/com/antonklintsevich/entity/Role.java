package com.antonklintsevich.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Role")
@Table(name = "role")
public class Role extends AbstractEntity {
    @Id
    @Column(name = "role_id")
    @GeneratedValue
    private Long id;
    @Column(name = "role_name")
    private String rolename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
