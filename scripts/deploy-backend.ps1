# PowerShell script to deploy Spring Boot backend to Windows Server
# Usage: .\deploy-backend.ps1 -JarPath "path\to\backend.jar" -ServiceName "RikkeiJobsBackend"

param(
    [Parameter(Mandatory=$true)]
    [string]$JarPath,
    
    [Parameter(Mandatory=$false)]
    [string]$ServiceName = "RikkeiJobsBackend",
    
    [Parameter(Mandatory=$false)]
    [string]$InstallPath = "C:\Applications\RikkeiJobs\Backend",
    
    [Parameter(Mandatory=$false)]
    [int]$Port = 8080,
    
    [Parameter(Mandatory=$false)]
    [string]$JavaHome = "C:\Program Files\Java\jdk-21"
)

# Check if running as Administrator
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
if (-not $isAdmin) {
    Write-Error "This script must be run as Administrator"
    exit 1
}

# Create installation directory
Write-Host "Creating installation directory: $InstallPath"
New-Item -ItemType Directory -Force -Path $InstallPath | Out-Null

# Copy JAR file
Write-Host "Copying JAR file..."
Copy-Item -Path $JarPath -Destination "$InstallPath\app.jar" -Force

# Create application.properties if it doesn't exist
$configPath = "$InstallPath\application.properties"
if (-not (Test-Path $configPath)) {
    Write-Host "Creating application.properties..."
    @"
# Production configuration
# Update these values according to your environment
spring.profiles.active=prod
server.port=$Port
"@ | Out-File -FilePath $configPath -Encoding UTF8
}

# Create startup script
$startScript = @"
@echo off
cd /d "$InstallPath"
set JAVA_HOME=$JavaHome
set PATH=%JAVA_HOME%\bin;%PATH%
java -jar -Dspring.profiles.active=prod -Dspring.config.location=file:./application.properties app.jar
"@
$startScript | Out-File -FilePath "$InstallPath\start.bat" -Encoding ASCII

# Check if service exists
$service = Get-Service -Name $ServiceName -ErrorAction SilentlyContinue

if ($service) {
    Write-Host "Stopping existing service..."
    Stop-Service -Name $ServiceName -Force -ErrorAction SilentlyContinue
    Start-Sleep -Seconds 2
}

# Create Windows Service using NSSM (Non-Sucking Service Manager)
# Download NSSM if not exists
$nssmPath = "$InstallPath\nssm.exe"
if (-not (Test-Path $nssmPath)) {
    Write-Host "NSSM not found. Please install NSSM first:"
    Write-Host "Download from: https://nssm.cc/download"
    Write-Host "Or install via Chocolatey: choco install nssm"
    exit 1
}

# Install/Update service
Write-Host "Installing/Updating Windows Service..."
& $nssmPath install $ServiceName "$JavaHome\bin\java.exe" "-jar `"$InstallPath\app.jar`" -Dspring.profiles.active=prod"
& $nssmPath set $ServiceName AppDirectory $InstallPath
& $nssmPath set $ServiceName DisplayName "RikkeiJobs Backend Service"
& $nssmPath set $ServiceName Description "RikkeiJobs Backend Application Service"
& $nssmPath set $ServiceName Start SERVICE_AUTO_START
& $nssmPath set $ServiceName AppStdout "$InstallPath\logs\stdout.log"
& $nssmPath set $ServiceName AppStderr "$InstallPath\logs\stderr.log"

# Create logs directory
New-Item -ItemType Directory -Force -Path "$InstallPath\logs" | Out-Null

# Start service
Write-Host "Starting service..."
Start-Service -Name $ServiceName

# Wait for service to start
Start-Sleep -Seconds 5

# Check service status
$service = Get-Service -Name $ServiceName
if ($service.Status -eq 'Running') {
    Write-Host "Service started successfully!" -ForegroundColor Green
    Write-Host "Service Status: $($service.Status)"
    Write-Host "Service can be managed via: Get-Service $ServiceName"
} else {
    Write-Warning "Service may not have started correctly. Status: $($service.Status)"
    Write-Host "Check logs at: $InstallPath\logs\"
}

Write-Host "Deployment completed!" -ForegroundColor Green

