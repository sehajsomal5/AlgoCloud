package model;

public class Problem {
    private String name;
    private String topic;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String source; // system/custom

    public Problem(String name, String topic, String description, String inputFormat, String outputFormat, String source) {
        this.name = name;
        this.topic = topic;
        this.description = description;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
        this.source = source;
    }

    // Getters
    public String getName() { return name; }
    public String getTopic() { return topic; }
    public String getDescription() { return description; }
    public String getInputFormat() { return inputFormat; }
    public String getOutputFormat() { return outputFormat; }
    public String getSource() { return source; }

    @Override
    public String toString() {
        return "Problem: " + name + "\n" +
               "Topic: " + topic + "\n" +
               "Description: " + description + "\n" +
               "Input Format: " + inputFormat + "\n" +
               "Output Format: " + outputFormat + "\n" +
               "Source: " + source;
    }
}
