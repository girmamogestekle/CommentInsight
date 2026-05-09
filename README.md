[![Java](https://img.shields.io/badge/Java-17-blue)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-Enabled-brightgreen)](https://spring.io/projects/spring-ai)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/girmamogestekle/Post-Comments-App/blob/master/LICENSE)
<!-- [![Swagger UI](https://img.shields.io/badge/Docs-Swagger_UI-blue)](https://girmamogestekle.github.io/Post-Comments-App/)
[![GitHub Action](https://img.shields.io/github/actions/workflow/status/girmamogestekle/Post-Comments-App/workflow.yml?branch=main&label=Build)](https://github.com/girmamogestekle/Post-Comments-App/actions)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=postEntity-comments-app&metric=alert_status)](https://sonarcloud.io/project/overview?id=postEntity-comments-app)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=postEntity-comments-app&metric=coverage)](https://sonarcloud.io/summary/new_code?id=postEntity-comments-app)
[![Docker](https://img.shields.io/badge/Docker-Ready-informational)](https://hub.docker.com/r/gmtekle/postEntity-comments-app) -->

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

## 🎯 Key Features
##### 📥 Automatic Comment Extraction
> Fetch comments directly from YouTube using API
<!-- ##### 🔍 Sentiment Analysis
Classify comments as Positive, Negative, or Neutral
##### 🧠 AI-Powered Insight Generation
Generate natural language summaries from user comments
##### 🔗 RESTful APIs
Analyze comments by simply providing a video URL
##### 🏗️ Microservices Architecture
Modular and scalable system design
##### 🔄 Extensible Platform
Easily add support for other platforms (Reddit, Amazon, blogs, etc.) -->

---

## 🏛️ Architecture
```
User Input (YouTube URL)
        ↓
+---------------------------+
|   API Gateway             |
+-------------+-------------+
```

---

## 🔄 System Flow
1. User provides YouTube video URL
2. System fetches comments via API
<!-- 3. Comments are cleaned and processed
4. Sentiment analysis is applied
5. AI generates a final conclusion
6. Results are returned via API or UI  -->

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

## ⚙️ Getting Started
##### Prerequisites
> Java 17+ ▪ Maven ▪ Docker (optional)
##### 🔧 Clone Repository
```
git clone https://github.com/your-username/comment-insight.git
cd comment-insight
```
##### ▶️ Run Locally
```
mvn clean install
mvn spring-boot:run
```
##### 🐳 Run with Docker
```
docker-compose up --build
```
##### 📡 API Example
###### Analyze YouTube Comments
```
POST /api/analyze
```
###### Request Body
```
{
  "videoUrl": "https://www.youtube.com/watch?v=example"
}
```
###### 🧪 Example Output
```
{
  "positive": 65,
  "negative": 20,
  "neutral": 15,
  "conclusion": "Most viewers appreciated the content quality, while some expressed concerns about audio clarity and pacing."
}
```

---

## 🔮 Future Enhancements

> 🌍 **Multi-platform Support** — Reddit, Amazon, blogs, forums  
> 🧩 **Plugin System** — Connector-based architecture  
> 🔍 **AI Analysis** — Aspect-based sentiment (price, quality, delivery)  
> 📊 **Analytics** — Advanced dashboard  
> 🤖 **AI Model** — Custom training  
> ☁️ **Deployment** — AWS / Kubernetes


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
