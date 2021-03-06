package web.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Route
{
	public String title;
	public String file;
	private String ext;
	
	/*
	constructor
	@param title - the route title
	@param file - a filepath to the template file
	*/
	public Route(String title, String file)
	{
		this.title = title;
		this.file = file;
		
		String[] parts = file.split("\\.");
		this.ext = parts[parts.length-1];		
	}

	/*
	Matches the file extenstion to an http file type
	@returns the http file type as a string
	*/
	public String getType()
	{
		HashMap<String, String> types = new HashMap<String, String>();
		// Text and Code
		types.put("txt", "text/plain");
		types.put("css", "text/css");
		types.put("js", "application/javascript");
		types.put("html", "text/html");
		types.put("xml", "text/xml");
		types.put("json", "application/json");
		
		// Images
		types.put("gif", "image/gif");
		types.put("jpeg", "image/jpeg");
		types.put("jpg", "image/jpeg");
		types.put("png", "image/png");
		types.put("tiff", "image/tiff");
		types.put("svg", "image/svg+xml");
		
		String value = types.get(ext);
		if (value == null)
			value = "text/plain";

		return value;
	}
	
	/*
	Reads the route template file and returns it
	@returns the route template file as a string
	*/
	public String getContent()
	{
		try {
			Path path = Paths.get(System.getProperty("user.dir"), "view", file);
			File file = new File(path.toString());
			Scanner sc = new Scanner(file);
			
			String content = "";
			while (sc.hasNextLine())
			{
				String line = sc.nextLine();
				content += line;
			}
			sc.close();
			
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
