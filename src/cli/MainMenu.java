package cli;

import util.ScannerUtil;
import cli.commands.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainMenu {
    private final Map<Integer, Command> commands = new HashMap<>();

    public MainMenu() {
        commands.put(1, new PracticeProblemCommand());
        commands.put(2, new VisualizerCommand());
        commands.put(3, new ViewProfileCommand());
        commands.put(4, new ChangeUserCommand());
        commands.put(5, new ExitCommand());
    }

    public void start() {
        Scanner scanner = ScannerUtil.getScanner();
        while (true) {
            System.out.println("\n=== AlgoCloud CLI ===");
            System.out.println("1. Practice Problems");
            System.out.println("2. Algorithms Visualizer");
            System.out.println("3. View Profile");
            System.out.println("4. Login / Change User");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            try {
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute(scanner);
                } else {
                    System.out.println("❌ Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        new MainMenu().start();
    }
}
