<template>
  <header class="border-b border-gray-300">
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-4">
      <div class="flex items-center justify-between gap-4">
        <!-- Left group: Logo + Desktop nav (keep nav near the left like the design) -->
        <div class="flex items-center gap-10 min-w-0">
          <div class="flex items-center gap-1 shrink-0">
            <img
              @click="handleChange"
              :src="rikkeiSvg"
              class="w-[70.6px] h-[32.09px] image"
            />
            <img
              @click="handleChange"
              :src="jobsSvg"
              class="w-[63.12px] h-[21.4px] image"
            />
          </div>

          <!-- Desktop nav -->
          <nav class="hidden lg:flex gap-6 items-center min-w-0">
            <RouterLink
              to="/homepage/listJob"
              class="h-link font-sf-pro-display relative"
              active-class="active"
              @mouseover="showJobMenu = true"
              @mouseleave="showJobMenu = false"
            >
              Việc làm
              <div
                v-if="showJobMenu"
                class="absolute top-full left-0 bg-white shadow-lg rounded-md w-60 z-20"
              >
                <div
                  class="flex items-center gap-3 px-4 py-3 hover:bg-gray-100 transition-colors"
                >
                  <SearchOutlined class="text-gray-500" />
                  <router-link to="/homepage/listJob" class="text-gray-600"
                    >Tìm kiếm việc làm</router-link
                  >
                </div>
                <div
                  class="flex items-center gap-3 px-4 py-3 hover:bg-gray-100 transition-colors"
                >
                  <HeartOutlined class="text-gray-500" />
                  <router-link to="/homepage/saveJob" class="text-gray-600"
                    >Việc làm đã lưu</router-link
                  >
                </div>
              </div>
            </RouterLink>

            <div
              v-if="user?.role === 'admin' || user?.role === 'user'"
              class="h-link font-sf-pro-display hover:cursor-pointer"
              active-class="active"
              @click="checkLogin"
            >
              Hồ sơ & CV
            </div>

            <RouterLink class="h-link font-sf-pro-display" active-class="active" to="/about-us">
              Về chúng tôi
            </RouterLink>
            <RouterLink class="h-link font-sf-pro-display" active-class="active" to="contact">
              Liên hệ
            </RouterLink>
          </nav>
        </div>

        <div class="flex items-center gap-3">
          <!-- Language -->
          <div class="hidden sm:flex items-center gap-2 pr-4 border-r border-gray-300 relative">
            <img
              :src="currentLanguage.icon"
              alt="Language Icon"
              class="w-6 h-6 rounded-full z-20"
            />
            <p class="text-gray-700 font-sf-pro-display m-0 flex items-center justify-center">
              {{ currentLanguage.text }}
            </p>
            <DownOutlined
              class="text-gray-500 text-sm flex items-center justify-center cursor-pointer"
              @click="toggleDropdown"
            />
            <div
              v-if="dropdownVisible"
              class="absolute top-full mt-2 bg-white border rounded-lg shadow-lg w-[150px] z-20 right-0"
            >
              <button @click="changeLanguage('vn')" class="w-full text-left px-4 py-2">VN</button>
              <button @click="changeLanguage('en')" class="w-full text-left px-4 py-2">EN</button>
              <button @click="changeLanguage('ja')" class="w-full text-left px-4 py-2">Japanese</button>
            </div>
          </div>

          <!-- Auth/User -->
          <div class="flex gap-2 items-center">
            <template v-if="userLoginId">
              <a-dropdown>
                <a class="ant-dropdown-link" @click.prevent>
                  <img :src="user?.avatar" alt="User Avatar" class="w-10 h-10 rounded-full" />
                </a>
                <template #overlay>
                  <a-menu class="w-[200px]">
                    <div class="flex gap-[10px] py-1 px-2 align-center">
                      <img
                        :src="user?.avatar"
                        alt="Profile Picture"
                        class="w-10 h-10 rounded-full object-cover"
                      />

                      <div class="leading-[0px]">
                        <h2 class="text-lg font-[600] text-[14px]">
                          {{ user.fullName }}
                        </h2>
                        <p class="text-gray-500 text-[12px] font-[400]">
                          {{ user.email }}
                        </p>
                      </div>
                    </div>

                    <a-menu-divider />
                    <a-menu-item>
                      <router-link to="/profile">Thông tin cá nhân</router-link>
                    </a-menu-item>
                    <a-menu-item><span>Cài đặt</span> </a-menu-item>
                    <a-menu-divider />
                    <a-menu-item>
                      <div @click="showConfirm" class="flex text-red align-center gap-2">
                        <LogoutOutlined /> Đăng xuất
                      </div>
                    </a-menu-item>
                  </a-menu>
                </template>
              </a-dropdown>
            </template>
            <template v-else>
              <router-link
                to="/register"
                class="hidden sm:flex text-gray-500 text-[14px] px-4 h-[40px] rounded items-center justify-center font-semibold"
              >
                Đăng ký
              </router-link>
              <router-link
                to="/login"
                class="bg-[#B60000] text-[14px] h-[40px] text-white px-4 rounded-lg flex items-center justify-center font-semibold"
              >
                Đăng nhập
              </router-link>
            </template>
          </div>

          <!-- Mobile menu button -->
          <button
            class="lg:hidden inline-flex items-center justify-center w-10 h-10 rounded border border-gray-200"
            @click="mobileMenuOpen = !mobileMenuOpen"
            aria-label="Toggle menu"
          >
            <span class="text-sm font-semibold">☰</span>
          </button>
        </div>
      </div>

      <!-- Mobile nav -->
      <div v-if="mobileMenuOpen" class="lg:hidden mt-4 border-t border-gray-200 pt-4">
        <div class="flex flex-col gap-3">
          <RouterLink to="/homepage/listJob" class="h-link font-sf-pro-display" active-class="active">
            Việc làm
          </RouterLink>

          <RouterLink to="/homepage/saveJob" class="h-link font-sf-pro-display" active-class="active">
            Việc làm đã lưu
          </RouterLink>

          <div
            v-if="user?.role === 'admin' || user?.role === 'user'"
            class="h-link font-sf-pro-display hover:cursor-pointer"
            @click="checkLogin"
          >
            Hồ sơ & CV
          </div>

          <RouterLink class="h-link font-sf-pro-display" active-class="active" to="/about-us">
            Về chúng tôi
          </RouterLink>
          <RouterLink class="h-link font-sf-pro-display" active-class="active" to="contact">
            Liên hệ
          </RouterLink>
        </div>
      </div>
    </div>

    <a-modal
      v-model:open="isModalVisible"
      title="Xác nhận đăng xuất"
      @ok="handleLogout"
      @cancel="handleCancel"
    >
      <p>Bạn có chắc chắn muốn đăng xuất?</p>
    </a-modal>
  </header>
</template>

<script setup>
import rikkeiSvg from "@/assets/rikkei.svg";
import jobsSvg from "@/assets/jobs.svg";
import {
  DownOutlined,
  HeartOutlined,
  LogoutOutlined,
  SearchOutlined,
} from "@ant-design/icons-vue";
import { onMounted, computed, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { message } from "ant-design-vue";
import { logout as logoutApi } from "@/apis/auth/loginApi";

const store = useStore();
const tokenRaw = localStorage.getItem("token");
const userLoginId = ref(tokenRaw ? JSON.parse(tokenRaw) : null);

const user = computed(() => store.state.user.userLogin || []);
const router = useRouter();
const showJobMenu = ref(false);
const mobileMenuOpen = ref(false);
const dropdownVisible = ref(false);
const currentLanguage = ref({
  text: "VN",
  icon: "https://firebasestorage.googleapis.com/v0/b/ptit-k5-5b7cb.appspot.com/o/covn.png?alt=media&token=563a289f-a54f-477c-a073-eeb86ea5b3a9",
});

const toggleDropdown = () => {
  dropdownVisible.value = !dropdownVisible.value;
};

const changeLanguage = (lang) => {
  if (lang === "vn") {
    currentLanguage.value = {
      text: "VN",
      icon: "https://firebasestorage.googleapis.com/v0/b/ptit-k5-5b7cb.appspot.com/o/covn.png?alt=media&token=563a289f-a54f-477c-a073-eeb86ea5b3a9",
    };
  } else if (lang === "en") {
    currentLanguage.value = {
      text: "EN",
      icon: "https://clientdev.mieusoft.com/assets/flag_EN-CQdtmuKv.jpg",
    };
  } else if (lang === "ja") {
    currentLanguage.value = {
      text: "Japanese",
      icon: "https://firebasestorage.googleapis.com/v0/b/ptit-k5-5b7cb.appspot.com/o/conhat.png?alt=media&token=6da4c191-838e-4bc3-8c74-86f6d54a02b2",
    };
  }
  dropdownVisible.value = false;
};

const handleChange = () => {
  router.push("/");
};
const checkLogin = () => {
  if (!userLoginId.value) {
    message.error("Vui lòng đăng nhập để xem Hồ sơ & CV.");
    return;
  }
  router.push(`/homepage/candidate/candidateDetail/${userLoginId.value}`);
};
const isModalVisible = ref(false);
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
    router.push("/login");
    isModalVisible.value = false;
  }
};

// Hàm xử lý khi hủy bỏ đăng xuất
const handleCancel = () => {
  isModalVisible.value = false;
};
onMounted(() => {
  if (userLoginId.value != null) {
    store.dispatch("getUser", userLoginId.value);
  }
});
</script>
<style scoped>
.h-link {
  @apply text-black no-underline transition-colors duration-300 ease-in-out;
}
.h-link:hover {
  @apply text-[rgba(171,31,36,1)];
}
.active {
  @apply text-[rgba(171,31,36,1)] font-bold;
}
.font-sf-pro-display {
  font-family: "SF Pro Display", sans-serif;
}
.relative .z-10 {
  z-index: 10;
}
.relative {
  position: relative;
}
.image:hover {
  cursor: pointer;
}
</style>
