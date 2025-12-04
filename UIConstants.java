public class UIConstants {
    
    // ==================== ANSI COLOR CODES ====================
    
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    // Bright colors
    public static final String BRIGHT_BLACK = "\u001B[90m";
    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String BRIGHT_PURPLE = "\u001B[95m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    
    // Background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    // Text styles
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String REVERSED = "\u001B[7m";
    
    // ==================== DIVIDERS & SEPARATORS ====================
    
    public static final String DIVIDER = "=".repeat(50);
    public static final String SEPARATOR = "-".repeat(50);
    public static final String THIN_DIVIDER = "·".repeat(50);
    
    // ==================== BOX DRAWING CHARACTERS ====================
    
    // Single line box
    public static final String BOX_TOP_LEFT = "┌";
    public static final String BOX_TOP_RIGHT = "┐";
    public static final String BOX_BOTTOM_LEFT = "└";
    public static final String BOX_BOTTOM_RIGHT = "┘";
    public static final String BOX_HORIZONTAL = "─";
    public static final String BOX_VERTICAL = "│";
    public static final String BOX_VERTICAL_RIGHT = "├";
    public static final String BOX_VERTICAL_LEFT = "┤";
    public static final String BOX_HORIZONTAL_DOWN = "┬";
    public static final String BOX_HORIZONTAL_UP = "┴";
    public static final String BOX_CROSS = "┼";
    
    // Double line box
    public static final String DBOX_TOP_LEFT = "╔";
    public static final String DBOX_TOP_RIGHT = "╗";
    public static final String DBOX_BOTTOM_LEFT = "╚";
    public static final String DBOX_BOTTOM_RIGHT = "╝";
    public static final String DBOX_HORIZONTAL = "═";
    public static final String DBOX_VERTICAL = "║";
    public static final String DBOX_VERTICAL_RIGHT = "╠";
    public static final String DBOX_VERTICAL_LEFT = "╣";
    public static final String DBOX_HORIZONTAL_DOWN = "╦";
    public static final String DBOX_HORIZONTAL_UP = "╩";
    public static final String DBOX_CROSS = "╬";
    
    // ==================== SYMBOLS & ICONS ====================
    
    public static final String SWORD = "⚔";
    public static final String SHIELD = "🛡";
    public static final String HEART = "❤";
    public static final String STAR = "★";
    public static final String COIN = "◎";
    public static final String SKULL = "☠";
    public static final String CHECKMARK = "✓";
    public static final String CROSS = "✗";
    public static final String ARROW_RIGHT = "→";
    public static final String ARROW_LEFT = "←";
    public static final String ARROW_UP = "↑";
    public static final String ARROW_DOWN = "↓";
    public static final String WARNING = "⚠";
    public static final String INFO = "ℹ";
    
    // Progress bar characters
    public static final String FILLED_BLOCK = "█";
    public static final String EMPTY_BLOCK = "░";
    public static final String LIGHT_SHADE = "░";
    public static final String MEDIUM_SHADE = "▒";
    public static final String DARK_SHADE = "▓";
    
    // ==================== HELPER METHODS ====================
    
    public static String colorize(String text, String color) {
        return color + text + RESET;
    }
    
    public static String bold(String text) {
        return BOLD + text + RESET;
    }
    
    public static String underline(String text) {
        return UNDERLINE + text + RESET;
    }
    
    public static String createBox(String content, int width) {
        int contentLength = content.length();
        int padding = (width - contentLength - 2) / 2;
        int rightPadding = width - contentLength - 2 - padding;
        
        StringBuilder box = new StringBuilder();
        box.append(BOX_TOP_LEFT).append(BOX_HORIZONTAL.repeat(width - 2)).append(BOX_TOP_RIGHT).append("\n");
        box.append(BOX_VERTICAL).append(" ".repeat(padding)).append(content).append(" ".repeat(rightPadding)).append(BOX_VERTICAL).append("\n");
        box.append(BOX_BOTTOM_LEFT).append(BOX_HORIZONTAL.repeat(width - 2)).append(BOX_BOTTOM_RIGHT);
        
        return box.toString();
    }
    
    public static String createDoubleBox(String content, int width) {
        int contentLength = content.length();
        int padding = (width - contentLength - 2) / 2;
        int rightPadding = width - contentLength - 2 - padding;
        
        StringBuilder box = new StringBuilder();
        box.append(DBOX_TOP_LEFT).append(DBOX_HORIZONTAL.repeat(width - 2)).append(DBOX_TOP_RIGHT).append("\n");
        box.append(DBOX_VERTICAL).append(" ".repeat(padding)).append(content).append(" ".repeat(rightPadding)).append(DBOX_VERTICAL).append("\n");
        box.append(DBOX_BOTTOM_LEFT).append(DBOX_HORIZONTAL.repeat(width - 2)).append(DBOX_BOTTOM_RIGHT);
        
        return box.toString();
    }
}