package com.restapi.rest.client;

import java.util.List;

import com.restapi.messanger.model.Message;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

public class GenericTypeDemo {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		List<Message> messages = client.target("http://localhost:8080/messanger/webapi/")
					.path("messages")
					.queryParam("year", 2022)
					.request(MediaType.APPLICATION_JSON)
					.get(new GenericType<List<Message>>() { });
		
		for(Message message : messages) {
			System.out.println(message.getMessege());
		}

	}

}
