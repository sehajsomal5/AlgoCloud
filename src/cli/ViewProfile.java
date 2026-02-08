package cli;

import core.User;
import util.FileHandler;

public class ViewProfile {
    public static void display() {
        System.out.println("👤 User Profile");
        User user = FileHandler.loadUser();
        System.out.println(user);
    }
}
