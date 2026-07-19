package creational_pattern.abstract_factory.vehicle;

public class EconomyVehicleFactory implements VehicleFactory {
    
    @Override
    public Car createCar() {
        return new EconomyCar();
    }
    
    @Override
    public Motorcycle createMotorcycle() {
        return new EconomyMotorcycle();
    }
}
