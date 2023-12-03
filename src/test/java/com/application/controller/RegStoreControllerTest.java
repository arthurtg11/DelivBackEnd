package com.application.controller;

import com.application.controller.config.FbrControllerTest;
import com.application.domain.TabUser;
import com.application.domain.enums.EVldAtivoCancelado;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RegStoreControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/regStore";

    @Test
    @Rollback
    public void insertStoreDTO() throws Exception {
        var storeDTO = new RegStoreDTO();

        TabUser tabUser = new TabUser();
        tabUser.setUsnDesName("teste");
        tabUser.setUsnDesPassword("1234");
        tabUser.setUsnDesUsername("joao@gmail.com");
        tabUser.setUsnDesPhone("28999378281");
        tabUser.setUsnVldSituacao(1L);
        storeDTO.setTabUser(tabUser);

        TabStore tabStore = new TabStore();
        tabStore.setStrDesName("TesteStore");
        tabStore.setStrDesFederal("18684557778");
        tabStore.setStrDesPhone("28999378281");
        tabStore.setStrVldPermRetlocal(0L);
        tabStore.setStrVldDescRetlocal(0L);
        tabStore.setStrVldDelivery(0L);
        tabStore.setStrVldMinValue(0L);
        tabStore.setStrMnyMinValue(BigDecimal.valueOf(10));
        tabStore.setStrVldFreeDeliv(0L);
        tabStore.setStrMnyFreeDeliv(new BigDecimal(0));
        tabStore.setStrDesColor("#000000");
        tabStore.setStrDesMainMsg("Bem Vindo");
        tabStore.setStrVldSituacao(EVldAtivoCancelado.ATIVO.getValue());
        storeDTO.setTabStore(tabStore);

        TabAddress tabAddress = new TabAddress();
        tabAddress.setAdrDesName("Casa");
        tabAddress.setAdrDesRua("Rua Carolina Pickler");
        tabAddress.setAdrDesNumero("11A");
        tabAddress.setAdrDesObs("Ao lado Igreja Assembleia");
        tabAddress.setAdrDesBairro("Alto Lage");
        tabAddress.setAdrDesCidade("Cariacica");
        tabAddress.setAdrDesEstado("ES");
        tabAddress.setAdrDesCep("29151266");
        tabAddress.setAdrVldSituacao(EVldAtivoCancelado.ATIVO.getValue());
        storeDTO.setTabAddress(tabAddress);

        postRequest(BASE, storeDTO)
                .andDo(print())
                .andExpect(status().isOk());
    }
}














