package com.enterprise.knowledge_nexus.controller;

import com.enterprise.knowledge_nexus.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> payload) {
        String query = payload.get("query");
        String response = chatService.chat(query);
        return Map.of("response", response);
    }
}
