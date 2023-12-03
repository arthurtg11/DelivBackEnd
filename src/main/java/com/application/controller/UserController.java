package com.application.controller;

import com.application.config.ListRequest;
import com.application.domain.TabUser;
import com.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    /**
     * TAB_USER
     **/
    @GetMapping("/{usnCod}")
    public ResponseEntity<TabUser> findByIdTabUser(@PathVariable @NotNull Long usnCod) throws Exception {
        return userService.findByIdTabUser(usnCod);
    }

    @PostMapping
    public ResponseEntity<TabUser> insertTabUser(@RequestBody TabUser tabUser) throws Exception {
        return userService.insertTabUser(tabUser);
    }

    @PutMapping
    public ResponseEntity<TabUser> updateTabUser(@RequestBody TabUser tabUser) throws Exception {
        return userService.updateTabUser(tabUser);
    }

    @PutMapping("/cancel/{usnCod}")
    public ResponseEntity cancelTabUser(@PathVariable @NotNull Long usnCod) throws Exception {
        return userService.cancelTabUser(usnCod);
    }


}