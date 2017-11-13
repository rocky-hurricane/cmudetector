package org.opencv.demo.dao.tools;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description Created by rocky on 11/11/2017.
 */
public class JDBCTools {

    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("helloc3p0");
    }

    /**
     * get connect to database
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void releaseDB(ResultSet resultSet, Statement statement, Connection connection){

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                // return the connection to pool
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //relate to transaction operation
    //commit transaction
    public static void commit(Connection connection){
        if(connection != null){
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //rollback transaction
    public static void rollback(Connection connection){
        if(connection != null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //begin transtraction
    public static void beginTx(Connection connection){
        if(connection != null){
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}


