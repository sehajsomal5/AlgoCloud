public class Main {
    public static void main(String[] args) {
        // Start web server in a separate thread
        new Thread(() -> {
            try {
                web.WebServer.start();
            } catch (Exception e) {
                System.err.println("Web server failed: " + e.getMessage());
            }
        }).start();

        // Start CLI
        cli.MainMenu.main(args);
    }
}