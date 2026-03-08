# Enterprise Knowledge Nexus

A secure, enterprise-grade Retrieval-Augmented Generation (RAG) platform.

## Features
- **Document Ingestion**: Upload PDF/TXT files.
- **Automated Processing**: Text extraction (Apache Tika), Chunking, and Embedding.
- **Vector Search**: Semantic retrieval using Elasticsearch/PostgreSQL.
- **Interactive Chat**: Chat with your documents using LLMs (Spring AI).

## Setup Instructions

### Prerequisites
- JDK 21
- Node.js 20+
- Docker Desktop

### 1. Start Database & Vector Store
```bash
docker-compose up -d
```

### 2. Backend Setup
1. Open the project in IntelliJ/Eclipse.
2. Update `src/main/resources/application.yml` with your OpenAI API Key.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

### 3. Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the dev server:
   ```bash
   npm run dev
   ```

## Tech Stack
- **Backend**: Java, Spring Boot, Spring AI
- **Frontend**: Vue.js, TailwindCSS
- **Data**: PostgreSQL, Elasticsearch
