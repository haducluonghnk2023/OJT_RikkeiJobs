# RikkeiJobs - Hệ Thống Tuyển Dụng

Dự án OJT - Hệ thống quản lý tuyển dụng và ứng viên cho Rikkei Academy.

## 📋 Mục Lục

- [Tổng Quan](#tổng-quan)
- [Kiến Trúc Hệ Thống](#kiến-trúc-hệ-thống)
- [Công Nghệ Sử Dụng](#công-nghệ-sử-dụng)
- [Cài Đặt và Chạy Dự Án](#cài-đặt-và-chạy-dự-án)
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
│  (Vue.js)       │     │  (Vue.js)       │     │  (Java 21)      │
└─────────────────┘     └─────────────────┘     └────────┬────────┘
                                                           │
                                                           ▼
                                                  ┌─────────────────┐
                                                  │  MySQL Database │
                                                  └─────────────────┘
```

## 🛠️ Công Nghệ Sử Dụng

### Backend

- **Framework**: Spring Boot 3.3.5
- **Java**: JDK 21 (theo `DB_RikkeiJobs/build.gradle`)
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

- Java JDK 21+
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

## 🔄 CI/CD

### GitHub Actions

- **CI** (`.github/workflows/ci.yml`): Chạy trên mỗi push/PR vào `main`, `develop`
  - Backend: build + test (với MySQL service)
  - Frontend Client: `npm ci && npm run build`
  - Frontend Admin: `npm ci && npm run build`

- **CD** (`.github/workflows/cd.yml`): Tự động deploy khi push lên `main`
  - Build backend JAR + frontend dist
  - Copy lên server qua SCP
  - Restart backend (systemd hoặc java -jar)

### Cấu hình Deploy tự động

1. **Tạo SSH key** (nếu chưa có):

   ```bash
   ssh-keygen -t ed25519 -C "github-deploy" -f deploy_key -N ""
   ```

   - Copy `deploy_key.pub` vào server: `~/.ssh/authorized_keys`
   - Nội dung `deploy_key` (private) → dùng làm Secret

2. **Thêm Secrets** trong GitHub: **Settings > Secrets and variables > Actions**

   | Secret                | Mô tả                                                         |
   | --------------------- | ------------------------------------------------------------- |
   | `SERVER_HOST`         | IP hoặc domain server (VD: `192.168.1.100`)                   |
   | `SERVER_USER`         | User SSH (VD: `deploy`, `root`)                               |
   | `SERVER_PORT`         | Port SSH (mặc định 22, bỏ trống nếu dùng 22)                  |
   | `DEPLOY_SSH_KEY`      | Nội dung file private key SSH                                 |
   | `DEPLOY_BACKEND_PATH` | Thư mục backend (mặc định `/opt/rikkeijobs`)                  |
   | `DEPLOY_CLIENT_PATH`  | Thư mục static client (mặc định `/var/www/rikkeijobs/client`) |
   | `DEPLOY_ADMIN_PATH`   | Thư mục static admin (mặc định `/var/www/rikkeijobs/admin`)   |

3. **Chuẩn bị server**:
   - Cài Java 21, Nginx (hoặc reverse proxy khác)
   - Tạo thư mục và cấu hình Nginx serve `client` và `admin`
   - (Tùy chọn) Tạo systemd service `rikkeijobs` để restart backend tự động

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
│   └── gradlew(.bat)
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
│   │   └── env.example          # env mẫu (Vite)
│   │
│   └── client_admin/          # Frontend Admin
│       ├── src/
│       ├── package.json
│       └── env.example          # env mẫu (Vite)
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

## 📝 Environment Variables

### Frontend (Vite)

Tạo file `.env` từ `env.example` và cấu hình:

```bash
cd nhom-5/client
copy env.example .env

cd ../client_admin
copy env.example .env
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
