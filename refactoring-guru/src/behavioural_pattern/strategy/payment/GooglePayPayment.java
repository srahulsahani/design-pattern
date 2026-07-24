package behavioural_pattern.strategy.payment;

public class GooglePayPayment implements PaymentStrategy {
    private String pin;

    public GooglePayPayment(String pin) {
        this.pin = pin;
    }

    @Override
    public void pay(Double amount) {
        System.out.println("Processing $" + amount + " payment through Google Pay");
    }
}
