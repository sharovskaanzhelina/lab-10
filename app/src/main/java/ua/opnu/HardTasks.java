package ua.opnu;

import ua.opnu.util.Customer;
import ua.opnu.util.DataProvider;
import ua.opnu.util.Order;
import ua.opnu.util.Product;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class HardTasks {

    private final List<Customer> customers = DataProvider.customers;
    private final List<Order> orders = DataProvider.orders;
    private final List<Product> products = DataProvider.products;

    public static void main(String[] args) {
        HardTasks tasks = new HardTasks();

        Objects.requireNonNull(tasks.getBooksWithPrice(), "Method getBooksWithPrice() returns null").forEach(System.out::println);
        Objects.requireNonNull(tasks.getOrdersWithBabyProducts(), "Method getOrdersWithBabyProducts() returns null").forEach(System.out::println);
    }

    public List<Product> getBooksWithPrice() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .filter(p -> p.getPrice() > 100)
                .toList();
    }

    public List<Order> getOrdersWithBabyProducts() {
        return orders.stream()
                .filter(o -> o.getProducts().stream()
                        .anyMatch(p -> p.getCategory().equals("Baby")))
                .toList();
    }

    public List<Product> applyDiscountToToys() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Toys"))
                .peek(p -> p.setPrice(p.getPrice() * 0.5))
                .toList();
    }

    public Optional<Product> getCheapestBook() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .findFirst();
    }

    public List<Order> getRecentOrders() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();
    }

    public DoubleSummaryStatistics getBooksStats() {
        return products.stream()
                .filter(p -> p.getCategory().equals("Books"))
                .mapToDouble(Product::getPrice)
                .summaryStatistics();
    }

    public Map<Integer, Integer> getOrdersProductsMap() {
        return orders.stream()
                .collect(Collectors.toMap(
                        Order::getId,
                        o -> o.getProducts().size()
                ));
    }

    public Map<String, List<Integer>> getProductsByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getId, Collectors.toList())
                ));
    }

}