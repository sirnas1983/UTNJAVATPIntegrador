package repository;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PersonRepository {

    @PersistenceContext
    private EntityManager em;
    public EntityManager getEm() {
        return em;
    }
    public void setEm(EntityManager em){
        this.em = em;
    }
    public List<Person> getPersons(){
        return em.createQuery("select p from Person p", Person.class).getResultList();
    }
    public Person getPersonById(long id){
        return em.find(Person.class, id);
    }
    public List<Client> getClients(){
        return em.createQuery("select c from Client c", Client.class).getResultList();
    }
    public List<Technician> getTechnicians(){
        return em.createQuery("select t from Technician t", Technician.class).getResultList();
    }
    public void addPerson(Person person) {
        em.persist(person);
    }
    public void updatePerson(Person person) {
        em.merge(person);
    }

    public Client getClientById(long id){
        return em.find(Client.class, id);
    }

    public List<Technician> getTechniciansBySpeciality(Service service) {
        Query query = em.createQuery("SELECT t FROM Technician t JOIN t.specialities c " +
                "WHERE c.id = :idService and t.isAvailable=1", Technician.class);
        query.setParameter("idService", service.getId());
        return query.getResultList();
    }

    public Technician selectTechnicianBySpecialityAndId(Service service, long id) throws NoResultException {
        Query query = em.createQuery("SELECT p FROM Technician p JOIN p.specialities c " +
                "WHERE c.id = :idService and p.id = :idTechnician", Technician.class);
        query.setParameter("idService", service.getId());
        query.setParameter("idTechnician",id);
        return (Technician) query.getSingleResult();
    }
}
