package com.application.controller;

import com.application.config.ListRequest;
import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class EmplyoeeControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/employee";


    @Test
    @Rollback
    public void getList() throws Exception {
        createAuth();

        ListRequest listRequest = new ListRequest();
        listRequest.addFilter("strCod", 1L);
        listRequest.addOrder("strCod", "DESC");

        postRequest(BASE, listRequest);
    }

    @Test
    @Rollback
    public void insert() throws Exception {
        createAuth();

        var tabStoreEmployee = new TabStoreEmployee();
        tabStoreEmployee.setStrCod(1L);
        tabStoreEmployee.setUsnDesName("Arthur Testes23");
        tabStoreEmployee.setUsnDesUsername("arthurteste3@gmail.com");
        tabStoreEmployee.setSteVldRole(3L);
        tabStoreEmployee.setUsnVldSituacao(1L);

        postRequest(BASE, tabStoreEmployee)
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void update() throws Exception {
        createAuth();

        var tabStoreEmployee = new TabStoreEmployee();
        tabStoreEmployee.setStrCod(1L);
        tabStoreEmployee.setUsnCod(3L);
        tabStoreEmployee.setSteVldRole(2L);
        putRequest(BASE, tabStoreEmployee);
    }

    @Test
    @Rollback
    public void delete() throws Exception {
        createAuth();
        deleteRequest(BASE + 3L);
    }

}














