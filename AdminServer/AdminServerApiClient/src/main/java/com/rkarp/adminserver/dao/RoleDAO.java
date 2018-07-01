package com.rkarp.adminserver.dao;

import java.util.List;

import com.rkarp.adminserver.exception.DuplicateRoleException;
import com.rkarp.adminserver.exception.RoleNotFoundException;
import com.rkarp.adminserver.model.Role;

public interface RoleDAO {

    public void addRole(Role role) throws DuplicateRoleException;

    public Role getRole(int id) throws RoleNotFoundException;

    public Role getRole(String roleName) throws RoleNotFoundException;

    public void updateRole(Role role) throws RoleNotFoundException, DuplicateRoleException;

    public void deleteRole(int id) throws RoleNotFoundException;

    public List<Role> getRoles();

}
