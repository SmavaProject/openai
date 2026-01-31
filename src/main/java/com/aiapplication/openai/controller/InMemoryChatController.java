package com.aiapplication.openai.controller;


import model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientAttributes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("api")
public class InMemoryChatController {

    private final ChatClient  openAiChatClient;

    public InMemoryChatController(@Qualifier("memoryChatClient") ChatClient openaiChatClient) {
        this.openAiChatClient = openaiChatClient;
    }

    @GetMapping("/smart-chat")
    public ResponseEntity<String> inMemoryChat(
            @RequestHeader("username") String username,
            @RequestParam("message") String message){
        return ResponseEntity.ok(openAiChatClient
                .prompt()
                .user(message)
                        .advisors(
                                advisorSpec ->
                                        advisorSpec.param(CONVERSATION_ID, username)
                        )
                .call()
                .content());

    }
}
