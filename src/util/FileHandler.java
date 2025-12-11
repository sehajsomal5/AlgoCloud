package util;

import core.User;

import java.io.*;

public class FileHandler {

    public static void saveUser(User user) {
        try (PrintWriter writer = new PrintWriter("data/user.txt")) {
            writer.println(user.getName());
            writer.println(user.getTotalProblemsSolved());
            writer.println(user.getTotalTimeSpent());
        } catch (IOException e) {
            System.out.println("Error saving user data.");
        }
    }

    public static User loadUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/user.txt"))) {
            String name = reader.readLine();
            int solved = Integer.parseInt(reader.readLine());
            long time = Long.parseLong(reader.readLine());

            User user = new User(name);
            for (int i = 0; i < solved; i++) user.addSolvedProblem(0);
            return user;
        } catch (IOException e) {
            return new User("Guest");
        }
    }
}
