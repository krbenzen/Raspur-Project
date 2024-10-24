package benzen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Shop {
    private List<Item> availableItems;
    private Random rng;
    private Scanner scanner;
    
    
 // must add GUI later to actually do this part
    //NOTE: make openShop to open shop for player java part 
    
    public Shop() {
        rng = new Random();
        scanner = new Scanner(System.in);
        availableItems = new ArrayList<>();

    }
}