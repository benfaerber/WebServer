package web.controller;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Controller
{
	/*
	Returns a hashmap containing route specific data.
	@param routeName - the route name the user requested
	@returns hashmap of data for each route. You can exclude other files to crunch data for complex routes
	*/
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
		else if (routeName.equals("documentation"))
		{
			double ranNum = Math.random()*100;
			String ranString = Double.toString(ranNum);
			data.put("randomNumber", ranString);
			return data;
		}
		
		return null;
	}
	
}
