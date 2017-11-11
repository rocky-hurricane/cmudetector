package org.opencv.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 11/11/2017.
 */
public interface BaseDao<T> {

    /**
     * INSERT, UPDATE, DELETE
     * @param connection
     * @param sql
     * @param args
     * @throws SQLException
     */
    void update(Connection connection, String sql, Object ... args) throws SQLException;

    /**
     * return a T object
     * @param connection
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    T get(Connection connection, String sql, Object ... args) throws SQLException;

    /**
     * return a List of T objects
     * @param connection
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    List<T> getForList(Connection connection, String sql, Object ... args) throws SQLException;

    /**
     * return a specific value, e.g. mean salary, total number of people, etc.
     * @param connection
     * @param sql
     * @param args
     * @param <E>
     * @return
     * @throws SQLException
     */
    <E> E getForValue(Connection connection, String sql, Object ... args) throws SQLException;


}
