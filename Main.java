import java.io.*;
import java.util.*;
public class Main {
  public static void main(String[] argv) {
    Utilities.clearScreen();
    Utilities.SetColors("green");
    System.out.println("Welcome to Ultimate Tic Tac Toe!");
    Utilities.ResetColors();
    Gamestate gameState = new Gamestate();
    Scanner scanner = new Scanner(System.in);
    gameState.run(scanner);
  }
}