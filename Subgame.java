import java.util.Scanner;

class Subgame {
    private int[][] board;
    private int winner = 0;
    private int[] lastMoveIndex;

    public Subgame() {
        this.board = new int[3][3];
        this.lastMoveIndex = new int[2];
        this.lastMoveIndex[0] = -1;
        this.lastMoveIndex[1] = -1;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = 0;
            }
        }
    }
    
    public boolean makeMove(int row, int col, int player) {
        if (board[row][col] == 0) {
            board[row][col] = player;
            lastMoveIndex[0] = row;
            lastMoveIndex[1] = col;
            return true;
        }
        return false;
    }

    public int[] getLastMoveIndex() {
        return lastMoveIndex;
    }
    
    public int checkWinner() {
        if (winner != 0) {
            return winner;
        }
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0]; // Row winner
                return winner;
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i]; // Column winner
                return winner;
            }
        }
        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0]; // Main diagonal winner
            return winner;
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2]; // Anti-diagonal winner
            return winner;
        }
        return 0; // Return the winner (0 if no winner yet)
    }
    
    public int getSymbol(int row, int col) {
        return board[row][col];
    }

    public void prompt(Scanner scanner, int currentPlayer) {
        /*
        System.out.println("Current board:\n 1 2 3");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int symbol = getSymbol(i, j);
                System.out.print((symbol == 1) ? "X" : (symbol == 2) ? "O" : "-");
                System.out.print(" ");
            }
            System.out.println();
        }
        */
        int x = -1;
        while (x < 1 || x > 3) {
            System.out.println("Which Row Do You Want to Play On? (row 1-3): ");
            x = Utilities.promptInt(scanner, "Enter the row (1-3): ");
            if (x == -10) {
                Utilities.setColors(Utilities.Colors.RED);
                System.out.println("Back is not available right now");
                Utilities.resetColors();
            } else if (x < 1 || x > 3) {
                Utilities.setColors(Utilities.Colors.RED);
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                Utilities.resetColors();
            }
        }
        int y = -1;
        while (y < 1 || y > 3) {
            System.out.println("Column (1-3): ");
            y = Utilities.promptInt(scanner, "Enter the column (1-3): ");
            if (y == -10) {
                Utilities.setColors(Utilities.Colors.RED);
                System.out.println("Back is not available right now");
                Utilities.resetColors();
            } else if (y < 1 || y > 3) {
                Utilities.setColors(Utilities.Colors.RED);
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                Utilities.resetColors();
            }
        }
        makeMove(x - 1, y - 1, currentPlayer);
    }
}