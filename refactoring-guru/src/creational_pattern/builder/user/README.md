# 🏗️ Builder Design Pattern

## 📖 What is Builder?

The **Builder** is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code.

### 🎯 Main Purpose

- **Construct complex objects step by step**: Break down the construction of complex objects into simple steps
- **Separate construction from representation**: Same construction process can create different representations
- **Eliminate telescoping constructors**: Avoid multiple constructor overloads with many parameters
- **Immutable objects**: Create immutable objects with all required fields set at once
- **Fluent interface**: Provide a readable and intuitive API for object creation
- **Optional parameters**: Allow optional parameters to be set or skipped flexibly

---

## 🔧 Implementation in This Example

This implementation demonstrates a **User object creation** where a User has multiple optional fields that can be set in any order using the Builder pattern.

### 📁 File Structure

```
builder/user/
├── User.java              # Product class with nested Builder
└── Main.java              # Demo code showing Builder usage
```

### 🏗️ Architecture

- **Product**: `User` - the complex object being constructed with multiple fields
- **Builder**: `User.Builder` - static nested class that constructs the User object step by step
- **Client**: `Main` - uses the Builder to create User instances

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                   User.Builder (Builder)                     │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - name: String                                     │   │
│  │  - email: String                                    │   │
│  │  - phone: String                                    │   │
│  │  - address: String                                  │   │
│  │  - age: int                                         │   │
│  │  - country: String                                  │   │
│  │  + name(String): Builder                            │   │
│  │  + email(String): Builder                           │   │
│  │  + phone(String): Builder                           │   │
│  │  + address(String): Builder                         │   │
│  │  + age(int): Builder                                │   │
│  │  + country(String): Builder                         │   │
│  │  + build(): User                                    │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ build()
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                        User (Product)                        │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - name: String (final)                            │   │
│  │  - email: String (final)                           │   │
│  │  - phone: String (final)                           │   │
│  │  - address: String (final)                         │   │
│  │  - age: int (final)                                │   │
│  │  - country: String (final)                         │   │
│  │  + User(Builder) [private constructor]             │   │
│  │  + getters...                                       │   │
│  │  + toString(): String                              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates a new `User.Builder` instance
2. **Client** chains setter methods to configure the builder (`.name()`, `.email()`, `.phone()`, etc.)
3. Each setter method returns the Builder itself for method chaining
4. **Client** calls `.build()` to create the final User object
5. **Builder** creates a new User object using its private constructor
6. **User** object is returned to the client with all specified fields set

---

## ✅ Problem Solved by Builder

### ❌ Without Builder (Telescoping Constructor Problem)

```java
// Problem: Multiple constructors with different parameter combinations
public class User {
    private final String name;
    private final String email;
    private final String phone;
    private final String address;
    private final int age;
    private final String country;

    // Constructor 1: Only required fields
    public User(String name, String email) {
        this(name, email, null, null, 0, null);
    }

    // Constructor 2: Required + phone
    public User(String name, String email, String phone) {
        this(name, email, phone, null, 0, null);
    }

    // Constructor 3: Required + phone + address
    public User(String name, String email, String phone, String address) {
        this(name, email, phone, address, 0, null);
    }

    // Constructor 4: All fields
    public User(String name, String email, String phone, String address, int age, String country) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.country = country;
    }
}

// Usage - confusing and error-prone
User user1 = new User("Rahul", "dummy@gmail.com");
User user2 = new User("Rahul", "dummy@gmail.com", "1234567890");
User user3 = new User("Rahul", "dummy@gmail.com", "1234567890", "Bangalore", 27, "India");
// Problem: What does "Bangalore" represent? Address? Country?
// Problem: Hard to remember parameter order
// Problem: Can't skip optional parameters in the middle
```

**Issues:**
- **Telescoping constructors**: Need multiple constructors for different parameter combinations
- **Parameter order confusion**: Hard to remember what each parameter represents
- **No parameter skipping**: Can't skip optional parameters in the middle
- **Readability**: Client code is hard to read and understand
- **Maintenance**: Adding new fields requires creating new constructors

### ✅ With Builder Pattern

```java
// Clean, readable, and flexible
User user = new User.Builder()
        .name("Rahul")
        .email("dummy@gmail.com")
        .phone("1234567890")
        .address("Bangalore,IN")
        .age(27)
        .country("India")
        .build();

// Can skip optional parameters
User simpleUser = new User.Builder()
        .name("John")
        .email("john@example.com")
        .build();
```

**Benefits:**
- **Readable API**: Method names make it clear what each parameter is
- **Flexible parameter order**: Set parameters in any order
- **Optional parameters**: Skip parameters you don't need
- **Immutable objects**: User object is immutable after creation
- **Fluent interface**: Method chaining provides a clean, readable API
- **Single construction point**: Only one place to modify construction logic
- **Validation**: Can add validation in the `build()` method

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         User                                     │
├─────────────────────────────────────────────────────────────────┤
│ - name: String (final)                                           │
│ - email: String (final)                                         │
│ - phone: String (final)                                         │
│ - address: String (final)                                       │
│ - age: int (final)                                              │
│ - country: String (final)                                       │
├─────────────────────────────────────────────────────────────────┤
│ + User(Builder) [private]                                       │
│ + getName(): String                                              │
│ + getEmail(): String                                            │
│ + getPhone(): String                                            │
│ + getAddress(): String                                          │
│ + getAge(): int                                                 │
│ + getCountry(): String                                          │
│ + toString(): String                                            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ contains
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    User.Builder                                 │
├─────────────────────────────────────────────────────────────────┤
│ - name: String                                                   │
│ - email: String                                                  │
│ - phone: String                                                  │
│ - address: String                                                │
│ - age: int                                                       │
│ - country: String                                                │
├─────────────────────────────────────────────────────────────────┤
│ + name(String): Builder                                         │
│ + email(String): Builder                                        │
│ + phone(String): Builder                                        │
│ + address(String): Builder                                      │
│ + age(int): Builder                                             │
│ + country(String): Builder                                      │
│ + build(): User                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### Relationship Types

- **User** → **User.Builder**: Composition (Builder is a static nested class within User)
- **User.Builder** → **User**: Creation (Builder creates User objects via `build()` method)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Product** (`User`) | Complex Object | The complex object being constructed with multiple fields |
| **Builder** (`User.Builder`) | Construction Class | Static nested class that constructs the Product step by step |
| **Client** (`Main`) | Consumer | Uses the Builder to create Product instances |

---

## 💡 When to Use Builder

- ✅ When you need to create complex objects with many optional parameters
- ✅ When you want to eliminate telescoping constructors
- ✅ When you want to provide a fluent, readable API for object creation
- ✅ When you need to create immutable objects
- ✅ When the construction process should be independent of the representation
- ✅ When you want to validate parameters before object creation
- ✅ When you need to create different representations of the same object

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
User{name='Rahul', email='dummy@gmail.com', phone='1234567900', address='Banglore,IN', age=27, country='null'}
```

This demonstrates how the Builder pattern creates a User object with specified fields while allowing optional parameters (like `country`) to remain null if not set.

---

## 🔍 Real-World Use Cases

- **StringBuilder/StringBuffer**: Building strings efficiently in Java
- **Document Builders**: Creating XML, HTML, or PDF documents
- **SQL Query Builders**: Constructing complex database queries
- **HTTP Request Builders**: Building HTTP requests with various headers and parameters
- **Configuration Objects**: Creating complex configuration objects with many optional settings
- **UI Component Builders**: Building complex UI components (e.g., AlertDialog in Android)
- **Meal Builders**: Creating custom meals with various ingredients (restaurant example)

---

## 🎓 Interview Questions

### Q1: What is the main difference between Builder and Factory Method patterns?

**Answer:** 
- **Factory Method** creates objects in a single step, typically with one creation method
- **Builder** constructs objects step by step, allowing complex construction with multiple steps
- Factory Method is for creating single objects, while Builder is for constructing complex objects with many parts

### Q2: Why use Builder instead of telescoping constructors?

**Answer:**
- Telescoping constructors are hard to read and maintain
- Parameter order can be confusing (what does the 3rd String parameter represent?)
- Can't skip optional parameters in the middle
- Builder provides named methods, making the code self-documenting
- Builder allows flexible parameter order and optional parameters

### Q3: Can a Builder create multiple objects?

**Answer:**
Yes, a Builder can be reused to create multiple objects. After calling `build()`, you can modify the builder's state and call `build()` again to create another object with different or same parameters.

### Q4: How does Builder ensure immutability of the created object?

**Answer:**
- The Product class (User) has a private constructor
- All fields are marked as `final`
- Only the Builder can create instances via the private constructor
- Once created, the object cannot be modified (no setters)

### Q5: What is method chaining in the context of Builder pattern?

**Answer:**
Method chaining is a technique where each setter method in the Builder returns `this` (the Builder instance itself). This allows multiple method calls to be chained together in a single statement, providing a fluent and readable API.

### Q6: When should validation be performed in Builder pattern?

**Answer:**
Validation can be performed at two levels:
1. **Individual setter methods**: Validate each parameter as it's set
2. **build() method**: Validate the complete object before creation (recommended for cross-field validation)

### Q7: Is Builder pattern only for objects with many fields?

**Answer:**
No, while Builder is commonly used for objects with many fields, it can be used for any object where:
- Construction is complex
- You want a fluent API
- You need to separate construction from representation
- You want to create different representations using the same construction process

---

## 🔑 Key Takeaways

1. **Builder pattern** separates the construction of complex objects from their representation
2. It **eliminates telescoping constructors** by providing a fluent, readable API
3. The pattern uses **method chaining** for intuitive object construction
4. It enables creation of **immutable objects** with all required fields set at once
5. **Optional parameters** can be easily included or excluded during construction
6. The Builder is often implemented as a **static nested class** within the Product
7. Validation can be added in the `build()` method to ensure object consistency
8. Builder provides **flexibility** in parameter order and selection

---

## 📚 Related Patterns

- **Abstract Factory**: Similar to Builder but focuses on creating families of related objects
- **Factory Method**: Creates objects in a single step, while Builder constructs step by step
- **Prototype**: Creates objects by cloning existing ones, while Builder constructs from scratch
- **Singleton**: Ensures only one instance exists, while Builder creates multiple instances
