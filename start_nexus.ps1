# Startup Script for Enterprise Knowledge Nexus (RAG Platform)
# Runs docker-compose, Spring Boot backend, and Vue.js frontend.

$JavaHome = "C:\Program Files\Java\jdk-21.0.11"
$NpmPath = "C:\Program Files\nodejs\npm.cmd"

# Set JAVA_HOME in current session
$env:JAVA_HOME = $JavaHome
Write-Host "Using JAVA_HOME: $env:JAVA_HOME" -ForegroundColor DarkGray
Write-Host "Using npm: $NpmPath" -ForegroundColor DarkGray
Write-Host ""

# 1. Start Vector Store & Database via Docker Compose
Write-Host "Starting Docker containers (PostgreSQL + Elasticsearch)..." -ForegroundColor Cyan
docker-compose up -d

if ($LASTEXITCODE -ne 0) {
    Write-Warning "Failed to start Docker containers. Make sure Docker Desktop is running."
}

# 2. Start Backend Spring Boot
Write-Host "Launching Spring Boot Backend on http://localhost:8080..." -ForegroundColor Gray
Start-Process powershell.exe -ArgumentList "-NoExit", "-Command", "`$env:JAVA_HOME = '$JavaHome'; .\mvnw.cmd spring-boot:run"

# 3. Start Frontend Vue App
Write-Host "Launching Vue.js Frontend..." -ForegroundColor Gray
Start-Process powershell.exe -ArgumentList "-NoExit", "-Command", "cd frontend; & '$NpmPath' run dev"

# 4. Open Browser
Start-Sleep -Seconds 5
Write-Host "Opening browser..." -ForegroundColor Green
Start-Process "http://localhost:5173"
