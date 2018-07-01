package com.rkarp.adminserver.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = -2707641174043923410L;
    static Logger logger = LoggerFactory.getLogger(PermissionDTO.class);
    
    private int id;

    @NotNull(message = "{error.permission.permissionname.null}")
    @NotEmpty(message = "{error.permission.permissionname.empty}")
    @Size(max = 50, message = "{permission.permissionname.role.max}")
    private String permissionname;
    
    private List<Integer> permRoles;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public List<Integer> getPermRoles() {
        return permRoles;
    }

    public void setPermRoles(List<Integer> permRoles) {
        this.permRoles = permRoles;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, permissionname='%s')", 
                this.getClass().getSimpleName(), 
                this.getId(), this.getPermissionname());
    }
}
