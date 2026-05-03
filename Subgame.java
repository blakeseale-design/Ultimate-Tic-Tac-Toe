import java.util.Scanner;

class Subgame {
        private int[][] board;
        private int winner=0;
        private int[] lastMoveIndex;
    
        public Subgame() {
            this.board = new int[3][3];
            
            this.lastMoveIndex = new int[2];
            this.lastMoveIndex[0]=-1;
            this.lastMoveIndex[1]=-1;
        }

        public void resetBoard() {
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    this.board[i][j]=0;
                }
            }
        }
    
        public boolean makeMove(int row, int col, int player) {
            if (board[row][col] == 0) {
                board[row][col] = player;
                lastMoveIndex[0]=row;
                lastMoveIndex[1]=col;
                return true;
            }
            return false;
        }
        public int[] getLastMoveIndex() {
            return lastMoveIndex;
        }
    
        public int checkWinner() {
            if(winner != 0) {
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
            System.out.println("Current board:");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int symbol = getSymbol(i, j);
                    System.out.print((symbol == 1) ? "X" : (symbol == 2) ? "O" : "-");
                    System.out.print(" ");
                }
                System.out.println();
            }
            int x = 3;
            while(x < 0 || x > 2) {
                System.out.println("Which Row Do You Want to Play On? (row: 0-2): ");
                x = Utilities.promptInt(scanner, "Enter the row (0-2): ");
                if(x == -10) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Back is not available right now");
                    Utilities.ResetColors();
                } if(x < 0 || x > 2) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Invalid input. Please enter a number between 0 and 2.");
                    Utilities.ResetColors();
                }
            }
            int y = 3;
            while(y < 0 || y > 2) {
                System.out.println("Column (0-2): ");
                y = Utilities.promptInt(scanner, "Enter the column (0-2): ");
                if(y == -10) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Back is not available right now");
                    Utilities.ResetColors();
                }
                if(y < 0 || y > 2) {
                    Utilities.SetColors(Utilities.Colors.RED);
                    System.out.println("Invalid input. Please enter a number between 0 and 2.");
                    Utilities.ResetColors();
                }
            }
            makeMove(x, y, currentPlayer);
        }
}