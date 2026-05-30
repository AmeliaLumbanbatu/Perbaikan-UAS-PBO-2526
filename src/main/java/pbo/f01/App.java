package pbo.f01;

import pbo.f01.service.ParkingService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import java.util.Scanner;

/**
 * Driver class utama
 * Nama: [Amelia Renata Lumbanbatu]
 * Nim: [12S24031]
 */

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("park-it-pu");
        EntityManager em = emf.createEntityManager();
        ParkingService service = new ParkingService(em);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("#");
            String command = parts[0];
            switch (command) {
                case "area-add":
                    service.addArea(parts[1], Integer.parseInt(parts[2]), parts[3]);
                    break;
                case "vehicle-add":
                    service.addVehicle(parts[1], parts[2], parts[3]);
                    break;
                case "park":
                    service.parkVehicle(parts[1], parts[2]);
                    break;
                case "display-all":
                    service.displayAll();
                    break;
            }
        }
        em.close();
        emf.close();
    }
}