package com.aiapplication.openai.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;

import java.util.List;

public class WebSearchDocumentRetriever implements DocumentRetriever {
    @Override
    public List<Document> retrieve(Query query) {
        return List.of();
    }
}
