package com.restapi.messanger.resources;

import java.net.URI;
import java.util.List;


import com.restapi.messanger.model.Message;
import com.restapi.messanger.resources.beans.MessageFilterBean;
import com.restapi.messanger.service.MessageService;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {

	private MessageService messageService = new MessageService();

	@GET
	public List<Message> getMesseges(@BeanParam MessageFilterBean filterBean) {
		
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessege(Message message, @Context UriInfo uriInfo) {
		Message newMessege = messageService.addMessage(message);
		String newId = String.valueOf(newMessege.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessege)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessege(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessege(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessege(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
		 Message  message = messageService.getMessage(messageId);
		 
		 message.addLink(getUriForSelf(uriInfo, message), "self");
		 message.addLink(getUriForprofile(uriInfo, message), "profile");
		 return message;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				 .path(MessageResource.class)
				 .path(Long.toString(message.getId()))
				 .build()
				 .toString();
		return uri;
	}
	private String getUriForprofile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
		return uri;
	}
}
