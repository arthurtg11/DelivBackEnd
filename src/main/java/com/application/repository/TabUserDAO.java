package com.application.repository;

import com.application.config.ListRequest;
import com.application.domain.TabUser;
import com.application.repository.config.FbrDAO;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TabUserDAO extends FbrDAO {

    protected TabUserDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    public static TabUser findById(Long usnCod) throws Exception {
        return FbrDAO.executeFindById(TabUser.class, new ListRequest().addFilter("usnCod", usnCod));
    }

    public static Long getCount(ListRequest listRequest) {
        return FbrDAO.executeGetCount(TabUser.class, listRequest);
    }

    public static List<TabUser> findList(ListRequest listRequest) {
        return FbrDAO.executeQueryList(TabUser.class, listRequest);
    }

    public static void update(TabUser tabUser) throws Exception {
        String sql = "UPDATE TAB_USER\n" +
                "   SET USN_VLD_VOTOU = :USN_VLD_VOTOU" +
                " WHERE USN_COD = :USN_COD";
        FbrDAO.executeUpdate(sql, tabUser);
    }
}






















