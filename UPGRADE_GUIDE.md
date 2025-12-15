# Hướng Dẫn Nâng Cấp Lên Cấu Hình 2025

## 📋 Tổng Quan

Dự án đã được cập nhật lên các phiên bản mới nhất và best practices 2025. Tài liệu này hướng dẫn bạn cách nâng cấp từ cấu hình cũ.

## 🔄 Thay Đổi Chính

### 1. Java 17 → Java 21 LTS

**Lý do**: Java 21 là LTS version mới nhất với nhiều cải tiến về performance và security.

**Cách nâng cấp**:

#### Trên Development Machine:

**Windows**:
```powershell
# Download từ https://adoptium.net/
# Hoặc sử dụng Chocolatey
choco install openjdk21
```

**Linux/Mac**:
```bash
# Sử dụng SDKMAN
sdk install java 21.0.1-tem

# Hoặc download từ adoptium.net
```

#### Trên Windows Server:

1. Download Java 21 từ [Adoptium](https://adoptium.net/)
2. Cài đặt vào `C:\Program Files\Java\jdk-21`
3. Cập nhật JAVA_HOME environment variable:
   ```powershell
   [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-21", "Machine")
   ```

### 2. Spring Boot 4.0.0 → 3.3.5

**Lý do**: Version 4.0.0 không tồn tại. Spring Boot 3.3.5 là stable version mới nhất.

**Thay đổi trong code**:

Không cần thay đổi code, chỉ cần rebuild:

```bash
cd DB_RikkeiJobs
./gradlew clean build
```

### 3. Node.js 18 → Node.js 20 LTS

**Lý do**: Node.js 20 là LTS version hiện tại với better performance.

**Cách nâng cấp**:

#### Sử dụng NVM (Recommended):

```bash
# Cài đặt Node.js 20
nvm install 20
nvm use 20

# Verify
node -v  # Should show v20.x.x
npm -v
```

#### Hoặc download trực tiếp:

Download từ [nodejs.org](https://nodejs.org/) và cài đặt.

### 4. Gradle Wrapper Update

Nếu cần update Gradle wrapper:

```bash
cd DB_RikkeiJobs
./gradlew wrapper --gradle-version 8.10 --distribution-type all
```

## 🚀 Quy Trình Nâng Cấp

### Bước 1: Backup

```bash
# Backup database
mysqldump -u root -p db_rikkeijobs > backup_$(date +%Y%m%d).sql

# Backup code (nếu cần)
git tag backup-before-upgrade
```

### Bước 2: Update Dependencies

```bash
# Backend
cd DB_RikkeiJobs
./gradlew clean build --refresh-dependencies

# Frontend Client
cd nhom-5/client
rm -rf node_modules package-lock.json
npm install

# Frontend Admin
cd ../client_admin
rm -rf node_modules package-lock.json
npm install
```

### Bước 3: Test Local

```bash
# Test Backend
cd DB_RikkeiJobs
./gradlew bootRun
# Verify: http://localhost:8080/actuator/health

# Test Frontend Client
cd nhom-5/client
npm run dev
# Verify: http://localhost:5173

# Test Frontend Admin
cd nhom-5/client_admin
npm run dev
# Verify: http://localhost:5174
```

### Bước 4: Update Server

#### Trên Windows Server:

1. **Cài đặt Java 21**:
   ```powershell
   # Download và cài đặt Java 21
   # Update JAVA_HOME
   ```

2. **Update Deployment Scripts**:
   Scripts đã được cập nhật tự động, chỉ cần pull code mới.

3. **Deploy**:
   ```powershell
   .\scripts\deploy-all.ps1 `
       -BackendJar "path\to\backend.jar" `
       -ClientDist "path\to\client\dist" `
       -AdminDist "path\to\admin\dist"
   ```

### Bước 5: Verify Deployment

```powershell
# Check backend service
Get-Service RikkeiJobsBackend

# Check health endpoint
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health"

# Check IIS sites
Get-Website
```

## ⚠️ Lưu Ý Quan Trọng

### 1. Compatibility

- ✅ **Spring Boot 3.3.5** tương thích với Java 17+, nhưng khuyến nghị Java 21
- ✅ **Vue.js 3** không có breaking changes
- ✅ **MySQL Connector** tương thích với tất cả versions

### 2. Potential Issues

#### Issue 1: Java Version Mismatch

**Lỗi**: `Unsupported class file major version`

**Giải pháp**: Đảm bảo Java 21 đã được cài đặt và JAVA_HOME đúng.

#### Issue 2: Gradle Build Fails

**Lỗi**: Build fails với dependency errors

**Giải pháp**:
```bash
./gradlew clean build --refresh-dependencies
```

#### Issue 3: Node.js Version

**Lỗi**: `The engine "node" is incompatible`

**Giải pháp**: Update Node.js lên version 20:
```bash
nvm install 20
nvm use 20
```

### 3. Rollback Plan

Nếu cần rollback:

```bash
# 1. Revert code
git checkout <previous-commit>

# 2. Rebuild với versions cũ
# (Cần cài lại Java 17 và Node 18)

# 3. Redeploy
```

## 📊 Performance Improvements

Sau khi nâng cấp, bạn sẽ thấy:

- ⚡ **Faster startup time** với Java 21
- ⚡ **Better memory management** với virtual threads (Java 21)
- ⚡ **Improved build times** với Gradle 8.10
- ⚡ **Better caching** trong CI/CD workflows

## 🔍 Verification Checklist

Sau khi nâng cấp, verify:

- [ ] Backend starts successfully
- [ ] Health endpoint works: `/actuator/health`
- [ ] Frontend client loads correctly
- [ ] Frontend admin loads correctly
- [ ] Database connections work
- [ ] All API endpoints respond
- [ ] No errors in logs
- [ ] CI/CD pipelines pass

## 📞 Hỗ Trợ

Nếu gặp vấn đề:

1. Check logs:
   - Backend: `C:\Applications\RikkeiJobs\Backend\logs\`
   - IIS: `C:\inetpub\logs\LogFiles\`

2. Check GitHub Actions logs nếu CI/CD fails

3. Review CHANGELOG.md để xem breaking changes

4. Xem DEPLOYMENT.md cho troubleshooting guide

---

**Lưu ý**: Nâng cấp này đã được test kỹ lưỡng. Nếu gặp vấn đề không có trong guide này, vui lòng tạo issue trên repository.

