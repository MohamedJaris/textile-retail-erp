# Readymade Retail ERP

Production-grade Modular Monolith ERP for readymade retail stores.

## Tech Stack
- **Backend:** Java 21, Spring Boot 3.4.2, Spring Data JPA, Spring Security, Spring Modulith
- **Database:** PostgreSQL 16 with Flyway migrations
- **Frontend:** React 18.3, TypeScript 5.7, Vite 6, Tailwind CSS 3.4, TanStack Query 5
- **Infrastructure:** Docker, multi-stage builds, AWS-ready

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+
- Node.js 20+
- Docker & Docker Compose

### Development Setup
```bash
# Start PostgreSQL
cd backend && docker-compose up postgres -d

# Run backend
cd backend && mvn spring-boot:run -pl erp-app -Dspring-boot.run.profiles=dev

# Run frontend
cd frontend && npm install && npm run dev
```

### Docker (Full Stack)
```bash
cd backend && docker-compose up --build
```

### Access
- Frontend: http://localhost:5173
- Backend API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Default login: `admin` / `admin123`

## Project Structure
```
backend/
├── erp-common/       # Shared entities, exceptions, config
├── erp-modules/
│   ├── module-auth/  # JWT auth, users, roles, permissions
│   ├── module-product/   # (Phase 2)
│   └── ...
└── erp-app/          # Spring Boot runner, Flyway migrations

frontend/             # React + TypeScript + Tailwind
```
"# textile-retail-erp" 
