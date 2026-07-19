# 🏭 Abstract Factory Design Pattern

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

This implementation demonstrates a **cross-platform GUI application** where different UI components (buttons, checkboxes) are created based on the operating system (Windows or macOS).

### 📁 File Structure

```
abstract_factory/
├── Button.java              # Abstract Product A interface
├── Checkbox.java            # Abstract Product B interface
├── WindowsButton.java       # Concrete Product A1 (Windows)
├── WindowsCheckbox.java     # Concrete Product B1 (Windows)
├── MacButton.java           # Concrete Product A2 (Mac)
├── MacCheckbox.java         # Concrete Product B2 (Mac)
├── GUIFactory.java          # Abstract Factory interface
├── WindowsFactory.java      # Concrete Factory 1 (Windows family)
├── MacFactory.java          # Concrete Factory 2 (Mac family)
├── Application.java         # Client class
└── Main.java                # Demo code
```

### 🏗️ Architecture

- **Abstract Products**: `Button` and `Checkbox` - define interfaces for UI components
- **Concrete Products**: `WindowsButton`, `WindowsCheckbox`, `MacButton`, `MacCheckbox` - platform-specific implementations
- **Abstract Factory**: `GUIFactory` - declares factory methods for creating products
- **Concrete Factories**: `WindowsFactory` and `MacFactory` - create product families for specific platforms
- **Client**: `Application` - uses factory to create compatible UI components

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    Application (Client)                      │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - button: Button                                    │   │
│  │  - checkbox: Checkbox                                │   │
│  │  + Application(GUIFactory)                           │   │
│  │  + paint(): void                                      │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              GUIFactory (Abstract Factory)                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  + createButton(): Button                            │   │
│  │  + createCheckbox(): Checkbox                        │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────┬───────────────────────┬───────────────────────┘
              │                       │
              ▼                       ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│   WindowsFactory        │  │   MacFactory             │
│   (Concrete Factory)    │  │   (Concrete Factory)    │
│  + createButton()       │  │  + createButton()        │
│  + createCheckbox()     │  │  + createCheckbox()      │
└────────────┬────────────┘  └────────────┬────────────┘
             │                            │
             ▼                            ▼
┌─────────────────────────┐  ┌─────────────────────────┐
│   WindowsButton         │  │   MacButton             │
│   WindowsCheckbox       │  │   MacCheckbox           │
│   (Product Family)      │  │   (Product Family)      │
└─────────────────────────┘  └─────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates a concrete factory (`WindowsFactory` or `MacFactory`)
2. **Client** passes the factory to the `Application` constructor
3. **Application** calls `createButton()` on the factory
4. **Concrete Factory** returns the appropriate button for its platform
5. **Application** calls `createCheckbox()` on the factory
6. **Concrete Factory** returns the appropriate checkbox for its platform
7. **Application** calls `paint()` on both components
8. **Products** render themselves in their platform-specific style

---

## ✅ Problem Solved by Abstract Factory

### ❌ Without Abstract Factory

```java
// Tight coupling - client must know concrete classes
public class Application {
    private Button button;
    private Checkbox checkbox;
    
    public Application(String osType) {
        if (osType.equals("Windows")) {
            button = new WindowsButton();      // Direct instantiation
            checkbox = new WindowsCheckbox();  // Direct instantiation
        } else if (osType.equals("Mac")) {
            button = new MacButton();          // Direct instantiation
            checkbox = new MacCheckbox();      // Direct instantiation
        }
        // Problem: Risk of mixing incompatible components
        // Problem: Adding new OS requires modifying this code
    }
}
```

**Issues:**
- Tight coupling between client and concrete classes
- Risk of creating incompatible product combinations (e.g., Windows button with Mac checkbox)
- Violates Open/Closed Principle (must modify code to add new platforms)
- Difficult to test and maintain
- Creation logic scattered throughout the codebase

### ✅ With Abstract Factory

```java
// Loose coupling - client works with abstract interfaces
GUIFactory factory = new WindowsFactory();  // or MacFactory
Application app = new Application(factory);
app.paint();  // Factory creates compatible components automatically
```

**Benefits:**
- **Product Family Consistency**: Ensures compatible products are created together
- **Open/Closed Principle**: New product families can be added without modifying existing code
- **Single Responsibility**: Each factory handles creation of one product family
- **Dependency Inversion**: High-level modules don't depend on low-level modules
- **Easy Testing**: Can easily mock factories for testing
- **Centralized Creation**: All creation logic for a family in one place

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                       Button                                      │
├─────────────────────────────────────────────────────────────────┤
│ + paint(): void                                                   │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    WindowsButton        │   │    MacButton            │
├─────────────────────────┤   ├─────────────────────────┤
│ + paint(): void         │   │ + paint(): void         │
│ "Windows style"         │   │ "Mac style"             │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                       Checkbox                                    │
├─────────────────────────────────────────────────────────────────┤
│ + paint(): void                                                   │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    WindowsCheckbox      │   │    MacCheckbox          │
├─────────────────────────┤   ├─────────────────────────┤
│ + paint(): void         │   │ + paint(): void         │
│ "Windows style"         │   │ "Mac style"             │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                    <<interface>>                                 │
│                      GUIFactory                                  │
├─────────────────────────────────────────────────────────────────┤
│ + createButton(): Button                                         │
│ + createCheckbox(): Checkbox                                     │
└─────────────────────────────────────────────────────────────────┘
                              △
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
┌─────────────────────────┐   ┌─────────────────────────┐
│    WindowsFactory       │   │    MacFactory           │
├─────────────────────────┤   ├─────────────────────────┤
│ + createButton()        │   │ + createButton()        │
│ + createCheckbox()      │   │ + createCheckbox()      │
│ returns WindowsButton   │   │ returns MacButton       │
│ returns WindowsCheckbox │   │ returns MacCheckbox     │
└─────────────────────────┘   └─────────────────────────┘


┌─────────────────────────────────────────────────────────────────┐
│                      Application                                  │
├─────────────────────────────────────────────────────────────────┤
│ - button: Button                                                  │
│ - checkbox: Checkbox                                            │
│ + Application(GUIFactory)                                        │
│ + paint(): void                                                   │
└─────────────────────────────────────────────────────────────────┘
```

### Relationship Types

- **Button** ← **WindowsButton**: Implementation
- **Button** ← **MacButton**: Implementation
- **Checkbox** ← **WindowsCheckbox**: Implementation
- **Checkbox** ← **MacCheckbox**: Implementation
- **GUIFactory** ← **WindowsFactory**: Implementation
- **GUIFactory** ← **MacFactory**: Implementation
- **Application** → **GUIFactory**: Dependency (uses factory interface)
- **Application** → **Button**: Dependency (uses product interface)
- **Application** → **Checkbox**: Dependency (uses product interface)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Abstract Product** (`Button`, `Checkbox`) | Interface | Declares interface for a type of product object |
| **Concrete Product** (`WindowsButton`, `MacButton`, etc.) | Implementation | Implements the Abstract Product interface |
| **Abstract Factory** (`GUIFactory`) | Interface | Declares factory methods for each abstract product |
| **Concrete Factory** (`WindowsFactory`, `MacFactory`) | Implementation | Implements factory methods to create concrete products |
| **Client** (`Application`) | Consumer | Uses Abstract Factory and Abstract Product interfaces |

---

## 💡 When to Use Abstract Factory

- ✅ When your code needs to work with families of related products
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
=== Windows Application ===
Rendering a button in Windows style.
Rendering a checkbox in Windows style.

=== Mac Application ===
Rendering a button in macOS style.
Rendering a checkbox in macOS style.
```

This demonstrates how the same `Application` class creates different UI components based on the factory used, ensuring that all components are from the same platform family and compatible with each other.

---

## 🔍 Real-World Use Cases

- **GUI Frameworks**: Creating cross-platform UI components (Windows, Mac, Linux)
- **Database Access**: Creating different database connections (MySQL, PostgreSQL, Oracle)
- **Document Processing**: Creating different document formats (PDF, Word, HTML)
- **Game Development**: Creating different game assets for various platforms
- **Cloud Services**: Creating resources for different cloud providers (AWS, Azure, GCP)

---

## 🎓 Key Takeaways

1. **Abstract Factory** is about creating **families** of related objects
2. It ensures **consistency** among products from the same family
3. It provides a **single point** to switch between entire product families
4. It follows the **Dependency Inversion Principle** - depend on abstractions, not concretions
5. It's more **complex** than Factory Method but provides greater flexibility for related products
