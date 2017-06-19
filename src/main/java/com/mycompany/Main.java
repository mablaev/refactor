package com.mycompany;


import com.mycompany.dao.PersonDao;
import com.mycompany.dao.impl.PersonDaoImpl;
import com.mycompany.service.PersonService;
import com.mycompany.service.impl.PersonServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = dataSource("jdbc:oracle:thin:@prod", "admin", "beefhead");
        PersonDao personDao = personDao(dataSource);
        PersonService personService = personService(personDao);

        System.out.println("John Smith mobile phone number is " + personService.getMobileNumber("John Smith"));
        System.out.println("The number of persons in DB is " + personService.getNumberOfPersons());
    }

    private static PersonService personService(PersonDao personDao) {
        return new PersonServiceImpl(personDao);
    }

    private static PersonDao personDao(DataSource dataSource) {
        return new PersonDaoImpl(dataSource);
    }

    private static DataSource dataSource(String url, String userName, String password) {
        //use Pooled data source here
        BasicDataSource dataSource = new BasicDataSource();

        //Class.forName("oracle.jdbc.ThinDriver");

        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }
}
