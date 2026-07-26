# 🔌 Adapter Design Pattern + Simple Factory - Payment Example

## 📖 What is Adapter + Simple Factory?

This implementation combines two design patterns:
- **Adapter Pattern**: Allows objects with incompatible interfaces to collaborate by wrapping them in an adapter
- **Simple Factory Pattern**: Provides a single class with a static method to create objects without exposing the instantiation logic to the client

### 🎯 Main Purpose

- **Bridge incompatible interfaces**: Allow classes with incompatible interfaces to work together
- **Centralize object creation**: Use Simple Factory to create payment processors without exposing instantiation logic
- **Decouple client from concrete classes**: Client doesn't need to know which adapter class to instantiate
- **Promote simplicity**: Simple Factory is the easiest factory pattern to implement and understand
- **Clean code**: Eliminate conditional statements from client code

---

## 🔧 Implementation in This Example

This implementation demonstrates a **payment processing system** where:
1. Different payment gateways (Stripe, RazorPay) with incompatible interfaces are adapted to work with a unified `PaymentProcessor` interface (Adapter pattern)
2. A `PaymentProcessorFactory` (Simple Factory) with a static method creates the appropriate payment processor based on the payment method

### 📁 File Structure

```
adapter/payment_sf/
├── PaymentProcessor.java         # Target interface
├── StripeAdapter.java            # Adapter for Stripe payment gateway
├── RazorPayAdapter.java          # Adapter for RazorPay payment gateway
├── PaymentProcessorFactory.java   # Simple Factory class
└── Main.java                      # Demo code showing Adapter + Simple Factory usage
```

### 🏗️ Architecture

- **Target Interface**: `PaymentProcessor` - defines the unified `pay(Double amountInUSD)` method
- **Adapters**: `StripeAdapter`, `RazorPayAdapter` - implement the target interface and adapt different payment gateways
- **Simple Factory**: `PaymentProcessorFactory` - single class with static method to create payment processors
- **Client**: `Main` - uses the factory to get payment processors and processes payments

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│         PaymentProcessorFactory (Simple Factory)            │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + createPaymentProcessor(                          │   │
│  │    String paymentMethod,                             │   │
│  │    String paymentDetails): PaymentProcessor         │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ creates (using switch/case)
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

1. **Client** specifies payment method and payment details (card number or phone number)
2. **Client** calls `PaymentProcessorFactory.createPaymentProcessor()` with payment method and details
3. **Simple Factory** uses conditional logic (switch/case) to determine which adapter to create
4. **Factory** returns the appropriate adapter instance (StripeAdapter or RazorPayAdapter)
5. **Client** calls `pay()` on the returned payment processor with the amount in USD
6. **Adapter** receives the payment request and performs necessary conversions (e.g., RazorPay converts USD to INR/paise)
7. **Adapter** processes the payment using the specific gateway's requirements
8. **Payment** is processed and result is displayed

---

## ✅ Problem Solved by Adapter + Simple Factory

### ❌ Without Adapter + Simple Factory Pattern

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
- Object creation logic scattered throughout client code

### ✅ With Adapter + Simple Factory Pattern

```java
// Loose coupling - client uses Simple Factory to create payment processors
String selectedMethod = "razorpay";
String paymentDetails = "9876543210";

// Simple Factory handles object creation
PaymentProcessor processor = PaymentProcessorFactory.createPaymentProcessor(selectedMethod, paymentDetails);

// Client doesn't need to know which adapter is being used
processor.pay(50.0);
```

**Benefits:**
- **Centralized Creation**: All object creation logic is in one place (the factory)
- **Single Responsibility**: Each adapter handles one payment gateway's adaptation logic; factory handles object creation
- **Loose Coupling**: Client doesn't need to know about gateway-specific implementations or how objects are created
- **Encapsulation**: Gateway-specific logic (currency conversion, parameters) is encapsulated in adapters
- **Simplicity**: Simple Factory is the easiest factory pattern to implement and understand
- **Reusability**: Adapters and factory can be reused across different parts of the application
- **Testability**: Each adapter and the factory can be tested independently
- **Clean Code**: Eliminates conditional statements and gateway-specific logic from client

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                   PaymentProcessorFactory                        │
│                   (Simple Factory)                               │
├─────────────────────────────────────────────────────────────────┤
│ + createPaymentProcessor(String, String): PaymentProcessor      │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ creates
                              ▼
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

- **PaymentProcessorFactory** → **PaymentProcessor**: Creation (Factory creates PaymentProcessor instances)
- **PaymentProcessorFactory** → **StripeAdapter**: Creation (Factory creates StripeAdapter instances)
- **PaymentProcessorFactory** → **RazorPayAdapter**: Creation (Factory creates RazorPayAdapter instances)
- **PaymentProcessor** ← **StripeAdapter**: Implementation (StripeAdapter implements PaymentProcessor)
- **PaymentProcessor** ← **RazorPayAdapter**: Implementation (RazorPayAdapter implements PaymentProcessor)
- **Main** → **PaymentProcessorFactory**: Usage (Client uses factory to create objects)
- **Main** → **PaymentProcessor**: Dependency (Client uses the target interface)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Target Interface** (`PaymentProcessor`) | Interface | Defines the unified interface that the client expects |
| **Adapter** (`StripeAdapter`, `RazorPayAdapter`) | Implementation | Implements the target interface and adapts incompatible interfaces |
| **Simple Factory** (`PaymentProcessorFactory`) | Creator | Single class with static method to create appropriate adapter instances |
| **Client** (`Main`) | Consumer | Uses the factory to get payment processors and processes payments |

---

## 💡 When to Use Adapter + Simple Factory

- ✅ When you want to use an existing class whose interface is incompatible with the rest of your system
- ✅ When you want to centralize object creation logic in one place
- ✅ When you need to use several existing subclasses but it's impractical to adapt their interface by subclassing every one
- ✅ When you want to decouple client code from specific implementations and object creation logic
- ✅ When you need to integrate third-party libraries or APIs with incompatible interfaces
- ✅ When you want a simple solution for object creation without the complexity of other factory patterns
- ✅ When you have a small number of object types to create

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
RazorPay: Paid 4350.0 Rs using phone Number 9876543210
```

This demonstrates how the Adapter + Simple Factory pattern allows the client to process payments through different gateways (Stripe, RazorPay) using a unified interface. The Simple Factory handles the object creation with conditional logic, and the adapters handle the gateway-specific logic (currency conversion, parameters). The client code remains clean and doesn't need to know about the implementation details.

---

## 🔍 Real-World Use Cases

- **Payment Gateways**: Integrating different payment providers (Stripe, PayPal, RazorPay) with a unified interface and simple factory for creation
- **Database Adapters**: Adapting different database APIs (JDBC, JPA, MongoDB) to work with a common interface with simple factory for connection creation
- **API Integration**: Adapting third-party APIs with different response formats to a standard format with simple factory for client creation
- **Legacy Code Integration**: Making legacy systems work with modern architectures using adapters and simple factory
- **File Format Converters**: Adapting different file formats (JSON, XML, CSV) to a common data structure with simple factory for reader creation
- **Logging Frameworks**: Adapting different logging libraries to a common logging interface with simple factory for logger creation
- **Cloud Storage**: Adapting different cloud storage providers (AWS S3, Azure Blob, Google Cloud) to a unified interface with simple factory for client creation

---

## 🎓 Key Improvements Over Basic Adapter

### Comparison: Basic Adapter vs Adapter + Simple Factory

| Aspect | Basic Adapter | Adapter + Simple Factory |
|--------|--------------|---------------------------|
| **Object Creation** | Client creates adapters directly using switch/case | Simple Factory handles object creation with switch/case |
| **Client Code** | Contains conditional logic for object creation | Clean, no conditional logic |
| **Coupling** | Client knows about concrete adapter classes | Client only knows about factory and interface |
| **Maintainability** | Adding new gateway requires modifying client code | Adding new gateway requires updating factory |
| **Testability** | Harder to test (client has creation logic) | Easier to test (factory can be mocked) |
| **Single Responsibility** | Client handles both creation and usage | Client only handles usage, factory handles creation |
| **Complexity** | No additional classes | One additional factory class |

### Code Comparison

**Basic Adapter (from payment/):**
```java
// Client has creation logic
PaymentProcessor processor = switch (selectedMethod.toLowerCase()) {
    case "stripe" -> new StripeAdapter("1234-5678-9101-1213");
    case "razorpay" -> new RazorPayAdapter("9876543210");
    default -> throw new IllegalArgumentException("Invalid payment method selected");
};
```

**Adapter + Simple Factory (from payment_sf/):**
```java
// Simple Factory handles creation logic
PaymentProcessor processor = PaymentProcessorFactory.createPaymentProcessor(selectedMethod, paymentDetails);
```

---

## 🎓 Simple Factory vs Factory Method vs Abstract Factory

### Comparison Table

| Aspect | Simple Factory | Factory Method | Abstract Factory |
|--------|--------------|----------------|------------------|
| **Structure** | Single class with static method | Abstract creator + concrete subclasses | Abstract factory + concrete factories |
| **Object Creation** | Conditional logic in one class | Each subclass creates one product type | Each factory creates product families |
| **Inheritance** | No inheritance required | Uses inheritance (subclasses) | Uses inheritance (subclasses) |
| **Polymorphism** | No polymorphism | Uses polymorphism | Uses polymorphism |
| **Open/Closed** | Violates (must modify factory) | Follows (add new subclass) | Follows (add new subclass) |
| **Complexity** | Simplest | Moderate | Most complex |
| **Use Case** | Small number of object types | One product hierarchy | Multiple product families |
| **Flexibility** | Low | High | Highest |

### When to Use Which

- **Simple Factory**: When you have a small number of object types and want a simple solution without inheritance
- **Factory Method**: When you have one product hierarchy and want to use polymorphism for object creation
- **Abstract Factory**: When you need to create families of related products (multiple product hierarchies)

---

## 🎓 Interview Questions

### Q1: What is the Simple Factory pattern?

**Answer:**
Simple Factory is a creational pattern that provides a single class with a static method to create objects without exposing the instantiation logic to the client. It uses conditional logic (if/else or switch/case) to determine which object to create based on input parameters.

### Q2: How does Simple Factory differ from Factory Method?

**Answer:**
- **Simple Factory**: One factory class with a static method that uses conditional logic to decide which object to create
- **Factory Method**: Abstract creator class with a factory method that subclasses implement to decide which object to create
- Simple Factory doesn't use inheritance, while Factory Method uses inheritance and polymorphism
- Simple Factory violates Open/Closed Principle (must modify factory to add new types), while Factory Method follows it (add new subclass)

### Q3: What are the advantages of Simple Factory?

**Answer:**
- **Simplicity**: Easiest factory pattern to implement and understand
- **Centralized Creation**: All object creation logic is in one place
- **Decoupling**: Client doesn't need to know about concrete classes
- **Testability**: Factory can be mocked for testing
- **Clean Code**: Eliminates conditional statements from client code

### Q4: What are the disadvantages of Simple Factory?

**Answer:**
- **Violates Open/Closed Principle**: Must modify the factory to add new object types
- **No Polymorphism**: Doesn't take advantage of polymorphism like Factory Method
- **Tight Coupling**: Factory is coupled to all concrete classes it can create
- **Hard to Extend**: Adding new types requires modifying existing code

### Q5: When should you use Simple Factory instead of Factory Method?

**Answer:**
Use Simple Factory when:
- You have a small number of object types to create
- You want a simple solution without the complexity of inheritance
- The object types don't change frequently
- You don't need the flexibility of polymorphism
- You're working on a small project or prototype

### Q6: What happens if we need to add a new payment gateway with Simple Factory?

**Answer:**
With Adapter + Simple Factory:
1. Create a new adapter class (e.g., `PayPalAdapter`) that implements `PaymentProcessor`
2. Modify the `PaymentProcessorFactory` to add a new case in the switch statement
3. No changes needed in client code
This violates the Open/Closed Principle because you must modify the existing factory class.
