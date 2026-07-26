package structural_pattern.adapter.payment_fm;

public class StripeAdapter implements PaymentProcessor{
    private final String cardNo;

    public StripeAdapter(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public void pay(Double amountInUSD) {
        System.out.println("Stripe: Paid $ "+ amountInUSD + " using Card Number: " + cardNo);
    }
}
