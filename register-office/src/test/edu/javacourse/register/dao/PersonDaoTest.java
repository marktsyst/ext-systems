package edu.javacourse.register.dao;

import edu.javacourse.register.domain.Person;
import edu.javacourse.register.domain.PersonFemale;
import edu.javacourse.register.domain.PersonMale;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PersonDaoTest {
    @Test
    public void findPersons() {
//        PersonDao dao = new PersonDao();
//        List<Person> persons = dao.findPersons();
//        persons.forEach(p -> {
//            System.out.println("Name: " + p.getFirstName());
//            System.out.println("Class for sex: " + p.getClass().getName());
//            System.out.println("Passport: " + p.getPassports().size());
//            System.out.println("Birth: " + p.getBirthCertificate());
//            if (p instanceof PersonMale m) {
//                System.out.println("Birth Cert: " + m.getBirthCertificates().size());
//                System.out.println("Marriage Cert: " + m.getMarriageCertificates().size());
//            } else if (p instanceof PersonFemale f) {
//                System.out.println("Birth Cert: " + f.getBirthCertificates().size());
//                System.out.println("Marriage Cert: " + f.getMarriageCertificates().size());
//            }
//        });
    }

}