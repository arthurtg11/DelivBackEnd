package com.application.repository;

import com.application.config.ListRequest;
import com.application.domain.TabUrna;
import com.application.repository.config.FbrDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TabUrnaDAO extends FbrDAO {

    protected TabUrnaDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    public static TabUrna findById(Long urnCod) throws Exception {
        return FbrDAO.executeFindById(TabUrna.class, new ListRequest().addFilter("urnCod", urnCod));
    }

    public static void update(TabUrna tabUrna) throws Exception {
        String sql = "UPDATE TAB_URNA\n" +
                "   SET URN_VLD_ATIVA = :URN_VLD_ATIVA\n" +
                " WHERE URN_COD = :URN_COD";
        FbrDAO.executeUpdate(sql, tabUrna);
    }
}






















