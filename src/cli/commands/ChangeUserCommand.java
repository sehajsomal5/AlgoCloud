package cli.commands;

import cli.ChangeUser;
import java.util.Scanner;

public class ChangeUserCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        ChangeUser.start(scanner);
    }
}
