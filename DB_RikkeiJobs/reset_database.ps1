# Script PowerShell để xóa và tạo lại database
# Chạy script này trước khi chạy ứng dụng với ddl-auto: create

$dbName = "db_rikkeijobs"
$dbUser = "root"
$dbPassword = "123456"
$dbHost = "localhost"
$dbPort = "3306"

Write-Host "Đang kết nối đến MySQL..." -ForegroundColor Yellow

# Tạo connection string
$connectionString = "mysql -h $dbHost -P $dbPort -u $dbUser -p$dbPassword"

# Xóa database nếu tồn tại
Write-Host "Đang xóa database $dbName..." -ForegroundColor Yellow
$dropDbQuery = "DROP DATABASE IF EXISTS $dbName;"
$dropDbCommand = "echo `"$dropDbQuery`" | $connectionString"

try {
    Invoke-Expression $dropDbCommand
    Write-Host "Đã xóa database $dbName" -ForegroundColor Green
} catch {
    Write-Host "Lỗi khi xóa database: $_" -ForegroundColor Red
    Write-Host "Vui lòng xóa database thủ công bằng lệnh: DROP DATABASE IF EXISTS $dbName;" -ForegroundColor Yellow
    exit 1
}

# Tạo database mới
Write-Host "Đang tạo database $dbName mới..." -ForegroundColor Yellow
$createDbQuery = "CREATE DATABASE $dbName CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
$createDbCommand = "echo `"$createDbQuery`" | $connectionString"

try {
    Invoke-Expression $createDbCommand
    Write-Host "Đã tạo database $dbName" -ForegroundColor Green
    Write-Host "Bây giờ bạn có thể chạy ứng dụng!" -ForegroundColor Green
} catch {
    Write-Host "Lỗi khi tạo database: $_" -ForegroundColor Red
    Write-Host "Vui lòng tạo database thủ công bằng lệnh: CREATE DATABASE $dbName CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;" -ForegroundColor Yellow
    exit 1
}

