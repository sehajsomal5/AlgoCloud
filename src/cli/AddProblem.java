package cli;

import storage.ProblemStorage;
import util.ScannerUtil;

import java.util.Scanner;

public class AddProblem {
    public static void start() {
        Scanner scanner = ScannerUtil.getScanner();
        System.out.println("🔧 Add a New Problem");

        System.out.print("Enter Problem Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Topic (e.g., Sorting, Searching): ");
        String topic = scanner.nextLine();

        System.out.print("Enter Problem Statement: ");
        String description = scanner.nextLine();

        System.out.print("Enter Expected Input Format: ");
        String inputFormat = scanner.nextLine();

        System.out.print("Enter Expected Output Format: ");
        String outputFormat = scanner.nextLine();

        // Save to problems.json
        boolean success = ProblemStorage.saveCustomProblem(name, topic, description, inputFormat, outputFormat);

        if (success) {
            System.out.println("✅ Problem added successfully!");
        } else {
            System.out.println("❌ Failed to add problem.");
        }
    }
}
