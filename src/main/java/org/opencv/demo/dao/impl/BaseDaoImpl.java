package org.opencv.demo.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.opencv.demo.dao.BaseDao;
import org.opencv.demo.dao.tools.ReflectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 11/11/2017.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    private QueryRunner queryRunner = null;
    private Class<T> type;

    public BaseDaoImpl(){
        queryRunner = new QueryRunner();
        type = ReflectionUtils.getSuperGenericType(getClass());
    }

    @Override
    public void update(Connection connection, String sql, Object... args) throws SQLException {
        queryRunner.update(connection, sql, args);
    }

    @Override
    public T get(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection, sql, new BeanHandler<>(type), args);
    }

    @Override
    public List<T> getForList(Connection connection, String sql, Object... args) throws SQLException {
        return queryRunner.query(connection, sql, new BeanListHandler<>(type), args);
    }

    @Override
    public <E> E getForValue(Connection connection, String sql, Object... args) throws SQLException {
        return (E) queryRunner.query(connection, sql, new ScalarHandler(), args);
    }
}
