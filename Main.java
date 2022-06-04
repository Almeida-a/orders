package orders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime left = LocalDateTime.parse(args[0], formatter),
                right = LocalDateTime.parse(args[1], formatter);

        // Read products, items and orders
        int numberOfOrders = 100;
        Order[] orders = new Order[numberOfOrders];
        for (int i = 0; i < numberOfOrders; i++) {
            orders[i] = Order.generateInstance();
        }

        // Filter orders using interval defining parameters
        orders = Arrays.stream(orders).filter(
                order -> order.getPlaced().isAfter(left) && order.getPlaced().isBefore(right)
        ).toArray(Order[]::new);

        // Group the orders using the Grouper class
        Grouper grouper = new Grouper(orders, Arrays.copyOfRange(args, 2, args.length));

        TreeMap<String, Integer> group = new TreeMap<>(grouper.group());

        group.forEach(
                (key, value) -> System.out.printf("%s months: %d orders\n", key, value)
        );
    }

}

