package creational_pattern.factory_method.factory_method;

public class Truck implements Transport {
    @Override
    public void deliver() {
        System.out.println("Delivering goods by road in truck..");
    }
}
