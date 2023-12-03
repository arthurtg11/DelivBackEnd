package com.application.controller;

import com.application.controller.config.FbrControllerTest;
import com.application.domain.dto.*;
import com.application.domain.enums.dto.EVldTipoPagamento;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderClientControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/client/order";


    private TabOrderClientDTO getDtoModel() {
        TabOrderClientDTO dto = new TabOrderClientDTO();
        dto.setUsnDesName("Arthur Turini Gambate");
        dto.setOrdVldPag(EVldTipoPagamento.DINHEIRO.getValue());
        dto.setUsnDesPhone("28999378281");
        dto.setUsnDesUsername("arthurtg2023@gmail.com");
        TabAddressDTO address = new TabAddressDTO();
        address.setAdrDesBairro("Jose elias");
        address.setAdrDesName("Teste");
        address.setAdrDesCidade("Alegre");
        address.setAdrDesEstado("ES");
        address.setAdrDesNumero("103");
        address.setAdrDesRua("Vila Viana");
        address.setAdrDesCep("29500");
        dto.setTabAddress(address);

        var dtoProdutos = new TabProdDTO();
        // Produto
        TabProdOrderDTO tabProdutos = new TabProdOrderDTO();
        tabProdutos.setPrdCod(1L);
        tabProdutos.setItmDesObs("Sem cebola");
        tabProdutos.setQtdProduto(1L);
        dtoProdutos.setTabProd(tabProdutos);
        // Adicional
        TabProdAdicOrderDTO tabAdicional = new TabProdAdicOrderDTO();
        tabAdicional.setPrdCod(1L);
        tabAdicional.setQtdProdutoAdic(1L);
        tabAdicional.setPraCod(1L);
        dtoProdutos.setTabProdAdic(List.of(tabAdicional));
        //

        dto.setTabProdDTO(List.of(dtoProdutos));
        return dto;
    }

    @Test
    @Rollback
    public void insertWithoutAuth() throws Exception {
        //createAuth();
        var dtoModel = getDtoModel();
        dtoModel.setStrCod(1L);

        postRequest(BASE + "/pedido", dtoModel).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void insertWithAuth() throws Exception {
        createAuth();
        var dtoModel = getDtoModel();

        postRequest(BASE + "/pedido", dtoModel).andDo(print()).andExpect(status().isOk());
    }
}














