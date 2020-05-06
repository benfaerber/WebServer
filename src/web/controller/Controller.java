package web.controller;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller
{
	public HashMap<String, String> request(String routeName)
	{
		if (routeName.equals("home"))
		{
			try {
				String currentUser = System.getProperty("user.name");
				String ipAddress = Inet4Address.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		else if (routeName.equals("about"))
		{
			
		}
		
		return null;
	}
	
}
