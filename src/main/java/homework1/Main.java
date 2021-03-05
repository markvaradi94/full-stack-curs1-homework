package homework1;

import homework1.exercise1.BetterProduct;
import homework1.exercise1.Product;
import homework1.exercise2.Shop;

import java.util.List;

import static homework1.exercise1.Category.*;
import static homework1.exercise1.Product.ProductBuilder.product;
import static homework1.exercise1.BetterProduct.BetterProductBuilder.betterProduct;

public class Main {
    public static void main(String[] args) {
        Product product = product()
                .name("Display")
                .price(2500)
                .category(ELECTRONICS)
                .category(DYI)
                .description("25 inch monitor")
                .build();

        System.out.println();
        System.out.println(product);

        BetterProduct product2 = betterProduct()
                .name("this thing right here")
                .price(2000)
                .category("DYI")
                .category("electronics")
                .category("foOd")
                .description("de toate")
                .build();

        System.out.println();
        System.out.println(product2);


        BetterProduct product3 = betterProduct()
                .name("cheese")
                .price(200)
                .category("food")
                .description("mouldy")
                .build();

        Shop shop = new Shop(List.of());

        System.out.println("=====================");
        System.out.println(shop.getProducts());
        System.out.println("=====================");
        System.out.println(shop.getInventoryItems());
        System.out.println("=====================");

        shop.addProduct
                (betterProduct()
                                .name("tomato")
                                .price(50)
                                .category("food")
                                .build(),
                        5);

        shop.addProduct(
                betterProduct()
                        .name("cheese")
                        .build(),
                10);

        shop.addProduct(
                betterProduct()
                        .name("PC")
                        .price(5000)
                        .category("ELECTRONICS")
                        .category("dyi")
                        .description("cheap")
                        .build(),
                1);

        System.out.println();
        System.out.println(shop.getInventoryItems());

        shop.increaseInventoryForProduct("pc", 30);

        System.out.println();
        System.out.println(shop.getInventoryItems());

        System.out.println(shop.buyItem("pc", 10));
        System.out.println();
        System.out.println(shop.getInventoryItems());

        System.out.println(shop.buyItem("pc", 50));
        System.out.println();
        System.out.println(shop.getInventoryItems());
        System.out.println(shop.getProducts());
    }
}
