# 🏭 Factory Method Design Pattern

## 📖 What is Factory Method?

The **Factory Method** is a creational design pattern that provides an interface for creating objects in a superclass but allows subclasses to alter the type of objects that will be created.

### 🎯 Main Purpose

- **Decouple object creation from object usage**: Clients don't need to know the exact class of objects they work with
- **Promote loose coupling**: Reduces dependency between client code and concrete classes
- **Enable extensibility**: New product types can be added without modifying existing client code
- **Centralize creation logic**: Object creation logic is encapsulated in one place

---

## 🔧 Implementation in This Example

This implementation demonstrates a **logistics system** where different transport methods are created based on the type of logistics (road or sea).

### 📁 File Structure

```
factory_method/
├── Transport.java          # Product interface
├── Truck.java              # Concrete product (road transport)
├── Ship.java               # Concrete product (sea transport)
├── Logistics.java          # Creator abstract class
├── RoadLogistics.java      # Concrete creator (creates Truck)
├── SeaLogistics.java       # Concrete creator (creates Ship)
└── Main.java               # Client code
```

### 🏗️ Architecture

- **Product Interface**: `Transport` - defines the `deliver()` method
- **Concrete Products**: `Truck` and `Ship` - implement specific delivery methods
- **Creator**: `Logistics` - abstract class with factory method `createTransport()`
- **Concrete Creators**: `RoadLogistics` and `SeaLogistics` - implement factory method to create specific products

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              Logistics (Abstract Creator)                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + createTransport(): Transport (Factory Method)    │   │
│  │  + planDelivery(): void                              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│   RoadLogistics         │  │   SeaLogistics          │
│   (Concrete Creator)    │  │   (Concrete Creator)    │
│  + createTransport()    │  │  + createTransport()    │
│  returns: Truck         │  │  returns: Ship          │
└────────────┬────────────┘  └────────────┬────────────┘
             │                            │
             ▼                            ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│        Truck            │  │        Ship             │
│   (Concrete Product)    │  │   (Concrete Product)    │
│  + deliver()            │  │  + deliver()            │
└─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates a concrete creator (`RoadLogistics` or `SeaLogistics`)
2. **Client** calls `planDelivery()` method on the creator
3. **Creator** internally calls `createTransport()` (factory method)
4. **Concrete Creator** returns the appropriate product (`Truck` or `Ship`)
5. **Creator** calls `deliver()` on the created product
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

### ✅ With Factory Method

```java
// Loose coupling - client works with abstract types
Logistics logistics = new RoadLogistics();  // or SeaLogistics
logistics.planDelivery();  // No need to know which transport is created
```

**Benefits:**
- **Open/Closed Principle**: New transport types can be added without modifying existing code
- **Single Responsibility**: Each class has one clear responsibility
- **Dependency Inversion**: High-level modules don't depend on low-level modules
- **Easy Testing**: Can easily mock creators for testing
- **Centralized Creation**: All creation logic in one place

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
│                  <<abstract>>                                    │
│                      Logistics                                   │
├─────────────────────────────────────────────────────────────────┤
│ # createTransport(): Transport  (Factory Method)                │
│ + planDelivery(): void                                           │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ extends
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    RoadLogistics         │   │    SeaLogistics          │
├─────────────────────────┤   ├─────────────────────────┤
│ + createTransport()     │   │ + createTransport()     │
│   returns Truck         │   │   returns Ship          │
└─────────────────────────┘   └─────────────────────────┘
```

### Relationship Types

- **Transport** ← **Truck**: Implementation (Truck implements Transport)
- **Transport** ← **Ship**: Implementation (Ship implements Transport)
- **Logistics** ← **RoadLogistics**: Inheritance (RoadLogistics extends Logistics)
- **Logistics** ← **SeaLogistics**: Inheritance (SeaLogistics extends Logistics)
- **Logistics** → **Transport**: Dependency (Logistics uses Transport)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Product** (`Transport`) | Interface | Defines the interface of objects the factory method creates |
| **Concrete Product** (`Truck`, `Ship`) | Implementation | Implements the Product interface |
| **Creator** (`Logistics`) | Abstract Class | Declares the factory method that returns new Product objects |
| **Concrete Creator** (`RoadLogistics`, `SeaLogistics`) | Implementation | Overrides the factory method to return Concrete Products |

---

## 💡 When to Use Factory Method

- ✅ When you don't know beforehand the exact types and dependencies of objects
- ✅ When you want to provide users with an extension point for creating objects
- ✅ When you want to save system resources by reusing existing objects
- ✅ When you want to decouple client code from concrete classes

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
Delivering Goods by road..
Delivering goods by Sea..
```

This demonstrates how the same `planDelivery()` method creates different transport objects based on the logistics type, without the client needing to know the specifics.
