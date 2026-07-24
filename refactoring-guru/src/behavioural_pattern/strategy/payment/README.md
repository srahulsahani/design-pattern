# 🎯 Strategy Design Pattern - Payment Example

## 📖 What is Strategy?

The **Strategy** is a behavioral design pattern that lets you define a family of algorithms, put each in a separate class, and make their objects interchangeable. The pattern lets the algorithm vary independently from clients that use it.

### 🎯 Main Purpose

- **Define a family of algorithms**: Encapsulate each algorithm in a separate class
- **Make algorithms interchangeable**: Allow algorithms to be swapped at runtime
- **Separate algorithm from context**: Decouple the algorithm implementation from the code that uses it
- **Eliminate conditional statements**: Replace complex conditional logic with strategy objects
- **Open/Closed Principle**: Add new strategies without modifying existing code
- **Runtime flexibility**: Change algorithm behavior at runtime without changing the context

---

## 🔧 Implementation in This Example

This implementation demonstrates a **payment processing system** where different payment methods (Credit Card, PayPal, Google Pay) can be selected and used interchangeably to process payments.

### 📁 File Structure

```
strategy/payment/
├── PaymentStrategy.java      # Strategy interface
├── PaymentProcessor.java     # Context class
├── CreditCardPayment.java    # Concrete strategy (Credit Card)
├── PayPalPayment.java        # Concrete strategy (PayPal)
├── GooglePayPayment.java     # Concrete strategy (Google Pay)
└── Main.java                 # Demo code showing strategy usage
```

### 🏗️ Architecture

- **Strategy Interface**: `PaymentStrategy` - defines the `pay()` method that all payment strategies must implement
- **Concrete Strategies**: `CreditCardPayment`, `PayPalPayment`, `GooglePayPayment` - implement specific payment logic
- **Context**: `PaymentProcessor` - maintains a reference to a strategy object and delegates payment processing
- **Client**: `Main` - creates strategies and uses the context to process payments

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              PaymentProcessor (Context)                      │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - strategy: PaymentStrategy                         │   │
│  │  + PaymentProcessor(PaymentStrategy)                 │   │
│  │  + setStrategy(PaymentStrategy): void                │   │
│  │  + processPayment(Double): void                      │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ uses
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              <<interface>> PaymentStrategy                  │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + pay(Double): void                                │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │                       │
              ▼                       ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐  ┌─────────────────────────┐
│   CreditCardPayment     │  │     PayPalPayment       │  │   GooglePayPayment      │
│   (Concrete Strategy)   │  │   (Concrete Strategy)   │  │   (Concrete Strategy)   │
│  + pay(Double)          │  │  + pay(Double)          │  │  + pay(Double)          │
└─────────────────────────┘  └─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** selects a payment method (PayPal, Google Pay, or Credit Card)
2. **Client** creates the appropriate concrete strategy object with required parameters
3. **Client** creates `PaymentProcessor` with the selected strategy
4. **Client** calls `processPayment()` with the payment amount
5. **PaymentProcessor** delegates the payment processing to the strategy's `pay()` method
6. **Concrete Strategy** executes its specific payment logic
7. **Payment** is processed according to the selected payment method

---

## ✅ Problem Solved by Strategy

### ❌ Without Strategy Pattern

```java
// Tight coupling - payment logic mixed with conditional statements
public class PaymentProcessor {
    public void processPayment(String paymentType, Double amount, String... params) {
        if (paymentType.equals("creditcard")) {
            String name = params[0];
            String cardNo = params[1];
            String cvv = params[2];
            String expiryDate = params[3];
            System.out.println("Processing $" + amount + " with Credit Card belong to " + name + ".");
        } else if (paymentType.equals("paypal")) {
            String email = params[0];
            System.out.println("Processing $" + amount + " through Paypal");
        } else if (paymentType.equals("gpay")) {
            String pin = params[0];
            System.out.println("Processing $" + amount + " through Google Pay");
        }
        // Problem: Adding new payment method requires modifying this code
        // Problem: Hard to maintain and test
        // Problem: Violates Open/Closed Principle
    }
}
```

**Issues:**
- Tight coupling between payment logic and conditional statements
- Violates Open/Closed Principle (must modify code to add new payment methods)
- Hard to maintain and test with growing number of payment methods
- Difficult to add new payment methods without breaking existing code
- Payment logic is scattered and not reusable

### ✅ With Strategy Pattern

```java
// Loose coupling - each payment method is a separate strategy
PaymentStrategy strategy = new PayPalPayment("user@example.com");
PaymentProcessor processor = new PaymentProcessor(strategy);
processor.processPayment(100.0);

// Can easily switch to another payment method
processor.setStrategy(new GooglePayPayment("1234"));
processor.processPayment(200.0);
```

**Benefits:**
- **Open/Closed Principle**: New payment methods can be added by creating new strategy classes
- **Single Responsibility**: Each strategy class handles one payment method
- **Runtime Flexibility**: Payment methods can be changed at runtime
- **Loose Coupling**: Context doesn't need to know concrete strategy implementations
- **Reusability**: Strategies can be reused across different contexts
- **Testability**: Each strategy can be tested independently
- **Clean Code**: Eliminates complex conditional statements

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                   <<interface>>                                 │
│                      PaymentStrategy                            │
├─────────────────────────────────────────────────────────────────┤
│ + pay(Double amount): void                                      │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┼───────────────┐
              │               │               │
┌─────────────────────────┐ ┌─────────────────────────┐ ┌─────────────────────────┐
│   CreditCardPayment     │ │     PayPalPayment       │ │   GooglePayPayment      │
├─────────────────────────┤ ├─────────────────────────┤ ├─────────────────────────┤
│ - name: String          │ │ - pin: String           │ │ - pin: String           │
│ - cardNo: String        │ ├─────────────────────────┤ ├─────────────────────────┤
│ - cvv: String           │ │ + PayPalPayment(String) │ │ + GooglePayPayment(String)│
│ - expiryDate: String    │ │ + pay(Double)            │ │ + pay(Double)            │
├─────────────────────────┤ └─────────────────────────┘ └─────────────────────────┘
│ + CreditCardPayment(    │
│   String, String,      │
│   String, String)       │
│ + pay(Double)           │
└─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                   PaymentProcessor (Context)                    │
├─────────────────────────────────────────────────────────────────┤
│ - strategy: PaymentStrategy                                     │
├─────────────────────────────────────────────────────────────────┤
│ + PaymentProcessor(PaymentStrategy)                            │
│ + setStrategy(PaymentStrategy): void                            │
│ + processPayment(Double): void                                  │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ uses
                              ▼
                      ┌───────────────┐
                      │ PaymentStrategy│
                      └───────────────┘
```

### Relationship Types

- **PaymentStrategy** ← **CreditCardPayment**: Implementation (CreditCardPayment implements PaymentStrategy)
- **PaymentStrategy** ← **PayPalPayment**: Implementation (PayPalPayment implements PaymentStrategy)
- **PaymentStrategy** ← **GooglePayPayment**: Implementation (GooglePayPayment implements PaymentStrategy)
- **PaymentProcessor** → **PaymentStrategy**: Association (Context maintains reference to Strategy)
- **PaymentProcessor** → **PaymentStrategy**: Usage (Context delegates to Strategy)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Strategy** (`PaymentStrategy`) | Interface | Declares the interface common to all supported algorithms |
| **Concrete Strategy** (`CreditCardPayment`, `PayPalPayment`, `GooglePayPayment`) | Implementation | Implements the Strategy interface with specific algorithm |
| **Context** (`PaymentProcessor`) | Client | Maintains a reference to a Strategy object and delegates work |
| **Client** (`Main`) | Consumer | Creates concrete strategy objects and configures the context |

---

## 💡 When to Use Strategy

- ✅ When you want to use different variants of an algorithm within an object and be able to switch from one algorithm to another during runtime
- ✅ When you have a lot of similar classes that only differ in how they execute some behavior
- ✅ When you want to isolate the implementation details of an algorithm from the code that uses it
- ✅ When a class has a massive conditional operator (switch/case) that switches between different behaviors
- ✅ When you want to eliminate conditional statements and make your code more maintainable
- ✅ When you need to add new algorithms without modifying the existing code (Open/Closed Principle)

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
Enter Payment Amount : $ 
100
Select payment method:
1. PayPal
2. GPay
3. Credit Card
4. Your Choice
1
Enter you Paypal Email: 
user@example.com
Processing $100.0 payment through Paypal
```

This demonstrates how the Strategy pattern allows different payment methods to be selected and used interchangeably without modifying the payment processing logic.

---

## 🔍 Real-World Use Cases

- **Payment Processing Systems**: Different payment methods (Credit Card, PayPal, Stripe, etc.)
- **Sorting Algorithms**: Different sorting strategies (Quick Sort, Merge Sort, Bubble Sort)
- **Compression Algorithms**: Different compression methods (ZIP, GZIP, LZMA)
- **Route Planning**: Different routing strategies (Fastest, Shortest, Avoid Tolls)
- **Authentication**: Different authentication methods (OAuth, JWT, Basic Auth)
- **Data Validation**: Different validation strategies (Email, Phone, Credit Card)
- **File Export**: Different export formats (PDF, Excel, CSV, JSON)
- **Caching Strategies**: Different caching policies (LRU, FIFO, Time-based)

---

## 🎓 Interview Questions

### Q1: What is the main difference between Strategy and State patterns?

**Answer:**
- **Strategy** is about interchangeable algorithms - the strategy is set by the client and typically doesn't change frequently
- **State** is about changing behavior based on internal state - the state transitions are managed internally and change frequently
- Strategy focuses on algorithm variation, while State focuses on state-dependent behavior

### Q2: How does Strategy pattern promote the Open/Closed Principle?

**Answer:**
The Strategy pattern allows you to add new algorithms (strategies) without modifying the existing context class. You simply create a new concrete strategy class that implements the strategy interface, and the context can use it without any changes to its code.

### Q3: Can a strategy have access to the context's data?

**Answer:**
Yes, a strategy can access the context's data if needed. This can be done by:
1. Passing the context as a parameter to the strategy's method
2. Having the strategy hold a reference to the context
3. Passing only the necessary data to the strategy's method

### Q4: When should you use Strategy vs. Template Method patterns?

**Answer:**
- Use **Strategy** when you want to completely replace algorithms and make them interchangeable at runtime
- Use **Template Method** when you want to define the skeleton of an algorithm and let subclasses override certain steps while keeping the overall structure the same

### Q5: How do you choose which strategy to use at runtime?

**Answer:**
The strategy can be selected based on:
- User input (like in this payment example)
- Configuration settings
- Environmental conditions (e.g., based on OS, browser, etc.)
- Business rules
- Performance characteristics

### Q6: Can strategies be combined or chained together?

**Answer:**
Yes, strategies can be combined using patterns like:
- **Chain of Responsibility**: Strategies can be chained and each can handle the request or pass it to the next
- **Composite**: Multiple strategies can be composed into a single strategy
- **Decorator**: Strategies can be decorated with additional behavior
