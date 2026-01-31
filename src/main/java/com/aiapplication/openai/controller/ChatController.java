package com.aiapplication.openai.controller;


import model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientAttributes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ChatController {

    private final ChatClient  openAiChatClient;

    public ChatController(@Qualifier("openAiChatClient") ChatClient openaiChatClient) {
        this.openAiChatClient = openaiChatClient;
    }

    @Value("classpath:/templates/userPromptTemplate.st")
    Resource userPromptTemplate;

    @GetMapping("/chat")
    public String chat(@RequestParam("customerName") String customerName,
                       @RequestParam("customerMessage") String customerMessage){
        String response = openAiChatClient
                .prompt()
                .system("""
                You are an Internal company IT-assistant. 
                If an unrelated question is asked, refuse responding. 
                Be professional and polite.
                """) //overwrites defaultSustem from the ChatClient bean defined in the config
                .user(promptUserSpec ->
                        promptUserSpec
                                .text(userPromptTemplate)
                                .param("customerName", customerName)
                                .param("customerMessage", customerMessage)
                )
                .call()
                .content();
        return response;
    }

    //gives structured output in json format
    @GetMapping("/chat-cities")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("county") String county){
       CountryCities response = openAiChatClient
                .prompt()
               .system("""
                       Provide the list of cities in the county
                       """)
                .user(county)
                .call()
                .entity(CountryCities.class);
        return ResponseEntity.ok(response);
    }
}
