package org.opencv.demo.service.impl;

import org.opencv.demo.dao.impl.StudentRecordDaoImpl;
import org.opencv.demo.model.StudentRecord;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 27/11/2017.
 */
public class StudentRecordServiceImpl extends BaseServiceImpl<StudentRecord> {
    public StudentRecordServiceImpl() throws SQLException {
        dao = new StudentRecordDaoImpl();
    }

    @Override
    public List<StudentRecord> getEntityBySQL(String sql, Object... objects) throws SQLException {
        return dao.getForList(connection,sql,objects);
    }

}
