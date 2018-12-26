//package net.rickiekarp.admin.dao;
//
//import java.util.List;
//
//import net.rickiekarp.admin.exception.DuplicateUserException;
//import net.rickiekarp.admin.exception.UserNotFoundException;
//import net.rickiekarp.admin.model.User;
//
//public interface UserDAO {
//
//    public void addUser(User user) throws DuplicateUserException;
//
//    public User getUser(int userId) throws UserNotFoundException;
//
//    public User getUser(String username) throws UserNotFoundException;
//
//    public void updateUser(User user) throws UserNotFoundException, DuplicateUserException;
//
//    public void deleteUser(int userId) throws UserNotFoundException;
//
//    public List<User> getUsers();
//
//}
