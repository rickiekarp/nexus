package com.rkarp.adminserver.dao;

import java.util.List;

import com.rkarp.adminserver.exception.DuplicatePermissionException;
import com.rkarp.adminserver.exception.PermissionNotFoundException;
import com.rkarp.adminserver.model.Permission;

public interface PermissionDAO {

    public void addPermission(Permission permission) throws DuplicatePermissionException;

    public Permission getPermission(int id) throws PermissionNotFoundException;

    public Permission getPermission(String permissionName) throws PermissionNotFoundException;

    public void updatePermission(Permission permission) throws PermissionNotFoundException, DuplicatePermissionException;

    public void deletePermission(int id) throws PermissionNotFoundException;

    public List<Permission> getPermissions();

}
