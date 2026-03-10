<template>
  <div ref="jobsTopRef" class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
      <p class="text-xl sm:text-2xl font-semibold">Việc làm nổi bật</p>
      <div class="flex items-center text-red-700 cursor-pointer">
        <router-link to="/homepage/listJob" class="mr-2">Xem tất cả</router-link>
        <font-awesome-icon :icon="['fas', 'arrow-right']" />
      </div>
    </div>

    <div class="mt-4 flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
      <div class="flex gap-3">
        <!-- Nút Tất cả -->
        <button
          :class="{
            'bg-[rgba(188,34,40,1)] text-white': activeLocation === 'random',
            'bg-[rgba(244,244,244,1)] text-gray-600':
              activeLocation !== 'random',
          }"
          class="px-4 py-2 rounded-lg transition-all"
          @click="handleRandomFilter"
        >
          Tất cả
        </button>
      </div>

      <!-- Dropdown lọc theo địa điểm - góc bên phải -->
      <div ref="provinceDropdownRef" class="relative">
        <div
          class="flex w-full sm:w-[276px] h-12 px-4 items-center border-[rgba(221,221,221,1)] border-2 bg-[rgba(255,255,255,1)] rounded-md cursor-pointer hover:border-red-500 transition-all relative"
          @click.stop="toggleProvinceDropdown"
        >
          <div class="flex items-center gap-2 h-full">
            <p
              class="text-[rgba(145,145,145,1)] font-semibold text-ic whitespace-nowrap leading-none m-0"
            >
              Lọc theo:
            </p>
            <p
              class="text-[rgba(61,61,61,1)] font-semibold text-ic truncate max-w-[120px] leading-none m-0"
            >
              {{ selectedProvinceName || "Địa điểm" }}
            </p>
          </div>
          <div class="absolute right-4 top-1/2 -translate-y-1/2">
            <font-awesome-icon
              :icon="['fas', 'chevron-down']"
              :class="{ 'rotate-180': showProvinceDropdown }"
              class="text-[rgba(61,61,61,1)] transition-transform"
            />
          </div>
        </div>

        <!-- Dropdown menu -->
        <transition name="dropdown">
          <div
            v-if="showProvinceDropdown"
            class="absolute right-0 mt-2 w-[90vw] sm:w-[320px] max-w-[320px] max-h-[400px] overflow-hidden bg-white border border-gray-200 rounded-lg shadow-xl z-[100]"
            @click.stop
          >
            <div class="p-3">
              <input
                v-model="provinceSearch"
                type="text"
                placeholder="Tìm kiếm tỉnh/thành..."
                class="w-full px-3 py-2 border border-gray-300 rounded-md mb-2 focus:outline-none focus:ring-2 focus:ring-red-500 text-sm"
                @click.stop
                @input.stop
              />
              <div class="max-h-[320px] overflow-y-auto custom-scrollbar">
                <template v-if="filteredProvinces.length > 0">
                  <div
                    v-for="province in filteredProvinces"
                    :key="province.code || province.id"
                    @click="selectProvince(province.name)"
                    class="px-3 py-2.5 hover:bg-red-50 cursor-pointer rounded-md transition-colors text-sm"
                    :class="{
                      'bg-red-50 text-red-700 font-semibold':
                        activeLocation === province.name,
                      'text-gray-700': activeLocation !== province.name,
                    }"
                  >
                    {{ province.name }}
                  </div>
                </template>
                <div v-else class="px-3 py-2 text-gray-500 text-center text-sm">
                  {{ isLoadingProvinces ? "Đang tải..." : "Không tìm thấy" }}
                </div>
              </div>
            </div>
          </div>
        </transition>
      </div>
    </div>
    <div class="mt-6">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        <div class="w-full min-w-0" v-for="(job, index) in jobsWithDaysLeft" :key="job?.id ?? index">
          <div class="group bg-white/90 backdrop-blur-sm w-full rounded-2xl border border-white/80 p-4 sm:p-5 relative overflow-hidden h-full flex flex-col shadow-[0_8px_24px_rgba(15,23,42,0.08)] transition-all duration-300 hover:-translate-y-1 hover:shadow-[0_18px_36px_rgba(15,23,42,0.14)]">
            <div class="flex items-start gap-3.5">
              <img
                :src="job.image"
                alt="Job Image for Software Engineer"
                class="rounded-xl object-cover w-[88px] h-[88px] shrink-0 border border-slate-100"
              />
              <div class="flex flex-col justify-between items-start gap-3 mb-3 min-w-0 flex-1 overflow-hidden pr-10">
                <p
                  @click="handleClick(job.id)"
                  class="!text-[17px] font-semibold text-red-700 leading-tight hover:cursor-pointer"
                >
                  <span class="line-clamp-2">{{ job.title }}</span>
                </p>
                <div class="flex flex-wrap gap-2">
                  <!-- Hiển thị tối đa 3 hoặc toàn bộ dựa trên trạng thái isExpanded -->
                  <p
                    v-for="(rank, eduIndex) in isExpanded
                      ? job?.rank
                      : job?.rank?.slice(0, 3)"
                    :key="eduIndex"
                    :class="[
                      'px-3 h-7 rounded-xl flex justify-center items-center text-[13px] font-semibold',
                      {
                        'bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)]':
                          rank === 'Fresher',
                        'bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]':
                          rank === 'Junior',
                        'bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]':
                          rank === 'Middle',
                        'bg-[rgba(245,246,255,1)] text-[#8c70a7]':
                          rank === 'Senior',
                        'bg-[#f1f2f5] text-[#573775]': rank === 'Lead',
                      },
                    ]"
                  >
                    {{ rank }}
                  </p>
                  <!-- Nút hiển thị/ẩn thêm -->
                  <div
                    v-if="job.rank?.length > 3"
                    @click="toggleExpanded"
                    class="cursor-pointer w-[27px] h-[26px] rounded-full bg-[rgba(221,221,221,1)] flex justify-center items-center text-[rgba(103,103,103,1)]"
                  >
                    {{ isExpanded ? "-" : "+" + (job.rank?.length - 3) }}
                  </div>
                </div>
              </div>
            </div>
            <div class="flex flex-wrap items-center gap-3 m-0 p-0 text-[12px] mb-3 min-w-0 text-slate-700">
              <div
                class="flex gap-[8px] items-center w-auto h-[18px] font-semibold"
              >
                <i class="fas fa-money-bill">
                  <font-awesome-icon
                    :icon="['fas', 'money-bill']"
                    class="text-[rgba(188,34,40,1)]"
                  />
                </i>
                <span class="text-[12px] h-[18px] font-[400] truncate max-w-[180px]">
                  {{ formatSalary(job.salary) }}
                </span>
              </div>
              <div class="flex items-center gap-1 min-w-0">
                <span
                  class="w-[16px] h-[16px] bg-[rgba(240,240,240,1)] flex items-center justify-center rounded-full"
                >
                  <div
                    class="w-[16px] h-[16px] bg-[rgba(216,0,39,1)] rounded-full flex items-center justify-center"
                  >
                    <img
                      class="w-[7.31px] h-[6.96px]"
                      :src="vnSvg"
                      alt="Vietnam"
                    />
                  </div>
                </span>

                <div class="truncate max-w-[140px]">
                  {{ job.province }}
                </div>
              </div>

              <div class="flex items-center">
                <i class="fas fa-briefcase text-[rgba(188,34,40,1)] mr-1"
                  ><font-awesome-icon :icon="['fas', 'briefcase']"
                /></i>
                <div class="truncate max-w-[160px]">
                  {{ job.workingTime }}
                </div>
              </div>
            </div>
            <p
              v-if="jobsWithDaysLeft"
              class="text-gray-500 text-[12px] leading-tight mt-auto pt-3 border-t border-slate-100"
            >
              Cập nhật {{ job.timeAgo }} - Còn
              <span class="font-semibold text-black">{{ job.daysLeft }}</span>
              ngày để ứng tuyển
            </p>

            <button
              @click.stop="toggleFavorite(job.id)"
              type="button"
              class="absolute top-5 right-5 favorite-heart-btn"
              :class="isFavorite(job.id) ? 'favorite-heart-btn--active' : 'favorite-heart-btn--idle'"
            >
              <font-awesome-icon
                :icon="['fas', 'heart']"
                :class="isFavorite(job.id) ? 'text-red-500' : 'text-[rgba(221,221,221,1)]'"
              />
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-6 flex flex-wrap items-center justify-center sm:justify-end gap-2">
      <button
        @click="changePage(currentPage - 1)"
        v-if="currentPage > 1"
        class="w-8 h-8 flex items-center justify-center rounded-full bg-gray-100 text-gray-600"
      >
        &lt;
      </button>

      <button
        v-for="page in visiblePages"
        :key="page"
        :class="{
          'bg-red-100 text-red-700': currentPage === page,
          'bg-gray-100 text-gray-600': currentPage !== page,
        }"
        @click="changePage(page)"
        class="w-8 h-8 flex items-center justify-center rounded-full"
      >
        {{ page }}
      </button>

      <button
        @click="changePage(currentPage + 1)"
        v-if="currentPage < activePagination"
        class="w-8 h-8 flex items-center justify-center rounded-full bg-gray-100 text-gray-600"
      >
        &gt;
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, onUnmounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { formatSalary } from "@/utils/formatters";
import vnSvg from "@/assets/img/vn.svg";
const router = useRouter();
const store = useStore();
const jobsTopRef = ref(null);
const provinceDropdownRef = ref(null);
const jobs = computed(() => store.state.jobs.jobs);
const currentPage = computed(() => store.state.jobs.currentPage);
const totalPages = computed(() => store.getters.totalPagesJob);
const activeLocation = ref("random");
const showProvinceDropdown = ref(false);
const provinceSearch = ref("");
const selectedProvinceName = ref("");
const isLoadingProvinces = ref(false);

// Lấy danh sách tỉnh/thành từ store
const provinces = computed(() => {
  const provincesData = store.state.provinces.provinces || [];
  // Đảm bảo provinces là array và có cấu trúc đúng
  if (Array.isArray(provincesData) && provincesData.length > 0) {
    return provincesData;
  }
  return [];
});

// Lọc tỉnh/thành theo từ khóa tìm kiếm
const filteredProvinces = computed(() => {
  if (!provinces.value || provinces.value.length === 0) {
    return [];
  }

  if (!provinceSearch.value) {
    return provinces.value;
  }

  const search = provinceSearch.value.toLowerCase().trim();
  return provinces.value.filter((province) => {
    const name = province.name || province.Name || "";
    return name.toLowerCase().includes(search);
  });
});

const isExpanded = ref(false);
const toggleExpanded = () => {
  isExpanded.value = !isExpanded.value;
};

const toggleProvinceDropdown = () => {
  showProvinceDropdown.value = !showProvinceDropdown.value;
  if (showProvinceDropdown.value) {
    provinceSearch.value = "";
  }
};

const selectProvince = (provinceName) => {
  if (!provinceName) return;

  selectedProvinceName.value = provinceName;
  activeLocation.value = provinceName;
  showProvinceDropdown.value = false;
  provinceSearch.value = "";
  const currentPage = store.state.jobs.currentPage || 1;
  store.dispatch("getJobsByLocation", {
    location: provinceName,
    page: currentPage,
  });
  scrollToJobsTop();
};

const handleLocationFilter = (location) => {
  activeLocation.value = location;
  selectedProvinceName.value = location;
  const currentPage = store.state.jobs.currentPage || 1;
  store.dispatch("getJobsByLocation", { location, page: currentPage });
  scrollToJobsTop();
};

// Đóng dropdown khi click bên ngoài
const handleClickOutside = (event) => {
  const target = event?.target;
  const container = provinceDropdownRef.value;
  if (!container || !(target instanceof Node)) return;
  if (!container.contains(target)) showProvinceDropdown.value = false;
};

onMounted(() => {
  document.addEventListener("click", handleClickOutside);
});

const calculateTimeAgo = (updateDate) => {
  const updateDateObj = new Date(updateDate); // Chuyển đổi thành đối tượng Date
  // console.log(updateDateObj, "12345");

  const now = new Date(); // Lấy thời gian hiện tại
  const diffTime = now - updateDateObj; // Tính chênh lệch thời gian
  const diffHours = Math.floor(diffTime / (1000 * 60 * 60)); // Số giờ đã trôi qua
  const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24)); // Số ngày đã trôi qua

  if (diffDays > 0) {
    return `${diffDays} ngày trước`;
  } else if (diffHours > 0) {
    return `${diffHours} giờ trước`;
  } else {
    const diffMinutes = Math.floor(diffTime / (1000 * 60)); // Số phút đã trôi qua
    return `${diffMinutes} phút trước`;
  }
};

const calculateDaysLeft = (deadline, updateDate) => {
  const deadlineDate = new Date(deadline.split("/").reverse().join("-")); // Chuyển đổi ngày sang định dạng YYYY-MM-DD
  const updateDateObj = new Date(updateDate); // Chuyển đổi thành đối tượng Date
  const diffTime = deadlineDate - updateDateObj; // Tính chênh lệch thời gian
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); // Chuyển đổi thành số ngày
  return diffDays >= 0 ? diffDays : 0; // Đảm bảo không có giá trị âm
};
const jobsWithDaysLeft = computed(() => {
  const result = {}; // Khởi tạo đối tượng rỗng
  jobs.value.forEach((job) => {
    const daysLeft = calculateDaysLeft(job.deadline, job.updateDate); // Tính số ngày còn lại
    const timeAgo = calculateTimeAgo(job.updateDate);

    // Gán đối tượng job với key là job.id
    result[job.id] = { ...job, daysLeft, timeAgo };
  });

  // Chuyển đối tượng result thành mảng và đảo ngược mảng
  const resultArray = Object.values(result).reverse();

  // console.log(resultArray); // In ra mảng đã đảo ngược

  return resultArray;
});
// console.log(jobsWithDaysLeft); // debug

// console.log(jobsWithDaysLeft, "33333");

// Tính toán các trang cần hiển thị (bao gồm dấu "...")
const visiblePages = computed(() => {
  const pages = [];
  const range = 1; // Số lượng trang liền kề trước và sau trang hiện tại

  // Nếu trang hiện tại lớn hơn range + 1 thì thêm trang đầu tiên
  if (currentPage.value > range + 1) pages.push(1);

  // Nếu trang hiện tại lớn hơn range + 2 thì thêm dấu "..."
  if (currentPage.value > range + 2) pages.push("...");

  // Thêm các trang liền kề trước và sau trang hiện tại
  for (
    let i = Math.max(1, currentPage.value - range);
    i <= Math.min(totalPages.value, currentPage.value + range);
    i++
  ) {
    pages.push(i);
  }

  // Nếu trang hiện tại gần cuối, thêm dấu "..."

  if (currentPage.value < totalPages.value - range - 1) pages.push("...");
  if (currentPage.value < totalPages.value - range)
    pages.push(totalPages.value);

  return pages;
});

const scrollToJobsTop = async () => {
  await nextTick();
  jobsTopRef.value?.scrollIntoView({ behavior: "smooth", block: "start" });
};

const changePage = async (page) => {
  if (page === "...") return; // Không thay đổi trang nếu là "..."
  await store.dispatch("getJobsByPage", page);
  await scrollToJobsTop();
};
const handleClick = (id) => {
  router.push(`/homepage/listJob/jobDetail/${id}`);
};

const isFavorite = (jobId) => store.getters["favorites/isFavorite"]?.(jobId) ?? false;
const toggleFavorite = (jobId) => {
  store.dispatch("favorites/toggleFavorite", jobId);
};
const handleRandomFilter = () => {
  activeLocation.value = "random";
  selectedProvinceName.value = "";
  const currentPage = store.state.jobs.currentPage || 1;
  store.dispatch("getRandomJobs", { page: currentPage });
  scrollToJobsTop();
};

onMounted(async () => {
  try {
    // Load dữ liệu tỉnh/thành
    isLoadingProvinces.value = true;
    if (!provinces.value || provinces.value.length === 0) {
      await store.dispatch("getAllProvince");
    }
    isLoadingProvinces.value = false;

    // Debug: Log để kiểm tra data
    // console.log("Provinces loaded:", store.state.provinces.provinces);

    // Load jobs - đảm bảo load xong mới render
    await Promise.all([
      store.dispatch("auth/getAllUsers"),
      store.dispatch("getJobsByPage", 1),
    ]);
  } catch (error) {
    console.error("Lỗi khi load dữ liệu:", error);
    isLoadingProvinces.value = false;
  }
});

onUnmounted(() => {
  document.removeEventListener("click", handleClickOutside);
});
</script>

<style scoped>
.header-item {
  color: rgba(188, 34, 40, 1);
  size: 16px;
  font-family: "SF Pro Display", sans-serif;
}
.header-item1 {
  color: rgba(188, 34, 40, 1);
}
.text-ic {
  font-family: "SF Pro Display", sans-serif;
}
.heart {
  border: 1px solid rgba(221, 221, 221, 1);
  border-radius: 4px;
}
.bg-red-700 {
  background-color: #c03428;
}
.text-gray-600 {
  color: rgba(103, 103, 103, 1);
}

/* Dropdown animation */
.dropdown-enter-active,
.dropdown-leave-active {
  transition: all 0.3s ease;
}

.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}

/* Custom scrollbar */
.custom-scrollbar::-webkit-scrollbar {
  width: 6px;
}

.custom-scrollbar::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.custom-scrollbar::-webkit-scrollbar-thumb {
  background: #c0c0c0;
  border-radius: 10px;
}

.custom-scrollbar::-webkit-scrollbar-thumb:hover {
  background: #a0a0a0;
}
</style>
