import java.util.*;

public class Main {
    private static GameUI ui = new GameUI();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        ui.displayGameTitle();

        Character player = null;
        boolean picked = false;
        do {
            try {
                typePrint("Choose your hero:\n1) Omen\n2) Timzkie\n3) Jade");
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1: player = new Omen(); picked = true; break;
                    case 2: player = new Timzkie(); picked = true; break;
                    case 3: player = new Jade(); picked = true; break;
                    default: typePrint("Invalid choice. Please select 1-3.\n"); continue;
                }
                if (picked) {
                    typePrint("You have chosen " + player.getName() + ". Here are your skills:");
                    player.showSkills();
                    boolean confirmed = false;
                    while (!confirmed) {
                        System.out.print("Continue with this hero? (Y/N): ");
                        String confirm = sc.nextLine().trim().toUpperCase();
                        if (confirm.equals("Y")) {
                            picked = true;
                            confirmed = true;
                        } else if (confirm.equals("N")) {
                            typePrint("Returning to hero selection...\n");
                            picked = false;
                            confirmed = true;
                        } else {
                            typePrint("Invalid input. Please enter Y or N.\n");
                        }
                    }
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