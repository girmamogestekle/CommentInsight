[![Java](https://img.shields.io/badge/Java-17-blue)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-Enabled-brightgreen)](https://spring.io/projects/spring-ai)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/girmamogestekle/Post-Comments-App/blob/master/LICENSE)
[![GitHub Actions](https://img.shields.io/github/actions/workflow/status/girmamogestekle/CommentInsight/docker-publish.yml?branch=develop&label=Docker%20Build)](https://github.com/girmamogestekle/CommentInsight/actions/workflows/docker-publish.yml)
[![Docker Hub](https://img.shields.io/badge/Docker%20Hub-commentinsight-blue?logo=docker)](https://hub.docker.com/u/commentinsight)
<!-- [![Swagger UI](https://img.shields.io/badge/Docs-Swagger_UI-blue)](https://girmamogestekle.github.io/Post-Comments-App/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=postEntity-comments-app&metric=alert_status)](https://sonarcloud.io/project/overview?id=postEntity-comments-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=postEntity-comments-app&metric=coverage)](https://sonarcloud.io/summary/new_code?id=postEntity-comments-app) -->

# 🚀 CommentInsight 
AI-Powered Comment Intelligence & Sentiment Analysis Platform
```
Scalable microservices platform for collecting, analyzing, and summarizing comments from social media platforms and websites using AI.
```
---

## 🚀 Overview
CommentInsight is an AI-powered microservices platform designed to collect, analyze, and summarize user comments from several platforms and websites. The system fetches comments from platforms such as YouTube and uses AI-driven sentiment analysis to generate audience insights including positive, negative, and neutral summaries, content recommendations, and overall audience reactions.

Built with Spring Boot microservices architecture, centralized configuration management, API Gateway routing, service discovery, resilience patterns, and secure cloud-based secret management, the platform is designed to be scalable, extensible, and ready for future AI-powered analytics across multiple platforms including Reddit, Amazon Reviews, TikTok, and more.

---

## 🏗️ Architecture & Design Principles

### System Architecture

```text
Client (Postman / Frontend)
        ↓
API Gateway
        ↓
Connector Service
        ↓
Platform Connector Services
        ↓
External APIs
        ↓
AI Sentiment Service
```

### 🧩 Microservices Structure

```text
comment-insight-parent
├── comment-insight-common
├── config-server
├── discovery-service
├── api-gateway-service
├── connector-service
├── youtube-connector-service
└── sentiment-service
```

### 🏆 Architecture Principles

* Microservices architecture
* Separation of concerns
* Shared DTO design
* Platform abstraction
* Externalized configuration
* Secure secret management
* Environment isolation
* Scalable pagination
* AI extensibility

---

## ⚡ System Features & Infrastructure

### 🔄 Pagination Support

The platform supports scalable pagination using reusable shared DTOs.

#### Supported Pagination Features

* `pageSize`
* `pageToken`
* `nextPageToken`
* `hasNextPage`

### ☁️ Centralized Configuration

Configurations are managed using:

* Spring Cloud Config Server
* Private Git configuration repository

#### Supported Environments

* `dev`
* `qa`
* `prod`

### 🔐 Secure Secret Management

Secrets are securely managed using:

* HashiCorp HCP Vault Dedicated

#### Sensitive Values Stored Securely

* API keys
* Git tokens
* JWT secrets
* Database passwords

### 🛡️ Resilience Patterns

Implemented resilience features include:

* Circuit Breaker
* Retry
* Rate Limiting
* Service Discovery

#### Technologies

* Resilience4j
* Redis
* Eureka
* Spring Cloud Gateway

### 🧱 Technologies Used

#### Backend

* Java 17
* Spring Boot 3
* Spring Cloud
* Maven

#### Microservices

* Eureka Discovery Server
* Spring Cloud Gateway
* Spring Cloud Config Server
* Resilience4j

#### AI

* Spring AI *(planned)*
* OpenAI API *(planned)*

#### Infrastructure

* Redis
* HashiCorp Vault
* GitHub
* HCP Vault Dedicated

---

## ⚙️ Requirements, Installation & Running the Project

### 📋 Requirements

Before running the project, make sure the following tools and services are installed:

#### Required Software

* Java 17+
* Maven 3.9+
* Git
* IntelliJ IDEA *(recommended)*
* Redis *(for rate limiting support)*

### ☁️ External Services

The project also requires:

* GitHub account
* Clone Private configuration repository
* HashiCorp HCP Vault Dedicated
* YouTube Data API v3 key

### 🔑 Required Environment Variables

Set the following environment variables before running the services:

```env
CONFIG_REPO_URI=your_private_config_repo_url
CONFIG_REPO_USERNAME=your_github_username
CONFIG_REPO_TOKEN=your_github_token

VAULT_HOST=your_vault_host
VAULT_TOKEN=your_vault_token

YOUTUBE_API_KEY=your_youtube_api_key
```

### 📥 Installation

#### 1. Clone the Repository

```bash
git clone https://github.com/your-username/comment-insight-parent.git
```

#### 2. Navigate to Project Directory

```bash
cd comment-insight-parent
```

#### 3. Build the Project

```bash
mvn clean install
```

This will:

* Build all microservices
* Install shared modules
* Resolve dependencies

### ⚙️ Running the Project

#### Recommended Startup Order

Start the services in the following order:

```text
1. Config Server
2. Discovery Service
3. API Gateway
4. Connector Service
5. YouTube Connector Service
6. Sentiment Service
```

##### ▶️ Start Config Server

```bash
cd config-server
mvn spring-boot:run
```

##### ▶️ Start Discovery Service

```bash
cd discovery-service
mvn spring-boot:run
```

##### ▶️ Start API Gateway

```bash
cd api-gateway-service
mvn spring-boot:run
```

##### ▶️ Start Connector Service

```bash
cd connector-service
mvn spring-boot:run
```

##### ▶️ Start YouTube Connector Service

```bash
cd youtube-connector-service
mvn spring-boot:run
```

##### ▶️ Start Sentiment Service

```bash
cd sentiment-service
mvn spring-boot:run
```

### 🧪 Verify Services

#### Eureka Dashboard

```text
http://localhost:8761
```

#### Config Server

```text
http://localhost:8888
```

#### API Gateway Health Check

```text
http://localhost:8080/actuator/health
```

### 📂 Example API Request

#### Fetch Paginated YouTube Comments

##### Endpoint

```http
POST /api/connectors/v1/comments/page
```

##### Request Body

```json
{
  "source": "YOUTUBE",
  "url": "https://www.youtube.com/watch?v=example",
  "pageSize": 20
}
```

### 🛠️ Development Notes

* Configuration files are managed centrally using Spring Cloud Config Server
* Sensitive values are securely managed using HashiCorp Vault
* Shared DTOs and exceptions are located in `comment-insight-common`
* Pagination is designed as reusable platform-independent architecture
* The system is designed for future multi-platform support

---

## 🎯 Product Features & Roadmap
### ⚙️ Core Features
#### ✅ YouTube Comment Integration
* Fetch total comments
* Fetch paginated comments
* Fetch recent comments
* Support page tokens
* Convert YouTube responses into unified models

#### ✅ AI-Powered Sentiment Analysis

The AI sentiment-service analyzes comments and returns:

* Positive Summary + Count

* Negative Summary + Count

* Neutral Summary + Count

* AI-Generated Overall Summary

* AI Recommendation

* AI Video Content Understanding

### 📈 Future Roadmap

#### Planned Platform Integrations

* Reddit
* Amazon Reviews
* TikTok
* Twitter/X
* Blog comments

#### Planned AI Features

* Topic extraction
* Emotion analysis
* Trend detection
* Toxicity detection
* Spam detection
* Multi-language support
* AI-generated reports
* AI agents for autonomous analysis

---

## 📌 Project Status

🚧 **Active Development**

CommentInsight is currently under active development.

The platform already supports:

* YouTube comment integration
* Pagination support
* Centralized configuration management
<!--* Secure secret management with Vault -->
* API Gateway routing
* Service discovery
* Resilience patterns

Current development focus includes:

* AI-powered sentiment analysis
* Audience insight generation
* Multi-platform integrations
* AI-generated recommendation summaries
* Advanced analytics features

Planned future integrations include Reddit, Amazon Reviews, TikTok, and additional AI-powered analysis capabilities.

---

## 🐳 Docker & CI/CD

### Running Locally with Docker Compose

```bash
# Copy the example env and fill in your secrets
cp .env.example .env

# Build all images and start the stack
docker compose build && docker compose up
```

### GitHub Actions — Build & Push to Docker Hub

The workflow at `.github/workflows/docker-publish.yml` automatically builds and pushes all six service images on every push.

| Branch | Tags pushed to Docker Hub |
|--------|--------------------------|
| `develop` | `commentinsight/<service>:dev` |
| `main` / `master` | `commentinsight/<service>:latest`, `commentinsight/<service>:<sha>` |
| Pull Request | Build only (no push) |

#### Required GitHub Secrets

Go to **Settings → Secrets and variables → Actions** in your GitHub repository and add:

| Secret | Description |
|--------|-------------|
| `DOCKER_HUB_USERNAME` | Your Docker Hub username |
| `DOCKER_HUB_TOKEN` | Docker Hub Access Token (create at hub.docker.com → Account Settings → Security) |

#### Docker Hub Images

| Service | Image |
|---------|-------|
| Config Server | `commentinsight/config-server` |
| Discovery Service | `commentinsight/discovery-service` |
| API Gateway | `commentinsight/api-gateway-service` |
| Connector Service | `commentinsight/connector-service` |
| YouTube Connector | `commentinsight/youtube-connector-service` |
| Sentiment Service | `commentinsight/sentiment-service` |

---

## 🤝 Contribution

<!--Contributions are welcome!
Fork the repository
Create a feature branch
Commit your changes
Open a pull request -->


---

## 📄 License

> MIT License

---

## 👨‍💻 Author
```
Girma Moges Teklemariam
Software Engineer | Java | Microservices | AI Enthusiast
```
