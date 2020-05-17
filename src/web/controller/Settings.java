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
	
	/*
	constructor
	Reads the settings file and puts it into the "map" hashmap
	*/
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
	
	/*
	Returns a string from the settings map
	@param key - the key from the settings key value pair text file
	*/
	public String get(String key)
	{
		key = key.toUpperCase();
		return map.get(key);
	}
	
	/*
	Returns a boolean from the settings map
	@param key - the key from the settings key value pair text file
	*/
	public boolean is(String key)
	{
		return !get(key).equals("false");
	}
	
	/*
	Returns an int from the settings map
	@param key - the key from the settings key value pair text file
	*/
	public int getInt(String key)
	{
		return Integer.parseInt(get(key));
	}
	
	/*
	Returns a float from the settings map
	@param key - the key from the settings key value pair text file
	*/
	public float getFloat(String key)
	{
		return Float.parseFloat(get(key));
	}
}
