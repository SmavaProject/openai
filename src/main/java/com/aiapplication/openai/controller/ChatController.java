package com.aiapplication.openai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientAttributes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ChatController {

    private final ChatClient  openAiChatClient;

    public ChatController(@Qualifier("openAiChatClient") ChatClient openaiChatClient) {
        this.openAiChatClient = openaiChatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        String response = openAiChatClient
                .prompt()
                .system("You are an HR chat bot in a big company. Only respond to HR related questions")
                .user(message)
                .call()
                .content();
        return response;
    }
}
