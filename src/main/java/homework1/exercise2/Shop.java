package homework1.exercise2;

import homework1.exercise1.BetterProduct;

import java.util.*;

import static java.util.Optional.ofNullable;

public class Shop {
    private final List<BetterProduct> products;
    private final List<InventoryItem> inventoryItems;

    public Shop(List<BetterProduct> products) {
        this.products = ofNullable(products)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);

        this.inventoryItems = Optional.of(createInventoryList())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }

    public List<BetterProduct> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public List<InventoryItem> getInventoryItems() {
        return Collections.unmodifiableList(inventoryItems);
    }

    private List<InventoryItem> createInventoryList() {
        List<InventoryItem> items = new ArrayList<>();
        products.forEach(product -> items.add(createInventoryEntryForProduct(product.getName())));
        return items;
    }

    public void addProduct(BetterProduct product, int quantity) {
        if (isAlreadyInInventory(product.getName())) {
            increaseItemQuantity(product.getName(), quantity);
        } else {
            addNewProduct(product, quantity);
        }
    }

    public void increaseInventoryForProduct(String productName, int quantity) {
        increaseItemQuantity(productName, quantity);
    }

    public InventoryItem buyItem(String productName, int quantity) {
        InventoryItem item = findOrThrowByProductName(productName);
        return enoughItemsInStock(item, quantity) ? sellItem(item, quantity) : partialSell(item);
    }

    private InventoryItem sellItem(InventoryItem item, int quantity) {
        InventoryItem soldItem = new InventoryItem(item.getItemName(), quantity);
        item.decreaseQuantity(quantity);
        return soldItem;
    }

    private InventoryItem partialSell(InventoryItem item) {
        if (item.getQuantity() > 0) {
            return getRemainingAmountOfProduct(item);
        } else {
            throw new RuntimeException("No " + item.getItemName() + "s in stock!");
        }
    }

    private InventoryItem getRemainingAmountOfProduct(InventoryItem item) {
        System.out.println();
        System.out.println("Insufficient stock! We can give you the amount of " + item.getQuantity() + " " + item.getItemName() + "s.");
        InventoryItem soldItem = new InventoryItem(item.getItemName(), item.getQuantity());
        item.decreaseQuantity(item.getQuantity());
        return soldItem;
    }

    private boolean enoughItemsInStock(InventoryItem item, int quantity) {
        return item.getQuantity() >= quantity;
    }

    private void addNewProduct(BetterProduct product, int quantity) {
        products.add(product);
        inventoryItems.add(new InventoryItem(product.getName(), quantity));
    }

    private void increaseItemQuantity(String productName, int quantity) {
        InventoryItem item = findOrThrowByProductName(productName);
        item.increaseQuantity(quantity);
    }

    private InventoryItem findOrThrowByProductName(String productName) {
        return inventoryItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find item with name " + productName));
    }

    private InventoryItem findOrCreateByName(String productName) {
        return inventoryItems.stream()
                .filter(item -> item.getItemName().equalsIgnoreCase(productName))
                .findFirst()
                .orElseGet(() -> new InventoryItem(productName, 0));
    }

    private boolean isAlreadyInInventory(String productName) {
        return products.stream().anyMatch(product -> product.getName().equalsIgnoreCase(productName));
    }

    private InventoryItem createInventoryEntryForProduct(String productName) {
        return !isAlreadyInInventory(productName) ? new InventoryItem(productName, 0) : findOrCreateByName(productName);
    }

    private class InventoryItem {
        private final String itemName;
        private int quantity;

        public InventoryItem(String itemName, int quantity) {
            this.itemName = itemName;
            this.quantity = quantity;
        }

        private void increaseQuantity(int amount) {
            this.quantity += amount;
        }

        private void decreaseQuantity(int amount) {
            this.quantity -= amount;
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InventoryItem that = (InventoryItem) o;
            return quantity == that.quantity && Objects.equals(itemName, that.itemName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(itemName, quantity);
        }

        @Override
        public String toString() {
            return "InventoryItem{" +
                    "itemName='" + itemName + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }

}
