package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ChatServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;



public class Main {
    public static void main(String[] args) throws Exception {

        SignUpServlet signUpServlet = new SignUpServlet();
        SignInServlet signInServlet = new SignInServlet();
        ChatServlet chatServlet = new ChatServlet();


        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signUP");
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signIN");
        contextHandler.addServlet(new ServletHolder(chatServlet), "/chat");

        Server server = new Server(8080);
        server.setHandler(contextHandler);


        server.start();
        server.join();
    }
}
