package org.company.project.model.repository;

import org.company.project.common.exception.NotMatchRecordVersionException;
import org.company.project.common.exception.UserNotFoundException;
import org.company.project.common.provider.Log4J;
import org.company.project.model.entity.Person;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Scope("prototype")//because super class is StateFull
@Lazy(value = false)//default
public class PersonRepository  extends GenericRepository {
    public void insert (Person person) throws SQLException {
        person.setPersonId(SequenceGeneratorWithPooled.generateId(getConnection()));
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("insert into person (person_id,name,family,salary,record_version) values (?,?,?,?,0)");
        preparedStatement.setLong(1, person.getPersonId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setLong(4, person.getSalary());
        preparedStatement.execute();
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class)
                .info("insert into person (person_id,name,family,salary,record_version) values (?,?,?,?,?)");
    }
    public void insertWithoutPooled (Person person) throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("select person_seq.nextval id from dual");
        ResultSet resultSet = preparedStatement.executeQuery();
        Log4J.getLogger(PersonRepository.class)
                .info("select person_seq.nextval id from dual");
        resultSet.next();
        person.setPersonId(resultSet.getLong("id"));
        preparedStatement = getConnection()
                .prepareStatement("insert into person (person_id,name,family,salary,record_version) values (?,?,?,?,0)");
        preparedStatement.setLong(1, person.getPersonId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getFamily());
        preparedStatement.setLong(4, person.getSalary());
        preparedStatement.execute();
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class)
                .info("insert into person (person_id,name,family,salary,record_version) values (?,?,?,?,?)");
    }
    public void update (Person person) throws SQLException, NotMatchRecordVersionException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("update person set name=?,family=?,salary=?,record_version=record_version+1 where person_id=? and record_version=?");
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getFamily());
        preparedStatement.setLong(3, person.getSalary());
        preparedStatement.setLong(4, person.getPersonId());
        preparedStatement.setLong(5, person.getRecordVersion());
        int rowEffect = preparedStatement.executeUpdate();
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class).info("update person set name=?,family=?,salary=?,record_version=? where person_id=? and record_version=?");
        if(rowEffect == 0)
            throw new NotMatchRecordVersionException();
    }
    public void delete (Person person) throws SQLException, NotMatchRecordVersionException {
        PreparedStatement preparedStatement = getConnection()
                .prepareStatement("delete from person where person_id=? and record_version=?");
        preparedStatement.setLong(1, person.getPersonId());
        preparedStatement.setLong(2, person.getRecordVersion());
        int rowEffect = preparedStatement.executeUpdate();
        System.out.println(rowEffect);//comment
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class).info("delete from person where person_id=? and record_version=?");
        if (rowEffect == 0)
            throw new NotMatchRecordVersionException();
    }
    public List<Person> selectOne (Person person) throws SQLException, UserNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("select * from person where person_id=?");
        preparedStatement.setLong(1, person.getPersonId());
        ResultSet resultSet = preparedStatement.executeQuery();
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class).info("select * from person where person_id=?");
        List<Person> personList = new ArrayList<>();
        if(resultSet.next())
             personList.add( new Person()
                    .setPersonId(resultSet.getLong("person_id"))
                    .setName(resultSet.getString("name"))
                    .setFamily(resultSet.getString("family"))
                    .setSalary(resultSet.getLong("salary"))
                    .setRecordVersion(resultSet.getLong("record_version")));
        else
            throw new UserNotFoundException();
        return personList;
    }
    public List<Person> selectAll () throws SQLException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("select * from person");
        ResultSet resultSet = preparedStatement.executeQuery();
        setPreparedStatement(preparedStatement);
        Log4J.getLogger(PersonRepository.class).info("select * from person");
        List<Person> personList = new ArrayList<>();
        while (resultSet.next()){
            personList
                    .add(new Person()
                            .setPersonId(resultSet.getLong("person_id"))
                            .setName(resultSet.getString("name"))
                            .setFamily(resultSet.getString("family"))
                            .setSalary(resultSet.getLong("salary"))
                            .setRecordVersion(resultSet.getLong("record_version")));
        }
        return personList;
    }
}
