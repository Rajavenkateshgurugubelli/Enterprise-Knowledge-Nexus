package com.enterprise.knowledge_nexus.repository;

import com.enterprise.knowledge_nexus.entity.DocumentMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentMetadata, String> {
    List<DocumentMetadata> findByUploadedBy(String uploadedBy);
}
