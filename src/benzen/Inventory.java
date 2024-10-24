package benzen;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    // initialize empty inventory
    public Inventory() {
        this.items = new ArrayList<>();
    }

    // add an item to the inventory
    public void addItem(Item item) {
        items.add(item);
    }

    // display all items in the inventory
    public void displayInventory() {
        if (items.isEmpty()) {
        } else {
            System.out.println("Your inventory has:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + ". " + items.get(i).getName());
            }
        }
    }

    // check if inventory contains a specific item
    public boolean containsItem(Item item) {
        return items.contains(item);
    }

    // clear all items in the inventory (used when resetting the player)
    public void clearInventory() {
        items.clear();
    }
}