package orders;

import java.time.LocalDateTime;

class Product {
    private final String name;
    private final String category;
    private final int weight, price;
    private final LocalDateTime creationDate;

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Product(String name, String category, int weight, int price, LocalDateTime creationDate) {
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.price = price;
        this.creationDate = creationDate;
    }

    /**
     * Generates product object with:
     * Random name/category; weight[1,100]; price[1,100];
     * Random creation date - [1,15] months ago
     * @return new, random product
     */
    public static Product generateInstance() {

        String name = String.format("Product%d", (int) (Math.random() * 1000 + 1)),
                category = String.format("Category%d", (int) (Math.random() * 1000 + 1));
        int weight = (int) (Math.random() * 100 + 1),
                price = (int) (Math.random() * 100 + 1);
        LocalDateTime creationDate = LocalDateTime.now().minusMonths((long) (Math.random() * 6 + 1));

        return new Product(name, category, weight, price, creationDate);
    }
}
