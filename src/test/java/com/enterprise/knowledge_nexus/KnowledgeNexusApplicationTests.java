package com.enterprise.knowledge_nexus;

import com.enterprise.knowledge_nexus.service.ChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KnowledgeNexusApplicationTests {

    @Mock
    private VectorStore vectorStore;

    @Mock
    private ChatModel chatModel;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void testChatFlow() {
        // Arrange
        String query = "What is the policy?";
        List<Document> mockDocs = List.of(new Document("This is the policy context details."));
        when(vectorStore.similaritySearch(any(SearchRequest.class))).thenReturn(mockDocs);

        org.springframework.ai.chat.messages.AssistantMessage assistantMessage = new org.springframework.ai.chat.messages.AssistantMessage("Mock response text");
        ChatResponse mockResponse = new ChatResponse(List.of(new Generation(assistantMessage)));
        when(chatModel.call(any(Prompt.class))).thenReturn(mockResponse);

        // Act
        String result = chatService.chat(query);

        // Assert
        assertEquals("Mock response text", result);
    }
}
