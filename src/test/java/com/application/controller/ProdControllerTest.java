package com.application.controller;

import com.application.config.EOrderSql;
import com.application.config.ListRequest;
import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProdControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/produto";
    private static final String BASE_ADIC = "/api/produto/adicional";
    private static final String BASE_GROUP = "/api/produto/group";

    private TabProd insertTabProd() throws Exception {
        var tabProd = new TabProd();
        tabProd.setPrdDesName("Laranja");
        tabProd.setPrdDesObs("Laranjinha");
        tabProd.setPrdMnyValue(new BigDecimal("120.98"));
        tabProd.setPrdVldStatus(1L);

        return resultConvertClass(
                postRequest(BASE, tabProd)
                        .andDo(print()) //
                        .andExpect(status().isOk()), TabProd.class);
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        createAuth();
        ListRequest listRequest = new ListRequest();
        listRequest.addFilter("strCod", 1L);
        listRequest.addOrder("prdCod", "DESC");

        postRequest(BASE + "/list", listRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void insert() throws Exception {
        createAuth();
        insertTabProd();
    }

    @Test
    @Rollback
    public void update() throws Exception {
        createAuth();
        TabProd tabProd = insertTabProd();

        tabProd.setPrdDesName("Laranja Lima");

        putRequest(BASE, tabProd).
                andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void delete() throws Exception {
        createAuth();
        var tabProd = insertTabProd();
        deleteRequest(BASE + "/" + tabProd.getPrdCod())
                .andDo(print())
                .andExpect(status().isOk());
    }

    public TabProdAdic insertAndReturnTabProdAdic() throws Exception {
        var tabProd = insertTabProd();
        var tabProdAdic = new TabProdAdic();
        tabProdAdic.setPraDesName("Limao Adicional");
        tabProdAdic.setPrdCod(tabProd.getPrdCod());
        tabProdAdic.setPraMnyValue(new BigDecimal("120.98"));
        tabProdAdic.setPraVldStatus(1L);

        return resultConvertClass(
                postRequest(BASE_ADIC, tabProdAdic)
                        .andDo(print())
                        .andExpect(status().isOk()), TabProdAdic.class);
    }

    @Test
    @Rollback
    public void insertTabProdAdic() throws Exception {
        createAuth();
        insertAndReturnTabProdAdic();
    }

    @Test
    @Rollback
    public void updateTabAdic() throws Exception {
        createAuth();
        var tabProdAdic = insertAndReturnTabProdAdic();

        tabProdAdic.setPraDesName("Limaozinho Adicional");

        putRequest(BASE_ADIC, tabProdAdic)
                .andDo(print()) //
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void deleteTabAdic() throws Exception {
        createAuth();
        var tabProdAdic = insertAndReturnTabProdAdic();
        deleteRequest(BASE_ADIC + "/" + tabProdAdic.getPrdCod() + "/" + tabProdAdic.getPraCod())
                .andDo(print())
                .andExpect(status().isOk());

        getRequest(BASE_ADIC + "/" + tabProdAdic.getPrdCod() + "/" + tabProdAdic.getPraCod())
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    /**
     * TAB_GROUP
     **/
    private TabGroup insertAndReturnTabGroup() throws Exception {
        var tabGroup = new TabGroup();
        tabGroup.setGrpDesName("Pizzas");

        return resultConvertClass(
                postRequest(BASE_GROUP, tabGroup)
                        .andDo(print()) //
                        .andExpect(status().isOk()), TabGroup.class);
    }

    @Test
    @Rollback
    public void findByIdTabGroup() throws Exception {
        createAuth();
        insertAndReturnTabGroup();
    }

    @Test
    @Rollback
    public void getListTabGroup() throws Exception {
        createAuth();
        ListRequest listRequest = new ListRequest();
        listRequest.addFilter("strCod", 1L);
        listRequest.setPageSize(10L);
        listRequest.setPageNumber(1L);
        listRequest.addOrder("grpCod", EOrderSql.DESC);

        postRequest(BASE + "/group/list", listRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void insertTabGroup() throws Exception {
        createAuth();
        var tabGroup = insertAndReturnTabGroup();
        getRequest(BASE_GROUP + "/" + tabGroup.getGrpCod());
    }

    @Test
    @Rollback
    public void updateTabGroup() throws Exception {
        createAuth();
        var tabGroup = insertAndReturnTabGroup();
        tabGroup.setGrpDesName("Doces");
        putRequest(BASE_GROUP, tabGroup);
    }

    @Test
    @Rollback
    public void deleteTabGroup() throws Exception {
        createAuth();
        var tabGroup = insertAndReturnTabGroup();
        deleteRequest(BASE_GROUP + "/" + tabGroup.getGrpCod())
                .andDo(print())
                .andExpect(status().isOk());

        getRequest(BASE_GROUP + "/" + tabGroup.getGrpCod())
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}














