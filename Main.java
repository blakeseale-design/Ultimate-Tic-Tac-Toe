import java.io.*;
import java.util.*;
public class Main {
  public static void main(String[] argv) {
    Subgame subgame = new Subgame();
    Scanner scanner = new Scanner(System.in);
    while(true) {
        System.out.println("Current board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print((subgame.getSymbol(i, j)==1)? "X" : (subgame.getSymbol(i, j)==2)? "O" : "-");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("Enter your move (row: 0-2): ");
        String x = scanner.next();
        System.out.println("Column (0-2): ");
        String y = scanner.next();
        try {
            int row = Integer.parseInt(x);
            int col = Integer.parseInt(y);
            if (row < 0 || row > 2 || col < 0 || col > 2) {
                System.out.println("Invalid input. Please enter numbers between 0 and 2.");
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid integers.");
            continue;
        }
        subgame.makeMove(Integer.parseInt(x), Integer.parseInt(y));
        int winner = subgame.checkWinner();
        if (winner != 0) {
            System.out.println("Player " + winner + " wins!");
        } else {
            System.out.println("No winner yet.");
        }
    }

    
  }
}