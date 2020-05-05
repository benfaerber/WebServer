package web.model;

import java.util.ArrayList;

public class Routes
{
	private ArrayList<Route> routes;
	private Route error404;
	
	public Routes()
	{
		routes = new ArrayList<Route>();
		error404 = new Route("404", "404.html");
		// "/" routes to index.html by default
		// Define your routes here:
		routes.add(new Route("home", "index.html"));
		routes.add(new Route("about", "about.html"));
		routes.add(new Route("berlin", "img/berlin.png"));
		routes.add(new Route("berlinData", "data/berlinData.json"));
	}
	
	public Route get(String routeName)
	{
		for (Route route : routes)
		{
			if (routeName.length() == 0 && route.file.equals("index.html"))
				return route;
			
			if (route.title.equals(routeName))
				return route;
		}
		return error404;
	}
}
