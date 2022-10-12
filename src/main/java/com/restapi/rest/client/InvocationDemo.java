package com.restapi.rest.client;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class InvocationDemo {

	public static void main(String[] args) {
		
		Invocation invocation = getMessegesOfYear(2022);
		Response response = invocation.invoke();
		System.out.println(response.getStatus());

	}

	private static Invocation getMessegesOfYear(int year) {
		Client client = ClientBuilder.newClient();
		return client.target("http://localhost:8080/messanger/webapi/")
					.path("messages")
					.queryParam("year", year)
					.request(MediaType.APPLICATION_JSON)
					.buildGet();
	}

}
