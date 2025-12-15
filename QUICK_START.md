# Quick Start Guide - RikkeiJobs

## 🚀 Chạy Nhanh Dự Án

### 1. Backend (5 phút)

```bash
# 1. Cài đặt MySQL và tạo database
mysql -u root -p
CREATE DATABASE db_rikkeijobs;

# 2. Cấu hình database trong application-dev.yml
# Sửa username và password

# 3. Chạy backend
cd DB_RikkeiJobs
.\gradlew.bat bootRun
```

Backend chạy tại: `http://localhost:8080`

### 2. Frontend Client (3 phút)

```bash
cd nhom-5\client
npm install
npm run dev
```

Client chạy tại: `http://localhost:5173`

### 3. Frontend Admin (3 phút)

```bash
cd nhom-5\client_admin
npm install
npm run dev
```

Admin chạy tại: `http://localhost:5174`

---

## 📦 Deploy Lên Windows Server

### Cách 1: Deploy Tự Động (GitHub Actions)

1. **Cấu hình Secrets trên GitHub** (xem `GITHUB_ACTIONS_SETUP.md`)
2. **Push code lên branch `main`**
3. **Workflow tự động chạy và deploy**

### Cách 2: Deploy Thủ Công

```powershell
# 1. Build tất cả
cd DB_RikkeiJobs
.\gradlew.bat build

cd ..\nhom-5\client
npm run build

cd ..\client_admin
npm run build

# 2. Deploy
cd ..\..\scripts
.\deploy-all.ps1 `
    -BackendJar "..\DB_RikkeiJobs\build\libs\DB_RikkeiJobs-0.0.1-SNAPSHOT.jar" `
    -ClientDist "..\nhom-5\client\dist" `
    -AdminDist "..\nhom-5\client_admin\dist"
```

---

## 🔧 Cấu Hình GitHub Actions

### Bước 1: Thêm Secrets

GitHub Repository → Settings → Secrets and variables → Actions

Thêm:
- `WINDOWS_SERVER_HOST`
- `WINDOWS_SERVER_USERNAME`
- `WINDOWS_SERVER_PASSWORD`
- `WINDOWS_SERVER_PORT` (5985)

### Bước 2: Cấu Hình WinRM trên Server

```powershell
Enable-PSRemoting -Force
Set-Item WSMan:\localhost\Service\Auth\Basic -Value $true
New-NetFirewallRule -DisplayName "WinRM HTTP" -Direction Inbound -LocalPort 5985 -Protocol TCP -Action Allow
```

### Bước 3: Push Code

```bash
git add .
git commit -m "Setup CI/CD"
git push origin main
```

Workflow tự động chạy!

---

## 📚 Tài Liệu Chi Tiết

- **README.md** - Tổng quan dự án
- **DEPLOYMENT.md** - Hướng dẫn deployment chi tiết
- **GITHUB_ACTIONS_SETUP.md** - Cấu hình CI/CD

---

## ⚠️ Lưu Ý Quan Trọng

1. **Không commit secrets** vào Git
2. **Test local** trước khi deploy
3. **Backup database** trước khi deploy production
4. **Kiểm tra logs** sau khi deploy

---

## 🆘 Troubleshooting Nhanh

### Backend không chạy?
- Kiểm tra Java đã cài: `java -version`
- Kiểm tra port 8080: `netstat -ano | findstr :8080`
- Xem logs trong console

### Frontend không load?
- Kiểm tra backend đã chạy
- Kiểm tra CORS trong `application-dev.yml`
- Xem console browser (F12)

### Deploy thất bại?
- Kiểm tra WinRM đã enable
- Kiểm tra secrets trên GitHub
- Xem logs trong GitHub Actions

---

## 📞 Cần Giúp Đỡ?

Xem file `DEPLOYMENT.md` để biết chi tiết hơn!

