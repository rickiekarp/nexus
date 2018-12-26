//package net.rickiekarp.admin.service;
//
//import java.util.List;
//
//import net.rickiekarp.admin.exception.DuplicatePermissionException;
//import net.rickiekarp.admin.exception.PermissionNotFoundException;
//import net.rickiekarp.admin.model.Permission;
//
//public interface PermissionService {
//
//    public void addPermission(Permission permission) throws DuplicatePermissionException;
//
//    public Permission getPermission(int id) throws PermissionNotFoundException;
//
//    public Permission getPermission(String permissionname) throws PermissionNotFoundException;
//
//    public void updatePermission(Permission permission) throws PermissionNotFoundException, DuplicatePermissionException;
//
//    public void deletePermission(int id) throws PermissionNotFoundException;
//
//    public List<Permission> getPermissions();
//
//}
