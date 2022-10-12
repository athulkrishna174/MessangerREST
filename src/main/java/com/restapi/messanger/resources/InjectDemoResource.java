package com.restapi.messanger.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET
	@Path("annotations")
	public String getParametersUsingAnnotations(@MatrixParam("param") String matrixParam,
											@HeaderParam("authId") String authId,
											@CookieParam("name") String name) {
		return "Matrix Param: " + matrixParam + ", Header Param: " + authId + ", Cookie Param: " + name; 
	}
	
	@GET
	@Path("context")
	public String getContextParam(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
		String path = uriInfo.getAbsolutePath().toString();
		String header = httpHeaders.getCookies().toString();
		return "Path: " + path + ", Cookies: " + header;
	}
}
