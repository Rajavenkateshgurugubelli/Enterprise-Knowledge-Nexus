package com.enterprise.knowledge_nexus.service;

import com.enterprise.knowledge_nexus.entity.DocumentMetadata;
import com.enterprise.knowledge_nexus.repository.DocumentRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class IngestionService {

    private final DocumentRepository documentRepository;
    private final VectorStore vectorStore;

    public IngestionService(DocumentRepository documentRepository, VectorStore vectorStore) {
        this.documentRepository = documentRepository;
        this.vectorStore = vectorStore;
    }

    public DocumentMetadata ingestDocument(MultipartFile file) throws IOException {
        // 1. Save Metadata to DB
        DocumentMetadata metadata = new DocumentMetadata();
        metadata.setFilename(file.getOriginalFilename());
        metadata.setContentType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setStatus(DocumentMetadata.ProcessingStatus.PROCESSING);
        metadata.setUploadedBy("admin"); // Hardcoded for MVP

        metadata = documentRepository.save(metadata);

        try {
            // 2. Parse Document using Tika (Handles PDF, DOCX, TXT, etc.)
            Resource resource = new ByteArrayResource(file.getBytes());
            TikaDocumentReader reader = new TikaDocumentReader(resource);
            List<Document> documents = reader.get();

            // 3. Split into tokens
            TokenTextSplitter splitter = new TokenTextSplitter();
            List<Document> chunks = splitter.apply(documents);

            // 4. Add Metadata to each chunk (Crucial for retrieval citations)
            for (Document chunk : chunks) {
                chunk.getMetadata().put("filename", file.getOriginalFilename());
                chunk.getMetadata().put("doc_id", metadata.getId());
            }

            // 5. Store embeddings in Vector DB
            vectorStore.add(chunks);

            // 6. Update Status
            metadata.setStatus(DocumentMetadata.ProcessingStatus.COMPLETED);
            return documentRepository.save(metadata);

        } catch (Exception e) {
            metadata.setStatus(DocumentMetadata.ProcessingStatus.FAILED);
            documentRepository.save(metadata);
            throw new RuntimeException("Failed to process document", e);
        }
    }

    public List<DocumentMetadata> getAllDocuments() {
        return documentRepository.findAll();
    }
}
