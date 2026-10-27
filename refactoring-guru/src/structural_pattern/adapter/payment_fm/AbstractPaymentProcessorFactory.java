package structural_pattern.adapter.payment_fm;

public abstract class AbstractPaymentProcessorFactory {
    
    public abstract PaymentProcessor createPaymentProcessor(String paymentDetails);
}
