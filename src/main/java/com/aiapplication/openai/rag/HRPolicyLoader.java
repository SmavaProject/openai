package com.aiapplication.openai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class HRPolicyLoader {

    private final VectorStore vectorStore;

    @Value("classpath:Our_HR_Policies.pdf")
    Resource policyFile;

    /*
    A bean of QdrantVectorStore class which implements VectorStore is created by Spring and we are injecting it as a
    dependency for our class
     */
    public HRPolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPdf(){
        TikaDocumentReader documentReader = new TikaDocumentReader(policyFile);
        java.util.List<Document> docs = documentReader.get();
        vectorStore.add(docs);
    }
}
