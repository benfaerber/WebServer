package web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Templater
{	
	/*
	Uses a template, route specific data, and globalData to create a rendered file
	@param content - the template loaded as a string
	@param data - the route specific data hashmap
	@param globalData - global data provided by the server
	@returns rendered content
	*/
	public String render(String content, HashMap<String, String> data, HashMap<String, String> globalData)
	{
		content = includeFiles(content);
		String patt = "\\[\\[( )?([a-zA-Z0-9])+( )?\\]\\]";
		ArrayList<String> matches = getMatches(patt, content);
	 
		 for (String match : matches)
		 {
			 String key = match.replace("[[", "").replace("]]", "").trim();
			 String val = "undefined";
			 if (data.get(key) != null)
				 val = data.get(key);
			 else if (globalData.get(key) != null)
				 val = globalData.get(key);
			 
			 content = content.replace(match, val);
		 }
		 
		content = displayCommands(content);
		return content;
	}
	
	/*
	Finds all the include commands, loads the requested files, and creates a file with subfiles loaded
	@param content - the loaded template string
	@returns a string with all subfiles included
	*/
	private String includeFiles(String content)
	{
		String patt = "include\\(.*?\\)";
		ArrayList<String> matches = getMatches(patt, content);
		
		for (String match : matches)
		{
			String filename = match.replace("include(", "").replace(")", "");
			try {
				Path path = Paths.get(System.getProperty("user.dir"), "view", filename);
				File file = new File(path.toString());
				Scanner sc = new Scanner(file);
				
				String readContent = "";
				while (sc.hasNextLine())
				{
					String line = sc.nextLine();
					readContent += line;
				}
				sc.close();
				
				content = content.replace(match, readContent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
	
	/*
	Changes escaped commands into regular looking commands.
	Used for rendering documentation without actually excecuting them as commands
	@param content - the rendered content with all commands already executed
	@returns a string with escaped commands as regular looking commands
	*/
	private String displayCommands(String content)
	{
		content = content.replace("include*(", "include(");
		content = content.replace("[*[", "[[").replace("]*]", "]]");
		return content;
	}
	
	/*
	Uses a regular expression to find a list of matches
	@param pattern - a regular expression as a string
	@param content - string to match the regular expression against
	*/
	private ArrayList<String> getMatches(String pattern, String content)
	{
		ArrayList<String> matches = new ArrayList<String>();
		Matcher m = Pattern.compile(pattern).matcher(content);
		while (m.find())
			matches.add(m.group());
		return matches;
	}
}
