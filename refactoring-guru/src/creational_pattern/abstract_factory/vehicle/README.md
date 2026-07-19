# 🏭 Abstract Factory Design Pattern - Vehicle Example

## 📖 What is Abstract Factory?

The **Abstract Factory** is a creational design pattern that lets you produce families of related objects without specifying their concrete classes.

### 🎯 Main Purpose

- **Create families of related objects**: Ensures that products from the same family are compatible
- **Decouple client code from concrete classes**: Clients work with abstract interfaces, not concrete implementations
- **Promote consistency**: Guarantees that related products work together correctly
- **Enable easy switching between product families**: Change entire families by switching factories
- **Centralize creation logic**: All object creation for a family is in one place

---

## 🔧 Implementation in This Example

This implementation demonstrates a **vehicle showroom system** where different types of vehicles (cars, motorcycles) are created based on the vehicle category (Luxury or Economy).

### 📁 File Structure

```
vehicle/
├── Car.java                    # Abstract Product A interface
├── Motorcycle.java              # Abstract Product B interface
├── LuxuryCar.java               # Concrete Product A1 (Luxury)
├── LuxuryMotorcycle.java        # Concrete Product B1 (Luxury)
├── EconomyCar.java              # Concrete Product A2 (Economy)
├── EconomyMotorcycle.java       # Concrete Product B2 (Economy)
├── VehicleFactory.java          # Abstract Factory interface
├── LuxuryVehicleFactory.java   # Concrete Factory 1 (Luxury family)
├── EconomyVehicleFactory.java  # Concrete Factory 2 (Economy family)
├── VehicleShowroom.java        # Client class
└── Main.java                   # Demo code
```

### 🏗️ Architecture

- **Abstract Products**: `Car` and `Motorcycle` - define interfaces for vehicle types
- **Concrete Products**: `LuxuryCar`, `LuxuryMotorcycle`, `EconomyCar`, `EconomyMotorcycle` - category-specific implementations
- **Abstract Factory**: `VehicleFactory` - declares factory methods for creating vehicles
- **Concrete Factories**: `LuxuryVehicleFactory` and `EconomyVehicleFactory` - create vehicle families for specific categories
- **Client**: `VehicleShowroom` - uses factory to create compatible vehicles

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                  VehicleShowroom (Client)                    │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - car: Car                                          │   │
│  │  - motorcycle: Motorcycle                            │   │
│  │  + VehicleShowroom(VehicleFactory)                 │   │
│  │  + showcaseVehicles(): void                          │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              VehicleFactory (Abstract Factory)               │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + createCar(): Car                                  │   │
│  │  + createMotorcycle(): Motorcycle                    │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│  LuxuryVehicleFactory   │  │  EconomyVehicleFactory  │
│  (Concrete Factory)     │  │  (Concrete Factory)     │
│  + createCar()          │  │  + createCar()          │
│  + createMotorcycle()   │  │  + createMotorcycle()   │
└────────────┬────────────┘  └────────────┬────────────┘
             │                            │
             ▼                            ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│  LuxuryCar              │  │  EconomyCar              │
│  LuxuryMotorcycle       │  │  EconomyMotorcycle       │
│  (Vehicle Family)       │  │  (Vehicle Family)       │
└─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates a concrete factory (`LuxuryVehicleFactory` or `EconomyVehicleFactory`)
2. **Client** passes the factory to the `VehicleShowroom` constructor
3. **VehicleShowroom** calls `createCar()` on the factory
4. **Concrete Factory** returns the appropriate car for its category
5. **VehicleShowroom** calls `createMotorcycle()` on the factory
6. **Concrete Factory** returns the appropriate motorcycle for its category
7. **VehicleShowroom** calls `showcaseVehicles()` to display both vehicles
8. **Products** display their characteristics (drive/ride behavior and max speed)

---

## ✅ Problem Solved by Abstract Factory

### ❌ Without Abstract Factory

```java
// Tight coupling - client must know concrete classes
public class VehicleShowroom {
    private Car car;
    private Motorcycle motorcycle;
    
    public VehicleShowroom(String category) {
        if (category.equals("Luxury")) {
            car = new LuxuryCar();           // Direct instantiation
            motorcycle = new LuxuryMotorcycle(); // Direct instantiation
        } else if (category.equals("Economy")) {
            car = new EconomyCar();           // Direct instantiation
            motorcycle = new EconomyMotorcycle(); // Direct instantiation
        }
        // Problem: Risk of mixing incompatible vehicles
        // Problem: Adding new category requires modifying this code
    }
}
```

**Issues:**
- Tight coupling between client and concrete classes
- Risk of creating incompatible vehicle combinations (e.g., Luxury car with Economy motorcycle)
- Violates Open/Closed Principle (must modify code to add new categories)
- Difficult to test and maintain
- Creation logic scattered throughout the codebase

### ✅ With Abstract Factory

```java
// Loose coupling - client works with abstract interfaces
VehicleFactory factory = new LuxuryVehicleFactory();  // or EconomyVehicleFactory
VehicleShowroom showroom = new VehicleShowroom(factory);
showroom.showcaseVehicles();  // Factory creates compatible vehicles automatically
```

**Benefits:**
- **Product Family Consistency**: Ensures compatible vehicles are created together
- **Open/Closed Principle**: New vehicle categories can be added without modifying existing code
- **Single Responsibility**: Each factory handles creation of one vehicle family
- **Dependency Inversion**: High-level modules don't depend on low-level modules
- **Easy Testing**: Can easily mock factories for testing
- **Centralized Creation**: All creation logic for a family in one place

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                        Car                                       │
├─────────────────────────────────────────────────────────────────┤
│ + drive(): void                                                   │
│ + getMaxSpeed(): int                                              │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    LuxuryCar             │   │    EconomyCar            │
├─────────────────────────┤   ├─────────────────────────┤
│ + drive(): void          │   │ + drive(): void          │
│ + getMaxSpeed(): int     │   │ + getMaxSpeed(): int     │
│ "Premium comfort"        │   │ "Fuel efficiency"       │
│ Max: 250 km/h           │   │ Max: 180 km/h           │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                    Motorcycle                                    │
├─────────────────────────────────────────────────────────────────┤
│ + ride(): void                                                    │
│ + getMaxSpeed(): int                                              │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    LuxuryMotorcycle     │   │    EconomyMotorcycle    │
├─────────────────────────┤   ├─────────────────────────┤
│ + ride(): void          │   │ + ride(): void          │
│ + getMaxSpeed(): int     │   │ + getMaxSpeed(): int     │
│ "Premium performance"    │   │ "Great mileage"        │
│ Max: 300 km/h           │   │ Max: 140 km/h           │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                  VehicleFactory                                  │
├─────────────────────────────────────────────────────────────────┤
│ + createCar(): Car                                               │
│ + createMotorcycle(): Motorcycle                                 │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│  LuxuryVehicleFactory   │   │  EconomyVehicleFactory  │
├─────────────────────────┤   ├─────────────────────────┤
│ + createCar()          │   │ + createCar()          │
│ + createMotorcycle()   │   │ + createMotorcycle()   │
│ returns LuxuryCar      │   │ returns EconomyCar     │
│ returns LuxuryMotorcycle│  │ returns EconomyMotorcycle│
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    VehicleShowroom                               │
├─────────────────────────────────────────────────────────────────┤
│ - car: Car                                                       │
│ - motorcycle: Motorcycle                                         │
│ + VehicleShowroom(VehicleFactory)                               │
│ + showcaseVehicles(): void                                       │
└─────────────────────────────────────────────────────────────────┘
```

### Relationship Types

- **Car** ← **LuxuryCar**: Implementation
- **Car** ← **EconomyCar**: Implementation
- **Motorcycle** ← **LuxuryMotorcycle**: Implementation
- **Motorcycle** ← **EconomyMotorcycle**: Implementation
- **VehicleFactory** ← **LuxuryVehicleFactory**: Implementation
- **VehicleFactory** ← **EconomyVehicleFactory**: Implementation
- **VehicleShowroom** → **VehicleFactory**: Dependency (uses factory interface)
- **VehicleShowroom** → **Car**: Dependency (uses product interface)
- **VehicleShowroom** → **Motorcycle**: Dependency (uses product interface)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Abstract Product** (`Car`, `Motorcycle`) | Interface | Declares interface for a type of product object |
| **Concrete Product** (`LuxuryCar`, `EconomyCar`, etc.) | Implementation | Implements the Abstract Product interface |
| **Abstract Factory** (`VehicleFactory`) | Interface | Declares factory methods for each abstract product |
| **Concrete Factory** (`LuxuryVehicleFactory`, `EconomyVehicleFactory`) | Implementation | Implements factory methods to create concrete products |
| **Client** (`VehicleShowroom`) |.Consumer | Uses Abstract Factory and Abstract Product interfaces |

---

## 💡 When to Use Abstract Factory

- ✅ When your code needs to work with families of related objects
- ✅ When you want to ensure products from a family are compatible
- ✅ When you want to provide a library of products and reveal only their interfaces
- ✅ When you need to support multiple types of product families
- ✅ When you want to decouple client code from concrete implementations

---

## 🎯 Key Difference: Abstract Factory vs Factory Method

| Aspect | Abstract Factory | Factory Method |
|--------|------------------|----------------|
| **Scope** | Creates families of related products | Creates single products |
| **Structure** | Multiple factory methods (one per product) | Single factory method |
| **Complexity** | More complex, multiple interfaces | Simpler, single interface |
| **Use Case** | When products must be compatible | When creating single product types |
| **Flexibility** | Can create entire product families | Creates one product at a time |

**Abstract Factory is essentially Factory Method applied to multiple product types.**

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
=== Luxury Vehicle Showroom ===
=== Vehicle Showcase ===
Car: Driving a luxury car with premium comfort and advanced features.
Max Speed: 250 km/h

Motorcycle: Riding a luxury motorcycle with premium performance and style.
Max Speed: 300 km/h

=== Economy Vehicle Showroom ===
=== Vehicle Showcase ===
Car: Driving an economy car with fuel efficiency and practicality.
Max Speed: 180 km/h

Motorcycle: Riding an economy motorcycle with great mileage and affordability.
Max Speed: 140 km/h
```

This demonstrates how the same `VehicleShowroom` class creates different vehicles based on the factory used, ensuring that all vehicles are from the same category family and compatible with each other.

---

## 🔍 Real-World Use Cases

- **Vehicle Manufacturing**: Creating different vehicle families (Luxury, Economy, Sports)
- **Game Development**: Creating different asset families for various game themes
- **E-commerce**: Creating product families for different categories (Electronics, Clothing)
- **Database Access**: Creating different database component families (Connection, Query, Transaction)
- **UI Frameworks**: Creating component families for different themes (Dark, Light, Custom)

---

## 🎓 Key Takeaways

1. **Abstract Factory** is about creating **families** of related objects
2. It ensures **consistency** among products from the same family
3. It provides a **single point** to switch between entire product families
4. It follows the **Dependency Inversion Principle** - depend on abstractions, not concretions
5. It's more **complex** than Factory Method but provides greater flexibility for related products
6. Perfect for scenarios where you need to maintain compatibility between related products
