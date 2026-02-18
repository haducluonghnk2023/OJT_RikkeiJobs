<template>
  <div class="forbidden-container">
    <div class="forbidden-content">
      <div class="error-code">403</div>
      <h1 class="error-title">Truy cập bị từ chối</h1>
      <p class="error-message">
        Bạn không có quyền truy cập trang này. Chỉ có quản trị viên mới có thể truy cập.
      </p>
      <div class="error-actions">
        <a-button type="primary" @click="goToLogin">Đăng nhập lại</a-button>
        <a-button @click="goBack">Quay lại</a-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const goToLogin = () => {
  localStorage.removeItem("token");
  // Cross-port: send user to client login (5173)
  window.location.href = "http://localhost:5173/login";
};

const goBack = () => {
  router.go(-1);
};
</script>

<style scoped>
.forbidden-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.forbidden-content {
  text-align: center;
  padding: 40px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
  max-width: 500px;
}

.error-code {
  font-size: 120px;
  font-weight: bold;
  color: #ff4d4f;
  line-height: 1;
  margin-bottom: 20px;
}

.error-title {
  font-size: 28px;
  color: #262626;
  margin-bottom: 16px;
  font-weight: 600;
}

.error-message {
  font-size: 16px;
  color: #595959;
  margin-bottom: 32px;
  line-height: 1.6;
}

.error-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
}
</style>

