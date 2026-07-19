package creational_pattern.factory_method.factory;

public class Logistics {
    private final TransportFactory.TransportType transportType;

    public Logistics(TransportFactory.TransportType transportType) {
        this.transportType = transportType;
    }

    public void planDelivery() {
        Transport transport = TransportFactory.createTransport(transportType);
        transport.deliver();
    }
}
