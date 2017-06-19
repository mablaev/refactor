package com.mycompany.dao;

import com.mycompany.model.Person;

import java.util.List;

public interface PersonDao {
    /**
     * @param person will be added to DB.
     */
    void addPerson(Person person);
    /**
     * Looks up the given person, null if not found.
     */
    Person findPerson(String name);

    /**
     * Be careful on using this method as it will load all rows from DB into memory.
     * If your table is huge it may lead to OutOfMemoryException.
     * @return All persons from DB, if no entries in DB result will return empty List.
     */
    List<Person> getAll();

    /**
     * @return Total amount of persons.
     */
    int countAll();
}
