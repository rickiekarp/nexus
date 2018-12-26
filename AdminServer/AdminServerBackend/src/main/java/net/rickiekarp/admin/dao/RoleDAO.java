//package net.rickiekarp.admin.dao;
//
//import net.rickiekarp.admin.exception.DuplicateRoleException;
//import net.rickiekarp.admin.exception.RoleNotFoundException;
//import net.rickiekarp.admin.model.Role;
//
//import java.util.List;
//
//public interface RoleDAO {
//
//    public void addRole(Role role) throws DuplicateRoleException;
//
//    public Role getRole(int id) throws RoleNotFoundException;
//
//    public Role getRole(String roleName) throws RoleNotFoundException;
//
//    public void updateRole(Role role) throws RoleNotFoundException, DuplicateRoleException;
//
//    public void deleteRole(int id) throws RoleNotFoundException;
//
//    public List<Role> getRoles();
//
//}
