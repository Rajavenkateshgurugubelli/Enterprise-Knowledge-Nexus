package com.enterprise.knowledge_nexus.controller;

import com.enterprise.knowledge_nexus.entity.DocumentMetadata;
import com.enterprise.knowledge_nexus.service.IngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*") // Allow frontend access
public class DocumentController {

    private final IngestionService ingestionService;

    public DocumentController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentMetadata> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            DocumentMetadata metadata = ingestionService.ingestDocument(file);
            return ResponseEntity.ok(metadata);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentMetadata>> getAllDocuments() {
        return ResponseEntity.ok(ingestionService.getAllDocuments());
    }
}
