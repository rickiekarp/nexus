package com.rkarp.adminserver.service;

import java.util.List;

import com.rkarp.adminserver.dao.UserDAO;
import com.rkarp.adminserver.exception.DuplicateUserException;
import com.rkarp.adminserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rkarp.adminserver.exception.UserNotFoundException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private UserDAO userDAO;

	@Override
	public void addUser(User user) throws DuplicateUserException {
		userDAO.addUser(user);
	}

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        return userDAO.getUser(userId);
    }

	@Override
	public User getUser(String username) throws UserNotFoundException {
		return userDAO.getUser(username);
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException, DuplicateUserException {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
		userDAO.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
