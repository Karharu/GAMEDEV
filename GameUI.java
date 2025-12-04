import java.util.*;

public class GameUI {
    private Scanner scanner;
    
    public GameUI() {
        this.scanner = new Scanner(System.in);
    }
    
    // ==================== SCREEN MANAGEMENT ====================
    
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    public void pause(String message) {
        System.out.println("\n" + message);
        scanner.nextLine();
    }
    
    // ==================== HEADERS & DIVIDERS ====================
    
    public void printHeader(String title) {
        System.out.println("\n" + UIConstants.DIVIDER);
        System.out.println(centerText(title, 50));
        System.out.println(UIConstants.DIVIDER);
    }
    
    public void printSubHeader(String title) {
        System.out.println("\n" + UIConstants.SEPARATOR);
        System.out.println("  " + title);
        System.out.println(UIConstants.SEPARATOR);
    }
    
    public void printDivider() {
        System.out.println(UIConstants.SEPARATOR);
    }
    
    public void printDoubleDivider() {
        System.out.println(UIConstants.DIVIDER);
    }
    
    // ==================== MESSAGES ====================
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public void displayError(String message) {
        System.out.println(UIConstants.RED + "❌ " + message + UIConstants.RESET);
    }
    
    public void displaySuccess(String message) {
        System.out.println(UIConstants.GREEN + "✓ " + message + UIConstants.RESET);
    }
    
    public void displayWarning(String message) {
        System.out.println(UIConstants.YELLOW + "⚠ " + message + UIConstants.RESET);
    }
    
    public void displayInfo(String message) {
        System.out.println(UIConstants.CYAN + "ℹ " + message + UIConstants.RESET);
    }
        
    // ==================== TYPEWRITER EFFECT ====================
    
    public void typewriterPrint(String text) {
        typewriterPrint(text, 30);
    }
    
    public void typewriterPrint(String text, int delayMs) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
    
    // ==================== PLAYER STATS ====================
    
    public void displayPlayerStats(String name, int health, int maxHealth, int mana, int maxMana, int gold, int level) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║       CHARACTER STATUS             ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.printf("║ Name:  %-27s║\n", name);
        System.out.printf("║ Level: %-27d║\n", level);
        System.out.printf("║ HP:    %s %-17s║\n", getHealthBar(health, maxHealth, 15), health + "/" + maxHealth);
        System.out.printf("║ Mana:  %s %-17s║\n", getManaBar(mana, maxMana, 15), mana + "/" + maxMana);
        System.out.printf("║ Gold:  %s%-26d║\n", UIConstants.YELLOW, gold);
        System.out.print(UIConstants.RESET);
        System.out.println("╚════════════════════════════════════╝");
    }
    
    public void displayQuickStats(String name, int health, int maxHealth, int gold) {
        System.out.printf("\n%s | HP: %s %s | Gold: %s%d%s\n", 
            name, 
            getHealthBar(health, maxHealth, 10),
            UIConstants.RESET + health + "/" + maxHealth,
            UIConstants.YELLOW,
            gold,
            UIConstants.RESET);
    }
    
    public String getHealthBar(int current, int max, int length) {
        int filled = max > 0 ? (int) ((current / (double) max) * length) : 0;
        filled = Math.max(0, Math.min(length, filled));
        String color;
        double percentage = max > 0 ? (current / (double) max) : 0;
        if (percentage > 0.5) {
            color = UIConstants.GREEN;
        } else if (percentage > 0.25) {
            color = UIConstants.YELLOW;
        } else {
            color = UIConstants.RED;
        }
        
        return color + "[" + "█".repeat(filled) + "░".repeat(length - filled) + "] " + current + "/" + max + UIConstants.RESET;

    }
    
    public String getManaBar(int current, int max, int length) {
        int filled = max > 0 ? (int) ((current / (double) max) * length) : 0;
        filled = Math.max(0, Math.min(length, filled));
        return UIConstants.BLUE + "[" + "█".repeat(filled) + "░".repeat(length - filled) + "] " + current + "/" + max + UIConstants.RESET;
    }
    
    // ==================== BATTLE DISPLAY ====================
    
    public void displayBattleStart(String enemyName) {
        printHeader("BATTLE");
        System.out.println(UIConstants.RED + "⚔ A " + enemyName + " appears!" + UIConstants.RESET);
    }
    
    public void displayBattleStatus(String playerName, int playerHp, int playerMaxHp, 
                                    String enemyName, int enemyHp, int enemyMaxHp) {
        System.out.println("\n" + UIConstants.SEPARATOR);
        System.out.printf("%s: %s %d/%d\n", 
            playerName, 
            getHealthBar(playerHp, playerMaxHp, 15), 
            playerHp, 
            playerMaxHp);
        System.out.printf("%s: %s %d/%d\n", 
            enemyName, 
            getHealthBar(enemyHp, enemyMaxHp, 15), 
            enemyHp, 
            enemyMaxHp);
        System.out.println(UIConstants.SEPARATOR);
    }
    
    public void displayCombatLog(List<String> recentActions) {
        System.out.println("\n" + UIConstants.CYAN + "Recent Events:" + UIConstants.RESET);
        for (String action : recentActions) {
            System.out.println("  > " + action);
        }
        printDivider();
    }
    
    // ==================== INVENTORY ====================
    
    public void displayInventory(List<String> items) {
        printHeader("INVENTORY");
        
        if (items.isEmpty()) {
            System.out.println("  Your inventory is empty.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.printf("  [%d] %s\n", i + 1, items.get(i));
            }
        }
        printDivider();
    }
    
    public void displayShop(List<String> items, List<Integer> prices) {
        printHeader("SHOP");
        
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  [%d] %-30s %s%dg%s\n", 
                i + 1, 
                items.get(i), 
                UIConstants.YELLOW,
                prices.get(i),
                UIConstants.RESET);
        }
        printDivider();
    }
    
    // ==================== MENUS ====================
    
    public int displayMenu(String title, String[] options) {
        printHeader(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("  [%d] %s\n", i + 1, options[i]);
        }
        printDivider();
        return getIntInput("Choose an option: ", 1, options.length);
    }
    
    public int displayBattleMenu() {
        String[] options = {"Attack", "Use Item", "Defend", "Run Away"};
        return displayMenu("BATTLE ACTIONS", options);
    }
    
    public int displayMainMenu() {
        String[] options = {"Start Adventure", "View Character", "Settings", "Quit"};
        return displayMenu("MAIN MENU", options);
    }
    
    // ==================== INPUT METHODS ====================
    
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                displayError("Please enter a valid number.");
            }
        }
    }
    
    public int getIntInput(String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            displayError("Please enter a number between " + min + " and " + max + ".");
        }
    }
    
    public boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            displayError("Please enter 'y' or 'n'.");
        }
    }
    
    // ==================== UTILITY METHODS ====================
    
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }
    
    public void displayAsciiArt(String[] art) {
        for (String line : art) {
            System.out.println(line);
        }
    }
    
    public void displayGameTitle() {
        String[] title = {
            "",
            "   ███████╗██╗  ██╗ █████╗ ██████╗  ██████╗ ██╗    ██╗███████╗",
            "   ██╔════╝██║  ██║██╔══██╗██╔══██╗██╔═══██╗██║    ██║██╔════╝",
            "   ███████╗███████║███████║██║  ██║██║   ██║██║ █╗ ██║███████╗",
            "   ╚════██║██╔══██║██╔══██║██║  ██║██║   ██║██║███╗██║╚════██║",
            "   ███████║██║  ██║██║  ██║██████╔╝╚██████╔╝╚███╔███╔╝███████║",
            "   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝",
            "",
            "              ██████╗ ███████╗    ████████╗██╗  ██╗███████╗",
            "             ██╔═══██╗██╔════╝    ╚══██╔══╝██║  ██║██╔════╝",
            "             ██║   ██║█████╗         ██║   ███████║█████╗  ",
            "             ██║   ██║██╔══╝         ██║   ██╔══██║██╔══╝  ",
            "             ╚██████╔╝██║            ██║   ██║  ██║███████╗",
            "              ╚═════╝ ╚═╝            ╚═╝   ╚═╝  ╚═╝╚══════╝",
            "",
            "                      ██████╗██████╗  ██████╗ ██╗    ██╗███╗   ██╗",
            "                     ██╔════╝██╔══██╗██╔═══██╗██║    ██║████╗  ██║",
            "                     ██║     ██████╔╝██║   ██║██║ █╗ ██║██╔██╗ ██║",
            "                     ██║     ██╔══██╗██║   ██║██║███╗██║██║╚██╗██║",
            "                     ╚██████╗██║  ██║╚██████╔╝╚███╔███╔╝██║ ╚████║",
            "                      ╚═════╝╚═╝  ╚═╝ ╚═════╝  ╚══╝╚══╝ ╚═╝  ╚═══╝",
            ""
        };
        displayAsciiArt(title);
    }
    
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}