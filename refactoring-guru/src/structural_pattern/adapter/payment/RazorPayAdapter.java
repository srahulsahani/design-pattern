package structural_pattern.adapter.payment;

public class RazorPayAdapter implements PaymentProcessor {
    private final String phoneNumber;
    private static final double USD_TO_INR = 87.0;

    public RazorPayAdapter(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public void pay(Double amountInUSD) {
        double amountInINR = amountInUSD *USD_TO_INR;
        int amountInPaise = (int) (amountInINR * 100);

        System.out.println("RazorPay: Paid " + amountInPaise/100.0 + " Rs using phone Number " + phoneNumber);
    }
}

