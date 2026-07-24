package behavioural_pattern.strategy.payment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        double amount = 0;
        System.out.println("Enter Payment Amount : $ ");
        if(sc.hasNextDouble()){
            amount = sc.nextDouble();
        }
        sc.nextLine();


        System.out.println("Select payment method:");
        System.out.println("1. PayPal");
        System.out.println("2. GPay");
        System.out.println("3. Credit Card");
        System.out.println("4. Your Choice");
        int choice = sc.nextInt();
        sc.nextLine();


        PaymentStrategy strategy = null;
        //We can use factory design pattern to creating strategies

        switch (choice){
            case 1:
                System.out.println("Enter you Paypal Email: ");
                String email = sc.nextLine();
                strategy = new PayPalPayment(email);
                break;

            case 2:
                System.out.println("Enter your pin: ");
                String pin = sc.nextLine();
                strategy = new GooglePayPayment(pin);
                break;
            case 3:
                System.out.println("Enter your name: ");
                String name = sc.nextLine();
                System.out.println("Enter your Card Number: ");
                String cardNo = sc.nextLine();
                System.out.println("Enter your CVV :");
                String cvv = sc.nextLine();
                System.out.println("Enter your Expiry Date: ");
                String expiryDate = sc.nextLine();
                strategy = new CreditCardPayment(name,cardNo,cvv,expiryDate);
                break;
            default:
                System.out.println("Invalid Choice: Exiting...");
                return;
        }

        PaymentProcessor processor = new PaymentProcessor(strategy);
        processor.processPayment(amount);

        sc.close();

    }
}
