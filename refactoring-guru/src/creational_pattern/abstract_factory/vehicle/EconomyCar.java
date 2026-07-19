package creational_pattern.abstract_factory.vehicle;

public class EconomyCar implements Car {
    @Override
    public void drive() {
        System.out.println("Driving an economy car with fuel efficiency and practicality.");
    }
    
    @Override
    public int getMaxSpeed() {
        return 180;
    }
}
