package structural_pattern.adapter.payment_sf;

public class Main {
    public static void main(String[] args) {
        String selectedMethod = "razorpay";
        String paymentDetails = "9876543210";

        // Using Simple Factory to create payment processor
        PaymentProcessor processor = PaymentProcessorFactory.createPaymentProcessor(selectedMethod, paymentDetails);

        processor.pay(50.0);
    }
}
