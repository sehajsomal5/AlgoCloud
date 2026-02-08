package cli.commands;

import cli.ViewProblems;
import java.util.Scanner;

public class ViewProblemsCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        ViewProblems.display();
    }
}
