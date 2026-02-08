package cli.commands;

import java.util.Scanner;

public class ExitCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        System.out.println("Exiting...");
        System.exit(0);
    }
}
