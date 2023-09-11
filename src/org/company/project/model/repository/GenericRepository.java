package org.company.project.model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class GenericRepository {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Connection getConnection() {
        return connection;
    }

    public GenericRepository setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public GenericRepository setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
        return this;
    }


    public void commit() throws Exception {
        connection.commit();
    }


    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
