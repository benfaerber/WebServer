package web.controller;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller
{
	public HashMap<String, String> request(String routeName)
	{
		HashMap<String, String> data = new HashMap<String, String>();
		if (routeName.equals("home"))
		{
			try {
				String currentUser = System.getProperty("user.name");
				String ipAddress = Inet4Address.getLocalHost().getHostAddress();
				data.put("username", currentUser);
				data.put("ip", ipAddress);
				return data;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
		}
		else if (routeName.equals("about"))
		{
			
		}
		
		return null;
	}
	
}
