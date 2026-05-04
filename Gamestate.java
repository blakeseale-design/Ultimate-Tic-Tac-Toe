import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Gamestate {
    private enum GameStatus {
        MAIN_MENU,
        IN_GAME,
        GAME_OVER
    }

    private enum Difficulty {
        EASY,
        HARD
    }

    private GameStatus currentState = GameStatus.MAIN_MENU;
    private Difficulty currentDifficulty = Difficulty.EASY;
    private int[] lastMoveIndex;
    private Subgame[][] board;
    private int theme = 0;
    int currentPlayer;
    public Gamestate() {
        setTheme();
        board = new Subgame[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = new Subgame();
            }
        }
        currentPlayer = 1; // Player 1 starts
        this.lastMoveIndex = new int[2];
        this.lastMoveIndex[0]=-1;
        this.lastMoveIndex[1]=-1;
    }
    private int prompt(Scanner scanner) {
        System.out.println("Current board:");
        printBoard();
        int x;
        int y;
        
        if(currentDifficulty == Difficulty.EASY || (lastMoveIndex[0]==-1&&lastMoveIndex[1]==-1)) {
            x = -1; // Changed from 3 to enter the while loop
            while(x < 1 || x > 3) {
                x = Utilities.promptInt(scanner, "Which Board (row 1-3): ");
                if(x==-10) {
                    resetBoard();
                    currentState = GameStatus.MAIN_MENU;
                    currentPlayer = 1;
                    return 1;
                } else if(x < 1 || x > 3) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Invalid input.");
                    Utilities.ResetColors();
                }
            }

            y = -1;
            while(y < 1 || y > 3) {
                y = Utilities.promptInt(scanner, "Which Board (col 1-3): ");
                if(y==-10) {
                    resetBoard();
                    currentState = GameStatus.MAIN_MENU;
                    currentPlayer = 1;
                    return 1;
                } else if(y < 1 || y > 3) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Invalid input.");
                    Utilities.ResetColors();
                }
            }
        } else {
            x = lastMoveIndex[0];
            y = lastMoveIndex[1];
            System.out.println("Acting on Board " + x + ", " + y + ".");
        }
        
        board[x-1][y-1].prompt(scanner, currentPlayer);
        lastMoveIndex = board[x-1][y-1].getLastMoveIndex();
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
        Utilities.clearScreen();
        return 0;
    }
    private int checkWinner() {
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
    private void printBoard() {
        System.out.println("       1       2       3\n");
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(j==0) {
                    if((i-1)%3==0) {
                        System.out.print(" " + String.valueOf((i-1)/3+1) + "   ");
                    } else {
                        System.out.print("     ");
                    }
                }
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
                System.out.println("\n     ----------------------");
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
                    switch(theme) {
                        case 1:
                            for(int i = 0; i < 19; i++) {
                                Utilities.SetColors((i%2==0)?Utilities.Colors.RED:Utilities.Colors.GREEN);
                                System.out.print("-~=~-");
                                Utilities.ResetColors();
                            }
                            System.out.println();
                            break;
                        case 2:
                            for(int i = 0; i < 19; i++) {
                                Utilities.SetColors((i%2==0)?Utilities.Colors.MAGENTA:Utilities.Colors.CYAN);
                                System.out.print("-~=~-");
                                Utilities.ResetColors();
                            }
                            System.out.println();
                            break;
                        case 3: 
                            for(int i = 0; i < 19; i++) {
                                Utilities.SetColors((i%2==0)?Utilities.Colors.BLUE:Utilities.Colors.YELLOW);
                                System.out.print("-~=~-");
                                Utilities.ResetColors();
                            }
                            System.out.println();
                            break;
                    }
                    System.out.println("Welcome to Ultimate Tic Tac Toe!\n1. Easy Mode\n2. Hard Mode\n3. Instructions\n4. Change Theme\n5. Credits\n6. Exit");
                    int input = 0;
                    while(input < 1 || input > 6) {
                        input = Utilities.promptInt(scanner, "Please select an option (1-6): ");
                        if(input==-10) {
                            Utilities.SetColors(Utilities.Colors.RED);
                            currentState = GameStatus.MAIN_MENU;
                            Utilities.ResetColors();
                        } else if(input < 1 || input > 6) {
                            Utilities.SetColors(Utilities.Colors.RED);
                            System.out.println("Invalid input. Please enter a number between 1 and 6.");
                            Utilities.ResetColors();
                        }
                    }
                    switch(input) {
                        case 1:
                            currentState = GameStatus.IN_GAME;
                            currentDifficulty = Difficulty.EASY;
                            break;
                        case 2:
                            currentState = GameStatus.IN_GAME;
                            currentDifficulty = Difficulty.HARD;
                            break;
                        case 3:
                            System.out.println("**Instructions**\nPress Enter Key To Return to Main Menu");
                            scanner.next();
                            Utilities.clearScreen();
                            break;
                        case 4:
                            int choice = -1;
                            boolean hitBack = false;
                            while(true) {
                                choice = Utilities.promptInt(scanner, "Choose Theme:\n1. Christmas\n2. Cyber\n3. Seaside");
                                if(choice == -10) {
                                    break;
                                } else if(choice < 1 && choice > 3) {
                                    Utilities.SetColors(Utilities.Colors.RED);
                                    System.out.println("Invalid Choice. Please enter a number between 1 and 3.");
                                    Utilities.ResetColors();
                                } else {
                                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("settings.csv"))) {
                                        bw.write(String.valueOf(choice));
                                        theme = choice;
                                    } catch (IOException e) {
                                        System.out.println("Error writing file.");
                                    }
                                    break;
                                }
                            }
                            Utilities.clearScreen();
                            break;
                        case 5:
                            System.out.println("Credits:\nDevelopers: Blake Seale, Joshua Denson\n         .-++++++++++++++=:.    .-++++++++++++++=:.       \n        ..-*###########+-..    .:=*###########+:..      \n         .:+###########=.       .:*###########-.        \n          :+###########*-.      .=*###########-.          \n..........:+############=:......-*############-........... \n:=*********##############********#############*********+-..\n=*######################################################+:.\n=*#######+=+######+=*####*=-::-+#####+=+######==*#######+:.\n=*#######+=+######=-=#####*-..=#####*--+######--++++++++=:.\n=*#######+=+######=:-*#####+:-*#####=.-+######-........... \n=*#######+=+######=..-#####*=+#####+-.-+######-.           \n=*#######+=+######=. :=#####+#####*=..-+######-.       ... \n=*#######+=+######=. .-*##########+:..-+######-.....:--==:.\n=*#######+=+######=.  .=#########*-. .-+######--++*#####+:.\n=*#######+=+######=:...-+#######*=:...-+######==*#######+:.\n=*########*#######*******########******#######*#########+:.\n-+#####################################################*=..\n..::::::::-+######+-:::::-*####=-:::::-+######-::::::::... \n          :+######=.     .=*##*-.    .-+######-.           \n         .:+######=..    .:+##-.     .-+######-.           \n        ..-*######*-..    .-*+:     .:=*######+:..         \n        .-+++++++++=-.     :=-.     .=+++++++++=:.         \n");
                            System.out.println("Press Any Key To Return to Main Menu:");
                            scanner.next();
                            Utilities.clearScreen();
                            break;
                        case 6:
                            System.out.println("Exiting the game. Goodbye!");
                            System.exit(0);
                    }
                    break;
                case GameStatus.IN_GAME:
                    while(true) {
                        if(prompt(scanner)==1) {
                            Utilities.clearScreen();
                            break;
                        }
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
    private void resetBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                this.board[i][j].resetBoard();
            }
        }
    }
    private void setTheme() {
        String path = "settings.csv";
        String line = "";
        ArrayList<String> values = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] list = line.split(",");
                for(int i = 0; i < list.length; i++) {
                    values.add(list[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            theme = Integer.parseInt(values.get(0));
        } catch (NumberFormatException e) {
            Utilities.SetColors(Utilities.Colors.RED);
            System.out.println("What??");
            Utilities.ResetColors();
        }
    }
}