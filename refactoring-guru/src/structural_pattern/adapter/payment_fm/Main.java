package structural_pattern.adapter.payment_fm;

public class Main {
    public static void main(String[] args) {
        String selectedMethod = "razorpay";
        String paymentDetails = "9876543210";

        // Using Factory Method pattern to create payment processor
        AbstractPaymentProcessorFactory factory = switch (selectedMethod.toLowerCase()) {
            case "stripe" -> new StripeFactory();
            case "razorpay" -> new RazorPayFactory();
            default -> throw new IllegalArgumentException("Invalid payment method: " + selectedMethod);
        };

        PaymentProcessor processor = factory.createPaymentProcessor(paymentDetails);
        processor.pay(50.0);
    }
}
