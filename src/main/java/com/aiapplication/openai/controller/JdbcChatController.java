package com.aiapplication.openai.controller;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("api")
public class JdbcChatController {

    private final ChatClient  openAiChatClient;

    public JdbcChatController(@Qualifier("jdbcChatClient") ChatClient openaiChatClient) {
        this.openAiChatClient = openaiChatClient;
    }

    @GetMapping("/jdbc-chat")
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
