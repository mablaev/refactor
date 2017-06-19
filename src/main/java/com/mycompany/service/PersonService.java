package com.mycompany.service;

import com.mycompany.service.impl.CountryDictionary;
import com.mycompany.model.Person;

import java.util.List;


public interface PersonService {
    /** checks if person has mobile phone in particular country
     * @param personName Person's name
     * @return
     */
    boolean hasMobileIssuedInSwitzerland(String personName);

    /**
     * @return counts all person entries
     */
    int getNumberOfPersons();

    /**
     * @param personName Person's name.
     * @return Gets the given user's mobile phone number, or null if he doesn't have one.
     */
    String getMobileNumber(String personName);

    /**
     * Returns all names in the book truncated to the given length.
     * Be careful on using this method as it will load all rows from DB into memory.
     * If your table is huge it may lead to OutOfMemoryException.
     */
    List<String> getTruncatedPersonNames(int maxLength);

    /**
     * @return all people who have mobile phone numbers.
     */
    List<Person> getPeopleWithMobilePhones();
}
