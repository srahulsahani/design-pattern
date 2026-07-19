package creational_pattern.abstract_factory.vehicle;

public class EconomyMotorcycle implements Motorcycle {
    @Override
    public void ride() {
        System.out.println("Riding an economy motorcycle with great mileage and affordability.");
    }
    
    @Override
    public int getMaxSpeed() {
        return 140;
    }
}
