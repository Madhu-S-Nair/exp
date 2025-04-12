package com.exp.service;

import com.exp.dto.CustomUserDetails;
import com.exp.entity.Users;
import com.exp.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class customUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public customUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user =userRepository.findByUsername(username);
        if(user.isPresent()){
            return  new CustomUserDetails(user.get());
        }
        throw  new UsernameNotFoundException("user not found");


    }
}
