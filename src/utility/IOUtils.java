package utility;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import model.Problem;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class IOUtils {
    private static final Gson gson = new Gson();

    public static boolean saveProblems(List<Problem> problems, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(problems, writer);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving problems: " + e.getMessage());
            return false;
        }
    }

    public static List<Problem> loadProblems(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, new TypeToken<List<Problem>>() {}.getType());
        } catch (IOException e) {
            System.out.println("Error loading problems: " + e.getMessage());
            return null;
        }
    }
}
