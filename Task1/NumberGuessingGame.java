import java.util.Random;
import java.util.Scanner;
public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.println("==================================");
        System.out.println("🎲 Welcome to the Number Guessing Game! 🎲");
        System.out.println("==================================");
        System.out.println("Rules:");
        System.out.println("1. Choose a difficulty level.");
        System.out.println("2. Guess the number within the range.");
        System.out.println("3. You'll receive hints after each guess.");
        System.out.println("4. Try to guess in as few attempts as possible!");
        System.out.println();
        int maxNumber = 100;
        System.out.println("Select Difficulty:");
        System.out.println("1. Easy (1 - 50)");
        System.out.println("2. Medium (1 - 100)");
        System.out.println("3. Hard (1 - 200)");
        System.out.print("Enter your choice (1/2/3): ");
        int difficulty = scanner.nextInt();
        switch (difficulty) {
            case 1:
                maxNumber = 50;
                break;
            case 2:
                maxNumber = 100;
                break;
            case 3:
                maxNumber = 200;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Medium (1-100).");
        }
        int targetNumber = random.nextInt(maxNumber) + 1;
        int guess;
        int attempts = 0;
        System.out.println("\nI'm thinking of a number between 1 and " + maxNumber + ".");
        System.out.println("Try to guess it!");
        do {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            attempts++;
            if (guess < 1 || guess > maxNumber) {
                System.out.println("❌ Guess out of range! Please enter a number between 1 and " + maxNumber + ".");
                continue;
            }
            if (guess < targetNumber) {
                System.out.println("📉 Too low! Try again.");
            } else if (guess > targetNumber) {
                System.out.println("📈 Too high! Try again.");
            } else {
                System.out.println("\n🎉 Congratulations! You guessed the number " + targetNumber + " correctly!");
                System.out.println("🎯 Attempts taken: " + attempts);
                int score = (maxNumber / attempts) * 10;
                System.out.println("🏆 Your Score: " + score);
            }
        } while (guess != targetNumber);
        System.out.println("\nThanks for playing!");
        System.out.println("==================================");
        scanner.close();
    }
}