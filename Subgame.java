class Subgame {
        private int[][] board;
        private int currentPlayer;
    
        public Subgame() {
            board = new int[3][3];
            currentPlayer = 1; // Player 1 starts
        }
    
        public boolean makeMove(int row, int col) {
            if (board[row][col] == 0) {
                board[row][col] = currentPlayer;
                currentPlayer = 3 - currentPlayer; // Switch player
                return true;
            }
            return false; // Invalid move
        }
    
        public int checkWinner() {
            // Check rows and columns
            for (int i = 0; i < 3; i++) {
                if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                    return board[i][0]; // Row winner
                }
                if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                    return board[0][i]; // Column winner
                }
            }
            // Check diagonals
            if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                return board[0][0]; // Main diagonal winner
            }
            if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                return board[0][2]; // Anti-diagonal winner
            }
            return 0; // No winner yet
        }
    
        public int getSymbol(int row, int col) {
            return board[row][col];
        }
}