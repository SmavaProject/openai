package com.aiapplication.openai.config;

import com.aiapplication.openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class InMemoryChatClientConfig {

    @Bean
    ChatClient.Builder getDefaultChatClientBuilder(OpenAiChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel);
    }

    @Bean("memoryChatClient")
    public ChatClient memoryChatClient (ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
                .build();
    }

}
