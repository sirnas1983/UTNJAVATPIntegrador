package repository;

import lombok.Getter;
import model.Client;
import model.Person;
import model.Service;
import model.Technician;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Getter
public class ServiceRepository {

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em){
        this.em = em;
    }

    public List<Service> getServices(){
        return em.createQuery("select s from Service s", Service.class).getResultList();
    }
    public Service getServiceById(long id){
        return em.find(Service.class, id);
    }
    public void addService(Service service) {
        em.persist(service);
    }
}
