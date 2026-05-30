package pbo.f01.repository;

import pbo.f01.entity.ParkingArea;
import javax.persistence.EntityManager;
import java.util.List;

public class ParkingAreaRepository {
    private EntityManager em;

    public ParkingAreaRepository(EntityManager em) {
        this.em = em;
    }

    public void save(ParkingArea area) {
        em.getTransaction().begin();
        em.persist(area);
        em.getTransaction().commit();
    }

    public ParkingArea findByName(String name) {
        try {
            return em.createQuery(
                "SELECT a FROM ParkingArea a WHERE a.name = :name", ParkingArea.class)
                .setParameter("name", name)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<ParkingArea> findAllSortedByName() {
        return em.createQuery(
            "SELECT a FROM ParkingArea a ORDER BY a.name ASC", ParkingArea.class)
            .getResultList();
    }

    public void update(ParkingArea area) {
        em.getTransaction().begin();
        em.merge(area);
        em.getTransaction().commit();
    }
}