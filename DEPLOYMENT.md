# Hướng Dẫn Deploy Dự Án RikkeiJobs

## Mục Lục
1. [Yêu Cầu Hệ Thống](#yêu-cầu-hệ-thống)
2. [Chuẩn Bị Môi Trường](#chuẩn-bị-môi-trường)
3. [Cấu Hình GitHub Actions](#cấu-hình-github-actions)
4. [Deploy Thủ Công](#deploy-thủ-công)
5. [Deploy Tự Động với CI/CD](#deploy-tự-động-với-cicd)
6. [Kiểm Tra và Troubleshooting](#kiểm-tra-và-troubleshooting)

---

## Yêu Cầu Hệ Thống

### Windows Server
- Windows Server 2016 trở lên
- IIS 10.0 trở lên
- PowerShell 5.1 trở lên
- .NET Framework 4.7.2 trở lên (cho IIS URL Rewrite)

### Phần Mềm Cần Cài Đặt

1. **Java JDK 17**
   - Download từ: https://adoptium.net/
   - Cài đặt và cấu hình JAVA_HOME

2. **Node.js 18+** (chỉ cần cho build, không cần trên server)
   - Download từ: https://nodejs.org/

3. **NSSM (Non-Sucking Service Manager)** - Để chạy Java app như Windows Service
   ```powershell
   # Cài đặt via Chocolatey
   choco install nssm
   
   # Hoặc download từ: https://nssm.cc/download
   ```

4. **IIS URL Rewrite Module**
   - Download từ: https://www.iis.net/downloads/microsoft/url-rewrite
   - Cần thiết cho SPA routing

5. **MySQL Server**
   - Cài đặt MySQL Server 8.0+
   - Tạo database và user

---

## Chuẩn Bị Môi Trường

### 1. Cấu Hình Database

Tạo database và user:

```sql
CREATE DATABASE db_rikkeijobs CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'rikkeijobs_user'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON db_rikkeijobs.* TO 'rikkeijobs_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Tạo Thư Mục Deployment

```powershell
# Tạo thư mục cho backend
New-Item -ItemType Directory -Force -Path "C:\Applications\RikkeiJobs\Backend"
New-Item -ItemType Directory -Force -Path "C:\Applications\RikkeiJobs\Backend\logs"

# Thư mục frontend sẽ được tạo tự động bởi script
```

### 3. Cấu Hình Environment Variables

Tạo file `C:\Applications\RikkeiJobs\Backend\application.properties`:

```properties
spring.profiles.active=prod
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/db_rikkeijobs?useSSL=true&serverTimezone=UTC
spring.datasource.username=rikkeijobs_user
spring.datasource.password=your_secure_password

# CORS Configuration
cors.allowed-origins=http://localhost,http://localhost:8081

# Logging
logging.level.com.data.db_rikkeijobs=INFO
logging.file.path=C:\Applications\RikkeiJobs\Backend\logs
```

---

## Cấu Hình GitHub Actions

### 1. Thêm Secrets vào GitHub Repository

Vào **Settings > Secrets and variables > Actions**, thêm các secrets sau:

```
WINDOWS_SERVER_HOST=your-server-ip-or-domain
WINDOWS_SERVER_USERNAME=administrator
WINDOWS_SERVER_PASSWORD=your-password
WINDOWS_SERVER_PORT=5985  # WinRM port
WINDOWS_SERVER_SSH_PORT=22  # Nếu dùng SSH thay vì WinRM
```

### 2. Cấu Hình WinRM trên Windows Server

```powershell
# Enable WinRM
Enable-PSRemoting -Force

# Cấu hình WinRM để chấp nhận kết nối từ xa
Set-Item WSMan:\localhost\Service\Auth\Basic -Value $true
Set-Item WSMan:\localhost\Service\AllowUnencrypted -Value $true

# Mở firewall
New-NetFirewallRule -DisplayName "WinRM HTTP" -Direction Inbound -LocalPort 5985 -Protocol TCP -Action Allow
```

### 3. Cấu Hình SSH (Tùy chọn - nếu muốn dùng SSH thay vì WinRM)

Cài đặt OpenSSH Server trên Windows:

```powershell
# Cài đặt OpenSSH Server
Add-WindowsCapability -Online -Name OpenSSH.Server~~~~0.0.1.0

# Khởi động service
Start-Service sshd
Set-Service -Name sshd -StartupType 'Automatic'

# Mở firewall
New-NetFirewallRule -Name sshd -DisplayName 'OpenSSH Server (sshd)' -Enabled True -Direction Inbound -Protocol TCP -Action Allow -LocalPort 22
```

---

## Deploy Thủ Công

### Bước 1: Build Backend

```powershell
cd DB_RikkeiJobs
.\gradlew.bat clean build -x test
```

File JAR sẽ được tạo tại: `DB_RikkeiJobs\build\libs\DB_RikkeiJobs-0.0.1-SNAPSHOT.jar`

### Bước 2: Build Frontend Client

```powershell
cd nhom-5\client
npm ci
npm run build
```

Thư mục `dist` sẽ chứa các file build.

### Bước 3: Build Frontend Admin

```powershell
cd nhom-5\client_admin
npm ci
npm run build
```

### Bước 4: Deploy

Sử dụng script deployment:

```powershell
# Deploy tất cả
.\scripts\deploy-all.ps1 `
    -BackendJar "DB_RikkeiJobs\build\libs\DB_RikkeiJobs-0.0.1-SNAPSHOT.jar" `
    -ClientDist "nhom-5\client\dist" `
    -AdminDist "nhom-5\client_admin\dist"

# Hoặc deploy từng phần
.\scripts\deploy-backend.ps1 -JarPath "path\to\backend.jar"
.\scripts\deploy-frontend-iis.ps1 -SourcePath "path\to\client\dist" -SiteName "RikkeiJobsClient" -Port 80
.\scripts\deploy-frontend-iis.ps1 -SourcePath "path\to\admin\dist" -SiteName "RikkeiJobsAdmin" -Port 8081
```

---

## Deploy Tự Động với CI/CD

### Workflow 1: CI/CD Pipeline (`.github/workflows/ci-cd.yml`)

Workflow này sẽ:
- Build backend khi có push/PR
- Build frontend client và admin
- Chạy tests
- Tạo artifacts

### Workflow 2: Deploy to Windows Server (`.github/workflows/deploy-windows.yml`)

Workflow này sẽ:
- Build tất cả components
- Deploy lên Windows Server qua WinRM hoặc SSH
- Chạy tự động khi push vào branch `main`

### Kích Hoạt Deployment

1. **Tự động**: Push code vào branch `main` sẽ tự động trigger deployment
2. **Thủ công**: Vào **Actions > Deploy to Windows Server > Run workflow**

---

## Kiểm Tra và Troubleshooting

### Kiểm Tra Backend Service

```powershell
# Kiểm tra service status
Get-Service RikkeiJobsBackend

# Xem logs
Get-Content C:\Applications\RikkeiJobs\Backend\logs\stdout.log -Tail 50
Get-Content C:\Applications\RikkeiJobs\Backend\logs\stderr.log -Tail 50

# Test API
Invoke-WebRequest -Uri "http://localhost:8080/api/v1/health" -Method GET
```

### Kiểm Tra IIS Sites

```powershell
# Liệt kê websites
Get-Website

# Kiểm tra site status
Get-Website -Name "RikkeiJobsClient"
Get-Website -Name "RikkeiJobsAdmin"

# Xem application pool
Get-WebAppPoolState -Name "RikkeiJobsClientAppPool"
```

### Troubleshooting

#### Backend không start

1. Kiểm tra Java đã cài đặt:
   ```powershell
   java -version
   ```

2. Kiểm tra port 8080 có bị chiếm:
   ```powershell
   netstat -ano | findstr :8080
   ```

3. Kiểm tra logs:
   ```powershell
   Get-Content C:\Applications\RikkeiJobs\Backend\logs\stderr.log
   ```

#### Frontend không load

1. Kiểm tra IIS đã cài URL Rewrite Module
2. Kiểm tra web.config có tồn tại
3. Kiểm tra permissions trên thư mục:
   ```powershell
   icacls "C:\inetpub\wwwroot\RikkeiJobsClient" /grant "IIS_IUSRS:(OI)(CI)F"
   ```

#### CORS Errors

Cập nhật `application.properties` với đúng origins:
```properties
cors.allowed-origins=http://localhost,http://localhost:8081,http://your-domain.com
```

### Restart Services

```powershell
# Restart backend
Restart-Service RikkeiJobsBackend

# Restart IIS
iisreset

# Hoặc restart từng site
Restart-WebAppPool -Name "RikkeiJobsClientAppPool"
Restart-WebAppPool -Name "RikkeiJobsAdminAppPool"
```

---

## Cấu Hình Nâng Cao

### Reverse Proxy với IIS (Nếu cần)

Nếu muốn chạy backend qua IIS thay vì port riêng:

1. Cài đặt **Application Request Routing (ARR)** và **URL Rewrite**
2. Tạo reverse proxy rule trong IIS

### SSL/HTTPS

1. Cài đặt SSL certificate
2. Cấu hình HTTPS binding trong IIS
3. Redirect HTTP sang HTTPS

### Monitoring

Có thể tích hợp với:
- **Application Insights** cho monitoring
- **Log Analytics** cho log aggregation
- **Performance Monitor** cho Windows Server metrics

---

## Lưu Ý Bảo Mật

1. **Không commit secrets vào Git**
2. **Sử dụng environment variables** cho sensitive data
3. **Cấu hình firewall** chỉ mở các port cần thiết
4. **Sử dụng HTTPS** trong production
5. **Regular updates** cho OS và software
6. **Backup database** định kỳ

---

## Hỗ Trợ

Nếu gặp vấn đề, kiểm tra:
1. Logs trong `C:\Applications\RikkeiJobs\Backend\logs\`
2. IIS logs trong `C:\inetpub\logs\LogFiles\`
3. Windows Event Viewer
4. GitHub Actions logs

