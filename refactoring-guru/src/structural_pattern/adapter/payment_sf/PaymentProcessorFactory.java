package structural_pattern.adapter.payment_sf;

public class PaymentProcessorFactory {
    
    public static PaymentProcessor createPaymentProcessor(String paymentMethod, String paymentDetails) {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
        
        return switch (paymentMethod.toLowerCase()) {
            case "stripe" -> new StripeAdapter(paymentDetails);
            case "razorpay" -> new RazorPayAdapter(paymentDetails);
            default -> throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        };
    }
}
