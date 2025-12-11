package core;

public class User {
    private String name;
    private int totalProblemsSolved;
    private long totalTimeSpent;

    public User(String name) {
        this.name = name;
        this.totalProblemsSolved = 0;
        this.totalTimeSpent = 0;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getTotalProblemsSolved() { return totalProblemsSolved; }
    public long getTotalTimeSpent() { return totalTimeSpent; }

    public void addSolvedProblem(long timeTaken) {
        totalProblemsSolved++;
        totalTimeSpent += timeTaken;
    }

    @Override
    public String toString() {
        return name + " | Solved: " + totalProblemsSolved + " | Time Spent: " + totalTimeSpent + "ms";
    }
}
