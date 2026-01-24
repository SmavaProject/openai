package com.aiapplication.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);
        return chatClientBuilder.build();
    }

}
