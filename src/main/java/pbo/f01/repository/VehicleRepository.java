//Amelia Renata Lumbanbatu - 12S24031

package pbo.f01.repository;

import pbo.f01.entity.Vehicle;
import javax.persistence.EntityManager;

public class VehicleRepository {
    private EntityManager em;

    public VehicleRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Vehicle vehicle) {
        em.getTransaction().begin();
        em.persist(vehicle);
        em.getTransaction().commit();
    }

    public Vehicle findByPlate(String plateNumber) {
        try {
            return em.createQuery(
                "SELECT v FROM Vehicle v WHERE v.plateNumber = :plate", Vehicle.class)
                .setParameter("plate", plateNumber)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Vehicle vehicle) {
        em.getTransaction().begin();
        em.merge(vehicle);
        em.getTransaction().commit();
    }
}