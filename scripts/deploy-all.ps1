# Master deployment script for all components
# Usage: .\deploy-all.ps1 -BackendJar "path\to\backend.jar" -ClientDist "path\to\client\dist" -AdminDist "path\to\admin\dist"

param(
    [Parameter(Mandatory=$false)]
    [string]$BackendJar,
    
    [Parameter(Mandatory=$false)]
    [string]$ClientDist,
    
    [Parameter(Mandatory=$false)]
    [string]$AdminDist,
    
    [Parameter(Mandatory=$false)]
    [string]$Environment = "production"
)

$ErrorActionPreference = "Stop"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  RikkeiJobs Deployment Script" -ForegroundColor Cyan
Write-Host "  Environment: $Environment" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path

# Deploy Backend
if ($BackendJar -and (Test-Path $BackendJar)) {
    Write-Host "`n[1/3] Deploying Backend..." -ForegroundColor Yellow
    & "$scriptPath\deploy-backend.ps1" -JarPath $BackendJar -ServiceName "RikkeiJobsBackend"
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Backend deployment failed!"
        exit 1
    }
} else {
    Write-Warning "Backend JAR not provided or not found. Skipping backend deployment."
}

# Deploy Frontend Client
if ($ClientDist -and (Test-Path $ClientDist)) {
    Write-Host "`n[2/3] Deploying Frontend Client..." -ForegroundColor Yellow
    & "$scriptPath\deploy-frontend-iis.ps1" -SourcePath $ClientDist -SiteName "RikkeiJobsClient" -Port 80
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Frontend client deployment failed!"
        exit 1
    }
} else {
    Write-Warning "Frontend client dist not provided or not found. Skipping client deployment."
}

# Deploy Frontend Admin
if ($AdminDist -and (Test-Path $AdminDist)) {
    Write-Host "`n[3/3] Deploying Frontend Admin..." -ForegroundColor Yellow
    & "$scriptPath\deploy-frontend-iis.ps1" -SourcePath $AdminDist -SiteName "RikkeiJobsAdmin" -Port 8081
    if ($LASTEXITCODE -ne 0) {
        Write-Error "Frontend admin deployment failed!"
        exit 1
    }
} else {
    Write-Warning "Frontend admin dist not provided or not found. Skipping admin deployment."
}

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "  Deployment Completed Successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

Write-Host "`nAccess URLs:" -ForegroundColor Cyan
Write-Host "  Client: http://localhost" -ForegroundColor White
Write-Host "  Admin: http://localhost:8081" -ForegroundColor White
Write-Host "  Backend API: http://localhost:8080" -ForegroundColor White

