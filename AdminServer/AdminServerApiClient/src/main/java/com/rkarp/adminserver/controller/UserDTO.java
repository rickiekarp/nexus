package com.rkarp.adminserver.controller;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1631797874369735181L;
    static Logger logger = LoggerFactory.getLogger(UserDTO.class);
    
    private int id;
    
    @NotNull(message = "{error.user.username.null}")
    @NotEmpty(message = "{error.user.username.empty}")
    @Size(max = 50, message = "{error.user.username.max}")
    private String username;

    @NotNull(message = "{error.user.password.null}")
    @NotEmpty(message = "{error.user.password.empty}")
    @Size(max = 50, message = "{error.user.password.max}")
    private String password;
    
    private boolean enabled;
    private int roleId;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
    @Override
    public String toString() {
        return String.format("%s(id=%d, username=%s, password=%s, enabled=%b, roleID=%d)", 
                this.getClass().getSimpleName(), 
                this.getId(), 
                this.getUsername(), 
                this.getPassword(), 
                this.getEnabled(),
                this.getRoleId());
    }
}
