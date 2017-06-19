package com.mycompany.service.impl;

import com.mycompany.dao.PersonDao;
import com.mycompany.model.Person;
import com.mycompany.model.PhoneNumber;
import com.mycompany.service.PersonService;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mycompany.service.impl.CountryDictionary.isMobilePhone;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao;

    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    /**
     * Avoid usage of this method, use getMobileNumber instead and check country by yourself.
     * This method will be removed in the future releases.
     *
     * @param personName
     * @return
     */
    @Override
    @Deprecated
    public boolean hasMobileIssuedInSwitzerland(String personName) {
        String mobileNumber = getMobileNumber(personName);
        if (mobileNumber != null && CountryDictionary.SWITZERLAND.isMobilePhone(mobileNumber)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNumberOfPersons() {
        return personDao.countAll();
    }


    @Override
    public String getMobileNumber(String personName) {
        Person person = personDao.findPerson(personName);
        if (person != null && person.getPhoneNumber() != null) {
            PhoneNumber phone = person.getPhoneNumber();
            return phone.getNumber();
        }

        return null;
    }

    @Override
    public List<String> getTruncatedPersonNames(int maxLength) {
        List<Person> people = personDao.getAll();

        Function<String, String> truncateName = name -> name.substring(0, Math.min(maxLength, name.length()));

        List<String> names = people.stream()
                .map(Person::getName)
                .map(truncateName)
                .collect(Collectors.toList());

        return names;
    }

    @Override
    public List<Person> getPeopleWithMobilePhones() {
        List<Person> people = personDao.getAll();

        Predicate<? super Person> hasMobile = person -> isMobilePhone(person.getPhoneNumber().getNumber());

        List<Person> result = people.stream()
                .filter(person -> Objects.nonNull(person.getPhoneNumber()))
                .filter(hasMobile)
                .collect(Collectors.toList());

        return result;
    }
}
