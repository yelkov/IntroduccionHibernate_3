package edu.badpals.harypotter;

import edu.badpals.harypotter.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        House slytherin = em.find(House.class,4);

        Person james = new Person();
        james.setFirstName("James");
        james.setLastName("Sirius");
        james.setId(109);
        james.setHouse(slytherin);

        Person albus = new Person();
        albus.setFirstName("Albus");
        albus.setLastName("Severus");
        albus.setId(110);
        albus.setHouse(slytherin);

        Person lily = new Person();
        lily.setFirstName("Lily");
        lily.setLastName("Luna");
        lily.setId(111);
        lily.setHouse(slytherin);

        try{
            em.getTransaction().begin();
            em.persist(james);
            em.persist(albus);
            em.persist(lily);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        Course astronomy = em.find(Course.class, 6);
        Enrollment matriculaLily = new Enrollment();
        EnrollmentId ids = new EnrollmentId();
        ids.setCourseEnrollment(6);
        ids.setPersonEnrollment(111);
        matriculaLily.setId(ids);
        matriculaLily.setCourseEnrollment(astronomy);
        matriculaLily.setPersonEnrollment(lily);
        try{
            em.getTransaction().begin();
            em.persist(matriculaLily);
            em.getTransaction().commit();
            System.out.println("Lily matriculada");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

    }
}
