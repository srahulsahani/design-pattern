package behavioural_pattern.strategy.payment;

public class CreditCardPayment implements PaymentStrategy{
    private String name;
    private String cardNo;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String name, String cardNo, String cvv, String expiryDate) {
        this.name = name;
        this.cardNo = cardNo;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public void pay(Double amount) {
        System.out.println("Processing $" + amount + " payment with Credit Card belong to " + name + ".");
    }
}
