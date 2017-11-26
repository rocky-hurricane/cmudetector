package org.opencv.demo.service.impl;

import org.opencv.demo.dao.impl.RecordDaoImpl;
import org.opencv.demo.model.Record;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 26/11/2017.
 */
public class RecordServiceImpl extends BaseServiceImpl<Record>{
    public RecordServiceImpl() throws SQLException {
        dao = new RecordDaoImpl();
    }


    @Override
    public void saveEntity(Record record) throws SQLException {
        String sql = "insert into record values(?,?,?,?)";
        dao.update(connection,sql,record.getDateVisit(),record.getTimeVisit(),record.getStudentId(),record.getReason());
    }

    @Override
    public void updateEntity(Record record) throws SQLException {

    }

    @Override
    public void deleteEntity(Record record) throws SQLException {

    }

    @Override
    public Record getEntity(String id) throws SQLException {
        return null;
    }

    @Override
    public List<Record> getEntityBySQL(String sql, Object... objects) {
        return null;
    }




}
