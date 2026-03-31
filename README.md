# 🛒 Ecommerce Order Engine Hackathon

## 📌 Project Overview
This project is a CLI-based Ecommerce Order Engine developed in Java.  
It simulates real-world e-commerce operations such as product management, cart handling, order processing, and system reliability features like failure handling and fraud detection.

---

## ⚙️ Features Implemented

### 🛍️ Core Features
- Add Product
- View Products
- Add to Cart
- Remove from Cart
- View Cart
- Place Order
- View Orders

### 🔥 Advanced Features
- ✅ Audit Logging System
- ✅ Failure Injection (Random failure simulation)
- ✅ Fraud Detection (High-value order detection)
- ✅ Idempotency Handling (Avoid duplicate orders)
- ✅ Low Stock Alert
- ✅ CLI-based interactive menu

---

## 🏗️ Design Approach

The system is designed using a modular approach:

- **Product Module** → Handles product details and inventory
- **Cart Module** → Manages cart operations
- **Order Module** → Handles order placement and tracking
- **Logger Utility** → Maintains system logs
- **Failure Handling** → Simulates real-world failures

---

## ⚠️ Assumptions

- No database is used (data stored in memory)
- Single-user CLI system
- Stock reduces when added to cart
- Random failures simulate real-world scenarios
- Fraud detection based on order amount

---

## ▶️ How to Run the Project

### Step 1: Compile
```bash
javac Main.java

Step 2: Run

java Main

# Sample Output

======================================
 Welcome to the ECOMMERCE ORDER ENGINE
======================================

******** MENU ********
1.Add Product
2.View Products
3.Add to Cart
4.Remove from Cart
5.View Cart
6.Place Order
7.View Orders
8.Low Stock Alert
9.Trigger Failure
10.Exit

# Future Enhancements
Multi-user support
Database integration (MySQL)
REST API using Spring Boot
Frontend UI (React)
Microservices architecture

## 👩‍💻 Author
Bhulakshmi