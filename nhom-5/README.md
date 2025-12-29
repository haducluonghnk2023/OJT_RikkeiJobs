# Nhóm 5 (Frontend)

Thư mục `nhom-5/` gồm 2 ứng dụng Vue:

- `client/`: web cho ứng viên/doanh nghiệp
- `client_admin/`: web quản trị

## Yêu cầu

- Node.js 18+

## Cấu hình môi trường

Hai app dùng biến môi trường Vite:

- `VITE_API_BASE_URL` (mặc định: `http://localhost:8080`)

Tạo file `.env` từ file mẫu `env.example`:

```bash
cd nhom-5/client
copy env.example .env

cd ../client_admin
copy env.example .env
```

## Chạy local

### Client

```bash
cd nhom-5/client
npm install
npm run dev
```

### Admin

```bash
cd nhom-5/client_admin
npm install
npm run dev
```


