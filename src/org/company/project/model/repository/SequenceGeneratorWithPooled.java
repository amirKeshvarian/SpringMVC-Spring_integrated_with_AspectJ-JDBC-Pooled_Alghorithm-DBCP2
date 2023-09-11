package org.company.project.model.repository;


import org.company.project.common.provider.Log4J;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Pooled Algorithm
public  class SequenceGeneratorWithPooled {
    private SequenceGeneratorWithPooled(){}
    private static long high = 0;
    private static long low = 0;
    private static final long INCREMENT_SIZE = 50;//SQL> create sequence s1 start with 1 increment by 50;
    private static long counter;

    public synchronized static long generateId (Connection connection) throws SQLException {
        moderated(connection);
        if (low <= 0){
            low = high;
            return low;
        }
        return low;
    }
    private  static void moderated(Connection connection) throws SQLException {
        if (low == 0 && high == 0) {
            nextVal(connection);//this 'if' block just first time execute and return value start with ?
            counter = 0;
        }
        if (low >= high) {
            nextVal(connection);
            counter = 0;
        }
        low = ((high - INCREMENT_SIZE) + 1) + counter;
        counter ++;
    }
    private   static void  nextVal (Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select s1.nextVal id from dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        Log4J.getLogger(SequenceGeneratorWithPooled.class).info("select s1.nextVal id from dual");
        resultSet.next();
        high = resultSet.getLong("id");
    }

}
