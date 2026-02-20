package web;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import storage.ProblemStorage;
import util.ProblemLoader;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebServer {
    private static final int PORT = 8080;
    private static final Gson gson = new Gson();

    public static void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/api/problems", new ProblemHandler());
            server.createContext("/api/profile", new ProfileHandler());
            server.createContext("/", new StaticFileHandler());
            server.setExecutor(null); // creates a default executor
            System.out.println("🚀 Web Server started on http://localhost:" + PORT);
            server.start();
        } catch (IOException e) {
            System.err.println("❌ Failed to start web server: " + e.getMessage());
        }
    }

    static class ProblemHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<core.Problem> systemProblems = ProblemLoader.loadProblems("data/problems.json");
                List<model.Problem> customProblems = ProblemStorage.loadCustomProblems();

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("systemProblems", systemProblems);
                responseData.put("customProblems", customProblems);

                String response = gson.toJson(responseData);
                sendResponse(exchange, response, "application/json");
            } else {
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
            }
        }
    }

    static class ProfileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                core.User user = util.FileHandler.loadUser();
                String response = gson.toJson(user);
                sendResponse(exchange, response, "application/json");
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }

    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html";
            }

            Path filePath = Paths.get("web" + path);
            if (Files.exists(filePath) && !Files.isDirectory(filePath)) {
                String contentType = getContentType(path);
                byte[] content = Files.readAllBytes(filePath);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, content.length);
                OutputStream os = exchange.getResponseBody();
                os.write(content);
                os.close();
            } else {
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private String getContentType(String path) {
            if (path.endsWith(".html"))
                return "text/html";
            if (path.endsWith(".css"))
                return "text/css";
            if (path.endsWith(".js"))
                return "application/javascript";
            if (path.endsWith(".png"))
                return "image/png";
            if (path.endsWith(".jpg"))
                return "image/jpeg";
            return "text/plain";
        }
    }

    private static void sendResponse(HttpExchange exchange, String response, String contentType) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
