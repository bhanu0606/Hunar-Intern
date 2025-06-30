import java.util.Scanner;
public class TicTacToeGame {
    static char[][] board = new char[3][3];
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Introductory message and rules
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Rules:");
        System.out.println("- The game is played on a 3x3 grid.");
        System.out.println("- Player 1 uses X and Player 2 uses O.");
        System.out.println("- Players take turns placing their mark in an empty cell.");
        System.out.println("- The first to get three marks in a row (horizontally, vertically, or diagonally) wins.");
        System.out.println("- If all cells fill with no winner, it is a tie.");
        boolean playAgain;
        do {
            initializeBoard();
            playGame();
            playAgain = askForRestart();
        } while (playAgain);
        System.out.println("Thanks for playing! Bye Bye.");
        scanner.close();
    }
    static void initializeBoard() {     // Initialize game board
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' '; // fill empty
    }
    static void playGame() {     // Main gameplay loop
        char currentPlayer = 'X';
        boolean gameEnded = false;
        while (!gameEnded) {
            displayBoard();
            System.out.println("Player " + currentPlayer + "'s turn");
            int row, col;    //Player input
            while (true) {
                System.out.print("Enter row (0-2): ");
                row = scanner.nextInt();
                System.out.print("Enter column (0-2): ");
                col = scanner.nextInt();
                if (isValidMove(row, col)) {
                    board[row][col] = currentPlayer;
                    break;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            }
            if (checkWinner(currentPlayer)) {  //Check winning conditions1
                displayBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                gameEnded = true;
            } else if (isBoardFull()) {
                displayBoard();
                System.out.println("It's a tie!");
                gameEnded = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';  //Switch turn
            }
        }
    }
    static void displayBoard() {        //Display the board
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2)
                    System.out.print("|");
            }
            System.out.println();
            if (i < 2)
                System.out.println("  -+-+-");
        }
    }
    static boolean isValidMove(int row, int col) {
        return (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ');
    }
    static boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++)    // check rows
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
        for (int i = 0; i < 3; i++)    // check columns
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)  // check diagonals
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }
    static boolean isBoardFull() {    // Check if the board is full
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return false;
        return true;
    }
    static boolean askForRestart() {
        System.out.print("Do you want to play again? (yes/no): ");
        scanner.nextLine();
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }
}
