package group47.cs2212.petgame;

import java.io.Serializable;

/**
 * Represents an item that can be purchased from the shop.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int price;
    private String type; // e.g., Food or Gift

    /**
     * Constructor for creating an item.
     *
     * @param name  The name of the item.
     * @param price The price of the item.
     * @param type  The type of the item (e.g., Food, Gift).
     */
    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
