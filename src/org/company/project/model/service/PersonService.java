package org.company.project.model.service;

import org.company.project.model.entity.Person;
import org.company.project.model.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Scope("prototype")//because PersonRepository is Prototype
@Lazy(value = false)//default
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public void save (Person person) throws Exception {
        person.setSalary(person.getSalary() - (person.getSalary()*10/100));
        personRepository.insert(person);
    }
    public void saveWithoutPooled(Person person) throws Exception{
        person.setSalary(person.getSalary() - (person.getSalary()*10/100));
        personRepository.insertWithoutPooled(person);
    }
    public void change (Person person) throws Exception {
        personRepository.update(person);
    }
    public void remove (Person person) throws Exception {
        personRepository.delete(person);
    }
    public Person findOne (Person person) throws Exception {
       List<Person> personList = personRepository.selectOne(person);
       return personList.iterator().next();
    }
    public List<Person> findAll () throws Exception {
        return personRepository.selectAll();
    }

}
