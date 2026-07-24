package behavioural_pattern.strategy.payment;

public class PayPalPayment implements PaymentStrategy {
    private String pin;

    public PayPalPayment(String pin) {
        this.pin = pin;
    }

    @Override
    public void pay(Double amount) {
        System.out.println("Processing $" + amount + " payment through Paypal");
    }
}
