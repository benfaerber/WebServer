package web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Settings
{
	private HashMap<String, String> map;
	
	public Settings()
	{
		map = new HashMap<String, String>();
		Path path = Paths.get(System.getProperty("user.dir"), "settings", "settings.txt");
		File file = new File(path.toString());
		Scanner sc;
		try {
			sc = new Scanner(file);
			
			while (sc.hasNextLine())
			{
				String[] parts = sc.nextLine().split("=");
				map.put(parts[0], parts[1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String get(String key)
	{
		key = key.toUpperCase();
		return map.get(key);
	}
	
	public boolean is(String key)
	{
		return !get(key).equals("false");
	}
	
	public int getInt(String key)
	{
		return Integer.parseInt(get(key));
	}
	
	public float getFloat(String key)
	{
		return Float.parseFloat(get(key));
	}
}
