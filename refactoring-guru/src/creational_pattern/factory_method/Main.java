package creational_pattern.factory_method;

public class Main {
    public static void main(String[] args) {
        // Road Delivery
        Logistics roadLogistics = new Logistics(TransportFactory.TransportType.ROAD);
        roadLogistics.planDelivery();

        // Sea Delivery
        Logistics seaLogistics = new Logistics(TransportFactory.TransportType.SEA);
        seaLogistics.planDelivery();
    }
}
