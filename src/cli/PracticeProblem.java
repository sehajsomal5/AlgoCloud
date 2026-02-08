package cli;

import java.util.Scanner;
import core.User;

public class PracticeProblem {
    public static void start(Scanner scanner) {
        System.out.println("🚀 Starting Practice Mode...");
        core.User user = util.FileHandler.loadUser();
        System.out.println("👤 Loaded User: " + user.getName());
        new PracticeEngine().startPractice(user);
    }
}
