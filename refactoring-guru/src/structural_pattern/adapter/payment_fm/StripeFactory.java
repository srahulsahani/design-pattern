package structural_pattern.adapter.payment_fm;

public class StripeFactory extends AbstractPaymentProcessorFactory {
    
    @Override
    public PaymentProcessor createPaymentProcessor(String paymentDetails) {
        return new StripeAdapter(paymentDetails);
    }
}
