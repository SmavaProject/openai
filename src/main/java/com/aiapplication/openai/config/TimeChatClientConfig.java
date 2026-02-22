package com.aiapplication.openai.config;

import com.aiapplication.openai.advisors.TokenUsageAuditAdvisor;
import com.aiapplication.openai.tools.HelpDeskTools;
import com.aiapplication.openai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TimeChatClientConfig {

    @Bean
    public ChatClient openAiChatClientForTools(OpenAiChatModel openAiChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .temperature(0.6)
                .maxTokens(200)
                .presencePenalty(0.6)
                .topP(0.8)
                .build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .build();
    }

    @Bean
    ChatClient.Builder getDefaultChatClientBuilderForTools(OpenAiChatModel openAiChatModel){
        return ChatClient.builder(openAiChatModel);
    }

    @Bean("timeChatClient")
    public ChatClient chatClient (@Qualifier("getDefaultChatClientBuilderForTools") ChatClient.Builder chatClientBuilder,
                                  ChatMemory chatMemory,
                                  TimeTools timeTools,
                                  HelpDeskTools helpDeskTools) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultTools(timeTools, helpDeskTools)
                .defaultAdvisors(List.of(loggerAdvisor, tokenUsageAdvisor, memoryAdvisor))
                .build();
    }
}
