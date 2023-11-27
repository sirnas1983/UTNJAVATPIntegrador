package repository;

import lombok.Getter;
import java.util.List;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Getter
public class ProblemRepository {

    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em){
        this.em = em;
    }

    public void addProblem(Problem problem){
        em.getTransaction().begin();
        em.persist(problem);
        em.getTransaction().commit();
    }

    public void updateIncident(Problem problem){
        em.getTransaction().begin();
        em.merge(problem);
        em.getTransaction().commit();
    }

    public List<Problem> getProblems(){
        return em.createQuery("select p from Problem p", Problem.class).getResultList();
    }

    public Problem getProblemById(Long id){
        return em.find(Problem.class, id);
    }
    public Problem getProblemByServicesById(List<Service> services, long id) throws NoResultException {
        Query query = em.createQuery("select p from Problem p JOIN p.service c " +
                "Where c in :services and p.id = :id", Problem.class);
        query.setParameter("services", services);
        query.setParameter("id", id);
        return (Problem) query.getSingleResult();
    }

    public List<?> getProblemByServices(List<Service> services){
        Query query = em.createQuery("select p from Problem p JOIN p.service c " +
                "Where c in :services", Problem.class);
        query.setParameter("services", services);
        return query.getResultList();
    }


}
