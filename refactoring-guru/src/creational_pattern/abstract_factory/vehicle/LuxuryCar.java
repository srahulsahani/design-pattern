package creational_pattern.abstract_factory.vehicle;

public class LuxuryCar implements Car {
    @Override
    public void drive() {
        System.out.println("Driving a luxury car with premium comfort and advanced features.");
    }
    
    @Override
    public int getMaxSpeed() {
        return 250;
    }
}
