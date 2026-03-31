import java.util.*;

class Main {

    static Scanner sc = new Scanner(System.in);

    // Data Stores
    static Map<Integer, Product> products = new HashMap<>();
    static Map<Integer, Integer> cart = new HashMap<>();
    static List<Order> orders = new ArrayList<>();
    static Set<String> orderRequests = new HashSet<>();

    static int productId = 1;
    static int orderId = 1;

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println(" Welcome to the ECOMMERCE ORDER ENGINE ");
        System.out.println("======================================");

        while (true) {
            System.out.println("\n******** MENU ********");
            System.out.println("1.Add Product\n2.View Products\n3.Add to Cart\n4.Remove from Cart\n5.View Cart\n6.Place Order\n7.View Orders\n8.Low Stock Alert\n9.Trigger Failure\n10.Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addProduct(); break;
                case 2: viewProducts(); break;
                case 3: addToCart(); break;
                case 4: removeFromCart(); break;
                case 5: viewCart(); break;
                case 6: placeOrder(); break;
                case 7: viewOrders(); break;
                case 8: lowStockAlert(); break;
                case 9: simulateFailure(); break;
                case 10: return;
                default: System.out.println("Invalid Choice");
            }
        }
    }

    // ---------------- PRODUCT ----------------

    static void addProduct() {
        sc.nextLine(); // clear buffer
        System.out.print("Product Name: ");
        String name = sc.nextLine();

        System.out.print("Price: ");
        double price = sc.nextDouble();

        System.out.print("Stock: ");
        int stock = sc.nextInt();

        products.put(productId, new Product(productId, name, price, stock));
        log("Added Product " + name);
        productId++;
    }

    static void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available");
            return;
        }
        for (Product p : products.values()) {
            System.out.println(p);
        }
    }

    // ---------------- CART ----------------

    static void addToCart() {
        System.out.print("Product ID: ");
        int id = sc.nextInt();

        System.out.print("Qty: ");
        int qty = sc.nextInt();

        Product p = products.get(id);

        if (p == null || p.stock < qty) {
            System.out.println("Not enough stock");
            return;
        }

        cart.put(id, cart.getOrDefault(id, 0) + qty);
        p.stock -= qty;

        log("Added to cart Product " + id + " qty=" + qty);
    }

    static void removeFromCart() {
        System.out.print("Product ID to remove: ");
        int id = sc.nextInt();

        if (!cart.containsKey(id)) {
            System.out.println("Product not in cart");
            return;
        }

        int qty = cart.get(id);
        products.get(id).stock += qty;

        cart.remove(id);

        log("Removed Product " + id + " from cart");
    }

    static void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        double total = 0;
        System.out.println("---- CART ----");

        for (int id : cart.keySet()) {
            Product p = products.get(id);
            int qty = cart.get(id);

            total += p.price * qty;
            System.out.println(p.name + " x " + qty);
        }

        System.out.println("Total: " + total);
    }

    // ---------------- ORDER ----------------

    static void placeOrder() {

        if (cart.isEmpty()) {
            System.out.println("Cart empty");
            return;
        }

        System.out.print("Enter Request ID: ");
        String requestId = sc.next();

        // Idempotency
        if (orderRequests.contains(requestId)) {
            System.out.println("Duplicate order ignored");
            return;
        }
        orderRequests.add(requestId);

        double total = 0;

        for (int id : cart.keySet()) {
            Product p = products.get(id);
            total += p.price * cart.get(id);
        }

        // Fraud detection
        if (total > 50000) {
            System.out.println("⚠ Fraud Alert: High value order");
        }

        // Failure simulation
        Random r = new Random();
        if (r.nextInt(5) == 0) {
            System.out.println("❌ Payment Failed");
            return;
        }

        Order o = new Order(orderId++, total);
        orders.add(o);

        log("Order Placed ID=" + o.id + " Amount=" + total);

        cart.clear();

        System.out.println("✅ Order Placed Successfully");
    }

    static void viewOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders yet");
            return;
        }
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    // ---------------- LOW STOCK ----------------

    static void lowStockAlert() {
        boolean found = false;

        for (Product p : products.values()) {
            if (p.stock < 5) {
                System.out.println("⚠ Low Stock: " + p.name);
                found = true;
            }
        }

        if (!found) {
            System.out.println("All products have sufficient stock");
        }
    }

    // ---------------- FAILURE ----------------

    static void simulateFailure() {
        System.out.println("Simulating failure...");
        try {
            Random r = new Random();
            if (r.nextBoolean()) {
                throw new RuntimeException("Random Failure Occurred");
            }
            System.out.println("No failure this time");
        } catch (Exception e) {
            log("Failure: " + e.getMessage());
        }
    }

    // ---------------- LOGGER ----------------

    static void log(String msg) {
        System.out.println("[LOG " + new Date() + "] " + msg);
    }
}

// ---------------- MODELS ----------------

class Product {
    int id;
    String name;
    double price;
    int stock;

    Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String toString() {
        return id + " " + name + " Price:" + price + " Stock:" + stock;
    }
}

class Order {
    int id;
    double amount;

    Order(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public String toString() {
        return "OrderID=" + id + " Amount=" + amount;
    }
}