# 🏗️ Builder Design Pattern - HTTP Request Example

## 📖 What is Builder?

The **Builder** is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code.

### 🎯 Main Purpose

- **Construct complex objects step by step**: Break down the construction of complex objects into simple steps
- **Separate construction from representation**: Same construction process can create different representations
- **Eliminate telescoping constructors**: Avoid multiple constructor overloads with many parameters
- **Immutable objects**: Create immutable objects with all required fields set at once
- **Fluent interface**: Provide a readable and intuitive API for object creation
- **Optional parameters**: Allow optional parameters to be set or skipped flexibly
- **Validation**: Enable validation before object creation

---

## 🔧 Implementation in This Example

This implementation demonstrates an **HTTP Request object creation** where an HttpRequest has mandatory fields (url, method) and multiple optional fields (headers, query params, body, timeout, bearer token, retry count) that can be set in any order using the Builder pattern.

### 📁 File Structure

```
builder/http_request/
├── HttpRequest.java        # Product class with nested Builder
└── Main.java               # Demo code showing Builder usage
```

### 🏗️ Architecture

- **Product**: `HttpRequest` - the complex object being constructed with mandatory and optional fields
- **Builder**: `HttpRequest.Builder` - static nested class that constructs the HttpRequest object step by step
- **Client**: `Main` - uses the Builder to create HttpRequest instances

---

## 🔄 Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Main)                         │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              HttpRequest.Builder (Builder)                   │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  // Mandatory fields                                │   │
│  │  - url: String                                      │   │
│  │  - method: String                                   │   │
│  │  // Optional fields                                 │   │
│  │  - headers: Map<String,String>                       │   │
│  │  - queryParams: Map<String,String>                   │   │
│  │  - body: String                                      │   │
│  │  - timeout: int (default: 3000)                      │   │
│  │  - bearerToken: String                               │   │
│  │  - retryCount: int (default: 0)                     │   │
│  │  + url(String): Builder                             │   │
│  │  + methods(String): Builder                          │   │
│  │  + body(String): Builder                             │   │
│  │  + timeout(int): Builder                             │   │
│  │  + bearerToken(String): Builder                      │   │
│  │  + retryCount(int): Builder                          │   │
│  │  + addHeaders(String, String): Builder               │   │
│  │  + addQueryParam(String, String): Builder            │   │
│  │  + build(): HttpRequest                              │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────┬───────────────────────────────────────┘
                      │
                      │ build()
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                    HttpRequest (Product)                     │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  - url: String (final)                              │   │
│  │  - method: String (final)                           │   │
│  │  - headers: Map<String,String> (final)               │   │
│  │  - queryParams: Map<String,String> (final)           │   │
│  │  - body: String (final)                              │   │
│  │  - timeout: int (final)                              │   │
│  │  - bearerToken: String (final)                       │   │
│  │  - retryCount: int (final)                           │   │
│  │  + HttpRequest(Builder) [private constructor]       │   │
│  │  + getters...                                         │   │
│  │  + toString(): String                                │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 📊 Execution Flow

1. **Client** creates a new `HttpRequest.Builder` instance
2. **Client** chains setter methods to configure the builder (`.url()`, `.methods()`, `.timeout()`, etc.)
3. Each setter method returns the Builder itself for method chaining
4. **Client** calls `.build()` to create the final HttpRequest object
5. **Builder** validates mandatory fields (url, method)
6. **Builder** creates a new HttpRequest object using its private constructor
7. **HttpRequest** object is returned to the client with all specified fields set

---

## 🔗 Method Chaining Explained Step by Step

Method chaining is a technique where each method returns the object itself (`this`), allowing multiple method calls to be chained together in a single statement. Let's break down how this works in the Builder pattern.

### The Magic Behind Method Chaining

Each setter method in the Builder follows this pattern:

```java
public Builder url(String url) {
    this.url = url;      // 1. Set the field value
    return this;         // 2. Return the Builder instance
}
```

The key is `return this` - it returns the same Builder object, allowing the next method to be called on it.

### Step-by-Step Execution

Let's trace through this Builder call:

```java
HttpRequest httpRequest = new HttpRequest.Builder()
        .url("https://google.com")
        .methods("GET")
        .retryCount(3)
        .timeout(5000)
        .bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv")
        .build();
```

#### Step 1: Create Builder Instance

```java
new HttpRequest.Builder()
```

**Builder State:**
```
Builder {
    url = null
    method = null
    headers = {}
    queryParams = {}
    body = null
    timeout = 3000 (default)
    bearerToken = null
    retryCount = 0 (default)
}
```

**Returns:** Builder instance (let's call it `builder1`)

---

#### Step 2: Call .url("https://google.com")

```java
builder1.url("https://google.com")
```

**Inside the method:**
```java
public Builder url(String url) {
    this.url = url;  // Sets url = "https://google.com"
    return this;      // Returns the same builder1
}
```

**Builder State after Step 2:**
```
Builder {
    url = "https://google.com"
    method = null
    headers = {}
    queryParams = {}
    body = null
    timeout = 3000 (default)
    bearerToken = null
    retryCount = 0 (default)
}
```

**Returns:** Same `builder1` instance

---

#### Step 3: Call .methods("GET")

```java
builder1.methods("GET")
```

**Inside the method:**
```java
public Builder methods(String methods) {
    this.method = methods;  // Sets method = "GET"
    return this;            // Returns the same builder1
}
```

**Builder State after Step 3:**
```
Builder {
    url = "https://google.com"
    method = "GET"
    headers = {}
    queryParams = {}
    body = null
    timeout = 3000 (default)
    bearerToken = null
    retryCount = 0 (default)
}
```

**Returns:** Same `builder1` instance

---

#### Step 4: Call .retryCount(3)

```java
builder1.retryCount(3)
```

**Inside the method:**
```java
public Builder retryCount(int retryCount) {
    this.retryCount = retryCount;  // Sets retryCount = 3
    return this;                   // Returns the same builder1
}
```

**Builder State after Step 4:**
```
Builder {
    url = "https://google.com"
    method = "GET"
    headers = {}
    queryParams = {}
    body = null
    timeout = 3000 (default)
    bearerToken = null
    retryCount = 3
}
```

**Returns:** Same `builder1` instance

---

#### Step 5: Call .timeout(5000)

```java
builder1.timeout(5000)
```

**Inside the method:**
```java
public Builder timeout(int timeout) {
    this.timeout = timeout;  // Sets timeout = 5000
    return this;             // Returns the same builder1
}
```

**Builder State after Step 5:**
```
Builder {
    url = "https://google.com"
    method = "GET"
    headers = {}
    queryParams = {}
    body = null
    timeout = 5000
    bearerToken = null
    retryCount = 3
}
```

**Returns:** Same `builder1` instance

---

#### Step 6: Call .bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv")

```java
builder1.bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv")
```

**Inside the method:**
```java
public Builder bearerToken(String bearerToken) {
    this.bearerToken = bearerToken;  // Sets bearerToken
    return this;                      // Returns the same builder1
}
```

**Builder State after Step 6:**
```
Builder {
    url = "https://google.com"
    method = "GET"
    headers = {}
    queryParams = {}
    body = null
    timeout = 5000
    bearerToken = "gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv"
    retryCount = 3
}
```

**Returns:** Same `builder1` instance

---

#### Step 7: Call .build()

```java
builder1.build()
```

**Inside the method:**
```java
public HttpRequest build() {
    if(url == null || url.isBlank()){
        throw new IllegalArgumentException("URL is mandatory");
    }
    if(method == null || method.isBlank()){
        throw new IllegalArgumentException("Method is mandatory");
    }
    return new HttpRequest(this);  // Creates HttpRequest using builder's current state
}
```

**Validation Check:**
- `url` = "https://google.com" ✓ (not null, not blank)
- `method` = "GET" ✓ (not null, not blank)

**HttpRequest Constructor receives the Builder:**
```java
private HttpRequest(Builder builder) {
    this.url = builder.url;
    this.method = builder.method;
    this.headers = builder.headers;
    this.queryParams = builder.queryParams;
    this.body = builder.body;
    this.timeout = builder.timeout;
    this.bearerToken = builder.bearerToken;
    this.retryCount = builder.retryCount;
}
```

**Final HttpRequest Object:**
```
HttpRequest {
    url = "https://google.com"
    method = "GET"
    headers = {}
    queryParams = {}
    body = null
    timeout = 5000
    bearerToken = "gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv"
    retryCount = 3
}
```

**Returns:** New HttpRequest instance with all fields set

---

### Visual Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                  new HttpRequest.Builder()                       │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url=null, method=null, timeout=3000, retryCount=0, ...}│
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .url("https://google.com")
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url="https://google.com", method=null, timeout=3000, ...}│
│  Returns: this (same Builder instance)                           │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .methods("GET")
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url="https://google.com", method="GET", timeout=3000, ...}│
│  Returns: this (same Builder instance)                           │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .retryCount(3)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url="https://google.com", method="GET", retryCount=3, ...}│
│  Returns: this (same Builder instance)                           │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .timeout(5000)
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url="https://google.com", method="GET", timeout=5000, retryCount=3, ...}│
│  Returns: this (same Builder instance)                           │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .bearerToken("...")
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  Builder: {url="https://google.com", method="GET", timeout=5000, retryCount=3, bearerToken="..."}│
│  Returns: this (same Builder instance)                           │
└────────────────────────────┬──────────────────────────────────────┘
                             │
                             │ .build() [with validation]
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│  HttpRequest: {url="https://google.com", method="GET", timeout=5000, ...}│
│  Returns: new HttpRequest instance                               │
└─────────────────────────────────────────────────────────────────┘
```

### Why Method Chaining Works

1. **Same Object Reference**: Every method returns `this`, which is the same Builder object
2. **Sequential Execution**: Methods execute one after another, each modifying the Builder's state
3. **Fluent API**: The chain reads like a sentence: "builder.url().methods().timeout()..."
4. **Single Statement**: The entire construction happens in one expression
5. **Intermediate State**: Builder holds the intermediate state until `build()` is called
6. **Default Values**: Optional fields have sensible defaults (timeout=3000, retryCount=0)

### Without Method Chaining (For Comparison)

```java
// Without method chaining - verbose and repetitive
HttpRequest.Builder builder = new HttpRequest.Builder();
builder = builder.url("https://google.com");
builder = builder.methods("GET");
builder = builder.retryCount(3);
builder = builder.timeout(5000);
builder = builder.bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv");
HttpRequest httpRequest = builder.build();
```

**With method chaining - clean and concise:**
```java
HttpRequest httpRequest = new HttpRequest.Builder()
        .url("https://google.com")
        .methods("GET")
        .retryCount(3)
        .timeout(5000)
        .bearerToken("gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv")
        .build();
```

---

## Validation in Builder Pattern

This implementation demonstrates an important feature of the Builder pattern: **validation in the `build()` method**.

### Validation Logic

```java
public HttpRequest build() {
    if(url == null || url.isBlank()){
        throw new IllegalArgumentException("URL is mandatory");
    }
    if(method == null || method.isBlank()){
        throw new IllegalArgumentException("Method is mandatory");
    }
    return new HttpRequest(this);
}
```

### Benefits of Validation in build()

1. **Mandatory Field Enforcement**: Ensures required fields (url, method) are set before object creation
2. **Centralized Validation**: All validation logic is in one place
3. **Fail Fast**: Throws exception immediately if validation fails
4. **Immutable Objects**: Once created, the object is guaranteed to be in a valid state
5. **Clear Error Messages**: Provides specific error messages for each validation failure

### Example of Validation Failure

```java
// This will throw IllegalArgumentException
HttpRequest invalidRequest = new HttpRequest.Builder()
        .url("https://google.com")
        // Missing method - will fail validation
        .build();
```

**Error:**
```
IllegalArgumentException: Method is mandatory
```

---

## ✅ Problem Solved by Builder

### ❌ Without Builder (Telescoping Constructor Problem)

```java
// Problem: Multiple constructors with different parameter combinations
public class HttpRequest {
    private final String url;
    private final String method;
    private final Map<String,String> headers;
    private final Map<String,String> queryParams;
    private final String body;
    private final int timeout;
    private final String bearerToken;
    private final int retryCount;

    // Constructor 1: Only mandatory fields
    public HttpRequest(String url, String method) {
        this(url, method, null, null, null, 3000, null, 0);
    }

    // Constructor 2: Mandatory + timeout
    public HttpRequest(String url, String method, int timeout) {
        this(url, method, null, null, null, timeout, null, 0);
    }

    // Constructor 3: Mandatory + body
    public HttpRequest(String url, String method, String body) {
        this(url, method, null, null, body, 3000, null, 0);
    }

    // Constructor 4: All fields
    public HttpRequest(String url, String method, Map<String,String> headers, 
                     Map<String,String> queryParams, String body, int timeout, 
                     String bearerToken, int retryCount) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.queryParams = queryParams;
        this.body = body;
        this.timeout = timeout;
        this.bearerToken = bearerToken;
        this.retryCount = retryCount;
    }
}

// Usage - confusing and error-prone
HttpRequest req1 = new HttpRequest("https://api.com", "GET");
HttpRequest req2 = new HttpRequest("https://api.com", "GET", 5000);
HttpRequest req3 = new HttpRequest("https://api.com", "GET", "body data", 3000, null, 0);
// Problem: What does 3000 represent? Timeout? Retry count?
// Problem: Hard to remember parameter order
// Problem: Can't skip optional parameters in the middle
```

**Issues:**
- **Telescoping constructors**: Need multiple constructors for different parameter combinations
- **Parameter order confusion**: Hard to remember what each parameter represents
- **No parameter skipping**: Can't skip optional parameters in the middle
- **Readability**: Client code is hard to read and understand
- **Maintenance**: Adding new fields requires creating new constructors
- **No validation**: Validation must be added to each constructor

### ✅ With Builder Pattern

```java
// Clean, readable, and flexible
HttpRequest request = new HttpRequest.Builder()
        .url("https://api.example.com")
        .methods("POST")
        .body("{\"key\":\"value\"}")
        .addHeaders("Content-Type", "application/json")
        .addHeaders("Authorization", "Bearer token123")
        .timeout(10000)
        .retryCount(3)
        .build();

// Can skip optional parameters - use defaults
HttpRequest simpleRequest = new HttpRequest.Builder()
        .url("https://api.example.com")
        .methods("GET")
        .build();

// Can add multiple headers/query params easily
HttpRequest complexRequest = new HttpRequest.Builder()
        .url("https://api.example.com")
        .methods("POST")
        .addHeaders("Content-Type", "application/json")
        .addHeaders("Accept", "application/json")
        .addQueryParam("page", "1")
        .addQueryParam("limit", "10")
        .build();
```

**Benefits:**
- **Readable API**: Method names make it clear what each parameter is
- **Flexible parameter order**: Set parameters in any order
- **Optional parameters**: Skip parameters you don't need (use defaults)
- **Immutable objects**: HttpRequest object is immutable after creation
- **Fluent interface**: Method chaining provides a clean, readable API
- **Single construction point**: Only one place to modify construction logic
- **Validation**: Centralized validation in `build()` method
- **Default values**: Sensible defaults for optional fields (timeout=3000, retryCount=0)
- **Collection support**: Easy to add multiple items to collections (headers, queryParams)

---

## 🔗 Class Relationships

### Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        HttpRequest                               │
├─────────────────────────────────────────────────────────────────┤
│ - url: String (final)                                           │
│ - method: String (final)                                        │
│ - headers: Map<String,String> (final)                           │
│ - queryParams: Map<String,String> (final)                      │
│ - body: String (final)                                          │
│ - timeout: int (final)                                          │
│ - bearerToken: String (final)                                   │
│ - retryCount: int (final)                                        │
├─────────────────────────────────────────────────────────────────┤
│ + HttpRequest(Builder) [private]                                │
│ + getUrl(): String                                               │
│ + getMethod(): String                                            │
│ + getHeaders(): Map<String,String>                               │
│ + getQueryParams(): Map<String,String>                           │
│ + getBody(): String                                              │
│ + getTimeout(): int                                              │
│ + getBearerToken(): String                                       │
│ + getRetryCount(): int                                           │
│ + toString(): String                                            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ contains
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   HttpRequest.Builder                            │
├─────────────────────────────────────────────────────────────────┤
│ - url: String                                                    │
│ - method: String                                                 │
│ - headers: Map<String,String>                                    │
│ - queryParams: Map<String,String>                                │
│ - body: String                                                   │
│ - timeout: int (default: 3000)                                  │
│ - bearerToken: String                                            │
│ - retryCount: int (default: 0)                                   │
├─────────────────────────────────────────────────────────────────┤
│ + url(String): Builder                                           │
│ + methods(String): Builder                                      │
│ + body(String): Builder                                         │
│ + timeout(int): Builder                                         │
│ + bearerToken(String): Builder                                  │
│ + retryCount(int): Builder                                      │
│ + addHeaders(String, String): Builder                           │
│ + addQueryParam(String, String): Builder                        │
│ + build(): HttpRequest                                          │
└─────────────────────────────────────────────────────────────────┘
```

### Relationship Types

- **HttpRequest** → **HttpRequest.Builder**: Composition (Builder is a static nested class within HttpRequest)
- **HttpRequest.Builder** → **HttpRequest**: Creation (Builder creates HttpRequest objects via `build()` method)

---

## 🎨 Key Participants

| Participant | Role | Description |
|-------------|------|-------------|
| **Product** (`HttpRequest`) | Complex Object | The complex object being constructed with mandatory and optional fields |
| **Builder** (`HttpRequest.Builder`) | Construction Class | Static nested class that constructs the Product step by step with validation |
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
- ✅ When you have mandatory fields that must be validated
- ✅ When you want to provide sensible default values for optional parameters

---

## 🚀 Output Example

When you run `Main.java`, the output is:

```
HttpRequest{url='https://google.com', method='GET', headers={}, queryParams={}, body='null', timeout=5000, bearerToken='gfdhklkjfbhjkfdvsbjhksfbvdhvnkbdjzkzfbvankzdv', retryCount=3}
```

This demonstrates how the Builder pattern creates an HttpRequest object with specified fields while using default values for optional fields that weren't set (headers={}, queryParams={}, body=null).

---

## 🔍 Real-World Use Cases

- **HTTP Client Libraries**: Building HTTP requests with various headers, parameters, and options (e.g., OkHttp, Retrofit)
- **Database Query Builders**: Constructing complex SQL queries with WHERE clauses, JOINs, and ORDER BY
- **Configuration Objects**: Creating complex configuration objects with many optional settings
- **UI Component Builders**: Building complex UI components (e.g., AlertDialog in Android, SwiftUI views)
- **Message Builders**: Creating messages with various headers, attachments, and metadata
- **API Request Builders**: Building API requests with authentication, pagination, and filtering
- **Document Builders**: Creating documents with various sections, formatting, and metadata

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
- Builder can provide default values for optional parameters

### Q3: Can a Builder create multiple objects?

**Answer:**
Yes, a Builder can be reused to create multiple objects. After calling `build()`, you can modify the builder's state and call `build()` again to create another object with different or same parameters.

### Q4: How does Builder ensure immutability of the created object?

**Answer:**
- The Product class (HttpRequest) has a private constructor
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
2. **build() method**: Validate the complete object before creation (recommended for cross-field validation and mandatory field checking)

The HttpRequest example validates mandatory fields (url, method) in the `build()` method.

### Q7: How do default values work in the Builder pattern?

**Answer:**
Default values are set when the Builder is initialized. In the HttpRequest example:
- `timeout` defaults to 3000 milliseconds
- `retryCount` defaults to 0
- `headers` and `queryParams` are initialized as empty HashMaps
If the client doesn't set these values, the defaults are used when creating the final object.

### Q8: What are the advantages of using Builder for objects with collection fields?

**Answer:**
- Builder can provide helper methods like `addHeaders()` and `addQueryParam()` to add items to collections
- Collections can be initialized as empty collections in the Builder
- Client can add multiple items without managing the collection directly
- Cleaner API compared to passing pre-built collections to constructors

---

## 🔑 Key Takeaways

1. **Builder pattern** separates the construction of complex objects from their representation
2. It **eliminates telescoping constructors** by providing a fluent, readable API
3. The pattern uses **method chaining** for intuitive object construction
4. It enables creation of **immutable objects** with all required fields set at once
5. **Optional parameters** can be easily included or excluded during construction
6. The Builder is often implemented as a **static nested class** within the Product
7. **Validation** can be added in the `build()` method to ensure object consistency
8. Builder provides **flexibility** in parameter order and selection
9. **Default values** can be provided for optional parameters
10. **Helper methods** can be added for complex fields (e.g., `addHeaders()` for collections)

---

## 📚 Related Patterns

- **Abstract Factory**: Similar to Builder but focuses on creating families of related objects
- **Factory Method**: Creates objects in a single step, while Builder constructs step by step
- **Prototype**: Creates objects by cloning existing ones, while Builder constructs from scratch
- **Singleton**: Ensures only one instance exists, while Builder creates multiple instances
