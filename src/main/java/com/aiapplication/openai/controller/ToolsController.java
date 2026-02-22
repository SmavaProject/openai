package com.aiapplication.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
public class ToolsController {

    private ChatClient chatClient;

    public ToolsController(@Qualifier("timeChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("all-tools")
    public ResponseEntity<String> localTime(@RequestHeader("username") String username,
                                            @RequestParam("message") String message){
        String response = chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(message)
                .toolContext(Map.of("username", username))
                .call()
                .content();

        return ResponseEntity.ok(response);
    }
}
