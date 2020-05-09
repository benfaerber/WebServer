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
	public String render(String content, HashMap<String, String> data, HashMap<String, String> globalData)
	{
		content = includeFiles(content);
		String patt = "\\[\\[( )?([a-zA-Z])+( )?\\]\\]";
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
				
				content = content.replace(match, readContent);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
	
	private String displayCommands(String content)
	{
		content = content.replace("include*(", "include(");
		content = content.replace("[*[", "[[").replace("]*]", "]]");
		return content;
	}
	
	private ArrayList<String> getMatches(String pattern, String content)
	{
		ArrayList<String> matches = new ArrayList<String>();
		Matcher m = Pattern.compile(pattern).matcher(content);
		while (m.find())
			matches.add(m.group());
		return matches;
	}
}
