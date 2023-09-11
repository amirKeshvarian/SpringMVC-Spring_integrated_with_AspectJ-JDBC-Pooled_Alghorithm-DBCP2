package org.company.project.common.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;

public class JDBCProvider {
    public static final int ORCLPDB1 = 1;
    public static final int XEPDB1 = 2;

    private static final BasicDataSource BASIC_DATA_SOURCE_ORCLPDB1 = new BasicDataSource();
    private static final BasicDataSource BASIC_DATA_SOURCE_XEPDB1 = new BasicDataSource();
    private JDBCProvider (){}

    static {
       BASIC_DATA_SOURCE_ORCLPDB1.setDriverClassName("oracle.jdbc.driver.OracleDriver");
       BASIC_DATA_SOURCE_ORCLPDB1.setUrl("jdbc:oracle:thin:@localhost:1521/orclpdb1");
       BASIC_DATA_SOURCE_ORCLPDB1.setUsername("amir");
       BASIC_DATA_SOURCE_ORCLPDB1.setPassword("myjava123");
       BASIC_DATA_SOURCE_ORCLPDB1.setMaxIdle(5);
       BASIC_DATA_SOURCE_ORCLPDB1.setMaxTotal(20);

        BASIC_DATA_SOURCE_XEPDB1.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        BASIC_DATA_SOURCE_XEPDB1.setUrl("jdbc:oracle:thin:@localhost:1521/xepdb1");
        BASIC_DATA_SOURCE_XEPDB1.setUsername("amir");
        BASIC_DATA_SOURCE_XEPDB1.setPassword("myjava123");
        BASIC_DATA_SOURCE_XEPDB1.setMaxIdle(5);
        BASIC_DATA_SOURCE_XEPDB1.setMaxTotal(20);
    }
    public static Connection getConnection (int databaseName) throws Exception{
        Connection connection = null;
        switch (databaseName){
            case 1 :
                connection = BASIC_DATA_SOURCE_ORCLPDB1.getConnection();
                connection.setAutoCommit(false);
                return connection;
            default:
                connection = BASIC_DATA_SOURCE_XEPDB1.getConnection();
                connection.setAutoCommit(false);
                return connection;
        }
    }
}
