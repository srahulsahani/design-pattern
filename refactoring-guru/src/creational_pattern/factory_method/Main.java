package creational_pattern.factory_method;

public class Main {
    public static void main(String[] args) {
        Logistics logistics;

        //Road Delivery
        logistics = new RoadLogistics();
        logistics.planDelivery();

        //Sea delivery
        logistics = new SeaLogistics();
        logistics.planDelivery();
    }
}
