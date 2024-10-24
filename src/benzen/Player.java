package benzen;

import java.util.ArrayList;

public class Player {
    private int health;
    private int maxHealth;
    private int currentLevel;
    private int currency;
    private ArrayList<Item> inventory; // will be used for items later

    //  initialize player stats
    public Player() {
        this.maxHealth = 100;
        this.health = maxHealth;
        this.currentLevel = 1; // start at level 1
        this.currency = 100; // gave 100 currency at the start
        this.inventory = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public int getCurrency() {
        return currency;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    // method to add coins to the players wallet
    public void addCoins(int coins) {
        this.currency += coins;
        System.out.println("Current balance: " + currency + " coins.");
    }

    // method for when the player gets hurt
    public void takeDamage(int damage) {
        this.health -= damage;
        if (health < 0) {
            health = 0;
        }
        System.out.println("Current health: " + health);
    }

    // method to heal
    public void heal(int amount) {
        this.health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
        System.out.println("Healed by " + amount + " HP. Current health: " + health);
    }

 
    // reset player stats (for restarting the game)
    public void reset() {
        this.health = maxHealth;
        this.currentLevel = 1;
        this.currency = 100; 
        this.inventory.clear(); 
        System.out.println("player has been reset");
    }

    // check if the player is dead 
    public boolean isDead() {
        return health <= 0;
    }

    // progress to the next level
    public void progressLevel() {
        currentLevel++;
        System.out.println("You have reached level " + currentLevel);
    }
}