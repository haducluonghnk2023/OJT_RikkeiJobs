# RikkeiJobs - Hệ Thống Tuyển Dụng

Dự án OJT - Hệ thống quản lý tuyển dụng và ứng viên cho Rikkei Academy.

## 📋 Mục Lục

- [Tổng Quan](#tổng-quan)
- [Kiến Trúc Hệ Thống](#kiến-trúc-hệ-thống)
- [Công Nghệ Sử Dụng](#công-nghệ-sử-dụng)
- [Cài Đặt và Chạy Dự Án](#cài-đặt-và-chạy-dự-án)
- [CI/CD và Deployment](#cicd-và-deployment)
- [Cấu Trúc Dự Án](#cấu-trúc-dự-án)
- [API Documentation](#api-documentation)

## 🎯 Tổng Quan

RikkeiJobs là một hệ thống quản lý tuyển dụng hoàn chỉnh bao gồm:
- **Backend API**: Spring Boot RESTful API
- **Frontend Client**: Ứng dụng web cho ứng viên và doanh nghiệp
- **Frontend Admin**: Ứng dụng quản trị hệ thống

## 🏗️ Kiến Trúc Hệ Thống

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Frontend       │     │  Frontend       │     │  Backend API    │
│  Client         │────▶│  Admin          │────▶│  Spring Boot    │
│  (Vue.js)       │     │  (Vue.js)       │     │  (Java 17)      │
└─────────────────┘     └─────────────────┘     └────────┬────────┘
                                                           │
                                                           ▼
                                                  ┌─────────────────┐
                                                  │  MySQL Database │
                                                  └─────────────────┘
```

## 🛠️ Công Nghệ Sử Dụng

### Backend
- **Framework**: Spring Boot 4.0.0
- **Java**: JDK 17
- **Build Tool**: Gradle
- **Database**: MySQL 8.0+
- **Security**: Spring Security

### Frontend
- **Framework**: Vue.js 3
- **Build Tool**: Vite
- **UI Library**: Ant Design Vue, Vuetify
- **State Management**: Vuex
- **Routing**: Vue Router

## 🚀 Cài Đặt và Chạy Dự Án

### Yêu Cầu

- Java JDK 17+
- Node.js 18+
- MySQL 8.0+
- Gradle 8.5+ (hoặc sử dụng Gradle Wrapper)

### 1. Clone Repository

```bash
git clone <repository-url>
cd OJT
```

### 2. Cấu Hình Database

Tạo database và cấu hình trong `DB_RikkeiJobs/src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_rikkeijobs
    username: root
    password: your_password
```

### 3. Chạy Backend

```bash
cd DB_RikkeiJobs
./gradlew bootRun
# Hoặc trên Windows
.\gradlew.bat bootRun
```

Backend sẽ chạy tại: `http://localhost:8080`

### 4. Chạy Frontend Client

```bash
cd nhom-5/client
npm install
npm run dev
```

Client sẽ chạy tại: `http://localhost:5173`

### 5. Chạy Frontend Admin

```bash
cd nhom-5/client_admin
npm install
npm run dev
```

Admin sẽ chạy tại: `http://localhost:5174`

## 🔄 CI/CD và Deployment

Dự án đã được cấu hình với GitHub Actions để tự động build và deploy.

### GitHub Actions Workflows

1. **CI/CD Pipeline** (`.github/workflows/ci-cd.yml`)
   - Build và test tự động khi có push/PR
   - Tạo artifacts cho deployment

2. **Deploy to Windows Server** (`.github/workflows/deploy-windows.yml`)
   - Deploy tự động lên Windows Server/IIS
   - Chạy khi push vào branch `main`

### Deployment Scripts

Các script PowerShell để deploy thủ công:

- `scripts/deploy-backend.ps1` - Deploy backend như Windows Service
- `scripts/deploy-frontend-iis.ps1` - Deploy frontend lên IIS
- `scripts/deploy-all.ps1` - Deploy tất cả components

### Hướng Dẫn Deployment Chi Tiết

Xem file [DEPLOYMENT.md](./DEPLOYMENT.md) để biết hướng dẫn chi tiết về:
- Cấu hình môi trường production
- Deploy lên Windows Server/IIS
- Cấu hình GitHub Actions secrets
- Troubleshooting

## 📁 Cấu Trúc Dự Án

```
OJT/
├── DB_RikkeiJobs/              # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/          # Source code
│   │   │   └── resources/     # Config files
│   │   └── test/              # Tests
│   ├── build.gradle
│   └── Dockerfile
│
├── nhom-5/
│   ├── client/                 # Frontend Client
│   │   ├── src/
│   │   │   ├── apis/          # API calls
│   │   │   ├── components/    # Vue components
│   │   │   ├── views/         # Pages
│   │   │   ├── router/        # Routes
│   │   │   └── store/         # Vuex store
│   │   ├── package.json
│   │   └── Dockerfile
│   │
│   └── client_admin/          # Frontend Admin
│       ├── src/
│       ├── package.json
│       └── Dockerfile
│
├── scripts/                    # Deployment scripts
│   ├── deploy-backend.ps1
│   ├── deploy-frontend-iis.ps1
│   └── deploy-all.ps1
│
├── .github/
│   └── workflows/             # GitHub Actions
│       ├── ci-cd.yml
│       └── deploy-windows.yml
│
├── DEPLOYMENT.md              # Hướng dẫn deployment
└── README.md                  # File này
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/v1
```

### Endpoints Chính

#### Authentication
- `POST /auth/register` - Đăng ký
- `POST /auth/login` - Đăng nhập

#### Users
- `GET /users` - Lấy danh sách users
- `GET /users/{id}` - Lấy thông tin user
- `PUT /users/{id}` - Cập nhật user

#### Jobs
- `GET /jobs` - Lấy danh sách việc làm
- `GET /jobs/{id}` - Chi tiết việc làm
- `POST /jobs` - Tạo việc làm mới

#### Enterprises
- `GET /enterprises` - Danh sách doanh nghiệp
- `GET /enterprises/{id}` - Chi tiết doanh nghiệp

#### CVs
- `GET /cvs` - Danh sách CV
- `POST /cvs` - Tạo CV mới
- `GET /cvs/{id}` - Chi tiết CV

Xem thêm trong code hoặc Swagger UI (nếu có cấu hình).

## 🔧 Development

### Backend Development

```bash
cd DB_RikkeiJobs
./gradlew bootRun
```

### Frontend Development

```bash
# Client
cd nhom-5/client
npm run dev

# Admin
cd nhom-5/client_admin
npm run dev
```

### Build Production

```bash
# Backend
cd DB_RikkeiJobs
./gradlew clean build

# Frontend
cd nhom-5/client
npm run build

cd nhom-5/client_admin
npm run build
```

## 🐳 Docker

Dự án đã có sẵn Dockerfile cho từng component:

```bash
# Build và chạy backend
cd DB_RikkeiJobs
docker build -t rikkeijobs-backend .
docker run -p 8080:8080 rikkeijobs-backend

# Build và chạy frontend client
cd nhom-5/client
docker build -t rikkeijobs-client .
docker run -p 80:80 rikkeijobs-client
```

## 📝 Environment Variables

Tạo file `.env` từ `.env.example` và cấu hình:

```bash
cp .env.example .env
```

Cập nhật các giá trị trong `.env` theo môi trường của bạn.

## 🧪 Testing

```bash
# Backend tests
cd DB_RikkeiJobs
./gradlew test

# Frontend tests (nếu có)
cd nhom-5/client
npm test
```

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is for educational purposes.

## 👥 Team

Nhóm 5 - OJT Rikkei Academy

## 📞 Liên Hệ

Nếu có vấn đề hoặc câu hỏi, vui lòng tạo issue trên repository.

---

**Lưu ý**: Đây là dự án học tập, không sử dụng cho mục đích thương mại.

