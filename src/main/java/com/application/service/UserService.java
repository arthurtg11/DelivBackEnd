package com.application.service;

import com.application.config.ListRequest;
import com.application.domain.TabUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<TabUser> findByIdTabUser(Long usnCod) throws Exception {
        return ResponseEntity.ok().body(TabUser.findById(usnCod));
    }

    @Transactional
    public ResponseEntity<List<TabUser>> getListTabUser(@Valid ListRequest listRequest) throws Exception {
        return ResponseEntity.ok().body(TabUser.findList(listRequest));
    }

    @Transactional
    public ResponseEntity<TabUser> insertTabUser(@Valid TabUser tabUser) throws Exception {
        tabUser.setUsnDesPassword(passwordEncoder.encode(tabUser.getUsnDesPassword()));
        return ResponseEntity.ok(tabUser.insert());
    }

    @Transactional
    public ResponseEntity<TabUser> updateTabUser(@Valid TabUser tabUser) throws Exception {
        tabUser.setUsnDesPassword(passwordEncoder.encode(tabUser.getUsnDesPassword()));
        return ResponseEntity.ok(tabUser.update());
    }

    public ResponseEntity cancelTabUser(Long usnCod) throws Exception {
        TabUser tabUser = TabUser.findById(usnCod);
        tabUser.update();
        return ResponseEntity.ok().build();
    }
}