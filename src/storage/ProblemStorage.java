package storage;

import model.Problem;
import utility.IOUtils;

import java.util.*;
import java.io.*;

public class ProblemStorage {

    private static final String PROBLEM_FILE = "data/custom_problems.json";

    public static boolean saveCustomProblem(String name, String topic, String description, String inputFormat, String outputFormat) {
        List<Problem> problems = loadCustomProblems();
        problems.add(new Problem(name, topic, description, inputFormat, outputFormat, "custom"));
        return IOUtils.saveProblems(problems, PROBLEM_FILE);
    }

    public static List<Problem> loadCustomProblems() {
        return IOUtils.loadProblems(PROBLEM_FILE);
    }
}
