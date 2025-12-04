import java.util.*;

public class Shop {
    public static void openShop(Character p, Scanner sc) {
        boolean shopping = true;
        Random rand = new Random();
        while (shopping) {
            System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║             THE TRAVELER'S TENT            ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ You have: " + p.getCurrency() + "G                                ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("Items for Sale:");
            System.out.println("──────────────────────────────────────────────");
            System.out.println("1) RUSTBORN FANG (50 gold)");
            System.out.println("2) CONQUEROR'S BLADE (100 gold)");
            System.out.println("3) KINGSBANE REAVER (200 gold)");
            System.out.println("4) Mana Potion (+20 mana) (15 gold)");
            System.out.println("5) Health Potion (+20 HP) (15 gold)");
            System.out.println("6) Exit Shop");
            System.out.print("Buy choice: ");

            int choice = safeIntRead(sc);
            switch(choice) {
                case 1:
                    if(p.spendGold(50)) { p.weaponDamage +=5; System.out.println("RUSTBORN FANG acquired!"); } else System.out.println("Not enough gold.");
                    break;
                case 2:
                    if(p.spendGold(100)) { p.weaponDamage +=10; System.out.println("CONQUEROR'S BLADE acquired!"); } else System.out.println("Not enough gold.");
                    break;
                case 3:
                    if(p.spendGold(200)) { p.weaponDamage +=20; System.out.println("KINGSBANE REAVER acquired!"); } else System.out.println("Not enough gold.");
                    break;
                case 4:
                    if(p.spendGold(15)) { p.restoreMana(40 - p.getMana()); System.out.println("Mana fully restored to 40!"); } else System.out.println("Not enough gold.");
                    break;
                case 5:
                    if(p.spendGold(15)) { int heal = rand.nextInt(16) + 25; p.healHp(heal); System.out.println("HP restored +"+heal+"!"); } else System.out.println("Not enough gold.");
                    break;
                case 6: System.out.println("Exiting shop..."); shopping = false; break;
                default: System.out.println("The traveller moves on! You miss the chance to trade."); shopping = false; break;
            }
            if(shopping) {
                System.out.print("Buy another item? 1=Yes 2=No: ");
                int more = safeIntRead(sc);
                if(more != 1){
                    System.out.println("Exiting shop...");
                    shopping = false;
                }
            }
        }
    }

    private static int safeIntRead(Scanner sc) {
        while(!sc.hasNextInt()) { sc.next(); System.out.print("Enter a number: "); }
        return sc.nextInt();
    }
}