import java.util.Scanner;

public class Gamestate {
    private enum GameStatus {
        MAIN_MENU,
        IN_GAME,
        GAME_OVER
    }

    private GameStatus currentState = GameStatus.MAIN_MENU;
    private Subgame[][] board;
    int currentPlayer;
    public Gamestate() {
        board = new Subgame[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Subgame();
            }
        }
        currentPlayer = 1; // Player 1 starts
    }
    public void prompt(Scanner scanner) {
        System.out.println("Current board:");
        printBoard();
        
        int x = -1; // Changed from 3 to enter the while loop
        while(x < 0 || x > 2) {
             x = Utilities.promptInt(scanner, "Which Board (row 0-2): ");
             if(x < 0 || x > 2) {
                Utilities.SetColors(Utilities.Colors.RED);
                System.out.println("Invalid input.");
                Utilities.ResetColors();
             }
        }

        int y = -1;
        while(y < 0 || y > 2) {
            y = Utilities.promptInt(scanner, "Which Board (col 0-2): ");
            if(y < 0 || y > 2) {
                Utilities.SetColors(Utilities.Colors.RED);
                System.out.println("Invalid input.");
                Utilities.ResetColors();
            }
        }
        
        board[x][y].prompt(scanner, currentPlayer);
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        Utilities.clearScreen();
    }

    public int checkWinner() {
        int[][] winnerBoard = new int[3][3];
        // Check if any subgame has a winner and update the main board accordingly
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int winner = board[i][j].checkWinner();
                if (winner != 0) {
                    winnerBoard[i][j] = winner; 
                }
            }
        }
        int winner = 0;
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (winnerBoard[i][0] != 0 && winnerBoard[i][0] == winnerBoard[i][1] && winnerBoard[i][1] == winnerBoard[i][2]) {
                winner = winnerBoard[i][0]; // Row winner
                return winner;
            }
            if (winnerBoard[0][i] != 0 && winnerBoard[0][i] == winnerBoard[1][i] && winnerBoard[1][i] == winnerBoard[2][i]) {
                winner = winnerBoard[0][i]; // Column winner
                return winner;
            }
        }
        // Check diagonals
        if (winnerBoard[0][0] != 0 && winnerBoard[0][0] == winnerBoard[1][1] && winnerBoard[1][1] == winnerBoard[2][2]) {
            winner = winnerBoard[0][0]; // Main diagonal winner
            return winner;
        }
        if (winnerBoard[0][2] != 0 && winnerBoard[0][2] == winnerBoard[1][1] && winnerBoard[1][1] == winnerBoard[2][0]) {
            winner = winnerBoard[0][2]; // Anti-diagonal winner
            return winner;
        }
        return 0;
    }

    public void printBoard() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                int subgameRow = i / 3;
                int subgameCol = j / 3;
                int cellRow = i % 3;
                int cellCol = j % 3;
                int currentWinner = board[subgameRow][subgameCol].checkWinner();
                if(currentWinner == 1) {
                    Utilities.SetColors(Utilities.Colors.GREEN);
                } else if(currentWinner == 2) {
                    Utilities.SetColors(Utilities.Colors.RED);
                } else {
                    Utilities.ResetColors();
                }
                int symbol = board[subgameRow][subgameCol].getSymbol(cellRow, cellCol);
                System.out.print((symbol == 1) ? "X" : (symbol == 2) ? "O" : "-");
                Utilities.ResetColors();
                if((j+1) % 3 == 0 && j != 0 && j < 8) {
                    System.out.print(" | ");
                } else {
                    System.out.print(" ");
                }
            }
            if((i+1) % 3 == 0 && i != 0 && i < 8) {
                System.out.println("\n----------------------");
            } else {
                System.out.println();
            }
        }
        Utilities.ResetColors();
    }

    public void run(Scanner scanner) {
        while(true) {
            int winner = 0;
            switch(currentState) {
                case GameStatus.MAIN_MENU:
                    System.out.println("Welcome to Ultimate Tic Tac Toe!\n1. Easy Mode\n2. Hard Mode\n3. Instructions\n4. Change Theme\n5. Credits\n6. Exit");
                    int input = 0;
                    while(input < 1 || input > 6) {
                        input = Utilities.promptInt(scanner, "Please select an option (1-6): ");
                        if(input < 1 || input > 6) {
                            Utilities.SetColors(Utilities.Colors.RED);
                            System.out.println("Invalid input. Please enter a number between 1 and 6.");
                            Utilities.ResetColors();
                        }
                    }
                    switch(input) {
                        case 1:
                            currentState = GameStatus.IN_GAME;
                            break;
                        case 2:
                            currentState = GameStatus.IN_GAME;
                            break;
                        case 3:
                            System.out.println("**Instructions**");
                            break;
                        case 4:
                            // Change Theme
                            break;
                        case 5:
                            System.out.println("Credits:\nDevelopers: Blake Seale, Joshua Denson\n         .-++++++++++++++=:.    .-++++++++++++++=:.       \n        ..-*###########+-..    .:=*###########+:..      \n         .:+###########=.       .:*###########-.        \n          :+###########*-.      .=*###########-.          \n..........:+############=:......-*############-........... \n:=*********##############********#############*********+-..\n=*######################################################+:.\n=*#######+=+######+=*####*=-::-+#####+=+######==*#######+:.\n=*#######+=+######=-=#####*-..=#####*--+######--++++++++=:.\n=*#######+=+######=:-*#####+:-*#####=.-+######-........... \n=*#######+=+######=..-#####*=+#####+-.-+######-.           \n=*#######+=+######=. :=#####+#####*=..-+######-.       ... \n=*#######+=+######=. .-*##########+:..-+######-.....:--==:.\n=*#######+=+######=.  .=#########*-. .-+######--++*#####+:.\n=*#######+=+######=:...-+#######*=:...-+######==*#######+:.\n=*########*#######*******########******#######*#########+:.\n-+#####################################################*=..\n..::::::::-+######+-:::::-*####=-:::::-+######-::::::::... \n          :+######=.     .=*##*-.    .-+######-.           \n         .:+######=..    .:+##-.     .-+######-.           \n        ..-*######*-..    .-*+:     .:=*######+:..         \n        .-+++++++++=-.     :=-.     .=+++++++++=:.         \n");
                            break;
                        case 6:
                            System.out.println("Exiting the game. Goodbye!");
                            System.exit(0);
                    }
                    break;
                case GameStatus.IN_GAME:
                    while(true) {
                        prompt(scanner);
                        winner = checkWinner();
                        if(winner != 0) {
                            currentState = GameStatus.GAME_OVER;
                            break;
                        }
                    }
                    break;
                case GameStatus.GAME_OVER:
                    Utilities.SetColors((winner == 1) ? Utilities.Colors.GREEN : Utilities.Colors.RED);
                    System.out.println("Player " + winner + " wins!");
                    Utilities.ResetColors();
                    System.out.println("Press Enter to return to the main menu...");
                    scanner.nextLine(); // Wait for user to press Enter
                    currentState = GameStatus.MAIN_MENU;
                    break;
            }
        }
    }
}