package com.mycompany.dao.impl;

import com.mycompany.dao.PersonDao;
import com.mycompany.model.Person;
import com.mycompany.model.PhoneNumber;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    private DataSource dataSource;

    public PersonDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addPerson(Person person) {

        String insertSql = "insert into PersonEntry(name, phone_number, date) values (?, ?, ?)";

        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(insertSql)) {

                ps.setString(1, person.getName());
                ps.setLong(3, System.currentTimeMillis());

                if (person.getPhoneNumber() != null) {
                    ps.setString(2, person.getPhoneNumber().getNumber());
                } else {
                    ps.setNull(2, Types.VARCHAR);
                }

                ps.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw new IllegalArgumentException("Something went wrong during insertion of Person=" + person + " into DB.", e);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Something went wrong during insertion of Person=" + person + " into DB.", e);
        }
    }


    @Override
    public Person findPerson(String personName) {
        String sql = "select * from PersonEntry where name = ?";
        Person result = null;
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, personName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = mapPerson(rs);

                    if (rs.next()) {
                        throw new IllegalStateException("More than one Person with name='" + personName + "' exists in DB.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred on findPerson with name='" + personName + "'", e);
        }

        return result;
    }

    private Person mapPerson(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        PhoneNumber phoneNumber = PhoneNumber.of(rs.getString("phoneNumber"));
        Date date = rs.getDate("date");

        return new Person(name, phoneNumber, date);
    }


    @Override
    public List<Person> getAll() {
        String sql = "select * from PersonEntry";

        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Person> result = new ArrayList<>();

            while (rs.next()) {
                Person person = mapPerson(rs);
                result.add(person);
            }

            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred on getAll()", e);
        }
    }


    @Override
    public int countAll() {
        String sql = "select count(*) from PersonEntry";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred on countAll()", e);
        }
    }
}
