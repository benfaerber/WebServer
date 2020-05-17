package web.model;

import java.util.ArrayList;

public class Routes
{
	private ArrayList<Route> routes;
	private Route error404;
	
	/*
	constructor
	Adds all the routes for the app
	*/
	public Routes()
	{
		routes = new ArrayList<Route>();
		error404 = new Route("404", "404.html");
		// "/" routes to index.html by default
		// Define your routes here:
		routes.add(new Route("home", "index.html"));
		routes.add(new Route("documentation", "docs.html"));
		routes.add(new Route("css", "css/style.css"));
		routes.add(new Route("data", "data/data.json"));
	}

	/*
	loads a route based on the routeName or 404s if the route is not found
	@param routeName - the user requested route
	@returns the requested route or the 404 route if not found
	*/	
	public Route get(String routeName)
	{
		for (Route route : routes)
		{
			if ((routeName.length() == 0 && route.file.equals("index.html")) || routeName.equals("favicon.ico"))
				return route;
			
			if (route.title.equals(routeName))
				return route;
		}
		return error404;
	}
}
