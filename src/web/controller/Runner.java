package web.controller;

import java.io.IOException;

public class Runner
{
	public static void main(String[] args) throws IOException
	{
		Settings settings = new Settings();
		int port = settings.getInt("port");
		boolean isDebug = settings.is("debug");

		Server server = new Server(isDebug);
		server.run(port);
	}
}
