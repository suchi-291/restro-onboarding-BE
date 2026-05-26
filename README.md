# Technical Documentation — Restaurant Onboarding & Order Management Prototype

## Project Overview

This project is a simplified SaaS-based restaurant onboarding and order management platform inspired by real-world food-tech systems.

The system supports:

```text
Restaurant onboarding
Menu configuration
Order placement
Payment tracking
Delivery tracking
```

The objective of this prototype is to simulate the onboarding and operational lifecycle of restaurant partners in a scalable SaaS platform.

---

# Tech Stack

| Layer              | Technology                  |
| ------------------ | --------------------------- |
| Backend Framework  | Spring Boot                 |
| Database           | MySQL                       |
| ORM                | Spring Data JPA / Hibernate |
| API Style          | REST APIs                   |
| Validation         | Jakarta Validation          |
| Build Tool         | Maven                       |
| Frontend (planned) | React                       |

---

# Package Structure

```text
com.restro
│
├── entity
├── repository
├── dto
├── service
├── controller
├── enums
└── exception
```

---

# Architecture Approach

The project follows a layered architecture.

```text
Controller Layer
↓
Service Layer
↓
Repository Layer
↓
Database
```

---

# Layer Responsibilities

## Controller Layer

Responsible for:

- receiving HTTP requests
- validating request payloads
- returning responses

Example:

```text
POST /restaurants
POST /orders
POST /payments/validate
```

Controllers should remain lightweight and should not contain business logic.

---

## Service Layer

Responsible for:

- business rules
- validations
- calculations
- orchestration logic

Examples:

- calculating order total
- validating menu availability
- checking duplicate payment processing
- updating delivery status

---

## Repository Layer

Responsible for:

- interacting with database
- CRUD operations
- query execution

Spring Data JPA repositories reduce boilerplate database code.

---

## Entity Layer

Represents database tables as Java classes.

Each entity maps to a relational table.

---

# Core Entities

## Restaurant

Represents a restaurant partner onboarded onto the platform.

### Fields

```text
id
name
location
taxPercentage
deliveryEnabled
active
```

### Notes

- `taxPercentage` uses `Double` for simplicity in the prototype.
- In production financial systems, `BigDecimal` is preferred for monetary precision.
- `deliveryEnabled` indicates whether the restaurant supports delivery operations.
- `active` indicates whether the restaurant is currently live on the platform.

---

## MenuItem

Represents individual food items configured by restaurants.

### Fields

```text
id
itemName
price
available
restaurant
```

### Notes

- Each menu item belongs to one restaurant.
- `available` allows restaurants to temporarily disable items.

---

## CustomerOrder

Represents a customer order placed against a restaurant.

### Fields

```text
id
restaurant
totalAmount
orderStatus
paymentStatus
```

### Notes

- Stores high-level order information.
- Detailed item-level information is maintained separately in `OrderItem`.

---

## OrderItem

Represents line items inside an order.

### Fields

```text
id
order
menuItem
quantity
itemPrice
```

### Notes

- `itemPrice` stores price snapshot at order time.
- This prevents future menu price changes from affecting historical orders.

---

## Payment

Represents payment transaction tracking.

### Fields

```text
id
order
gatewayOrderId
gatewayPaymentId
signature
paymentStatus
processed
```

### Notes

- Supports payment reconciliation.
- `processed` helps prevent duplicate callback processing (idempotency).

---

## Delivery

Represents delivery tracking details.

### Fields

```text
id
order
deliveryPartnerName
trackingNumber
deliveryStatus
```

### Notes

- Tracks delivery lifecycle.
- `trackingNumber` is stored as `String` because tracking IDs are often alphanumeric.

---

# Enum Design

Enums are used instead of raw strings for:

- consistency
- type safety
- cleaner code

---

## OrderStatus

```text
CREATED
PAYMENT_PENDING
PAYMENT_SUCCESS
PREPARING
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

---

## PaymentStatus

```text
PENDING
SUCCESS
FAILED
DUPLICATE_CALLBACK
```

---

## DeliveryStatus

```text
PENDING
ASSIGNED
PICKED_UP
OUT_FOR_DELIVERY
DELIVERED
CANCELLED
```

---

# Relationship Design

The project uses unidirectional JPA relationships for simplicity.

---

# Why Relationships Are Needed

Relationships model real-world business associations.

Example:

```text
One restaurant has many menu items.
One order has many order items.
One order has one payment.
```

These relationships are implemented using JPA annotations.

---

# Current Relationship Design

## Restaurant → MenuItem

### Business Meaning

One restaurant can configure many menu items.

### Database Design

`menu_item` table contains:

```text
restaurant_id
```

### JPA Mapping

Inside `MenuItem`:

```java
@ManyToOne
@JoinColumn(name = "restaurant_id")
private Restaurant restaurant;
```

---

## Restaurant → CustomerOrder

### Business Meaning

One restaurant can receive many customer orders.

### Database Design

`customer_order` table contains:

```text
restaurant_id
```

### JPA Mapping

Inside `CustomerOrder`:

```java
@ManyToOne
@JoinColumn(name = "restaurant_id")
private Restaurant restaurant;
```

---

## CustomerOrder → OrderItem

### Business Meaning

One customer order can contain multiple order items.

### Database Design

`order_item` table contains:

```text
order_id
```

### JPA Mapping

Inside `OrderItem`:

```java
@ManyToOne
@JoinColumn(name = "order_id")
private CustomerOrder order;
```

---

## OrderItem → MenuItem

### Business Meaning

Each order item points to the menu item ordered.

### Database Design

`order_item` table contains:

```text
menu_item_id
```

### JPA Mapping

Inside `OrderItem`:

```java
@ManyToOne
@JoinColumn(name = "menu_item_id")
private MenuItem menuItem;
```

---

## CustomerOrder → Payment

### Business Meaning

One order has one payment record.

### Database Design

`payment` table contains:

```text
order_id
```

### JPA Mapping

Inside `Payment`:

```java
@OneToOne
@JoinColumn(name = "order_id")
private CustomerOrder order;
```

---

## CustomerOrder → Delivery

### Business Meaning

One order has one delivery tracking record.

### Database Design

`delivery` table contains:

```text
order_id
```

### JPA Mapping

Inside `Delivery`:

```java
@OneToOne
@JoinColumn(name = "order_id")
private CustomerOrder order;
```

---

# Why We Are Using Unidirectional Relationships

In production systems, relationships are often bidirectional.

Example:

```java
Restaurant
    → List<MenuItem>

MenuItem
    → Restaurant
```

using:

```java
@OneToMany(mappedBy = "restaurant")
private List<MenuItem> menuItems;
```

However, for this prototype we intentionally use unidirectional relationships.

---

# Why We Simplified It

## Production Systems

Production-grade systems usually include:

- bidirectional relationships
- lazy loading optimization
- DTO mapping frameworks
- pagination
- caching
- complex joins
- recursive serialization handling

---

## Prototype Approach

For this prototype:

- simpler codebase
- easier debugging
- avoids recursive JSON serialization issues
- faster development within limited timeline
- easier explanation during interviews

---

# Why We Avoided `@OneToMany` Collections Initially

Example:

```java
@OneToMany(mappedBy = "restaurant")
private List<MenuItem> menuItems;
```

Potential issues:

- infinite recursion in JSON responses
- increased complexity
- unnecessary for current API scope

Instead:

- child entity knows parent
- parent does not maintain collection

This keeps the model lightweight and interview-friendly.

---

# Production vs Prototype Decisioning

| Area              | Production                     | Prototype       |
| ----------------- | ------------------------------ | --------------- |
| Monetary Values   | BigDecimal                     | Double          |
| Relationships     | Bidirectional                  | Unidirectional  |
| Authentication    | OAuth/JWT fully integrated     | Deferred        |
| Delivery Tracking | Real integrations              | Simulated       |
| Payments          | Actual gateway                 | Mock validation |
| Logging           | Structured centralized logging | Basic logs      |
| Error Handling    | Enterprise-grade               | Simplified      |

---

# Current Milestone

At this stage:

- entity modeling is complete
- enums are complete
- database relationship design is complete

Next steps:

- repositories
- DTOs
- service layer
- controllers
- validation
- frontend integration