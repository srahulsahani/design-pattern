package creational_pattern.factory_method.factory;

public class TransportFactory {
    
    public enum TransportType {
        ROAD,
        SEA
    }
    
    public static Transport createTransport(TransportType type) {
        return switch (type) {
            case ROAD -> new Truck();
            case SEA -> new Ship();
            default -> throw new IllegalArgumentException("Unknown transport type: " + type);
        };
    }
}
