package com.application.controller;

import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class StoreControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/store";

    public TabStore findByIdAndReturn() throws Exception {
        return resultConvertClass(
                getRequest(BASE)
                        .andDo(print())
                        .andExpect(status().isOk()), TabStore.class);
    }

    @Test
    @Rollback
    public void findById() throws Exception {
        createAuth();
        findByIdAndReturn();
    }

    @Test
    @Rollback
    public void updateStore() throws Exception {
        createAuth();
        var tabStore = findByIdAndReturn();
        tabStore.setStrDesName("Nova Empresa!!!");

        putRequest(BASE, tabStore)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void updateAddress() throws Exception {
        createAuth();
        var tabStore = findByIdAndReturn();
        tabStore.setAdrDesBairro("VILA VELHA");

        var storeDTO = new StoreDTO();
        storeDTO.setStrDesName(tabStore.getStrDesName());
        storeDTO.setStrDesFederal(tabStore.getStrDesFederal());
        storeDTO.setStrDesPhone(tabStore.getStrDesPhone());
        storeDTO.setStrVldPermRetlocal(tabStore.getStrVldPermRetlocal());
        storeDTO.setStrVldDescRetlocal(tabStore.getStrVldDescRetlocal());
        storeDTO.setStrVldDelivery(tabStore.getStrVldDelivery());
        storeDTO.setStrVldMinValue(tabStore.getStrVldMinValue());
        storeDTO.setStrMnyMinValue(tabStore.getStrMnyMinValue());
        storeDTO.setStrVldFreeDeliv(tabStore.getStrVldFreeDeliv());
        storeDTO.setStrMnyFreeDeliv(tabStore.getStrMnyFreeDeliv());
        storeDTO.setStrDesColor(tabStore.getStrDesColor());
        storeDTO.setStrDesMainMsg(tabStore.getStrDesMainMsg());

        storeDTO.setAdrDesRua(tabStore.getAdrDesRua());
        storeDTO.setAdrDesNumero(tabStore.getAdrDesNumero());
        storeDTO.setAdrDesEstado(tabStore.getAdrDesEstado());
        storeDTO.setAdrDesCidade(tabStore.getAdrDesCidade());
        storeDTO.setAdrDesBairro(tabStore.getAdrDesBairro());
        storeDTO.setAdrDesCep(tabStore.getAdrDesCep());
        storeDTO.setAdrDesObs(tabStore.getAdrDesObs());

        putRequest(BASE, storeDTO)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancel() throws Exception {
        createAuth();
        putRequest(BASE + "/cancel")
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void cancelError() throws Exception {
        createAuth();
        putRequest(BASE + "/cancel")
                .andDo(print())
                .andExpect(status().isOk());

        putRequest(BASE + "/cancel")
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}














