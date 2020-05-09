package web.controller;

import web.model.Routes;
import web.model.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.time.Instant;

public class Server
{
	public boolean isDebug = true;
	private Controller controller;
	private Routes routes;
	private Templater templater;
	private HashMap<String, String> globalData;
	private int requestsServed = 0;
	
	private long startTime = 0;
	private long currentTime = 0;
	private long runTime = 0;
	
	public Server(boolean isDebug)
	{
		this.isDebug = isDebug;
		this.routes = new Routes();
		this.controller = new Controller();
		this.templater = new Templater();
	}
	
    public void run(int port) throws IOException
    {
    	boolean running = true;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server is running on localhost:" + port + "...");
        startTime = Instant.now().getEpochSecond();
        while (running)
        {
            try (Socket socket = server.accept())
            {
            	// Analytics
            	requestsServed++;
            	currentTime = Instant.now().getEpochSecond();
            	runTime = currentTime - startTime;
            	
            	// Global Data
            
            	globalData = new HashMap<String, String>();
            	globalData.put("requestsServed", Integer.toString(requestsServed));
            	globalData.put("startTime", Long.toString(startTime));
            	globalData.put("currentTime", Long.toString(currentTime));
            	globalData.put("runTime", Long.toString(runTime));
            	
            	// See which route is requested
            	String requestedRoute = getRoute(socket);
            	
            	if (this.isDebug)
            		System.out.println("A request was made for " + requestedRoute + "...");
            	
            	// Find the chosen route or 404
            	requestedRoute = requestedRoute.replace("/", "");
            	Route serveRoute = routes.get(requestedRoute);
            	
            	// Grab the route specific data from the controller
            	HashMap<String, String> grabbedData = controller.request(serveRoute.title);
            	
            	String template = serveRoute.getContent();
            	String renderedContent = templater.render(template, grabbedData, globalData);
            	
            	// Serve the file
            	serve(socket, renderedContent, serveRoute.getType());
            	
            	if (this.isDebug)
            	{
            		if (serveRoute.title.equals("404"))
                		System.out.println("This request gave a 404 error...");
            		else
                		System.out.println("This request was fullfilled...");
            	}
            }
        }
        server.close();
    }
    
    public String getRoute(Socket socket)
    {
		try {
	    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	String request = br.readLine();
	    	String[] parts = request.split(" ");
	    	return parts[1];
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public void serve(Socket socket, String content, String type)
    {
        String response = "HTTP/1.1 200 OK\r\n";
        response += "Content-Type: " + type + "; charset=utf-8\r\n";
        response += "\r\n";
        // Start Response Body
        response += content;
        try {
			socket.getOutputStream().write(response.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
