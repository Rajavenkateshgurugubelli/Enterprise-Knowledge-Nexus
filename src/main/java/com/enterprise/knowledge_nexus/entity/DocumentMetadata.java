package com.enterprise.knowledge_nexus.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
public class DocumentMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String contentType;

    private long size;

    @CreationTimestamp
    private LocalDateTime uploadedAt;

    private String uploadedBy; // For future user association

    // Status of processing: PENDING, PROCESSED, FAILED
    @Enumerated(EnumType.STRING)
    private ProcessingStatus status;

    public enum ProcessingStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}
