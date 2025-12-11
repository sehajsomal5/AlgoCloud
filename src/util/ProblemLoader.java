package util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Problem;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ProblemLoader {

    public static List<Problem> loadProblems(String filePath) {
        try {
            Gson gson = new Gson();
            Type problemListType = new TypeToken<List<Problem>>(){}.getType();
            FileReader reader = new FileReader(filePath);
            return gson.fromJson(reader, problemListType);
        } catch (Exception e) {
            System.out.println("⚠️ Failed to load problems: " + e.getMessage());
            return List.of();
        }
    }
}
