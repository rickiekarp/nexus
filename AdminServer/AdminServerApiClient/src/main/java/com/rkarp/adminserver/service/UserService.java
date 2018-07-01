package com.rkarp.adminserver.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.rkarp.adminserver.exception.DuplicateUserException;
import com.rkarp.adminserver.exception.UserNotFoundException;
import com.rkarp.adminserver.model.User;

public interface UserService extends UserDetailsService {

    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;

    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException, DuplicateUserException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();
}
