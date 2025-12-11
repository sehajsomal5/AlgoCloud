package core;

public class Problem {
    private String title;
    private String description;
    private String explanation;

    // Default constructor for Gson
    public Problem() {}

    public Problem(String title, String description, String explanation) {
        this.title = title;
        this.description = description;
        this.explanation = explanation;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getExplanation() { return explanation; }

    @Override
    public String toString() {
        return "🧠 " + title + "\n\n" + description;
    }
}
