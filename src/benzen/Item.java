package benzen;

public class Item {
    private String name;
    private int price;
    private String rarity;

    public Item(String name, int price, String rarity) {
        this.name = name;
        this.price = price;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getRarity() {
        return rarity;
    }

    // idk i just made a health potion for now
    public void applyEffect(Player player) {
        if (name.equals("Health Potion")) {
            player.heal(50); // Heal the player by 50 points
        }
        // other items later
        // maybe display rarity???
    }
}