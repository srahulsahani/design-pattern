package creational_pattern.abstract_factory.vehicle;

public class Main {
    
    public static void main(String[] args) {
        // Luxury Vehicle Showroom
        System.out.println("=== Luxury Vehicle Showroom ===");
        VehicleFactory luxuryFactory = new LuxuryVehicleFactory();
        VehicleShowroom luxuryShowroom = new VehicleShowroom(luxuryFactory);
        luxuryShowroom.showcaseVehicles();
        
        // Economy Vehicle Showroom
        System.out.println("\n=== Economy Vehicle Showroom ===");
        VehicleFactory economyFactory = new EconomyVehicleFactory();
        VehicleShowroom economyShowroom = new VehicleShowroom(economyFactory);
        economyShowroom.showcaseVehicles();
    }
}
