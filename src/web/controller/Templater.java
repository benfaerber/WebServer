package web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Templater
{	
	public String render(String content, HashMap<String, String> data, HashMap<String, String> globalData)
	{
		// Get matches
		String patt = "\\[\\[( )?([a-zA-Z])+( )?\\]\\]";
		ArrayList<String> matches = new ArrayList<String>();
		Matcher m = Pattern.compile(patt).matcher(content);
		while (m.find())
			matches.add(m.group());
	 
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
		 		
		return content;
	}
}
