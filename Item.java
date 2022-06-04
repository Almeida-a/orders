package orders;

class Item {
    private final int cost, shippingFee, taxAmount;

    /**
     * What type of item is this? Defined by class Product
     */
    private final Product product;

    /**
     * Generates a random Item object with random:
     * cost[1, 1000]; shippingFee[1, 20]; taxAmount[1, 10]
     * and a random Product instance
     * @return the random Item object
     */
    public static Item generateInstance() {

        int cost = (int) (Math.random() * 1000 + 1),
                shippingFee = (int) (Math.random() * 20 + 1),
                taxAmount = (int) (Math.random() * 10 + 1);
        Product product = Product.generateInstance();

        return new Item(cost, shippingFee, taxAmount, product);
    }

    public int getCost() {
        return cost;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public int getTaxAmount() {
        return taxAmount;
    }

    public Product getProduct() {
        return product;
    }

    public Item(int cost, int shippingFee, int taxAmount, Product product) {
        this.cost = cost;
        this.shippingFee = shippingFee;
        this.taxAmount = taxAmount;
        this.product = product;
    }
}
