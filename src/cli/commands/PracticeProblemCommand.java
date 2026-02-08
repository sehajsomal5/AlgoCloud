package cli.commands;

import cli.PracticeProblem;
import java.util.Scanner;

public class PracticeProblemCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        PracticeProblem.start(scanner);
    }
}
