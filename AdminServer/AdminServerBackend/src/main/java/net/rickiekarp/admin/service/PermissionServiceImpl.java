//package net.rickiekarp.admin.service;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import net.rickiekarp.admin.dao.PermissionDAO;
//import net.rickiekarp.admin.exception.DuplicatePermissionException;
//import net.rickiekarp.admin.exception.PermissionNotFoundException;
//import net.rickiekarp.admin.model.Permission;
//
//@Service
//@Transactional
//public class PermissionServiceImpl implements PermissionService {
//    static Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
//
//    @Autowired
//    private PermissionDAO permissionDAO;
//
//    @Override
//    public void addPermission(Permission permission) throws DuplicatePermissionException {
//        permissionDAO.addPermission(permission);
//    }
//
//    @Override
//    public Permission getPermission(int id) throws PermissionNotFoundException {
//        return permissionDAO.getPermission(id);
//    }
//
//    @Override
//    public Permission getPermission(String permissionname) throws PermissionNotFoundException {
//        return permissionDAO.getPermission(permissionname);
//    }
//
//    @Override
//    public void updatePermission(Permission permission) throws PermissionNotFoundException, DuplicatePermissionException {
//        permissionDAO.updatePermission(permission);
//    }
//
//    @Override
//    public void deletePermission(int id) throws PermissionNotFoundException {
//        permissionDAO.deletePermission(id);
//    }
//
//    @Override
//    public List<Permission> getPermissions() {
//        return permissionDAO.getPermissions();
//    }
//}
