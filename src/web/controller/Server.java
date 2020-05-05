package web.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server
{
    public void run(int port) throws IOException {
    	boolean running = true;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is running on http://localhost:" + port + "...");
        while (running)
        {
            try (Socket socket = server.accept())
            {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            }
        }
        server.close();
    }
}
