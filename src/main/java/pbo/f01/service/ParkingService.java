package pbo.f01.service;

import pbo.f01.entity.ParkingArea;
import pbo.f01.entity.Vehicle;
import pbo.f01.repository.ParkingAreaRepository;
import pbo.f01.repository.VehicleRepository;
import javax.persistence.EntityManager;
import java.util.List;

public class ParkingService {
    private ParkingAreaRepository areaRepo;
    private VehicleRepository vehicleRepo;
    private EntityManager em;

    public ParkingService(EntityManager em) {
        this.em = em;
        this.areaRepo = new ParkingAreaRepository(em);
        this.vehicleRepo = new VehicleRepository(em);
    }

    public void addArea(String name, int capacity, String allowedType) {
        ParkingArea area = new ParkingArea(name, capacity, allowedType);
        areaRepo.save(area);
    }

    public void addVehicle(String plateNumber, String owner, String type) {
        Vehicle vehicle = new Vehicle(plateNumber, owner, type);
        vehicleRepo.save(vehicle);
    }

    public void parkVehicle(String plateNumber, String areaName) {
        Vehicle vehicle = vehicleRepo.findByPlate(plateNumber);
        if (vehicle == null) return;
        ParkingArea area = areaRepo.findByName(areaName);
        if (area == null) return;
        if (!vehicle.getType().equals(area.getAllowedType())) return;
        em.refresh(area);
        if (area.getOccupied() >= area.getCapacity()) return;
        vehicle.setParkingArea(area);
        vehicleRepo.update(vehicle);
    }

    public void displayAll() {
        List<ParkingArea> areas = areaRepo.findAllSortedByName();
        for (ParkingArea area : areas) {
            em.refresh(area);
            System.out.println(area.getName() + " " + area.getAllowedType() +
                               " " + area.getCapacity() + "|" + area.getOccupied());
            for (Vehicle v : area.getVehicles()) {
                System.out.println(v.getPlateNumber() + " " + v.getOwner() +
                                   " " + v.getType());
            }
        }
    }
}