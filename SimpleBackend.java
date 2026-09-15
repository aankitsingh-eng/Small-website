import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleBackend {
    public static void main(String[] args) throws IOException {
        // Cloud platform (Render) ke liye dynamic PORT set kiya gaya hai
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        // Server ab sahi port par chalu hoga
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Project endpoints
        server.createContext("/api/projects/ludo", new LudoProjectHandler());
        server.createContext("/api/projects/todo", new TodoListHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Java Backend started on port: " + port);
    }

    // CORS Headers method (Frontend request block na ho uske liye)
    private static void setCORSHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    // Ludo प्रोजेक्ट हैंडलर
    static class LudoProjectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCORSHeaders(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // JSON रिस्पांस
            String response = "{" +
                "\"id\": \"ludo\"," +
                "\"title\": \"Mobile-Responsive Ludo Game\"," +
                "\"description\": \"A fully functional web-based board game built with custom UI and robust game logic, optimized for a great experience on mobile devices and desktops alike.\"," +
                "\"features\": [\"Intuitive mobile-first interface\", \"Customizable player count\", \"Real-time game state tracking\", \"Notification sounds for moves\"]" +
                "}";

            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            byte[] responseBytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }

    // To-Do List प्रोजेक्ट हैंडलर
    static class TodoListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCORSHeaders(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // JSON रिस्पांस
            String response = "{" +
                "\"id\": \"todo\"," +
                "\"title\": \"Advanced To-Do List\"," +
                "\"description\": \"A robust task management application with advanced features like a running status timer, priority levels, and customizable notification sounds for important deadlines.\"," +
                "\"features\": [\"Status tracking with visible timer\", \"Multiple priority levels (High, Medium, Low)\", \"Customizable notification sounds\", \"Archived task view\"]" +
                "}";

            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            byte[] responseBytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
            }
                
