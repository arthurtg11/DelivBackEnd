package com.application.controller;

import com.application.config.EOrderSql;
import com.application.config.ListRequest;
import com.application.controller.config.FbrControllerTest;
import com.application.domain.TabUser;
import com.application.domain.TabUser.EVldCliFuncionario;
import com.application.domain.enums.EVldSituacao;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/user";

    /**
     * TAB_USER
     **/
    public TabUser findByIdAndReturn() throws Exception {
        return resultConvertClass(
                getRequest(BASE + "/1")
                        .andExpect(status().isOk()), TabUser.class);
    }

    @Test
    @Rollback
    public void findById() throws Exception {
        findByIdAndReturn();
    }

    public TabUser insertAndReturn() throws Exception {
        TabUser tabUser = new TabUser();
        tabUser.setUsnDesName("Lucas S");
        tabUser.setUsnDesPassword("1234A");
        tabUser.setUsnDesUsername("lucasS@gmail.com");
        tabUser.setUsnVldClifun(EVldCliFuncionario.CLIENTE.getValue());
        tabUser.setUsnVldSituacao(EVldSituacao.ATIVO.getValue());
        tabUser.setUsnDesPhone("(27)9 9996-3212");

        return resultConvertClass(
                postRequest(BASE, tabUser)
                        .andDo(print())
                        .andExpect(status().isOk()), TabUser.class);
    }

    @Test
    @Rollback
    public void insert() throws Exception {
        insertAndReturn();
    }

    @Test
    @Rollback
    public void update() throws Exception {
        TabUser tabUser = findByIdAndReturn();
        tabUser.setUsnDesName("Lucas");
        tabUser.setUsnDesPassword("1234");
        tabUser.setUsnDesUsername("lucas03@gmail.com");
        tabUser.setUsnDesPhone("27 995742020");

        putRequest(BASE, tabUser)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancel() throws Exception {
        getMvc().perform(MockMvcRequestBuilders
                        .put(BASE + "/cancel/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancelError() throws Exception {
        putRequest(BASE + "/cancel/1")
                .andDo(print())
                .andExpect(status().isOk());

        putRequest(BASE + "/cancel/1")
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    /**
     * TAB_ADDRESS
     **/
    public TabAddress findByIdAndReturnTabAddress() throws Exception {
        return resultConvertClass(
                getRequest(BASE + "/address/1")
                        .andDo(print())
                        .andExpect(status().isOk()), TabAddress.class);
    }

    @Test
    @Rollback
    public void findByIdTabAddress() throws Exception {
        findByIdAndReturnTabAddress();
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        ListRequest listRequest = new ListRequest();
        listRequest.addFilter("adrCod#IN", List.of(1, 2));

        listRequest.setPageNumber(1L);
        listRequest.setPageSize(2L);
        listRequest.addOrder("adrCod", EOrderSql.DESC);

        postRequest(BASE + "/address/list", listRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void insertTabAddress() throws Exception {
        Long usnCod = insertAndReturn().getUsnCod();

        TabAddress tabAddress = new TabAddress();
        tabAddress.setUsnCod(usnCod);
        tabAddress.setAdrDesName("Trabalho");
        tabAddress.setAdrDesRua("Rod. Gov. José Henrique Sette");
        tabAddress.setAdrDesNumero("170");
        tabAddress.setAdrDesObs("Ao lado terminal itaciba");
        tabAddress.setAdrDesBairro("Alto Lage");
        tabAddress.setAdrDesCidade("Cariacica");
        tabAddress.setAdrDesEstado("ES");
        tabAddress.setAdrDesCep("29150410");
        tabAddress.setAdrVldSituacao(EVldSituacao.ATIVO.getValue());

        postRequest(BASE + "/address", tabAddress)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void updateTabAddress() throws Exception {
        TabAddress tabAddress = findByIdAndReturnTabAddress();
        tabAddress.setAdrDesName("Supermercado");

        putRequest(BASE + "/address", tabAddress)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancelTabAddress() throws Exception {
        putRequest(BASE + "/address/cancel/1")
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancelErrorTabAddress() throws Exception {
        putRequest(BASE + "/address/cancel/1")
                .andDo(print())
                .andExpect(status().isOk());

        putRequest(BASE + "/address/cancel/1")
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}














