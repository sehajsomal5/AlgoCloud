package util;

import core.User;

import java.io.*;

public class FileHandler {

    private static final String USERS_DIR = "data/users/";
    private static final String CURRENT_USER_FILE = "data/current_user.txt";

    static {
        new File(USERS_DIR).mkdirs();
    }

    public static void saveUser(User user) {
        // Save user specific data
        try (PrintWriter writer = new PrintWriter(USERS_DIR + user.getName() + ".txt")) {
            writer.println(user.getName());
            writer.println(user.getTotalProblemsSolved());
            writer.println(user.getTotalTimeSpent());
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }

        // Update current user pointer
        try (PrintWriter writer = new PrintWriter(CURRENT_USER_FILE)) {
            writer.println(user.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User loadUser() {
        String currentUserName = "Guest";

        // Try to read current user name
        try (BufferedReader reader = new BufferedReader(new FileReader(CURRENT_USER_FILE))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                currentUserName = line.trim();
            }
        } catch (IOException e) {
            // Ignore, default to Guest
        }

        return loadUserByName(currentUserName);
    }

    public static User loadUserByName(String name) {
        File userFile = new File(USERS_DIR + name + ".txt");
        if (!userFile.exists()) {
            return new User(name);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String loadedName = reader.readLine();
            int solved = Integer.parseInt(reader.readLine());
            long time = Long.parseLong(reader.readLine());

            User user = new User(loadedName);
            for (int i = 0; i < solved; i++)
                user.addSolvedProblem(0);
            // We need to set time manually or add a constructor/method for it
            // For now, let's just hack it by adding a method to User or finding a way
            // Since User.totalTimeSpent is private and has no setter, we might need to
            // update User class too.
            // But wait, addSolvedProblem adds time. We can just add one "dummy" problem
            // with total time?
            // Or better: Update User class to load data.
            return new User(loadedName, solved, time);
        } catch (IOException | NumberFormatException e) {
            return new User(name);
        }
    }
}
