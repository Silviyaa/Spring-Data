package com.example.spring_data_intro;

import com.example.spring_data_intro.models.User;
import com.example.spring_data_intro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        User first = new User("Sisi",22);
        userService.registerUser(first);

        User second = new User("Sisi",22);
        userService.registerUser(second);

        User first1 = new User("qna",22);
        userService.registerUser(first);

        User second1 = new User("qna",22);
        userService.registerUser(second);
    }
}
