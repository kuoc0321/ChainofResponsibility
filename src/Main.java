import middleware.Middleware;
import middleware.RoleCheckMiddleware;
import middleware.ThrottingMiddleware;
import middleware.UserExsistsMiddleware;
import server.Server;

import javax.swing.text.html.StyleSheet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;
    private static void init() {
        server = new Server();
        server.register("admin@gmail.com" , "1");
        server.register("finuser@gmail.com" , "2");
        Middleware middleware = new ThrottingMiddleware(2);
        middleware.setNextChain(new UserExsistsMiddleware(server)).setNextChain(new RoleCheckMiddleware());
        server.setMiddleware(middleware);
    }
    public static void main(String[] args) throws IOException {
        init();
        boolean success;
        do {
                System.out.println("Enter email: ");
                String email = reader.readLine();
                System.out.println("Enter password: ");
                String password = reader.readLine();
                success = server.login(email, password);
        }while(!success);
    }
}