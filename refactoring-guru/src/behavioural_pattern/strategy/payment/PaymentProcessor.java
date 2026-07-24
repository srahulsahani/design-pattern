package behavioural_pattern.strategy.payment;

public class PaymentProcessor {
    private PaymentStrategy strategy;

    public PaymentProcessor(PaymentStrategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy){
        this.strategy = strategy;
    }

    public void processPayment(Double amount){
        strategy.pay(amount);
    }

}
