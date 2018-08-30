package com.devglan.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devglan.data.Users;

@Service
@EnableAsync
public class UserService {

	private String url = "http://localhost:9090/getAll";
	private String url2 = "http://localhost:9090/getAll/101";
	private String url3 = "http://localhost:9090/getAll/101";
	RestTemplate template = new RestTemplate();

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@Async
	public void getData() throws InterruptedException {
		Thread.sleep(5000);
		System.out.println("getData : - Started");
		System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
		List<Users> result = template.getForObject(url, List.class);
		messagingTemplate.convertAndSendToUser("user", "/queue/user", result);
		//return CompletableFuture.completedFuture(result);
	}

	// @Async("executor")
	public CompletableFuture<Users> addData() {
		String requestJson = "{\"id\":100,\"name\":\"Shahjade\",\"contact\":\"88882225\",\"email\":\"moni@gmail.com\",\"work\":\"REST App\",\"city\":\"Indore\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		Users result = template.postForObject(url, entity, Users.class);
		return CompletableFuture.completedFuture(result);
	}

	@Async
	public CompletableFuture<Users> getById() {
		System.out.println("getById --- Started");
		System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());
		Users result = template.getForObject(url2, Users.class);
		return CompletableFuture.completedFuture(result);

	}

	public CompletableFuture<Users> delete() {
		Users result = template.getForObject(url3, Users.class);
		return CompletableFuture.completedFuture(result);
	}
}
