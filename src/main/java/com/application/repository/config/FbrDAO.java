package com.application.repository.config;

import com.application.config.EOperadorSql;
import com.application.config.FbrUtils;
import com.application.config.ListRequest;
import com.application.config.exceptions.FindByIDNotFoundException;
import com.application.config.exceptions.TooManyRollsException;
import com.application.config.validator.ValidatorEnumInterface;
import com.application.domain.FbrModel;
import com.application.domain.dto.GetCountDTO;
import com.google.common.io.Resources;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
public class FbrDAO<T extends FbrModel> {

    static JdbcTemplate jdbcTemplate;

    static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    protected FbrDAO(HikariDataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    protected static void execute(String sql, Object... obj) {
        log.info(sql);
        jdbcTemplate.update(sql, obj);
    }

    /*
     * FindById
     */
    @Transactional
    protected static <T extends FbrModel> T executeFindById(Class<T> t, ListRequest listRequest) throws Exception {
        var list = executeQueryList(t, listRequest);

        if (list.isEmpty())
            throw new FindByIDNotFoundException();

        if (list.size() > 1)
            throw new TooManyRollsException();

        return (T) list.get(0);
    }

    /*
     * GetCount
     */
    @Transactional
    protected static <T extends FbrModel> Long executeGetCount(Class<T> t, ListRequest listRequest) {
        String sql = "SELECT * FROM " + convertJavaNamesToSql(t.getSimpleName());
        return executeGetCount(sql, listRequest);
    }

    @Transactional
    protected static Long executeGetCount(String sql, ListRequest listRequest) {
        return executeGetCount(sql, listRequest, new HashMap<>());
    }

    @Transactional
    protected static Long executeGetCount(String sql, ListRequest listRequest, Map<String, Object> params) {
        sql = addRequestParamsInSql(sql, listRequest, true);

        // Add ListRequest Parameters Values
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (var map : listRequest.getFilterList().entrySet())
            parameters.addValue(convertJavaNamesToSql(map.getKey()), map.getValue());

        // Add Parameters Values
        for (var map : params.entrySet())
            parameters.addValue(convertJavaNamesToSql(map.getKey()), map.getValue());

        log.info(sql);
        listRequest.getFilterList().forEach((key, value) -> log.info(key + ":" + value));
        return namedParameterJdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(GetCountDTO.class))
                .stream().map(l -> l.getResultCount()).findFirst().orElse(0L);
    }

    /*
     * QueryList
     */
    @Transactional
    protected static <T extends FbrModel> List<T> executeQueryList(Class<T> t, ListRequest listRequest) {
        String sql = "SELECT * FROM " + convertJavaNamesToSql(t.getSimpleName());
        return executeQueryList(sql, t, listRequest);
    }

    @Transactional
    protected static <T extends FbrModel> List<T> executeQueryList(String sql, Class<T> t, ListRequest listRequest) {
        return executeQueryList(sql, t, listRequest, new HashMap<>());
    }

    @Transactional
    protected static <T extends FbrModel> List<T> executeQueryList(String sql, Class<T> t, ListRequest listRequest, Map<String, Object> params) {
        sql = addRequestParamsInSql(sql, listRequest);

        // Add ListRequest Parameters Values
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (var map : listRequest.getFilterList().entrySet())
            parameters.addValue(convertJavaNamesToSql(map.getKey()), map.getValue());

        // Add Parameters Values
        for (var map : params.entrySet())
            parameters.addValue(convertJavaNamesToSql(map.getKey()), map.getValue());

        log.info(sql);
        listRequest.getFilterList().forEach((key, value) -> log.info(key + ":" + value));
        return (List<T>) namedParameterJdbcTemplate.query(sql, parameters, BeanPropertyRowMapper.newInstance(t));
    }

    /*
     * Build Query
     */
    @Transactional
    private static String addRequestParamsInSql(String sql, ListRequest listRequest) {
        return addRequestParamsInSql(sql, listRequest, false);
    }

    @Transactional
    private static String addRequestParamsInSql(String sql, ListRequest listRequest, boolean getCount) {
        var builder = new StringBuilder("SELECT " + (getCount ? "COUNT(1) AS RESULT_COUNT" : "*") + " FROM ( " + sql + " ) WHERE 1 = 1");

        if (listRequest.getPageSize() > 0 && !getCount)
            builder.insert(0, "SELECT * FROM ( ");

        for (Map.Entry<String, Object> map : listRequest.getFilterList().entrySet()) {
            builder.append(" AND ");
            builder.append(convertJavaNamesToSql(map.getKey()));
            builder.append(" ");
            builder.append(FbrUtils.extractOperator(map.getKey()));

            if (FbrUtils.extractOperator(map.getKey()).equals(EOperadorSql.IN.getValue())) {
                builder.append(" (");
                var list = (List) map.getValue();
                builder.append(list.stream().filter(i -> i != null) //
                        .map(i -> {
                            if (i instanceof ValidatorEnumInterface)
                                return ((ValidatorEnumInterface) i).getValue().toString();
                            if (i instanceof DayOfWeek)
                                return String.valueOf(((DayOfWeek) i).getValue());
                            return i.toString();
                        }).collect(Collectors.joining(",")));
                builder.append(")");
            } else {
                builder.append(" :");
                builder.append(convertJavaNamesToSql(map.getKey()));
            }
        }

        if (!listRequest.getOrderList().isEmpty() && !getCount)
            builder.append(" ORDER BY" + listRequest.getOrderList().stream() //
                    .map(l -> l.entrySet().stream() //
                            .map(map -> " " + convertJavaNamesToSql(map.getKey()) + " " + map.getValue()) //
                            .collect(Collectors.joining(","))) //
                    .collect(Collectors.joining(",")));

        if (listRequest.getPageSize() > 0 && !getCount) {
            builder.append(")");
            var minSize = (listRequest.getPageNumber() - 1) * listRequest.getPageSize();
            builder.append(" OFFSET " + minSize + " ROWS FETCH NEXT " + listRequest.getPageSize() + " ROWS ONLY");
        }

        return builder.toString();
    }

    private static String convertJavaNamesToSql(String name) {
        Pattern pattern = Pattern.compile("(\\w)*");
        Matcher matcher = pattern.matcher(name);

        String result = null;
        if (matcher.find()) result = matcher.group(0);

        return FbrUtils.convertJavaNamesToTable(result);
    }

    /*
     * Used in Insert/Update
     */
    private static <T extends FbrModel> MapSqlParameterSource generateParameters(T t) throws Exception {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Field field : t.getClass().getDeclaredFields()) {
            field.trySetAccessible();
            Object data = field.get(t);
            if (!field.isAnnotationPresent(FbrModel.IgnoreField.class)) {
                parameters.addValue(convertJavaNamesToSql(field.getName()), field.getType().cast(data));
            }
        }
        return parameters;
    }

    @Transactional
    public static <T extends FbrModel> Long executeInsert(String sql, String[] keys, T t) throws Exception {
        var params = generateParameters(t);
        log.info(sql);
        params.getValues().forEach((key, value) -> log.info(key + ":" + value));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, keys);
        return keyHolder.getKey().longValue();
    }

    @Transactional
    protected static <T extends FbrModel> void executeUpdate(String sql, T t) throws Exception {
        var params = generateParameters(t);
        sql = "BEGIN \n" + sql;
        sql = sql + ";\n commit; \n END;";
        System.out.println(sql);
        params.getValues().forEach((key, value) -> log.info(key + ":" + value));
        namedParameterJdbcTemplate.update(sql, params);
    }

    protected static String readFile(final String filePath) throws IOException {
        return Resources.toString(Resources.getResource("sql/" + filePath + ".sql"), StandardCharsets.UTF_8);
    }
}
