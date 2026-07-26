# 🔌 Adapter Design Pattern - Payment Example

## 📖 What is Adapter?

The **Adapter** is a structural design pattern that allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces, wrapping an object in an adapter to make it compatible with another class.

### 🎯 Main Purpose

- **Bridge incompatible interfaces**: Allow classes with incompatible interfaces to work together
- **Convert interface**: Transform one interface into another expected by the client
- **Reuse existing code**: Use existing classes without modifying their source code
- **Decouple client from implementation**: Client code doesn't need to know about the adapted object's interface
- **Promote flexibility**: Easy to add new adapters for different implementations

---

## 🔧 Implementation in This Example

This implementation demonstrates a **payment processing system** where different payment gateways (Stripe, RazorPay) with incompatible interfaces are adapted to work with a unified `PaymentProcessor` interface.

### 📁 File Structure

```
adapter/payment/
├── PaymentProcessor.java      # Target interface
├── StripeAdapter.java         # Adapter for Stripe payment gateway
├── RazorPayAdapter.java       # Adapter for RazorPay payment gateway
└── Main.java                  # Demo code showing adapter usage
```

### 🏗️ Architecture

- **Target Interface**: `PaymentProcessor` - defines the unified `pay(Double amountInUSD)` method
- **Adapters**: `StripeAdapter`, `RazorPayAdapter` - implement the target interface and adapt different payment gateways
- **Client**: `Main` - uses the unified interface to process payments through different gateways

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              PaymentProcessor (Target Interface)              │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + pay(Double amountInUSD): void                   │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│   StripeAdapter         │  │   RazorPayAdapter      │
│   (Adapter)             │  │   (Adapter)            │
│  ┌─────────────────────┐│  │  ┌─────────────────────┐│
│  │ - cardNo: String    ││  │  │ - phoneNumber: String││
│  │ + StripeAdapter(   ││  │  │ + RazorPayAdapter(  ││
│  │   String)          ││  │  │   String)          ││
│  │ + pay(Double)       ││  │  │ + pay(Double)       ││
│  └─────────────────────┘│  │  └─────────────────────┘│
│  Adapts Stripe API      │  │  Adapts RazorPay API    │
│  (USD, Card Number)     │  │  (INR conversion, Phone)│
└─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** selects a payment method (stripe or razorpay)
2. **Client** creates the appropriate adapter with required parameters (card number or phone number)
3. **Client** calls `pay()` with the amount in USD
4. **Adapter** receives the payment request in USD
5. **Adapter** performs necessary conversions (e.g., RazorPay converts USD to INR/paise)
6. **Adapter** processes the payment using the specific gateway's requirements
7. **Payment** is processed and result is displayed

---

## ✅ Problem Solved by Adapter

### ❌ Without Adapter Pattern

```java
// Tight coupling - client must handle different payment gateways directly
public class PaymentClient {
    public void processPayment(String gateway, Double amount, String... params) {
        if (gateway.equals("stripe")) {
            String cardNo = params[0];
            // Stripe-specific code
            System.out.println("Stripe: Paid $" + amount + " using Card: " + cardNo);
        } else if (gateway.equals("razorpay")) {
            String phone = params[0];
            // RazorPay-specific code with currency conversion
            double amountInINR = amount * 87.0;
            int amountInPaise = (int) (amountInINR * 100);
            System.out.println("RazorPay: Paid " + amountInPaise/100.0 + " Rs using Phone: " + phone);
        }
        // Problem: Adding new gateway requires modifying this code
        // Problem: Currency conversion logic mixed with client code
        // Problem: Violates Open/Closed Principle
    }
}
```

**Issues:**
- Tight coupling between client and specific payment gateway implementations
- Gateway-specific logic (currency conversion, parameters) mixed with client code
- Violates Open/Closed Principle (must modify code to add new gateways)
- Hard to maintain and test with growing number of payment gateways
- Client needs to know implementation details of each gateway

### ✅ With Adapter Pattern

```java
// Loose coupling - client works with unified interface
PaymentProcessor processor = switch (selectedMethod.toLowerCase()) {
    case "stripe" -> new StripeAdapter("1234-5678-9101-1213");
    case "razorpay" -> new RazorPayAdapter("9876543210");
    default -> throw new IllegalArgumentException("Invalid payment method selected");
};

processor.pay(50.0);  // Unified interface - no need to know gateway details
```

**Benefits:**
- **Open/Closed Principle**: New payment gateways can be added by creating new adapter classes
- **Single Responsibility**: Each adapter handles one payment gateway's adaptation logic
- **Loose Coupling**: Client doesn't need to know about gateway-specific implementations
- **Encapsulation**: Gateway-specific logic (currency conversion, parameters) is encapsulated in adapters
- **Reusability**: Adapters can be reused across different parts of the application
- **Testability**: Each adapter can be tested independently
- **Clean Code**: Eliminates conditional statements and gateway-specific logic from client

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                   <<interface>>                                 │
│                   PaymentProcessor                               │
├─────────────────────────────────────────────────────────────────┤
│ + pay(Double amountInUSD): void                                 │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    StripeAdapter        │   │    RazorPayAdapter      │
├─────────────────────────┤   ├─────────────────────────┤
│ - cardNo: String        │   │ - phoneNumber: String   │
├─────────────────────────┤   │ - USD_TO_INR: double    │
│ + StripeAdapter(        │   │   (constant: 87.0)      │
│   String)               │   ├─────────────────────────┤
│ + pay(Double)           │   │ + RazorPayAdapter(      │
│ "Processes in USD"      │   │   String)               │
└─────────────────────────┘   │ + pay(Double)           │
                              │ "Converts USD to INR"   │
                              └─────────────────────────┘
```

### Relationship Types

- **PaymentProcessor** ← **StripeAdapter**: Implementation (StripeAdapter implements PaymentProcessor)
- **PaymentProcessor** ← **RazorPayAdapter**: Implementation (RazorPayAdapter implements PaymentProcessor)
- **Main** → **PaymentProcessor**: Dependency (Client uses the target interface)
- **Main** → **StripeAdapter**: Usage (Client creates concrete adapter)
- **Main** → **RazorPayAdapter**: Usage (Client creates concrete adapter)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Target Interface** (`PaymentProcessor`) | Interface | Defines the unified interface that the client expects |
| **Adapter** (`StripeAdapter`, `RazorPayAdapter`) | Implementation | Implements the target interface and adapts incompatible interfaces |
| **Client** (`Main`) | Consumer | Uses the target interface to interact with objects |

---

## 💡 When to Use Adapter

- ✅ When you want to use an existing class whose interface is incompatible with the rest of your system
- ✅ When you want to create a reusable class that cooperates with unrelated or unforeseen classes
- ✅ When you need to use several existing subclasses but it's impractical to adapt their interface by subclassing every one
- ✅ When you want to decouple client code from specific implementations
- ✅ When you need to integrate third-party libraries or APIs with incompatible interfaces
- ✅ When you want to provide different interfaces for the same underlying implementation

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
RazorPay: Paid 4350.0 Rs using phone Number 9876543210
```

This demonstrates how the Adapter pattern allows the client to process payments through different gateways (Stripe, RazorPay) using a unified interface. The RazorPay adapter automatically converts the USD amount to INR (using the conversion rate of 87.0) and processes the payment in paise, while the Stripe adapter processes directly in USD.

---

## 🔍 Real-World Use Cases

- **Payment Gateways**: Integrating different payment providers (Stripe, PayPal, RazorPay) with a unified interface
- **Database Adapters**: Adapting different database APIs (JDBC, JPA, MongoDB) to work with a common interface
- **API Integration**: Adapting third-party APIs with different response formats to a standard format
- **Legacy Code Integration**: Making legacy systems work with modern architectures
- **File Format Converters**: Adapting different file formats (JSON, XML, CSV) to a common data structure
- **Logging Frameworks**: Adapting different logging libraries to a common logging interface
- **Cloud Storage**: Adapting different cloud storage providers (AWS S3, Azure Blob, Google Cloud) to a unified interface

---

## 🎓 Interview Questions

### Q1: What is the main difference between Adapter and Facade patterns?

**Answer:**
- **Adapter** makes two existing interfaces work together by converting one interface to another
- **Facade** provides a simplified interface to a complex subsystem, hiding complexity
- Adapter is about interface compatibility, while Facade is about simplifying access

### Q2: What are the two types of Adapter patterns?

**Answer:**
1. **Class Adapter**: Uses inheritance to adapt the interface (requires multiple inheritance in some languages)
2. **Object Adapter**: Uses composition to wrap the adaptee (more flexible, works in single-inheritance languages like Java)

### Q3: How does Adapter pattern promote the Open/Closed Principle?

**Answer:**
The Adapter pattern allows you to add new adapters for new interfaces without modifying existing client code. You simply create a new adapter class that implements the target interface, and the client can use it without any changes.

### Q4: Can an adapter adapt multiple interfaces?

**Answer:**
Yes, an adapter can adapt multiple interfaces if needed. However, it's generally better to keep adapters focused on a single responsibility. If you need to adapt multiple interfaces, consider using multiple adapters or the Facade pattern.

### Q5: When should you use Adapter vs. Decorator patterns?

**Answer:**
- Use **Adapter** when you need to make two incompatible interfaces work together
- Use **Decorator** when you want to add new behavior to an object dynamically without changing its interface
- Adapter changes the interface, Decorator enhances the behavior while keeping the same interface

### Q6: What is the difference between Adapter and Bridge patterns?

**Answer:**
- **Adapter** is used to make existing incompatible interfaces work together (usually after the fact)
- **Bridge** is used to separate abstraction from implementation so both can vary independently (usually designed upfront)
- Adapter is about fixing incompatibility, Bridge is about preventing it
