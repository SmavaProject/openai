package com.aiapplication.openai.config;

import com.aiapplication.openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .temperature(0.6)
                .maxTokens(200)
                .presencePenalty(0.6)
                .topP(0.8)
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultSystem("You are an HR chat bot in a big company. Only respond to HR related questions")
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultUser("How can you help me?")
                .build();
    }

}
