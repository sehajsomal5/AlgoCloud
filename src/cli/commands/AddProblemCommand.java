package cli.commands;

import cli.AddProblem;
import java.util.Scanner;

public class AddProblemCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        AddProblem.start();
    }
}
