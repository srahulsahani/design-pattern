package creational_pattern.abstract_factory.vehicle;

public class LuxuryMotorcycle implements Motorcycle {
    @Override
    public void ride() {
        System.out.println("Riding a luxury motorcycle with premium performance and style.");
    }
    
    @Override
    public int getMaxSpeed() {
        return 300;
    }
}
