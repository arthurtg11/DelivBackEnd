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

    public static void insert(TabUser tabUser) throws Exception {
        String sql = "INSERT INTO TAB_USER\n" +
                "            (USN_COD, USN_DES_USERNAME, USN_DES_NAME, USN_DES_PASSWORD, USN_VLD_CLIFUN,\n" +
                "             USN_BIT_PHOTO, USN_DES_PHONE, USN_VLD_SITUACAO, USN_DTA_CAD\n" +
                "            )\n" +
                "     VALUES ( (SELECT NVL (MAX (USN_COD), 0) + 1\n" +
                "                 FROM TAB_USER), :USN_DES_USERNAME, :USN_DES_NAME, :USN_DES_PASSWORD, :USN_VLD_CLIFUN,\n" +
                "             :USN_BIT_PHOTO, :USN_DES_PHONE, 1, SYSDATE\n" +
                "            )";

        Long usnCod = FbrDAO.executeInsert(sql, new String[]{"USN_COD"}, tabUser);
        tabUser.setUsnCod(usnCod);
    }

    public static void update(TabUser tabUser) throws Exception {
        String sql = "UPDATE TAB_USER\n" +
                "   SET USN_DES_NAME = :USN_DES_NAME,\n" +
                "       USN_DES_PASSWORD = :USN_DES_PASSWORD,\n" +
                "       USN_VLD_CLIFUN = :USN_VLD_CLIFUN,\n" +
                "       USN_BIT_PHOTO = :USN_BIT_PHOTO,\n" +
                "       USN_DES_PHONE = :USN_DES_PHONE,\n" +
                "       USN_VLD_SITUACAO = :USN_VLD_SITUACAO\n" +
                " WHERE USN_COD = :USN_COD";
        FbrDAO.executeUpdate(sql, tabUser);
    }
}






















