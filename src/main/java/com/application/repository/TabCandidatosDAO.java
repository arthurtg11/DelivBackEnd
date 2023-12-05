package com.application.repository;

import com.application.config.ListRequest;
import com.application.domain.TabCandidatos;
import com.application.repository.config.FbrDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TabCandidatosDAO extends FbrDAO {

    protected TabCandidatosDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    public static TabCandidatos findById(Long canCod) throws Exception {
        return FbrDAO.executeFindById(TabCandidatos.class, new ListRequest().addFilter("canCod", canCod));
    }

    public static Long getCount(ListRequest listRequest) {
        return FbrDAO.executeGetCount(TabCandidatos.class, listRequest);
    }

    public static List<TabCandidatos> findList(ListRequest listRequest) {
        return FbrDAO.executeQueryList(TabCandidatos.class, listRequest);
    }


    public static void update(TabCandidatos tabCandidatos) throws Exception {
        String sql = "UPDATE TAB_CANDIDATOS\n" +
                "   SET CAN_NUM_VOTOS = :CAN_NUM_VOTOS\n" +
                " WHERE CAN_COD = :CAN_COD";
        FbrDAO.executeUpdate(sql, tabCandidatos);
    }
}






















