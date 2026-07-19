package creational_pattern.abstract_factory.vehicle;

public class LuxuryVehicleFactory implements VehicleFactory {
    
    @Override
    public Car createCar() {
        return new LuxuryCar();
    }
    
    @Override
    public Motorcycle createMotorcycle() {
        return new LuxuryMotorcycle();
    }
}
