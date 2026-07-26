package structural_pattern.adapter.payment;

public class Main {
    public static void main(String[] args) {
        String selectedMethod = "razorpay";

        //Old Switch Case
/*        switch (selectedMethod.toLowerCase()){
            case "stripe":
                processor = new StripeAdapter("1234-5678-9101-1213");
                break;
            case "razorpay":
                processor = new RazorPayAdapter("9876543210");
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method selected");
        }*/

        //New Switch Case
         PaymentProcessor processor = switch (selectedMethod.toLowerCase()) {
             case "stripe" -> new StripeAdapter("1234-5678-9101-1213");
             case "razorpay" -> new RazorPayAdapter("9876543210");
             default -> throw new IllegalArgumentException("Invalid payment method selected");
         };



        processor.pay(50.0);
    }
}
