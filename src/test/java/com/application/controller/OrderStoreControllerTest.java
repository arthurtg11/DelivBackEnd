package com.application.controller;

import com.application.config.ListRequest;
import com.application.controller.config.FbrControllerTest;
import com.application.domain.enums.EVldEstagioPedido;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderStoreControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/orderStore";

    //Somente enquanto não possui o controller do cliente inserir o pedido.
    private TabOrder insertTabOrder() throws Exception {
        TabOrder order = new TabOrder();
        order.setStrCod(1L);
        order.setUsnCod(6L);
        order.setOrdDtaData(new Date());
        order.setOrdMnyValue(new BigDecimal("120.0"));
        order.setOrdVldEstagio(EVldEstagioPedido.PENDENTE.getValue());
        order.setOrdVldPag(1L);
        order.setAdrCod(1L);
        order.insert();

        var tabProd = new TabProd();
        tabProd.setStrCod(1L);
        tabProd.setPrdDesName("Laranja");
        tabProd.setPrdDesObs("Laranjinha");
        tabProd.setPrdMnyValue(new BigDecimal("120.98"));
        tabProd.setPrdVldStatus(1L);

        TabProd tabProd1 = resultConvertClass(
                postRequest("/api/produto", tabProd)
                        .andDo(print())
                        .andExpect(status().isOk()), TabProd.class);

        TabOrderItem orderItem = new TabOrderItem();
        orderItem.setStrCod(1L);
        orderItem.setOrdCod(order.getOrdCod());
        orderItem.setPrdCod(tabProd1.getPrdCod());
        orderItem.setItmDesObs("Sem Cebola");
        orderItem.setItmMnyValue(new BigDecimal("120"));
        orderItem.setItmQtdQuantidade(1L);
        orderItem.insert();


        return order;
    }


    @Test
    @Rollback
    public void getList() throws Exception {
        createAuth();
        insertTabOrder();

        ListRequest listRequest = new ListRequest();

        postRequest(BASE + "/list", listRequest).andDo(print()).andExpect(status() //
                .isOk());
    }

    @Test
    @Rollback
    public void findByIdOrder() throws Exception {
        createAuth();
        TabOrder tabOrder = insertTabOrder();

        ListRequest listRequest = new ListRequest();

        postRequest(BASE + "/" + tabOrder.getOrdCod(), listRequest).andDo(print()).andExpect(status() //
                .isOk());
    }


    @Test
    @Rollback
    public void atualizaStatus() throws Exception {
        createAuth();
        var tabOrder = insertTabOrder();

        putRequest(BASE + "/status/aceito/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());

        putRequest(BASE + "/status/preparo/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());

        putRequest(BASE + "/status/entrega/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());

        putRequest(BASE + "/status/concluir/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());
    }

    @Test
    @Rollback
    public void recusaPedido() throws Exception {
        createAuth();
        var tabOrder = insertTabOrder();

        putRequest(BASE + "/status/recusar/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());
    }

    @Test
    @Rollback
    public void atualizaStatusError() throws Exception {
        createAuth();
        var tabOrder = insertTabOrder();

        putRequest(BASE + "/status/preparo/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isOk());

        putRequest(BASE + "/status/aceito/" + tabOrder.getOrdCod(), null) //
                .andDo(print()) //
                .andExpect(status() //
                        .isBadRequest());
    }

}














