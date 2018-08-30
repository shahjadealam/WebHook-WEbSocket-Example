package com.devglan.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devglan.data.Users;
import com.devglan.service.UserService;
import com.google.gson.Gson;

@Controller
public class WebSocketController {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private UserService service;
	
	@MessageMapping("/message")
	@SendToUser("/queue/reply")
	public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
		String name = new Gson().fromJson(message, Map.class).get("name").toString();
		//messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
		return name;
	}
	
	@MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

	
	//@MessageMapping("/users")
	@ResponseBody
	@RequestMapping(value="/users")
	//@SendToUser("/queue/user")
	public String getAllUser(@Payload String message, Principal principal) throws Exception {
		//service.delete();
		//service.addData();
		service.getData();
		System.out.println("After service call");
		//messagingTemplate.convertAndSendToUser("user", "/queue/user", users);
		//System.out.println("After message template");
		//CompletableFuture<Users> getId = service.getById();
		//String str = getdata.get().get(0).toString();
		//System.out.println("All Data---->"+getdata.get().get(0));
		//System.out.println("Data By ID---->"+getId.get());
		//return getdata;
		return "done";
	}
	
}
