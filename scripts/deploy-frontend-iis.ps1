# PowerShell script to deploy Vue.js frontend to IIS
# Usage: .\deploy-frontend-iis.ps1 -SourcePath "path\to\dist" -SiteName "RikkeiJobsClient" -Port 80

param(
    [Parameter(Mandatory=$true)]
    [string]$SourcePath,
    
    [Parameter(Mandatory=$false)]
    [string]$SiteName = "RikkeiJobsClient",
    
    [Parameter(Mandatory=$false)]
    [string]$AppPoolName = "RikkeiJobsClientAppPool",
    
    [Parameter(Mandatory=$false)]
    [int]$Port = 80,
    
    [Parameter(Mandatory=$false)]
    [string]$PhysicalPath = "C:\inetpub\wwwroot\$SiteName"
)

# Check if running as Administrator
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
if (-not $isAdmin) {
    Write-Error "This script must be run as Administrator"
    exit 1
}

# Import WebAdministration module
Import-Module WebAdministration -ErrorAction Stop

Write-Host "Deploying frontend to IIS..." -ForegroundColor Cyan

# Create physical path
Write-Host "Creating physical path: $PhysicalPath"
New-Item -ItemType Directory -Force -Path $PhysicalPath | Out-Null

# Copy files
Write-Host "Copying files from $SourcePath to $PhysicalPath..."
Copy-Item -Path "$SourcePath\*" -Destination $PhysicalPath -Recurse -Force

# Create web.config for SPA routing
$webConfig = @"
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <system.webServer>
    <rewrite>
      <rules>
        <rule name="SPA Routes" stopProcessing="true">
          <match url=".*" />
          <conditions logicalGrouping="MatchAll">
            <add input="{REQUEST_FILENAME}" matchType="IsFile" negate="true" />
            <add input="{REQUEST_FILENAME}" matchType="IsDirectory" negate="true" />
          </conditions>
          <action type="Rewrite" url="/index.html" />
        </rule>
      </rules>
    </rewrite>
    <staticContent>
      <mimeMap fileExtension=".json" mimeType="application/json" />
      <mimeMap fileExtension=".woff" mimeType="application/font-woff" />
      <mimeMap fileExtension=".woff2" mimeType="application/font-woff2" />
    </staticContent>
    <httpProtocol>
      <customHeaders>
        <add name="X-Content-Type-Options" value="nosniff" />
        <add name="X-Frame-Options" value="SAMEORIGIN" />
        <add name="X-XSS-Protection" value="1; mode=block" />
      </customHeaders>
    </httpProtocol>
    <caching enabled="true" enableKernelCache="true">
      <profiles>
        <add extension=".html" policy="DisableCache" />
        <add extension=".js" policy="CacheUntilChange" />
        <add extension=".css" policy="CacheUntilChange" />
        <add extension=".png" policy="CacheUntilChange" />
        <add extension=".jpg" policy="CacheUntilChange" />
        <add extension=".svg" policy="CacheUntilChange" />
      </profiles>
    </caching>
  </system.webServer>
</configuration>
"@
$webConfig | Out-File -FilePath "$PhysicalPath\web.config" -Encoding UTF8

# Check if Application Pool exists
$appPool = Get-IISAppPool -Name $AppPoolName -ErrorAction SilentlyContinue

if (-not $appPool) {
    Write-Host "Creating Application Pool: $AppPoolName"
    New-WebAppPool -Name $AppPoolName
    Set-ItemProperty -Path "IIS:\AppPools\$AppPoolName" -Name managedRuntimeVersion -Value ""
    Set-ItemProperty -Path "IIS:\AppPools\$AppPoolName" -Name enable32BitAppOnWin64 -Value $false
}

# Configure Application Pool
Write-Host "Configuring Application Pool..."
Set-ItemProperty -Path "IIS:\AppPools\$AppPoolName" -Name processModel.idleTimeout -Value ([TimeSpan]::FromMinutes(0))
Set-ItemProperty -Path "IIS:\AppPools\$AppPoolName" -Name recycling.periodicRestart.time -Value ([TimeSpan]::FromHours(0))

# Check if Website exists
$website = Get-Website -Name $SiteName -ErrorAction SilentlyContinue

if ($website) {
    Write-Host "Website already exists. Updating..."
    Set-ItemProperty -Path "IIS:\Sites\$SiteName" -Name physicalPath -Value $PhysicalPath
    Set-ItemProperty -Path "IIS:\Sites\$SiteName" -Name applicationPool -Value $AppPoolName
} else {
    Write-Host "Creating Website: $SiteName"
    New-Website -Name $SiteName -Port $Port -PhysicalPath $PhysicalPath -ApplicationPool $AppPoolName
}

# Start Application Pool
Write-Host "Starting Application Pool..."
Start-WebAppPool -Name $AppPoolName

# Start Website
Write-Host "Starting Website..."
Start-Website -Name $SiteName

# Verify deployment
Start-Sleep -Seconds 2
$siteStatus = (Get-Website -Name $SiteName).State
$poolStatus = (Get-WebAppPoolState -Name $AppPoolName).Value

Write-Host "`nDeployment Summary:" -ForegroundColor Green
Write-Host "  Website Name: $SiteName"
Write-Host "  Website Status: $siteStatus"
Write-Host "  App Pool Status: $poolStatus"
Write-Host "  Physical Path: $PhysicalPath"
Write-Host "  Port: $Port"
Write-Host "`nWebsite URL: http://localhost:$Port" -ForegroundColor Cyan

Write-Host "`nDeployment completed successfully!" -ForegroundColor Green

