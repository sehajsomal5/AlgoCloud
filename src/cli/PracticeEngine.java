package cli;

import core.Problem;
import core.User;
import util.FileHandler;
import util.ProblemLoader;
import util.Timer;

import java.util.List;
import java.util.Scanner;

public class PracticeEngine {
    private Scanner scanner = new Scanner(System.in);
    public void startPractice(User user) {
        List<Problem> ps = ProblemLoader.loadProblems("data/problems.json");
        if (ps.isEmpty()) { System.out.println("⚠️ No problems."); return; }
        for (int i = 0; i < ps.size(); i++)
            System.out.printf("%d. %s%n", i+1, ps.get(i).getTitle());
        System.out.print("Pick: ");
        int c = scanner.nextInt(); scanner.nextLine();
        if (c<1||c>ps.size()) { System.out.println("❌ Invalid"); return; }
        Problem p = ps.get(c-1);
        System.out.println("\n🧠 " + p.getTitle() + "\n" + p.getDescription());
        System.out.println("\n⏳ ENTER to start timer"); scanner.nextLine();
        Timer t = new Timer();
        t.start();
        System.out.println("✅ Write your thoughts then ENTER"); scanner.nextLine();
        t.stop();
        long tm = t.getElapsedTime();
        System.out.println("\n⏱ Time: " + tm + " ms\n📘 Explanation:\n" + p.getExplanation());
        user.addSolvedProblem(tm);
        FileHandler.saveUser(user);
        System.out.println("✅ Saved");
    }
}
