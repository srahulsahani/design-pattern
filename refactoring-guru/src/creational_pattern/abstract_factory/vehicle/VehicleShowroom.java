package creational_pattern.abstract_factory.vehicle;

public class VehicleShowroom {
    private Car car;
    private Motorcycle motorcycle;
    
    public VehicleShowroom(VehicleFactory factory) {
        car = factory.createCar();
        motorcycle = factory.createMotorcycle();
    }
    
    public void showcaseVehicles() {
        System.out.println("=== Vehicle Showcase ===");
        System.out.print("Car: ");
        car.drive();
        System.out.println("Max Speed: " + car.getMaxSpeed() + " km/h");
        
        System.out.print("\nMotorcycle: ");
        motorcycle.ride();
        System.out.println("Max Speed: " + motorcycle.getMaxSpeed() + " km/h");
    }
}
