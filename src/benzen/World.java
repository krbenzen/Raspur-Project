package benzen;
import java.util.List;

public class World {

    private Player player;
    private Shop shop;
    private int currentLevelNumber;
    private boolean isRunning;
  private Level currentLevel;
  private combatSystem combatSystem;
    
    
    public World() {
        this.player = new Player(); 
        // makes the player
//        this.combatSystem = new CombatSystem(); 
        // implement later, focus on shops first
//        this.currentLevelNumber = 1; // start at level 1
        this.isRunning = true; // Set game to running
    }
    
    
    
    // NOTE
    //all the features for now will all be text until I add my gui
    
    
    
    
    // starts game
    public void start() {
        while (isRunning) {
            System.out.println("Starting level "); // Generate new level

            // starts the combat
            combatSystem.startCombat(player, currentLevel.getCreatures());

            // check if dead
            if (player.isDead()) {
                System.out.println("Game Over");
                resetGame();
                continue;
            }

            System.out.println("You've completed Level " + currentLevelNumber);
            rewardPlayer();

            // shop
            if (currentLevelNumber % 5 == 0) {
                shop = new Shop();
                shop.openShop(player); // will open the shop
            }

            // Progress to next level
            currentLevelNumber++;
        }
    }

    // reset the game when dying
    private void resetGame() {
        currentLevelNumber = 1;
        player.reset(); 
    }

    // rewards after completing 1 level
    private void rewardPlayer() {
        int coinsEarned = currentLevelNumber;
        System.out.println("You've earned " + coinsEarned + " coins.");
        player.addCoins(coinsEarned); 
    }

    // ends game
    public void end() {
        isRunning = false;
    }

    public static void main(String[] args) {
        World world = new World();
        world.start();
    }
}
