package creational_pattern.factory_method;

public class Ship implements Transport{
    @Override
    public void deliver() {
        System.out.println("Delivering goods by Sea..");
    }
}
