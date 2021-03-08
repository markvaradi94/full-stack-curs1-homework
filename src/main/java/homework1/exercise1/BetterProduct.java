package homework1.exercise1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class BetterProduct {
    private String name;
    private int price;
    private List<Category> categories;
    private String description;

    public BetterProduct(String name, int price, List<Category> categories, String description) {
        this.name = name.toUpperCase();
        this.price = price;
        this.categories = ofNullable(categories)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        this.description = description;
    }

    public BetterProduct() {
        this.categories = new ArrayList<>();
    }

    private BetterProduct(BetterProductBuilder builder) {
        this.name = builder.state.name;
        this.price = builder.state.price;
        this.categories = ofNullable(builder.state.categories)
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
        this.description = builder.state.description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetterProduct that = (BetterProduct) o;
        return price == that.price && Objects.equals(name, that.name) && Objects.equals(categories, that.categories) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, categories, description);
    }

    @Override
    public String toString() {
        return "BetterProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", categories=" + categories +
                ", description='" + description + '\'' +
                '}';
    }

    public static class BetterProductBuilder {
        private BetterProduct state = new BetterProduct();

        public static BetterProductBuilder betterProduct() {
            return new BetterProductBuilder();
        }

        public BetterProductBuilder name(String name) {
            state.name = name;
            return this;
        }

        public BetterProductBuilder price(int price) {
            state.price = price;
            return this;
        }

        public BetterProductBuilder category(String category) {
            state.categories.add(Category.valueOf(category.toUpperCase()));
            return this;
        }

        public BetterProductBuilder description(String description) {
            state.description = description;
            return this;
        }

        public BetterProduct build() {
            return new BetterProduct(this);
        }
    }
}
