<template>
  <div class="max-w-7xl mx-auto p-4 page-enter">
    <!-- <div class="flex justify-between items-center mb-10">
      <div
        class="flex gap-1 items-center space-x-3 w-[337px] h-[48px] bd px-2.5"
      >
        <font-awesome-icon :icon="['fas', 'briefcase']" class="icon" />
        <p class="flex-2 mt-4 text-center item">Vị trí</p>
        <font-awesome-icon :icon="['fas', 'angle-down']" class="ml-auto" />
      </div>
      <div
        class="flex gap-1 items-center space-x-3 w-[337px] h-[48px] bd px-2.5"
      >
        <font-awesome-icon :icon="['fas', 'address-card']" class="icon" />
        <p class="flex-2 mt-4 items-center text-center item">Tên ứng viên</p>
        <font-awesome-icon :icon="['fas', 'angle-down']" class="ml-auto" />
      </div>
      <div
        class="flex gap-1 items-center space-x-3 w-[337px] h-[48px] bd px-2.5"
      >
        <font-awesome-icon :icon="['fas', 'award']" class="icon" />
        <p class="flex-2 mt-4 text-center item">Kinh nghiệm làm việc</p>
        <font-awesome-icon :icon="['fas', 'angle-down']" class="ml-auto" />
      </div>
      <div>
        <button class="w-[140px] h-[48px] btn">Tìm kiếm</button>
      </div>
    </div> -->

    <!-- Header -->
    <div class="surface-glass flex justify-between items-center mb-5 mt-8 p-4 sm:p-5">
      <h2 class="text-2xl font-bold header-item">Tất cả ứng viên</h2>
      <div class="flex gap-4 header-item1 items-center">
        Sắp xếp theo:
        <div class="relative">
          <button
            @click="toggleDropdown"
            class="flex items-center space-x-2 cursor-pointer px-3 py-2 rounded-xl bg-white border border-slate-200"
          >
            <div class="header-item-text">
              {{ sortOption === "asc" ? "Từ A-Z" : "Từ Z-A" }}
            </div>
            <font-awesome-icon
              :icon="['fas', 'angle-down']"
              class="header-item-icon"
            />
          </button>
          <div
            v-if="isDropdownOpen"
            class="absolute bg-white border rounded-xl z-[1] shadow-md mt-2 w-[110px]"
          >
            <div
              @click="setSortOption('asc')"
              class="px-4 py-2 hover:bg-gray-100 cursor-pointer z-1"
            >
              Từ A-Z
            </div>
            <div
              @click="setSortOption('desc')"
              class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
            >
              Từ Z-A
            </div>
          </div>
        </div>
      </div>
    </div>

    <hr class="bg-red-500/60 mb-6" />

    <div v-if="isLoading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div
        v-for="idx in 8"
        :key="`candidate-skeleton-${idx}`"
        class="bg-white/80 rounded-2xl border border-white/80 overflow-hidden"
      >
        <div class="h-48 bg-slate-200 skeleton-shimmer"></div>
        <div class="p-4 space-y-3">
          <div class="h-5 w-2/3 rounded bg-slate-200 skeleton-shimmer"></div>
          <div class="h-4 w-1/3 rounded bg-slate-200 skeleton-shimmer"></div>
          <div class="h-4 w-full rounded bg-slate-200 skeleton-shimmer"></div>
          <div class="h-4 w-5/6 rounded bg-slate-200 skeleton-shimmer"></div>
        </div>
      </div>
    </div>

    <!-- Candidates Grid -->
    <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      <div
        v-for="(candidate, index) in activeCandidates"
        :key="candidate.id"
        class="bg-white/85 backdrop-blur rounded-2xl shadow transition-all duration-300 overflow-hidden border border-white/70 stagger-item"
        :style="{ '--i': index }"
        @click="handleClick(candidate.id)"
        :class="{
          'cursor-not-allowed': !currentUser || currentUser.status !== 'active',
          'hover:shadow-lg hover:cursor-pointer hover:-translate-y-1':
            currentUser && currentUser.status === 'active',
        }"
      >
        <!-- Candidate Image -->
        <div class="relative h-48 bg-gray-100">
          <img
            :src="candidate.avatar"
            :alt="candidate.fullName"
            class="w-full h-full object-cover"
          />
          <span
            :class="[
              'absolute top-4 right-4 px-3 py-1 rounded-full text-sm font-medium shadow-sm',
              getPositionColor(candidate.level),
            ]"
          >
            {{ candidate.level }}
          </span>
        </div>

        <!-- Candidate Info -->
        <div class="p-4">
          <h3 class="text-lg font-semibold">{{ candidate.fullName }}</h3>
          <p class="text-gray-600 text-sm">
            {{ calculateAge(candidate.birthdate) }} tuổi
          </p>

          <!-- Level & Language -->
          <div class="mt-4 space-y-2">
            <div class="flex items-center text-sm">
              <font-awesome-icon
                :icon="['fas', 'graduation-cap']"
                class="w-5 h-5 text-red-600 mr-2"
              />
              <span class="text-gray-700 font-medium">Trình độ:</span>
              <span class="ml-2 text-gray-600">{{ candidate.level }}</span>
            </div>

            <div class="flex items-center text-sm">
              <svg
                class="w-5 h-5 text-red-600 mr-2"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M3 5h12M9 3v2m1.048 9.5A18.022 18.022 0 016.412 9m6.088 9h7M11 21l5-10 5 10M12.751 5C11.783 10.77 8.07 15.61 3 18.129"
                />
              </svg>

              <span class="text-gray-700 font-medium">Ngoại ngữ:</span>
              <span
                v-for="(edu, eduIndex) in candidate.foreignLanguage"
                :key="eduIndex"
                class="ml-2 text-gray-600"
                >{{ edu }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div
      v-if="!isLoading && activeCandidates.length === 0"
      class="empty-state-card p-8 text-center mt-2"
    >
      <p class="text-lg font-semibold text-slate-800">Chưa có ứng viên phù hợp</p>
      <p class="text-sm text-slate-500 mt-1">
        Thử tải lại hoặc đổi cách sắp xếp để xem thêm dữ liệu.
      </p>
      <button class="gradient-btn mt-4 px-4 h-10 rounded-xl font-medium" @click="fetchPage(1)">
        Tải lại danh sách
      </button>
    </div>

    <div v-if="!isLoading && activeCandidates.length > 0" class="mt-6 flex justify-end space-x-2">
      <button
        class="px-4 py-2 border rounded-xl bg-white"
        :class="{ 'opacity-50 cursor-not-allowed': currentPage === 1 }"
        @click="previousPage"
        :disabled="currentPage === 1"
      >
        Previous
      </button>
      <button
        v-for="page in totalPages"
        :key="page"
        class="px-4 py-2 border rounded-xl bg-white"
        :class="{ 'gradient-btn text-white border-transparent': currentPage === page }"
        @click="fetchPage(page)"
      >
        {{ page }}
      </button>
      <button
        class="px-4 py-2 border rounded-xl bg-white"
        :class="{
          'opacity-50 cursor-not-allowed': currentPage === totalPages,
        }"
        @click="nextPage"
        :disabled="currentPage === totalPages"
      >
        Next
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { message } from "ant-design-vue";

const router = useRouter();
const store = useStore();
const isDropdownOpen = ref(false);
const sortOption = ref("asc");
const isLoading = ref(true);
const totalPages = computed(() => store.getters.totalPagesCandidate);

const currentPage = computed(() => store.state.candidate.currentPage);
// const candidateStatus = JSON.parse(localStorage.getItem("status"));
const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};
const setSortOption = (option) => {
  if (sortOption.value !== option) {
    sortOption.value = option;
    fetchPage(1);
  }
  isDropdownOpen.value = false;
};

const calculateAge = (birthdate) => {
  const birthDate = new Date(birthdate);
  const today = new Date();
  let age = today.getFullYear() - birthDate.getFullYear();
  const monthDiff = today.getMonth() - birthDate.getMonth();
  if (
    monthDiff < 0 ||
    (monthDiff === 0 && today.getDate() < birthDate.getDate())
  ) {
    age--;
  }
  return age;
};
const activeCandidates = computed(() =>
  store.state.candidate.candidates.filter(
    (user) => String(user?.status || "").toLowerCase() === "active" && user.role === "user"
  )
);

// console.log(store);
const currentUser = computed(() => store.state.user.userLogin); // Giả sử bạn lưu người dùng hiện tại trong module auth

const getPositionColor = (role) => {
  const colors = {
    Fresher: "bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)]",
    Junior: "bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]",
    Middle: "bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]",
    Senior: "bg-[rgba(245,246,255,1)] text-[#8c70a7]",
    Lead: "bg-[#f1f2f5] text-[#573775]",
  };
  return colors[role] || "bg-gray-100 text-gray-800";
};

const fetchPage = async (page) => {
  isLoading.value = true;
  try {
    if (sortOption.value === "asc") {
      await store.dispatch("getCandidatesByPageA", page);
    } else {
      await store.dispatch("getCandidatesByPageDe", page);
    }
  } finally {
    isLoading.value = false;
  }
};

const previousPage = () => {
  if (currentPage.value > 1) {
    fetchPage(currentPage.value - 1);
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    fetchPage(currentPage.value + 1);
  }
};

const hasCv = () => store.getters.hasCv;
const handleClick = (id) => {
  if (!currentUser || String(currentUser.value?.status || "").toLowerCase() !== "active") {
    return;
  }
  if (currentUser.value?.role === "user" && !hasCv()) {
    message.warning("Bạn cần tải CV lên để xem chi tiết ứng viên.");
    return;
  }
  router.push(`/homepage/candidate/candidateDetail/${id}`);
};

onMounted(async () => {
  await fetchPage(1);
});
</script>

<style scoped>
.btn {
  background: rgba(171, 31, 36, 1);
  border-radius: 8px;
  border: 1px;
  color: rgba(255, 255, 255, 1);
}
.bd {
  border: 1px solid wheat;
  border-radius: 8px;
}
.icon {
  color: rgba(188, 34, 40, 1);
}
.item {
  font-family: "SF Pro Display", sans-serif;
  color: rgba(103, 103, 103, 1);
}
.header-item {
  color: rgba(0, 0, 0, 1);
  size: 24px;
  font-family: "SF Pro Display", sans-serif;
}
.header-item1 {
  color: rgba(89, 89, 89, 1);
  font-family: "SF Pro Display", sans-serif;
  size: 12px;
}
.header-item-text {
  color: rgba(188, 34, 40, 1);
  font-family: "SF Pro Display", sans-serif;
  size: 14px;
}
.header-item-icon {
  color: rgba(188, 34, 40, 1);
}
</style>
