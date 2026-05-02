import java.util.Scanner;

class Utilities {
    private static Colors currentColor;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public enum Colors {
        RESET("\033[0m"),
        RED("\033[31m"),
        GREEN("\033[32m"),
        YELLOW("\033[33m"),
        BLUE("\033[34m"),
        MAGENTA("\033[35m"),
        CYAN("\033[36m");

        private final String code;

        Colors(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
    public static void SetColors(Colors color) {
        System.out.print(color);
        currentColor = color;
    }
    public static void SetColors(String color) {
        String toUpper = color.toUpperCase();
        try {
            Colors c = Colors.valueOf(toUpper);
            System.out.print(c);
            currentColor = c;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid color. Available colors: RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, RESET.");
        }
    }
    public static void ResetColors() {
        System.out.print(Colors.RESET);
    }
    public static int promptInt(Scanner scanner, String message) {
        System.out.println(message);
        String str = scanner.next();
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            if(str.toLowerCase().equals("q")) {
                System.out.println("Exiting the game. Goodbye!");
                System.exit(0);
            };
            SetColors(Colors.RED);
            System.out.println("Invalid input. Please enter a valid integer.");
            ResetColors();
            return promptInt(scanner, message); // Recursively prompt until valid input is received
        }
        
    }
}