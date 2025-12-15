# Hướng Dẫn Cấu Hình GitHub Actions CI/CD

## Bước 1: Tạo Repository trên GitHub

1. Đăng nhập vào GitHub
2. Tạo repository mới hoặc sử dụng repository hiện có
3. Push code lên repository

## Bước 2: Thêm Secrets vào GitHub

### Cách thêm Secrets:

1. Vào repository trên GitHub
2. Click **Settings** (Cài đặt)
3. Trong menu bên trái, click **Secrets and variables** > **Actions**
4. Click **New repository secret**
5. Thêm các secrets sau:

### Secrets Cần Thêm:

| Secret Name | Mô Tả | Ví Dụ |
|------------|-------|-------|
| `WINDOWS_SERVER_HOST` | IP hoặc domain của Windows Server | `192.168.1.100` hoặc `server.example.com` |
| `WINDOWS_SERVER_USERNAME` | Username để kết nối | `administrator` |
| `WINDOWS_SERVER_PASSWORD` | Password để kết nối | `YourSecurePassword123!` |
| `WINDOWS_SERVER_PORT` | Port WinRM (mặc định 5985) | `5985` |
| `WINDOWS_SERVER_SSH_PORT` | Port SSH (nếu dùng SSH, mặc định 22) | `22` |

### Lưu Ý:
- **KHÔNG** commit secrets vào code
- Secrets chỉ hiển thị khi tạo, không thể xem lại sau khi lưu
- Có thể update secrets bất cứ lúc nào

## Bước 3: Cấu Hình WinRM trên Windows Server

### Trên Windows Server, mở PowerShell với quyền Administrator:

```powershell
# Enable WinRM
Enable-PSRemoting -Force

# Cấu hình WinRM
Set-Item WSMan:\localhost\Service\Auth\Basic -Value $true
Set-Item WSMan:\localhost\Service\AllowUnencrypted -Value $true

# Mở firewall cho WinRM
New-NetFirewallRule -DisplayName "WinRM HTTP" -Direction Inbound -LocalPort 5985 -Protocol TCP -Action Allow

# Kiểm tra WinRM đã chạy
Get-Service WinRM
```

### Test kết nối từ máy khác:

```powershell
# Từ máy khác, test kết nối
Test-WSMan -ComputerName <SERVER_IP> -Port 5985
```

## Bước 4: Cấu Hình SSH (Tùy chọn)

Nếu muốn dùng SSH thay vì WinRM:

```powershell
# Cài đặt OpenSSH Server
Add-WindowsCapability -Online -Name OpenSSH.Server~~~~0.0.1.0

# Khởi động service
Start-Service sshd
Set-Service -Name sshd -StartupType 'Automatic'

# Mở firewall
New-NetFirewallRule -Name sshd -DisplayName 'OpenSSH Server (sshd)' -Enabled True -Direction Inbound -Protocol TCP -Action Allow -LocalPort 22
```

## Bước 5: Kích Hoạt Workflows

### Workflow Tự Động:

Workflows sẽ tự động chạy khi:
- **Push code** vào branch `main` hoặc `develop`
- **Tạo Pull Request** vào branch `main` hoặc `develop`

### Workflow Thủ Công:

1. Vào tab **Actions** trên GitHub
2. Chọn workflow **Deploy to Windows Server**
3. Click **Run workflow**
4. Chọn branch và environment
5. Click **Run workflow**

## Bước 6: Kiểm Tra Workflow

### Xem Logs:

1. Vào tab **Actions**
2. Click vào workflow run mới nhất
3. Xem logs từng step để debug nếu có lỗi

### Các Step Trong Workflow:

1. **Checkout code** - Lấy code từ repository
2. **Set up JDK** - Cài đặt Java 17
3. **Build Backend** - Build Spring Boot application
4. **Set up Node.js** - Cài đặt Node.js
5. **Build Frontend** - Build Vue.js applications
6. **Deploy** - Deploy lên Windows Server

## Troubleshooting

### Lỗi: "Connection refused" hoặc "Cannot connect"

**Nguyên nhân**: WinRM chưa được cấu hình hoặc firewall chặn

**Giải pháp**:
```powershell
# Kiểm tra WinRM service
Get-Service WinRM

# Kiểm tra firewall rules
Get-NetFirewallRule -DisplayName "*WinRM*"

# Test kết nối
Test-WSMan -ComputerName localhost
```

### Lỗi: "Authentication failed"

**Nguyên nhân**: Username/password sai hoặc user không có quyền

**Giải pháp**:
- Kiểm tra lại secrets trên GitHub
- Đảm bảo user có quyền Administrator
- Test kết nối thủ công với credentials

### Lỗi: "Build failed"

**Nguyên nhân**: Code có lỗi hoặc dependencies thiếu

**Giải pháp**:
- Xem logs chi tiết trong GitHub Actions
- Test build local trước khi push
- Kiểm tra `build.gradle` và `package.json`

### Lỗi: "Deployment failed"

**Nguyên nhân**: Script deployment có vấn đề hoặc server không sẵn sàng

**Giải pháp**:
- Kiểm tra scripts deployment có đúng path không
- Kiểm tra server có đủ quyền không
- Xem logs trên server

## Best Practices

1. **Luôn test local trước khi push**
2. **Sử dụng branches** để test trước khi merge vào main
3. **Review code** trước khi merge
4. **Monitor deployments** sau khi deploy
5. **Backup database** trước khi deploy production
6. **Sử dụng staging environment** để test trước

## Cấu Hình Nâng Cao

### Chỉ Deploy Khi Tag Release:

Sửa file `.github/workflows/deploy-windows.yml`:

```yaml
on:
  push:
    tags:
      - 'v*'
```

### Deploy Theo Schedule:

```yaml
on:
  schedule:
    - cron: '0 2 * * *'  # 2 AM mỗi ngày
```

### Deploy Theo Environment:

Có thể tạo nhiều environments (staging, production) và deploy riêng biệt.

## Liên Kết Hữu Ích

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [WinRM Documentation](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)
- [OpenSSH for Windows](https://docs.microsoft.com/en-us/windows-server/administration/openssh/openssh_install_firstuse)

