//package net.rickiekarp.admin.service;
//
//import java.util.List;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import net.rickiekarp.admin.exception.DuplicateUserException;
//import net.rickiekarp.admin.exception.UserNotFoundException;
//import net.rickiekarp.admin.model.User;
//
//public interface UserService extends UserDetailsService {
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
//}
