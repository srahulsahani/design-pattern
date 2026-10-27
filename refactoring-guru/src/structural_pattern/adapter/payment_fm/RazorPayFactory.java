package structural_pattern.adapter.payment_fm;

public class RazorPayFactory extends AbstractPaymentProcessorFactory {
    
    @Override
    public PaymentProcessor createPaymentProcessor(String paymentDetails) {
        return new RazorPayAdapter(paymentDetails);
    }
}
