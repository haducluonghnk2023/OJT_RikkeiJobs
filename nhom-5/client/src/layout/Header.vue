<template>
  <header class="sticky top-0 z-50 border-b border-white/50 bg-white/78 backdrop-blur-xl shadow-[0_8px_24px_rgba(15,23,42,0.08)]">
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-3">
      <div class="flex items-center justify-between gap-4">
        <div class="flex items-center gap-10 min-w-0">
          <div class="flex items-center gap-1 shrink-0">
            <img
              @click="handleChange"
              src="@/assets/rikkei.svg"
              class="w-[70.6px] h-[32.09px] image"
            />
            <img
              @click="handleChange"
              src="@/assets/jobs.svg"
              class="w-[63.12px] h-[21.4px] image"
            />
          </div>

          <nav class="hidden lg:flex gap-6 items-center min-w-0">
            <div
              class="relative"
              @mouseenter="showJobMenu = true"
              @mouseleave="showJobMenu = false"
            >
              <RouterLink
                to="/homepage/listJob"
                class="h-link font-sf-pro-display nav-pill"
                active-class="active"
              >
                Việc làm
              </RouterLink>
              <div
                v-if="showJobMenu"
                class="absolute top-[calc(100%+8px)] left-1/2 -translate-x-1/2 bg-white/95 backdrop-blur-lg shadow-lg rounded-2xl w-72 z-30 border border-slate-100 p-1"
              >
                <router-link
                  to="/homepage/listJob"
                  class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-100 transition-colors text-gray-700 no-underline"
                >
                  <SearchOutlined class="text-gray-500" />
                  <span class="whitespace-nowrap">Tìm kiếm việc làm</span>
                </router-link>
                <router-link
                  to="/homepage/saveJob"
                  class="flex items-center gap-3 px-4 py-3 rounded-xl hover:bg-gray-100 transition-colors text-gray-700 no-underline"
                >
                  <HeartOutlined class="text-gray-500" />
                  <span class="whitespace-nowrap">Việc làm đã lưu</span>
                </router-link>
              </div>
            </div>

            <div
              v-if="user?.role === 'admin' || user?.role === 'user'"
              class="h-link font-sf-pro-display hover:cursor-pointer nav-pill"
              active-class="active"
              @click="checkLogin"
            >
              Hồ sơ & CV
            </div>

            <RouterLink
              class="h-link font-sf-pro-display nav-pill"
              active-class="active"
              to="/about-us"
            >
              Về chúng tôi
            </RouterLink>
            <RouterLink
              class="h-link font-sf-pro-display nav-pill"
              active-class="active"
              to="/contact"
            >
              Liên hệ
            </RouterLink>
          </nav>
        </div>

        <div class="flex items-center gap-3">
          <!-- Language -->
          <div
            class="hidden sm:flex items-center gap-2 pr-4 border-r border-gray-300 relative"
          >
            <img
              :src="currentLanguage.icon"
              alt="Language Icon"
              class="w-6 h-6 rounded-full z-20"
            />
            <p
              class="text-gray-700 font-sf-pro-display m-0 flex items-center justify-center"
            >
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
              <button
                @click="changeLanguage('vn')"
                class="w-full text-left px-4 py-2"
              >
                VN
              </button>
              <button
                @click="changeLanguage('en')"
                class="w-full text-left px-4 py-2"
              >
                EN
              </button>
              <button
                @click="changeLanguage('ja')"
                class="w-full text-left px-4 py-2"
              >
                Japanese
              </button>
            </div>
          </div>

          <!-- Auth/User -->
          <div class="flex gap-2 items-center">
            <template v-if="userLoginId">
              <a-dropdown>
                <a class="ant-dropdown-link" @click.prevent>
                  <img
                    :src="user?.avatar"
                    alt="User Avatar"
                    class="w-10 h-10 rounded-full"
                  />
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
                      <div
                        @click="showConfirm"
                        class="flex text-red align-center gap-2"
                      >
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
                class="hidden sm:flex text-gray-600 text-[14px] px-4 h-[40px] rounded-xl items-center justify-center font-semibold border border-slate-200 bg-white"
              >
                Đăng ký
              </router-link>
              <router-link
                to="/login"
                class="gradient-btn text-[14px] h-[40px] px-4 rounded-xl flex items-center justify-center font-semibold shadow-sm"
              >
                Đăng nhập
              </router-link>
            </template>
          </div>

          <button
            class="lg:hidden inline-flex items-center justify-center w-10 h-10 rounded-xl border border-gray-200 bg-white/80"
            @click="mobileMenuOpen = !mobileMenuOpen"
            aria-label="Toggle menu"
          >
            <span class="text-sm font-semibold">☰</span>
          </button>
        </div>
      </div>

      <div
        v-if="mobileMenuOpen"
        class="lg:hidden mt-4 border-t border-gray-200 pt-4"
      >
        <div class="flex flex-col gap-3">
          <RouterLink
            to="/homepage/listJob"
            class="h-link font-sf-pro-display"
            active-class="active"
          >
            Việc làm
          </RouterLink>

          <RouterLink
            to="/homepage/saveJob"
            class="h-link font-sf-pro-display"
            active-class="active"
          >
            Việc làm đã lưu
          </RouterLink>

          <div
            v-if="user?.role === 'admin' || user?.role === 'user'"
            class="h-link font-sf-pro-display hover:cursor-pointer"
            @click="checkLogin"
          >
            Hồ sơ & CV
          </div>

          <RouterLink
            class="h-link font-sf-pro-display"
            active-class="active"
            to="/about-us"
          >
            Về chúng tôi
          </RouterLink>
          <RouterLink
            class="h-link font-sf-pro-display"
            active-class="active"
            to="/contact"
          >
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
const hasCv = () => store.getters.hasCv;
const checkLogin = () => {
  if (!userLoginId.value) {
    message.error("Vui lòng đăng nhập để xem Hồ sơ & CV.");
    return;
  }
  if (user.value?.role === "user" && !hasCv()) {
    message.warning("Bạn cần tải CV lên để xem thông tin ứng viên.");
    router.push("/profile/cv");
    return;
  }
  router.push(`/homepage/candidate/candidateDetail/${userLoginId.value}`);
};
const isModalVisible = ref(false);
const showConfirm = () => {
  isModalVisible.value = true;
};

const handleLogout = async () => {
  try {
    await logoutApi();
  } catch (e) {
  } finally {
    localStorage.removeItem("token");
    router.push("/login");
    isModalVisible.value = false;
  }
};

const handleCancel = () => {
  isModalVisible.value = false;
};
onMounted(async () => {
  if (userLoginId.value != null) {
    await store.dispatch("getUser", userLoginId.value);
    await store.dispatch("getCv");
  }
});
</script>
<style scoped>
.h-link {
  @apply text-slate-700 no-underline transition-colors duration-300 ease-in-out;
}
.h-link:hover {
  @apply text-[rgba(171,31,36,1)];
}
.active {
  @apply text-[rgba(171,31,36,1)] font-semibold;
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
.nav-pill {
  padding: 8px 12px;
  border-radius: 10px;
}
.nav-pill:hover {
  background: rgba(255, 255, 255, 0.85);
}
</style>
