package com.development.taskmgmt_pro.service;

import com.development.taskmgmt_pro.model.User;
import com.development.taskmgmt_pro.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        var fetchUser = userRepository.findByEmailId(emailId)
                .orElseThrow(()-> new UsernameNotFoundException("User not found "+emailId));
        return new org.springframework.security.core.userdetails.User(
                fetchUser.getEmailId(),
                fetchUser.getPassword(),
                fetchUser.getRoles().stream().map(SimpleGrantedAuthority::new).toList());
    }
}