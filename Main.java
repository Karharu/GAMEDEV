import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        typePrint("=== WELCOME TO SHADOWS OF THE CROWN ===\n");

        Character player = null;
        boolean picked = false;
        do {
            try {
                typePrint("Choose yiour hero:\n1) Omen\n2) Timzkie\n3) Jade\nChoice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: player = new Omen(); picked = true; break;
                    case 2: player = new Timzkie(); picked = true; break;
                    case 3: player = new Jade(); picked = true; break;
                    default: typePrint("Invalid choice. Please select 1-3.\n");
                }
                if (picked) {
                    typePrint("You have chosen " + player.getName() + ". Here are your skills:");
                    player.showSkills();
                }
            } catch (Exception e) {
                typePrint("Invalid input. Enter a number 1-3.\n");
                sc.nextLine();
            }
        } while (!picked);

        Game engine = new Game(player, sc);
        boolean proceed = engine.travelToCastle();
        if (!proceed || player.isDead()) {
            typePrint("\nYour journey ends here. Farewell.\n");
            return;
        }

        boolean finished = engine.assaultCastle();
        if (!finished || player.isDead()) {
            typePrint("\nYou met your end at the castle. Alas, your story fades.\n");
            return;
        }

        sc.close();
    }
    
    public static void typePrint(String text) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(50); } catch (InterruptedException e) { } // Speed sa typing effect sa game
        }
        System.out.println();
    }
}