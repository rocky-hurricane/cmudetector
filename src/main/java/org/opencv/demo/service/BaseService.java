package org.opencv.demo.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 13/11/2017.
 */
public interface BaseService<T> {

    public void saveEntity(T t) throws SQLException;
    public void updateEntity(T t) throws SQLException;
    public void deleteEntity(T t) throws SQLException;
    public T getEntity(int id) throws SQLException;
    public List<T> getEntityBySQL(String sql, Object ... objects);

}
