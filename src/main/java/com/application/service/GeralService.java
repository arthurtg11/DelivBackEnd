package com.application.service;

import com.application.config.exceptions.TooManyRollsException;
import com.application.domain.TabUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeralService implements UserDetailsService {

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String gerDesUsername) {
        TabUser tabUser = null;
        try {
            tabUser = TabUser.findByUsnDesUsername(gerDesUsername);
        } catch (TooManyRollsException e) {
            throw new RuntimeException(e);
        }
        if (tabUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database {}", gerDesUsername);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(tabUser.getUsnDesUsername(), tabUser.getUsnDesPassword(), authorities);
    }
}
