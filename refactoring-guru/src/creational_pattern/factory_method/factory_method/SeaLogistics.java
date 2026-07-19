package creational_pattern.factory_method.factory_method;

public class SeaLogistics extends Logistics {
    
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}
