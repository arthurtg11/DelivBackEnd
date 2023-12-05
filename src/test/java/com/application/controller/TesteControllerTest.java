package com.application.controller;

import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TesteControllerTest extends FbrControllerTest {

    @Test
    @Rollback
    public void findById() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("11"));
        createAuth();
    }
}














