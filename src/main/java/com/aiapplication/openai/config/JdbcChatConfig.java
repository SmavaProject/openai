package com.aiapplication.openai.config;

import com.aiapplication.openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class JdbcChatConfig {

     /*
     This bean is not mandatory, Spring will create it for us.
     We create it to get better control over the object of ChatMemory, e.g. set smaller number of maxMessages
      */
     @Bean
     ChatMemory getChatMemory(JdbcChatMemoryRepository chatMemoryRepository){
     return MessageWindowChatMemory
        .builder()
        .maxMessages(10)
        .chatMemoryRepository(chatMemoryRepository)
        .build();
     }




    /*
    If JDBC is configured in pom, Spring by default will create a bean of JdbcChatMemoryRepository
    not InMemoryChatMemoryRepository
     */
    @Bean("jdbcChatClient")
    public ChatClient memoryChatClient (@Qualifier("getDefaultChatClientBuilder") ChatClient.Builder chatClientBuilder,
                                        ChatMemory chatMemory,
                                        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {

        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenAuditAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return chatClientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenAuditAdvisor, retrievalAugmentationAdvisor))
                .build();
    }


    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor
                .builder()
                .documentRetriever(VectorStoreDocumentRetriever
                        .builder()
                        .vectorStore(vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .build()
                )
                .build();
    }

}
