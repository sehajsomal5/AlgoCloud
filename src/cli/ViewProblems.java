package cli;

import storage.ProblemStorage;
import model.Problem;
import java.util.List;

public class ViewProblems {
    public static void display() {
        System.out.println("\n📋 SYSTEM PROBLEMS:");
        List<core.Problem> systemProblems = util.ProblemLoader.loadProblems("data/problems.json");
        if (systemProblems.isEmpty()) {
            System.out.println("❌ No system problems found.");
        } else {
            for (int i = 0; i < systemProblems.size(); i++) {
                System.out.println((i + 1) + ". " + systemProblems.get(i).getTitle());
            }
        }

        System.out.println("\n📜 CUSTOM PROBLEMS:");
        List<model.Problem> customProblems = ProblemStorage.loadCustomProblems();
        if (customProblems.isEmpty()) {
            System.out.println("   (No custom problems yet)");
        } else {
            for (int i = 0; i < customProblems.size(); i++) {
                System.out.println((i + 1) + ". " + customProblems.get(i).getName() + " ("
                        + customProblems.get(i).getDescription() + ")");
            }
        }
    }
}
