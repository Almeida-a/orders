package orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * General information about an order
 */
class Order {
    private final String costumerName, costumerContact, shippingAddress;
    private final int grandTotal;

    /**
     * List of items in the order
     */
    private final ArrayList<Item> items;

    public String getCostumerName() {
        return costumerName;
    }

    public String getCostumerContact() {
        return costumerContact;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public int getGrandTotal() {
        return grandTotal;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public LocalDateTime getPlaced() {
        return placed;
    }

    /**
     *
     * @return A list of ages regarding each product of the order
     */
    public Collection<LocalDateTime> getProductAges() {
        return items.stream().map(
                item -> item.getProduct().getCreationDate()
        ).collect(Collectors.toList());
    }

    public Order(String costumerName, String costumerContact, String shippingAddress, int grandTotal, ArrayList<Item> items, LocalDateTime placed) {
        this.costumerName = costumerName;
        this.costumerContact = costumerContact;
        this.shippingAddress = shippingAddress;
        this.grandTotal = grandTotal;
        this.items = items;
        this.placed = placed;
    }

    /**
     * Date when the order was placed
     */
    private final LocalDateTime placed;

    /**
     * Generates a random instance of an order
     * @return random instance of an order
     */
    public static Order generateInstance() {
        int customerId = (int) (Math.random() * 1000);
        String costumerName = String.format("Customer[%d]", customerId),
                costumerContact = String.format("CostumerContact[%d]", customerId),
                shippingAddress = String.format("ShippingAddress[%d]", customerId);
        int grandTotal = (int) (Math.random() * 1000 + 1);
        LocalDateTime placed = LocalDateTime.now().minusMonths(
                (long) (Math.random() * SimulationParameters.ORDER_MONTHS_TIMEOUT + 1)
        );

        int itemCount = (int) (Math.random() * SimulationParameters.MAX_ITEMS_PER_ORDER + 1);
        ArrayList<Item> items = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            items.add(i, Item.generateInstance());
        }

        return new Order(costumerName, costumerContact, shippingAddress, grandTotal, items, placed);
    }
}
