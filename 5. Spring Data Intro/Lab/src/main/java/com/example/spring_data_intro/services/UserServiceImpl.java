package com.example.spring_data_intro.services;

import com.example.spring_data_intro.models.User;
import com.example.spring_data_intro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerUser(User user) {
        boolean exists = userRepository.exists(user);
        if(!exists) {
            this.userRepository.save(user);
        }
    }
}
