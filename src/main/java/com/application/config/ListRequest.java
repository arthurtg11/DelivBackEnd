package com.application.config;

import com.application.config.exceptions.FbrValidatorException;
import com.application.config.validator.ValidatorLong;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListRequest {
    Map<String, Object> filterList = new HashMap<>();

    @NotEmpty
    List<Map<String, EOrderSql>> orderList = new ArrayList<>();

    @ValidatorLong(precision = 2)
    Long pageSize = 20L;

    Long pageNumber = 1L;

    public static ListRequest createListRequest() {
        return new ListRequest();
    }

    public ListRequest addFilter(String key, Object value) {
        filterList.put(key, value);
        return this;
    }

    public ListRequest addOrder(String key, String value) {
        orderList.add(Map.of(key, EOrderSql.valueOf(value)));
        return this;
    }

    public ListRequest addOrder(String key, EOrderSql value) {
        orderList.add(Map.of(key, value));
        return this;
    }

    public void isRequired(String... fields) throws Exception {
        for (String field : fields) {
            var value = filterList.get(field);

            if (value == null)
                throw new FbrValidatorException("Field " + field + " is Required!");
        }
    }

    public Map<String, Object> getFilterList() {
        return filterList;
    }

    public List<Map<String, EOrderSql>> getOrderList() {
        return orderList;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public Long getPageNumber() {
        return pageNumber;
    }
}
