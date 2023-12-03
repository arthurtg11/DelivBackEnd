package com.application.controller;

import com.application.config.EOrderSql;
import com.application.config.ListRequest;
import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class TimesControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/times";

    /**
     * TAB_STORE_TIMES
     **/
    public TabStoreTimes findByIdAndReturn() throws Exception {
        TabStoreTimes tabStoreTimes = insertAndReturn();
        return resultConvertClass(
                getRequest(BASE + "/" + tabStoreTimes.getStiCod())
                        .andDo(print())
                        .andExpect(status().isOk()), TabStoreTimes.class);
    }

    @Test
    @Rollback
    public void findById() throws Exception {
        createAuth();
        findByIdAndReturn();
    }

    @Test
    @Rollback
    public void getList() throws Exception {
        createAuth();
        TabStoreTimes tabStoreTimes = insertAndReturn();

        ListRequest listRequest = new ListRequest();
        listRequest.addFilter("strCod", tabStoreTimes.getStrCod());
        listRequest.addFilter("stiCod", tabStoreTimes.getStiCod());

        listRequest.setPageNumber(1L);
        listRequest.setPageSize(10L);
        listRequest.addOrder("stiCod", EOrderSql.DESC);

        postRequest(BASE + "/list", listRequest)
                .andDo(print())
                .andExpect(status().isOk());
    }

    public TabStoreTimes insertAndReturn() throws Exception {
        TabStoreTimes tabStoreTimes = new TabStoreTimes();
        tabStoreTimes.setStiDtaBegin(LocalTime.parse("07:05"));
        tabStoreTimes.setStiDtaEnd(LocalTime.parse("12:25"));
        tabStoreTimes.setStiVldDays(EVldDays.MONDAY.getValue());

        return resultConvertClass(
                postRequest(BASE, tabStoreTimes)
                        .andDo(print())
                        .andExpect(status().isOk()), TabStoreTimes.class);
    }

    @Test
    @Rollback
    public void insert() throws Exception {
        createAuth();
        insertAndReturn();
    }

    @Test
    @Rollback
    public void update() throws Exception {
        createAuth();
        TabStoreTimes tabStoreTimes = insertAndReturn();
        tabStoreTimes.setStiDtaEnd(LocalTime.parse("12:00:00"));

        putRequest(BASE, tabStoreTimes)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Rollback
    public void delete() throws Exception {
        createAuth();
        TabStoreTimes tabStoreTimes = insertAndReturn();

        deleteRequest(BASE + "/" + tabStoreTimes.getStiCod())
                .andDo(print())
                .andExpect(status().isOk());
    }
}














