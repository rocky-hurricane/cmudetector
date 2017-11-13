package org.opencv.demo.service.impl;

import org.opencv.demo.dao.BaseDao;
import org.opencv.demo.dao.tools.JDBCTools;
import org.opencv.demo.service.BaseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 13/11/2017.
 */
public class BaseServiceImpl<T> implements BaseService<T>{

    Connection connection = JDBCTools.getConnection();
    BaseDao dao;

    public BaseServiceImpl() throws SQLException {
    }

    @Override
    public void saveEntity(T t) throws SQLException {

    }

    @Override
    public void updateEntity(T t) throws SQLException {

    }

    @Override
    public void deleteEntity(T t) throws SQLException {

    }

    @Override
    public T getEntity(int id) throws SQLException {
        return null;
    }

    @Override
    public List<T> getEntityBySQL(String sql, Object... objects) {
        return null;
    }
}
