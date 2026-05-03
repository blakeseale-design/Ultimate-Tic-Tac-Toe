import java.io.*;
import java.util.*;
public class Main {
  public static void main(String[] argv) {
    Utilities.clearScreen();
    Gamestate gameState = new Gamestate();
    Scanner scanner = new Scanner(System.in);
    gameState.run(scanner);
  }
}