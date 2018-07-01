package com.rkarp.adminserver.dao;

import java.util.List;

import com.rkarp.adminserver.exception.DuplicateUserException;
import com.rkarp.adminserver.exception.UserNotFoundException;
import com.rkarp.adminserver.model.User;

public interface UserDAO {

    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;
    
    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException, DuplicateUserException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();

}
