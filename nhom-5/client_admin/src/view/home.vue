<template>
  <a-layout class="min-h-[100vh]">
    <a-layout-sider
      v-model:collapsed="collapsed"
      :trigger="null"
      collapsible
      :width="200"
      :collapsedWidth="80"
      :style="{
        position: 'fixed',
        left: '0',
        top: '0',
        height: '100vh',
        overflow: 'auto',
        zIndex: 10,
      }"
    >
      <div class="logo" />
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="1" title="Người dùng">
          <router-link to="/user" class="flex items-center gap-2 uppercase">
            <UserOutlined />
            <span>Người dùng</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="2" title="CV">
          <router-link to="/cv" class="flex items-center gap-2 uppercase">
            <FileTextOutlined />
            <span>CV</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="3" title="Ngôn ngữ CV">
          <router-link to="/language" class="flex items-center gap-2 uppercase">
            <AlipayCircleOutlined />
            <span>Ngôn ngữ CV</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="4" title="Job">
          <router-link to="/job" class="flex items-center gap-2 uppercase">
            <DatabaseOutlined />
            <span>Job</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="5" title="Doanh nghiệp">
          <router-link to="/enterprise" class="flex items-center gap-2 uppercase">
            <DatabaseOutlined />
            <span>Doanh nghiệp</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="6" title="Chứng chỉ">
          <router-link to="/certificate" class="flex items-center gap-2 uppercase">
            <BookOutlined />
            <span>Chứng chỉ</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="7" title="Phỏng vấn">
          <router-link to="/interview" class="flex items-center gap-2 uppercase">
            <FileProtectOutlined />
            <span>Phỏng vấn</span>
          </router-link>
        </a-menu-item>
        <a-menu-item key="9" title="Đăng xuất">
          <div class="flex items-center gap-2 uppercase" @click="showConfirm">
            <LogoutOutlined />
            <div>Đăng xuất</div>
          </div>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout
      :style="{
        marginLeft: collapsed ? '80px' : '200px',
        transition: 'margin-left 0.2s',
      }"
    >
      <a-layout-header style="background: #fff; padding: 0">
        <div class="text-lg h-full w-20 hover:bg-[#F5F5F5] ease-in rounded-lg">
          <MenuUnfoldOutlined
            v-if="collapsed"
            class="trigger px-7 mt-5"
            @click="() => (collapsed = !collapsed)"
          />
          <MenuFoldOutlined
            v-else
            class="trigger px-7 mt-5"
            @click="() => (collapsed = !collapsed)"
          />
        </div>
      </a-layout-header>
      <a-layout-content
        :style="{
          margin: '24px 16px',
          padding: '24px',

          minHeight: '100vh',
        }"
      >
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
    <a-modal
      v-model:visible="isModalVisible"
      title="Xác nhận đăng xuất"
      @ok="handleLogout"
      @cancel="handleCancel"
    >
      <p>Bạn có chắc chắn muốn đăng xuất?</p>
    </a-modal>
  </a-layout>
</template>

<script setup>
import {
  AlipayCircleOutlined,
  BookOutlined,
  DatabaseOutlined,
  FileProtectOutlined,
  FileTextOutlined,
  InsuranceOutlined,
  LogoutOutlined,
  MenuFoldOutlined,
  MenuUnfoldOutlined,
  UserOutlined,
} from "@ant-design/icons-vue";
import { ref, watchEffect } from "vue";
import { useRoute, useRouter } from "vue-router";
import { logout as logoutApi } from "@/apis/authApi";
const route = useRoute();
const selectedKeys = ref([]);
const collapsed = ref(false);
const router = useRouter();
const isModalVisible = ref(false);

const routeToMenuKey = (path) => {
  const p = String(path || "");
  // Support both "/xxx" and "/admin/xxx" depending on alias setup
  if (p === "/" || p === "/admin") return "1";
  if (p.startsWith("/user") || p.startsWith("/admin/user")) return "1";
  if (p.startsWith("/cv") || p.startsWith("/admin/cv")) return "2";
  if (p.startsWith("/language") || p.startsWith("/admin/language")) return "3";
  if (p.startsWith("/job") || p.startsWith("/admin/job")) return "4";
  if (p.startsWith("/enterprise") || p.startsWith("/admin/enterprise")) return "5";
  if (p.startsWith("/certificate") || p.startsWith("/admin/certificate")) return "6";
  if (p.startsWith("/interview") || p.startsWith("/admin/interview")) return "7";
  return "1";
};

// Keep the highlighted menu item in sync with the current route (fixes refresh/reset)
watchEffect(() => {
  selectedKeys.value = [routeToMenuKey(route.path)];
});
// Hiển thị modal khi nhấp vào "Đăng xuất"
const showConfirm = () => {
  isModalVisible.value = true;
};

// Hàm xử lý đăng xuất
const handleLogout = async () => {
  // Server-side logout to clear HttpOnly cookies/session (best-effort)
  try {
    await logoutApi();
  } catch (e) {
    // ignore; still clear local client state below
  } finally {
    localStorage.removeItem("token");
    // Cross-port: after admin logout always go back to client login (5173)
    window.location.href = "http://localhost:5173/login";
    isModalVisible.value = false;
  }
};

// Hàm xử lý khi hủy bỏ đăng xuất
const handleCancel = () => {
  isModalVisible.value = false;
};
</script>
<style>
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.3);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}
</style>
