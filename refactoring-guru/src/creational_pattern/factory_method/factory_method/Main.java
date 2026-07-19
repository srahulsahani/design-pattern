package creational_pattern.factory_method.factory_method;

public class Main {
    public static void main(String[] args) {
        Logistics logistics;
        
        // Road Delivery
        System.out.println("=== Road Delivery ===");
        logistics = new RoadLogistics();
        logistics.planDelivery();
        
        // Sea Delivery
        System.out.println("\n=== Sea Delivery ===");
        logistics = new SeaLogistics();
        logistics.planDelivery();
    }
}
