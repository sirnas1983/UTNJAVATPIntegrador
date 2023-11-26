package repository;

import lombok.Getter;
import model.Incident;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Getter
public class IncidentRepository {

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em){
        this.em = em;
    }

    public void addIncident(Incident incident){
        em.getTransaction().begin();
        em.persist(incident);
        em.getTransaction().commit();
    }

    public void updateIncident(Incident incident){
        em.getTransaction().begin();
        em.merge(incident);
        em.getTransaction().commit();
    }


    public List<Incident> getIncidents() {
        return em.createQuery("select i from Incident i", Incident.class).getResultList();
    }

    public List<Incident> getUnresolvedIncidents() {
        return em.createQuery("select i from Incident i where i.isResolved=0", Incident.class)
                .getResultList();

    }

    public List<Incident> getResolvedIncidents() {
        return em.createQuery("select i from Incident i where i.isResolved=1", Incident.class)
                .getResultList();

    }

    public Incident getUnresolvedIncidentById(Long id) {
        return em.createQuery("select i from Incident i where i.isResolved=0 and i.id= :id", Incident.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
