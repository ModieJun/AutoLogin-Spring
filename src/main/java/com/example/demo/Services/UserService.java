package com.example.demo.Services;

import com.example.demo.DB.UserRepository;
import com.example.demo.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    private  final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
        User init = new User("jj", this.encoder.encode("123"));
//        User init = new User("jj", "123");
        this.repository.save(init);
    }

    public boolean addNewUser(User user) {
        User u = repository.findByUsername(user.getUsername());
        if (u != null) {
            return false;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }

    public User findByUsername(String name){
        return this.repository.findByUsername(name);
    }
}
