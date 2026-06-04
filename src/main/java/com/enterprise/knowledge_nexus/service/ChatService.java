package com.enterprise.knowledge_nexus.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final VectorStore vectorStore;
    private final ChatModel chatModel;

    public ChatService(VectorStore vectorStore, ChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
    }

    public String chat(String query) {
        // 1. Retrieve relevant documents (RAG)
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.query(query).withTopK(3));

        String context = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n\n"));

        // 2. Construct Prompt
        String systemText = """
                You are an enterprise knowledge assistant.
                Answer the user's question mostly based on the provided context.
                If the answer is not in the context, say "I don't have that information in my knowledge base."

                CONTEXT:
                {context}
                """;

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("context", context));

        UserMessage userMessage = new UserMessage(query);

        // 3. Call LLM
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return chatModel.call(prompt).getResult().getOutput().getContent();
    }
}
