package web.controller;

import java.util.HashMap;

public class Templater
{	
	public String render(String content, HashMap<String, String> data, HashMap<String, String> globalData)
	{
		String beginDelimiter = "[[";
		String endDelimiter = "]]";
		
		HashMap<String, String> replace = new HashMap<String, String>();
		int currentIndex = 0;
		while (currentIndex < content.length())
		{
			int begIndex = content.indexOf(beginDelimiter, currentIndex);
			int endIndex = content.indexOf(endDelimiter, begIndex);
			String key = content.substring(begIndex + beginDelimiter.length(), endIndex);
			String assembled = beginDelimiter + key + endDelimiter;
			key = key.trim();
			
			// Route specific values are looked for first
			String value = "undefined";
			if (data.get(key) != null)
				value = data.get(key);
			else if (globalData.get(key) != null)
				value = globalData.get(key);
			
			replace.put(assembled, value);
			currentIndex += endIndex;
		}
		
		
		
		return content;
	}
}
