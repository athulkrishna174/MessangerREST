package com.restapi.rest.client;

import com.restapi.messanger.model.Message;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class RestApiClient {
	
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		WebTarget baseTarget = client.target("http://localhost:8080/messanger/webapi/");
		WebTarget messagesTarget = baseTarget.path("messages");
		WebTarget singleMessageTarget = messagesTarget.path("{messageId}");
		
		Message message = singleMessageTarget
				.resolveTemplate("messageId", "2")
				.request(MediaType.APPLICATION_JSON)
				.get(Message.class);
		
		System.out.println(message.getMessege());
		
		Message newMessage = new Message(3, "Message from JAX-RS Client", "Athul");
		Response postResponse = messagesTarget
				.request()
				.post(Entity.json(newMessage));
		
		if(postResponse.getStatus() != 201) {
			System.out.println("Error");
		}
		System.out.println(postResponse.readEntity(Message.class).getMessege());
	}
	
}
