package com.example.demo.Services;

import com.example.demo.DB.UserRepository;
import com.example.demo.Model.User;
import com.example.demo.Model.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= repository.findByUsername(s);

        if (user==null) {
            throw new UsernameNotFoundException("User " + s + "Not found");
        }
        return new UserPrinciple(user);
    }


}
