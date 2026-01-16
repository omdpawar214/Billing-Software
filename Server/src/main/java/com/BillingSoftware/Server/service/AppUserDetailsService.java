package com.BillingSoftware.Server.service;

import com.BillingSoftware.Server.entity.UserEntity;
import com.BillingSoftware.Server.repositery.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{

    //repository requirement filled by constructor injection
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUserName(String email) throws UsernameNotFoundException {
         UserEntity  existingUser=  userRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException("User name not found !"));
        return new User(existingUser.getEmail(), existingUser.getPass(), Collections.singleton(new SimpleGrantedAuthority(existingUser.getRole())));
    }
}
