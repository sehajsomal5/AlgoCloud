package cli.commands;

import visualizer.Visualizer;
import java.util.Scanner;

public class VisualizerCommand implements Command {
    @Override
    public void execute(Scanner scanner) {
        Visualizer.start();
    }
}
