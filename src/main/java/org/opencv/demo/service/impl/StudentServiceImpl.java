package org.opencv.demo.service.impl;

import org.opencv.demo.dao.impl.StudentDaoImpl;
import org.opencv.demo.dao.tools.JDBCTools;
import org.opencv.demo.model.Student;
import org.opencv.demo.service.BaseService;
import org.opencv.demo.service.StudentService;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description Created by rocky on 13/11/2017.
 */
public class StudentServiceImpl extends BaseServiceImpl<Student> implements StudentService{


    String studentId;
    String firstName;
    String lastName;
    String gender;
    String program;
    java.sql.Date dateBirth;
    String dateEnrollment;

    public StudentServiceImpl() throws SQLException {
        dao = new StudentDaoImpl();
    }

    @Override
    public void saveEntity(Student student) throws SQLException {
        setStudent(student);
        String sql = "insert into student values(?,?,?,?,?,?,?)";
        dao.update(connection, sql, studentId, firstName, lastName, gender, program, dateBirth, dateEnrollment );
    }

    @Override
    public void updateEntity(Student student) throws SQLException {
        deleteEntity(student);
        saveEntity(student);
    }

    @Override
    public void deleteEntity(Student student) throws SQLException {
        setStudent(student);
        String sql = "delete from student where student_id = ?";
        dao.update(connection, sql, studentId);
    }

    @Override
    public Student getEntity(String id) throws SQLException {
        String sql = "select student_id studentId, first_name firstName, last_name lastName, " +
                "gender, program, date_birth dateBirth, date_enrollment dateEnrollment from student where student_id = ?";
        return (Student) dao.get(connection, sql, id);
    }

    @Override
    public List<Student> getEntityBySQL(String sql, Object... objects) {
        return null;
    }

    public void setStudent(Student student){
        studentId = student.getStudentId();
        firstName = student.getFirstName();
        lastName = student.getLastName();
        gender = student.getGender();
        program = student.getProgram();
        dateBirth = student.getDateBirth();
        dateEnrollment = student.getDateEnrollment();
    }
}
