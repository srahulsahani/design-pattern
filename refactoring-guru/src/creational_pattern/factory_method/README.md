# 🏭 Factory Method Design Pattern (Enhanced)

## 📖 What is Factory Method?

The **Factory Method** is a creational design pattern that provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.

**This enhanced implementation uses a dedicated Factory Class** for centralized object creation, which is a common variation that further simplifies the design.

### 🎯 Main Purpose

- **Decouple object creation from object usage**: Clients don't need to know the exact class of objects they work with
- **Promote loose coupling**: Reduces dependency between client code and concrete classes
- **Enable extensibility**: New product types can be added without modifying existing client code
- **Centralize creation logic**: Object creation logic is encapsulated in one place (the Factory Class)
- **Simplify client code**: No need for multiple creator subclasses

---

## 🔧 Implementation in This Example

This implementation demonstrates a **logistics system** where different transport methods are created based on the type of logistics (road or sea).

### 📁 File Structure

```
factory_method/
├── Transport.java          # Product interface
├── Truck.java              # Concrete product (road transport)
├── Ship.java               # Concrete product (sea transport)
├── TransportFactory.java   # Factory class (creates objects)
├── Logistics.java          # Client class (uses factory)
└── Main.java               # Demo code
```

### 🏗️ Architecture

- **Product Interface**: `Transport` - defines the `deliver()` method
- **Concrete Products**: `Truck` and `Ship` - implement specific delivery methods
- **Factory Class**: `TransportFactory` - centralized object creation with static factory method
- **Client Class**: `Logistics` - uses the factory to create transport objects

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Logistics (Client)                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - transportType: TransportType                    │   │
│  │  + Logistics(TransportType)                         │   │
│  │  + planDelivery(): void                              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              TransportFactory (Factory Class)               │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  enum TransportType { ROAD, SEA }                    │   │
│  │  + createTransport(TransportType): Transport         │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│        Truck            │  │        Ship             │
│   (Concrete Product)    │  │   (Concrete Product)    │
│  + deliver()            │  │  + deliver()            │
└─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates `Logistics` object with a transport type (ROAD or SEA)
2. **Client** calls `planDelivery()` method on `Logistics`
3. **Logistics** calls `TransportFactory.createTransport()` with the transport type
4. **TransportFactory** returns the appropriate product (`Truck` or `Ship`) based on type
5. **Logistics** calls `deliver()` on the created product
6. **Product** executes its specific delivery logic

---

## ✅ Problem Solved by Factory Method

### ❌ Without Factory Method

```java
// Tight coupling - client must know concrete classes
public void planDelivery(String type) {
    if (type.equals("road")) {
        Truck truck = new Truck();  // Direct instantiation
        truck.deliver();
    } else if (type.equals("sea")) {
        Ship ship = new Ship();     // Direct instantiation
        ship.deliver();
    }
    // Problem: Adding new transport requires modifying this code
}
```

**Issues:**
- Tight coupling between client and concrete classes
- Violates Open/Closed Principle (must modify code to add new types)
- Difficult to test and maintain
- Creation logic scattered throughout the codebase

### ✅ With Factory Class (Enhanced Approach)

```java
// Loose coupling - client works with factory and enum
Logistics logistics = new Logistics(TransportFactory.TransportType.ROAD);
logistics.planDelivery();  // Factory creates appropriate transport
```

**Benefits:**
- **Open/Closed Principle**: New transport types can be added by adding enum value and case
- **Single Responsibility**: Factory class solely handles object creation
- **Simpler Design**: No need for multiple creator subclasses
- **Type Safety**: Enum ensures only valid transport types are used
- **Centralized Creation**: All creation logic in one static method
- **Easy Testing**: Can easily mock factory for testing

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                       Transport                                  │
├─────────────────────────────────────────────────────────────────┤
│ + deliver(): void                                                │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│        Truck            │   │        Ship             │
├─────────────────────────┤   ├─────────────────────────┤
│ + deliver(): void        │   │ + deliver(): void        │
│ "Delivering by road.."   │   │ "Delivering by sea.."    │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│              TransportFactory (Factory Class)                    │
├─────────────────────────────────────────────────────────────────┤
│ + enum TransportType { ROAD, SEA }                              │
│ + createTransport(TransportType): Transport                     │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ creates
                              ▼
                      ┌───────────────┐
                      │   Transport   │
                      └───────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                      Logistics (Client)                          │
├─────────────────────────────────────────────────────────────────┤
│ - transportType: TransportType                                   │
│ + Logistics(TransportType)                                       │
│ + planDelivery(): void                                           │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ uses
                              ▼
                      ┌───────────────┐
                      │ TransportFactory │
                      └───────────────┘
```

### Relationship Types

- **Transport** ← **Truck**: Implementation (Truck implements Transport)
- **Transport** ← **Ship**: Implementation (Ship implements Transport)
- **TransportFactory** → **Transport**: Creation (Factory creates Transport objects)
- **Logistics** → **TransportFactory**: Usage (Logistics uses Factory to create objects)
- **Logistics** → **Transport**: Dependency (Logistics uses Transport interface)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Product** (`Transport`) | Interface | Defines the interface of objects the factory creates |
| **Concrete Product** (`Truck`, `Ship`) | Implementation | Implements the Product interface |
| **Factory** (`TransportFactory`) | Factory Class | Contains static factory method for object creation |
| **Client** (`Logistics`) | Consumer | Uses the factory to create and use product objects |

---

## 💡 When to Use Factory Class

- ✅ When you don't know beforehand the exact types and dependencies of objects
- ✅ When you want to centralize object creation logic in one place
- ✅ When you want to use enums for type-safe object creation
- ✅ When you want to avoid multiple creator subclasses
- ✅ When you want to decouple client code from concrete classes

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
Delivering Goods by road..
Delivering goods by Sea..
```

This demonstrates how the same `planDelivery()` method creates different transport objects based on the logistics type, without the client needing to know the specifics.
