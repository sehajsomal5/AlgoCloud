package cli.commands;

import cli.ViewProfile;
import java.util.Scanner;

public class ViewProfileCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        ViewProfile.display();
    }
}
