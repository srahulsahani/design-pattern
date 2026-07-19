package creational_pattern.factory_method;

public abstract class Logistics {
    public abstract Transport createTransport();

    public void planDelivery(){
        Transport transport = createTransport();
        transport.deliver();
    }
}
