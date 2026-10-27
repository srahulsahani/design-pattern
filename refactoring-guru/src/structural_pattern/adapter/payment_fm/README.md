# 🔌 Adapter Design Pattern + Factory Method Pattern - Payment Example

## 📖 What is Adapter + Factory Method?

This implementation combines two design patterns:
- **Adapter Pattern**: Allows objects with incompatible interfaces to collaborate by wrapping them in an adapter
- **Factory Method Pattern**: Defines an interface for creating an object but lets subclasses decide which class to instantiate

### 🎯 Main Purpose

- **Bridge incompatible interfaces**: Allow classes with incompatible interfaces to work together
- **Defer object creation to subclasses**: Factory Method lets subclasses decide which class to instantiate
- **Decouple client from concrete classes**: Client doesn't need to know which adapter class to instantiate
- **Promote flexibility**: Easy to add new payment gateways by adding new adapters and new factory subclasses
- **Open/Closed Principle**: New payment methods can be added without modifying existing code (except the client's switch statement)

---

## 🔧 Implementation in This Example

This implementation demonstrates a **payment processing system** where:
1. Different payment gateways (Stripe, RazorPay) with incompatible interfaces are adapted to work with a unified `PaymentProcessor` interface (Adapter pattern)
2. Factory Method pattern is used with concrete factory subclasses (`StripeFactory`, `RazorPayFactory`) that decide which adapter to instantiate

### 📁 File Structure

```
adapter/paymentimproved/
├── PaymentProcessor.java               # Target interface
├── StripeAdapter.java                  # Adapter for Stripe payment gateway
├── RazorPayAdapter.java                # Adapter for RazorPay payment gateway
├── AbstractPaymentProcessorFactory.java  # Abstract creator with factory method
├── StripeFactory.java                  # Concrete creator for Stripe
├── RazorPayFactory.java                # Concrete creator for RazorPay
└── Main.java                          # Demo code showing Adapter + Factory Method usage
```

### 🏗️ Architecture

- **Target Interface**: `PaymentProcessor` - defines the unified `pay(Double amountInUSD)` method
- **Adapters**: `StripeAdapter`, `RazorPayAdapter` - implement the target interface and adapt different payment gateways
- **Abstract Creator**: `AbstractPaymentProcessorFactory` - declares the factory method `createPaymentProcessor()`
- **Concrete Creators**: `StripeFactory`, `RazorPayFactory` - implement the factory method to create specific adapters
- **Client**: `Main` - uses concrete factories to create payment processors and processes payments

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│         AbstractPaymentProcessorFactory                      │
│         (Abstract Creator)                                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + createPaymentProcessor(                          │   │
│  │    String paymentDetails): PaymentProcessor         │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│   StripeFactory         │  │   RazorPayFactory      │
│   (Concrete Creator)    │  │   (Concrete Creator)    │
│  ┌─────────────────────┐│  │  ┌─────────────────────┐│
│  │ + createPayment     ││  │  │ + createPayment     ││
│  │   Processor(String) ││  │  │   Processor(String) ││
│  └─────────────────────┘│  │  └─────────────────────┘│
│  Returns StripeAdapter  │  │  Returns RazorPayAdapter│
└────────────┬────────────┘  └────────────┬────────────┘
             │                            │
             ▼                            ▼
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
2. **Client** creates the appropriate concrete factory based on the payment method (StripeFactory or RazorPayFactory)
3. **Client** calls `createPaymentProcessor()` on the factory with payment details
4. **Concrete Factory** implements the factory method to create the appropriate adapter instance
5. **Factory** returns the adapter instance (StripeAdapter or RazorPayAdapter)
6. **Client** calls `pay()` on the returned payment processor with the amount in USD
7. **Adapter** receives the payment request and performs necessary conversions (e.g., RazorPay converts USD to INR/paise)
8. **Adapter** processes the payment using the specific gateway's requirements
9. **Payment** is processed and result is displayed

---

## ✅ Problem Solved by Adapter + Factory Method

### ❌ Without Adapter + Factory Method Pattern

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

### ✅ With Adapter + Factory Method Pattern

```java
// Loose coupling - client uses concrete factories to create payment processors
String selectedMethod = "razorpay";
String paymentDetails = "9876543210";

// Client creates the appropriate concrete factory
AbstractPaymentProcessorFactory factory = switch (selectedMethod.toLowerCase()) {
    case "stripe" -> new StripeFactory();
    case "razorpay" -> new RazorPayFactory();
    default -> throw new IllegalArgumentException("Invalid payment method: " + selectedMethod);
};

// Factory method creates the appropriate adapter
PaymentProcessor processor = factory.createPaymentProcessor(paymentDetails);

// Client doesn't need to know which adapter is being used
processor.pay(50.0);
```

**Benefits:**
- **Open/Closed Principle**: New payment gateways can be added by creating new adapter classes and new factory subclasses
- **Single Responsibility**: Each adapter handles one payment gateway's adaptation logic; each factory handles creation of one adapter type
- **Loose Coupling**: Client doesn't need to know about gateway-specific implementations or how objects are created
- **Encapsulation**: Gateway-specific logic (currency conversion, parameters) is encapsulated in adapters
- **Polymorphism**: Factory method uses polymorphism - each factory subclass decides which adapter to create
- **Reusability**: Adapters and factories can be reused across different parts of the application
- **Testability**: Each adapter and factory can be tested independently
- **Clean Code**: Object creation logic is delegated to factory subclasses

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│         <<abstract>> AbstractPaymentProcessorFactory            │
├─────────────────────────────────────────────────────────────────┤
│ + createPaymentProcessor(String): PaymentProcessor               │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ extends
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    StripeFactory       │   │    RazorPayFactory      │
├─────────────────────────┤   ├─────────────────────────┤
│ + createPayment        │   │ + createPayment        │
│   Processor(String)    │   │   Processor(String)    │
│ returns StripeAdapter │   │ returns RazorPayAdapter│
└────────────┬────────────┘   └────────────┬────────────┘
             │                            │
             │ creates                    │ creates
             ▼                            ▼
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

- **AbstractPaymentProcessorFactory** ← **StripeFactory**: Inheritance (StripeFactory extends abstract factory)
- **AbstractPaymentProcessorFactory** ← **RazorPayFactory**: Inheritance (RazorPayFactory extends abstract factory)
- **StripeFactory** → **StripeAdapter**: Creation (StripeFactory creates StripeAdapter instances)
- **RazorPayFactory** → **RazorPayAdapter**: Creation (RazorPayFactory creates RazorPayAdapter instances)
- **PaymentProcessor** ← **StripeAdapter**: Implementation (StripeAdapter implements PaymentProcessor)
- **PaymentProcessor** ← **RazorPayAdapter**: Implementation (RazorPayAdapter implements PaymentProcessor)
- **Main** → **AbstractPaymentProcessorFactory**: Dependency (Client uses abstract factory type)
- **Main** → **StripeFactory**: Usage (Client creates concrete factory)
- **Main** → **RazorPayFactory**: Usage (Client creates concrete factory)
- **Main** → **PaymentProcessor**: Dependency (Client uses the target interface)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Target Interface** (`PaymentProcessor`) | Interface | Defines the unified interface that the client expects |
| **Adapter** (`StripeAdapter`, `RazorPayAdapter`) | Implementation | Implements the target interface and adapts incompatible interfaces |
| **Abstract Creator** (`AbstractPaymentProcessorFactory`) | Abstract Class | Declares the factory method for creating payment processors |
| **Concrete Creator** (`StripeFactory`, `RazorPayFactory`) | Implementation | Implements the factory method to create specific adapters |
| **Client** (`Main`) | Consumer | Uses concrete factories to create payment processors and processes payments |

---

## 💡 When to Use Adapter + Factory Method

- ✅ When you want to use an existing class whose interface is incompatible with the rest of your system
- ✅ When you want to defer object creation to subclasses and let them decide which class to instantiate
- ✅ When you need to use several existing subclasses but it's impractical to adapt their interface by subclassing every one
- ✅ When you want to decouple client code from specific implementations and object creation logic
- ✅ When you need to integrate third-party libraries or APIs with incompatible interfaces
- ✅ When you want to provide a framework where subclasses can specify which objects to create
- ✅ When you want to use polymorphism for object creation
- ✅ When you have multiple ways to create objects and want to centralize the creation logic

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
RazorPay: Paid 4350.0 Rs using phone Number 9876543210
```

This demonstrates how the Adapter + Factory Method pattern allows the client to process payments through different gateways (Stripe, RazorPay) using a unified interface. The concrete factories handle the object creation through the factory method, and the adapters handle the gateway-specific logic (currency conversion, parameters). The client code remains clean and doesn't need to know about the implementation details.

---

## 🔍 Real-World Use Cases

- **Payment Gateways**: Integrating different payment providers (Stripe, PayPal, RazorPay) with a unified interface and factory method for creation
- **Database Adapters**: Adapting different database APIs (JDBC, JPA, MongoDB) to work with a common interface with factory method for connection creation
- **API Integration**: Adapting third-party APIs with different response formats to a standard format with factory method for client creation
- **Legacy Code Integration**: Making legacy systems work with modern architectures using adapters and factory methods
- **File Format Converters**: Adapting different file formats (JSON, XML, CSV) to a common data structure with factory method for reader creation
- **Logging Frameworks**: Adapting different logging libraries to a common logging interface with factory method for logger creation
- **Cloud Storage**: Adapting different cloud storage providers (AWS S3, Azure Blob, Google Cloud) to a unified interface with factory method for client creation

---

## 🎓 Key Improvements Over Basic Adapter

### Comparison: Basic Adapter vs Adapter + Factory Method

| Aspect | Basic Adapter | Adapter + Factory Method |
|--------|--------------|---------------------------|
| **Object Creation** | Client creates adapters directly using switch/case | Concrete factories handle object creation via factory method |
| **Client Code** | Contains conditional logic for object creation | Client selects factory, factory method creates object |
| **Coupling** | Client knows about concrete adapter classes | Client knows about concrete factory classes, not adapters |
| **Maintainability** | Adding new gateway requires modifying client code | Adding new gateway requires new adapter + new factory class |
| **Testability** | Harder to test (client has creation logic) | Easier to test (factories can be mocked independently) |
| **Single Responsibility** | Client handles both creation and usage | Client selects factory, factory handles creation, adapter handles adaptation |
| **Polymorphism** | No polymorphism in object creation | Uses polymorphism - each factory subclass decides what to create |

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

**Adapter + Factory Method (from paymentimproved/):**
```java
// Client selects concrete factory, factory method handles creation
AbstractPaymentProcessorFactory factory = switch (selectedMethod.toLowerCase()) {
    case "stripe" -> new StripeFactory();
    case "razorpay" -> new RazorPayFactory();
    default -> throw new IllegalArgumentException("Invalid payment method: " + selectedMethod);
};

PaymentProcessor processor = factory.createPaymentProcessor(paymentDetails);
```

---

## 🎓 Interview Questions

### Q1: What is the benefit of combining Adapter with Factory Method patterns?

**Answer:**
- **Adapter** handles interface compatibility between incompatible systems
- **Factory Method** defers object creation to subclasses, letting them decide which class to instantiate
- Together, they provide a clean separation where the client doesn't need to know about either the incompatible interfaces or how to create the appropriate adapter
- This combination promotes the Single Responsibility Principle and makes the code more maintainable
- Factory Method uses polymorphism for object creation, making the system more flexible

### Q2: How does the Factory Method pattern improve the basic Adapter implementation?

**Answer:**
- Moves object creation logic from client to concrete factory subclasses
- Each factory subclass knows how to create one specific type of adapter
- Uses polymorphism - the client works with the abstract factory type, not concrete factories
- Makes it easier to add new payment gateways (add new adapter + new factory class)
- Improves testability by allowing factories to be mocked independently
- Follows the Single Responsibility Principle (client selects factory, factory creates adapter, adapter handles payment)

### Q3: What is the difference between Simple Factory and Factory Method patterns?

**Answer:**
- **Simple Factory**: One factory class with a static method that uses conditional logic to decide which object to create
- **Factory Method**: Abstract creator class with a factory method that subclasses implement to decide which object to create
- Simple Factory is a simpler pattern but doesn't use polymorphism
- Factory Method uses inheritance and polymorphism, making it more flexible and extensible
- Factory Method follows the Open/Closed Principle better (add new subclasses instead of modifying existing code)

### Q4: What happens if we need to add a new payment gateway?

**Answer:**
With Adapter + Factory Method:
1. Create a new adapter class (e.g., `PayPalAdapter`) that implements `PaymentProcessor`
2. Create a new factory class (e.g., `PayPalFactory`) that extends `AbstractPaymentProcessorFactory`
3. Update the client's switch statement to handle the new payment method
This follows the Open/Closed Principle - the system is open for extension (new classes) but mostly closed for modification (only the switch statement needs updating).

### Q5: How does this combination promote loose coupling?

**Answer:**
- Client depends on the `PaymentProcessor` interface (abstraction), not concrete adapter classes
- Client depends on the `AbstractPaymentProcessorFactory` (abstraction), not concrete factory classes
- Adapters are independent and can be modified without affecting the client
- Factories are independent and can be modified without affecting the client
- Each factory subclass is responsible for creating one specific adapter type

### Q6: When should you use Factory Method vs Abstract Factory?

**Answer:**
- **Factory Method**: Use when you have one product hierarchy and want to let subclasses decide which class to instantiate (like this example)
- **Abstract Factory**: Use when you need to create families of related objects (e.g., creating Windows buttons and checkboxes together, or Mac buttons and checkboxes together)
- Factory Method is for one product type, Abstract Factory is for multiple product families
