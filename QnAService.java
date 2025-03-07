package com.ai.Service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class QnAService {
	
	//access to api key and url [Gemini]
	
	@Value("${gemini.api.url}")
	private String geminiApi;
	
	@Value("${gemini.api.key}")
	private String geminiKey;
	
	private final WebClient webClient;
	
	public QnAService(WebClient.Builder webClient) {
		super();
		this.webClient = webClient.build();
	}

	public String getAnswer(String question)
	{
		//construct the request payload
		Map<String, Object> requestBody= Map.of(
				"contents",new Object[] {
						Map.of("parts",new Object[] 
								{
										Map.of("text",question)
								})
				}
				);
				
		
		//make API call
		
		String response=webClient.post().uri(geminiApi + geminiKey).header("Content-Type", "application/json")
		.bodyValue(requestBody).retrieve().bodyToMono(String.class).block();
		
		//return response
		return response;
	}
}
