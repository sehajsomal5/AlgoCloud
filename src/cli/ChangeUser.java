package cli;

import util.FileHandler;
import core.User;
import java.util.Scanner;

public class ChangeUser {
    public static void start(Scanner scanner) {
        System.out.print("👤 Enter new username (or existing to login): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("❌ Invalid name.");
            return;
        }

        User user = FileHandler.loadUserByName(name);
        FileHandler.saveUser(user); // This updates the current_user.txt pointer
        System.out.println("✅ Switched to user: " + user.getName());
    }
}
