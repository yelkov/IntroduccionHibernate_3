package edu.badpals.harypotter;

import edu.badpals.harypotter.entities.*;
import jakarta.persistence.*;

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

        Course astronomy = getCourse(em,"Astronomy");
        Enrollment matriculaLily = new Enrollment();
        EnrollmentId ids = new EnrollmentId();
        ids.setCourseEnrollment(astronomy.getId());
        ids.setPersonEnrollment(lily.getId());
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

    public static Course getCourse(EntityManager em ,String courseName){
        try{
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.name = :name");
            query.setParameter("name", courseName);
            Course course = (Course) query.getSingleResult();
            return course;
        } catch (NoResultException e) {
            System.out.println("Curso no encontrado.");
        }catch (NonUniqueResultException e) {
            System.out.println("Error: Se encontraron múltiples cursos con el mismo nombre.");
        }
        catch (Exception e) {
            System.out.println("Error al seleccionar curso.");
            e.printStackTrace();
        }
        return null;
    }
}
