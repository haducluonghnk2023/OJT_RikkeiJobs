<template>
  <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
    <div class="flex flex-col sm:flex-row flex-wrap gap-3 sm:gap-4">
      <div
        class="flex gap-2 flex-1 min-w-0 min-h-[48px] items-center border-2 rounded-md border-[rgba(221,221,221,1)] bg-[rgba(255,255,255,1)] px-3 sm:px-4 py-2 sm:py-3"
      >
        <font-awesome-icon
          :icon="['fas', 'magnifying-glass']"
          class="text-[rgba(188,34,40,1)] shrink-0"
        />
        <input
          class="text-[rgba(103,103,103,1)] flex-1 min-w-0 border-none outline-none bg-transparent"
          placeholder="Vị trí ứng tuyển"
          v-model="pQ"
        />
      </div>
      <div
        @click="toggleDropdownId"
        class="flex sm:w-[200px] lg:w-[227px] gap-2 h-[48px] px-3 sm:px-4 py-2 sm:py-3 border-[rgba(221,221,221,1)] border-2 bg-[rgba(255,255,255,1)] rounded-md min-w-0 cursor-pointer relative"
      >
        <div class="text-[rgba(103,103,103,1)] min-w-0 flex-1 flex items-center gap-2 truncate">
          <font-awesome-icon
            :icon="['fas', 'briefcase']"
            class="text-[rgba(188,34,40,1)]"
          />

          <span v-if="selectedIndustries" class="ml-1">
            {{ selectedIndustries || "Tất cả ngành nghề" }}
          </span>
          <span v-else class="ml-1"> Tất cả ngành nghề </span>
        </div>
        <div>
          <font-awesome-icon
            :icon="['fas', 'chevron-down']"
            class="text-[rgba(61,61,61,1)] w-[16px] h-[16px]"
            @click="toggleDropdownId"
          />
        </div>
        <ul
          v-if="showDropdownId"
          class="absolute top-full left-0 mt-1 min-w-[180px] sm:min-w-[200px] bg-white text-black shadow-lg rounded-lg z-20 overflow-y-auto max-h-[200px]"
        >
          <li
            v-for="(item, index) in industries"
            :key="index"
            :class="[
              'p-2 hover:bg-gray-200 cursor-pointer',
              { 'bg-blue-100 font-bold': item === selectedIndustries },
            ]"
            @click="selectIndustries(item)"
          >
            {{ item }}
          </li>
        </ul>
      </div>
      <div
        @click="toggleDropdown"
        class="flex sm:w-[180px] lg:w-[204px] gap-2 h-[48px] px-3 sm:px-4 py-2 sm:py-3 border-[rgba(221,221,221,1)] border-2 bg-[rgba(255,255,255,1)] rounded-md min-w-0 cursor-pointer relative"
      >
        <div class="text-[rgba(103,103,103,1)] min-w-0 flex-1 flex items-center gap-2 truncate">
          <font-awesome-icon
            :icon="['fas', 'location-dot']"
            class="text-[rgba(188,34,40,1)]"
          />
          <span v-if="selectedProvince" class="ml-1">
            {{ selectedProvince }}
          </span>
          <span v-else class="ml-1">
            {{ selectedProvince || "Tất cả địa điểm" }}
          </span>
        </div>
        <div>
          <font-awesome-icon
            :icon="['fas', 'chevron-down']"
            class="text-[rgba(61,61,61,1)] w-[16px] h-[16px]"
            @click="toggleDropdown"
          />
        </div>
        <ul
          v-if="showDropdown"
          class="absolute top-full left-0 mt-1 min-w-[180px] sm:min-w-[200px] bg-white text-black shadow-lg rounded-lg z-20 overflow-y-auto max-h-[200px]"
        >
          <li
            v-for="(item, index) in province"
            :key="index"
            :class="[
              'p-2 hover:bg-gray-200 cursor-pointer',
              { 'bg-blue-100 font-bold': item.name === selectedProvince },
            ]"
            @click="selectProvince(item.name)"
          >
            {{ item.name }}
          </li>
        </ul>
      </div>
      <button
        @click="applyFiltersNow"
        class="w-full sm:w-[140px] h-[48px] flex justify-center items-center border-2 border-[rgba(171,31,36,1)] rounded-md bg-[rgba(171,31,36,1)] text-white font-medium"
      >
        Tìm kiếm
      </button>
    </div>
    <!-- ------------------------------- -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3 mt-6 sm:mt-8">
      <div class="text-[rgba(0,0,0,1)] text-xl sm:text-2xl font-bold">
        Tất cả việc làm
      </div>
      <div class="flex items-center gap-3 sm:gap-4">
        <div class="flex w-[179px] h-[20px] gap-[8px]">
          <p>Sắp xếp theo:</p>
          <p>Mới nhất</p>
        </div>
        <div class="w-[60px] h-[24px] gap-[8px]">
          <font-awesome-icon
            @click="disableDisplay()"
            :icon="['fas', 'table-cells-large']"
            :class="{
              'w-[24px] h-[20px]  rounded text-[rgba(188,34,40,1)] border-2 border-[rgba(188,34,40,1)]':
                isSorted == false,
              'w-[24px] h-[20px]  rounded text-[rgba(181,181,181,1)]  border-2 border-[rgba(181,181,181,1)]':
                isSorted == true,
            }"
          />
          <font-awesome-icon
            @click="changeDisplay"
            :icon="['fas', 'table-list']"
            :class="{
              'w-[24px] h-[20px]  rounded text-[rgba(188,34,40,1)] border-2 border-[rgba(188,34,40,1)]':
                isSorted == true,
              'w-[24px] h-[20px]  rounded text-[rgba(181,181,181,1)]  border-2 border-[rgba(181,181,181,1)]':
                isSorted == false,
            }"
          />
        </div>
        <font-awesome-icon
          :icon="['fas', 'rotate-right']"
          class="w-[16px] h-[16px] mt-1"
          @click="refesh"
        />
      </div>
    </div>

    <hr class="my-4 sm:my-6 border-gray-200" />
    <div v-if="isLoadingJobs" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <div
        v-for="idx in 9"
        :key="`job-skeleton-${idx}`"
        class="bg-white/80 rounded-2xl border border-white/80 p-4 sm:p-5"
      >
        <div class="flex gap-4">
          <div class="w-20 h-20 rounded-lg bg-slate-200 skeleton-shimmer"></div>
          <div class="flex-1 space-y-2">
            <div class="h-5 w-5/6 rounded bg-slate-200 skeleton-shimmer"></div>
            <div class="h-4 w-2/3 rounded bg-slate-200 skeleton-shimmer"></div>
            <div class="h-4 w-1/2 rounded bg-slate-200 skeleton-shimmer"></div>
          </div>
        </div>
        <div class="space-y-2 mt-4">
          <div class="h-4 w-full rounded bg-slate-200 skeleton-shimmer"></div>
          <div class="h-4 w-4/5 rounded bg-slate-200 skeleton-shimmer"></div>
        </div>
      </div>
    </div>
    <div
      v-else-if="paginatedJobsWithDaysLeft.length === 0"
      class="empty-state-card p-8 text-center"
    >
      <p class="text-lg font-semibold text-slate-800">Không tìm thấy việc làm phù hợp</p>
      <p class="text-sm text-slate-500 mt-1">
        Hãy thử làm mới bộ lọc hoặc mở rộng phạm vi tìm kiếm.
      </p>
      <button class="gradient-btn mt-4 px-4 h-10 rounded-xl font-medium" @click="refesh">
        Xóa bộ lọc
      </button>
    </div>
    <!-- ---------------------------s------- -->
    <!-- Grid view: giống trang home -->
    <div
      v-else-if="!isSorted"
      class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4"
    >
      <div
        v-for="(job, index) in paginatedJobsWithDaysLeft"
        :key="index"
        class="w-full min-w-0 stagger-item"
        :style="{ '--i': index }"
      >
        <div class="bg-white w-full rounded-2xl shadow-md border border-white/80 p-4 sm:p-5 hover:shadow-lg transition-all duration-300 relative overflow-hidden h-full flex flex-col lift-hover">
          <div class="flex items-start gap-4">
            <img
              :src="job.image"
              alt=""
              class="rounded-md object-cover w-20 h-20 shrink-0"
            />
            <div class="flex flex-col justify-between items-start gap-3 mb-3 min-w-0 flex-1 overflow-hidden">
              <p
                @click="handleClickA(job.id)"
                class="!text-[16px] font-semibold text-red-700 leading-tight hover:cursor-pointer"
              >
                <span class="line-clamp-2">{{ job.title }}</span>
              </p>
              <div class="flex flex-wrap gap-2">
                <p
                  v-for="(edu, eduIndex) in job.rank?.slice(0, 3)"
                  :key="eduIndex"
                  :class="[
                    'px-3 h-[26px] rounded-xl flex justify-center items-center text-[14px] font-semibold',
                    {
                      'bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)]': edu === 'Fresher',
                      'bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]': edu === 'Junior',
                      'bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]': edu === 'Middle',
                      'bg-[rgba(245,246,255,1)] text-[#8c70a7]': edu === 'Senior',
                      'bg-[#f1f2f5] text-[#573775]': edu === 'Lead',
                    },
                  ]"
                >
                  {{ edu }}
                </p>
              </div>
            </div>
          </div>
          <div class="flex flex-wrap items-center gap-3 m-0 p-0 text-[12px] mb-4 min-w-0">
            <div class="flex gap-[8px] items-center">
              <font-awesome-icon :icon="['fas', 'money-bill']" class="text-[rgba(188,34,40,1)]" />
              <span class="truncate max-w-[140px]">{{ formatSalary(job.salary) }}</span>
            </div>
            <div class="flex items-center gap-1 min-w-0">
              <span class="w-4 h-4 shrink-0 flex items-center justify-center rounded-full overflow-hidden">
                <img v-if="vietnamProvinces.includes(job.province)" class="w-2 h-2" :src="vnSvg" alt="" />
              </span>
              <span class="truncate max-w-[140px]">{{ job.province?.replace(/^Thành phố\s*|^Tỉnh\s*/, "") }}</span>
            </div>
            <div class="flex items-center">
              <font-awesome-icon :icon="['fas', 'briefcase']" class="text-[rgba(188,34,40,1)] mr-1 shrink-0" />
              <span class="truncate max-w-[100px]">{{ job.workingTime }}</span>
            </div>
          </div>
          <p class="text-gray-500 text-[12px] leading-tight mt-auto">
            Cập nhật {{ job.timeAgo }} - Còn
            <span class="font-semibold text-black">{{ job.daysLeft }}</span>
            ngày để ứng tuyển
          </p>
          <button
            type="button"
            @click.stop="toggleFavorite(job.id)"
            class="absolute top-4 right-4 favorite-heart-btn"
            :class="isFavorite(job.id) ? 'favorite-heart-btn--active' : 'favorite-heart-btn--idle'"
          >
            <font-awesome-icon :icon="['fas', 'heart']" :class="isFavorite(job.id) ? 'text-red-500' : 'text-[rgba(221,221,221,1)]'" />
          </button>
        </div>
      </div>
    </div>
    <!-- List view -->
    <div v-else class="space-y-4">
      <div
        v-for="(job, index) in paginatedJobsWithDaysLeft"
        :key="index"
        class="flex flex-col sm:flex-row sm:items-center gap-4 p-4 sm:p-5 rounded-2xl border border-white/80 bg-white/85 backdrop-blur min-w-0 lift-hover stagger-item"
        :style="{ '--i': index }"
      >
        <div class="flex flex-1 min-w-0 gap-4">
          <img
            :src="job.image"
            alt=""
            class="w-20 h-20 sm:w-[100px] sm:h-[100px] rounded-md object-cover shrink-0"
          />
          <div class="min-w-0 flex-1">
            <p
              @click="handleClickA(job.id)"
              class="text-base sm:text-lg text-[rgba(17,17,17,1)] hover:cursor-pointer font-bold line-clamp-2"
            >
              {{ job.title }}
            </p>
            <div class="flex flex-wrap gap-2 mt-2">
              <span
                v-for="(edu, eduIndex) in isExpanded ? job.rank : job.rank?.slice(0, 3)"
                :key="eduIndex"
                :class="[
                  'px-3 h-6 rounded-xl flex items-center text-sm font-semibold shrink-0',
                  {
                    'bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)]': edu === 'Fresher',
                    'bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]': edu === 'Junior',
                    'bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]': edu === 'Middle',
                    'bg-[rgba(245,246,255,1)] text-[#8c70a7]': edu === 'Senior',
                    'bg-[#f1f2f5] text-[#573775]': edu === 'Lead',
                  },
                ]"
              >
                {{ edu }}
              </span>
              <button
                v-if="job.rank?.length > 3"
                @click="toggleExpanded"
                class="w-7 h-6 rounded-full bg-gray-200 flex items-center justify-center text-gray-600 text-sm"
              >
                {{ isExpanded ? "-" : "+" + (job.rank?.length - 3) }}
              </button>
            </div>
            <div class="flex flex-wrap items-center gap-3 mt-2 text-[12px] text-gray-600">
              <span class="flex items-center gap-1">
                <font-awesome-icon :icon="['fas', 'clock']" class="text-[rgba(188,34,40,1)]" />
                Cập nhật {{ job.timeAgo }}
              </span>
              <span class="flex items-center gap-1 truncate max-w-[150px]">
                <img v-if="vietnamProvinces.includes(job.province)" class="w-4 h-4" :src="vnSvg" alt="" />
                {{ job.province?.replace(/^Thành phố\s*|^Tỉnh\s*/, "") }}
              </span>
              <span class="flex items-center gap-1">
                <font-awesome-icon :icon="['fas', 'briefcase']" class="text-[rgba(188,34,40,1)]" />
                {{ job.workingTime }}
              </span>
            </div>
            <p class="text-[12px] text-gray-500 mt-1">
              Còn <strong>{{ job.daysLeft }}</strong> ngày để ứng tuyển
            </p>
          </div>
        </div>
        <div class="flex items-center gap-3 shrink-0">
          <span class="text-[rgba(188,34,40,1)] text-base font-bold">{{ formatSalary(job.salary) }}</span>
          <button
            @click="handleClick(job.id)"
            class="px-4 h-10 bg-[rgba(171,31,36,1)] text-white rounded-md text-sm font-medium"
          >
            Ứng tuyển
          </button>
          <button
            type="button"
            @click.stop="toggleFavorite(job.id)"
            class="favorite-heart-btn w-10 h-10 rounded-md border-2 shrink-0"
            :class="isFavorite(job.id) ? 'favorite-heart-btn--active' : 'favorite-heart-btn--idle'"
          >
            <font-awesome-icon :icon="['fas', 'heart']" :class="isFavorite(job.id) ? 'text-red-500' : ''" />
          </button>
        </div>
      </div>
    </div>

    <div v-if="!isLoadingJobs" class="flex flex-wrap items-center justify-center sm:justify-end gap-2 mt-6 pb-8">
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
        v-if="currentPage < totalPages"
        class="w-8 h-8 flex items-center justify-center rounded-full bg-gray-100 text-gray-600"
      >
        &gt;
      </button>
    </div>
  </div>
</template>

<script setup>
import { computed, nextTick, onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { formatSalary } from "@/utils/formatters";
import vnSvg from "@/assets/img/vn.svg";

const store = useStore();
const router = useRouter();
const route = useRoute();

// Các dữ liệu và trạng thái từ Vuex
const jobs = computed(() => store.state.jobs.jobs);
const currentPage = computed(() => store.state.jobs.currentPage);
const totalPages = computed(() => store.getters.totalPagesJob);
const industries = computed(() => store.state.jobs.industries);
const province = computed(() => store.state.provinces.provinces);

// Các giá trị lọc
const selectedProvince = ref(route.query.province || "");
const selectedIndustries = ref(route.query.industry || "");
const pQ = ref(route.query.position || "");

// Quản lí trạng thái
const showDropdown = ref(false);
const showDropdownId = ref(false);
const isSorted = ref(false);
const isExpanded = ref(false);
const isLoadingJobs = ref(true);

const jobItems = ref([]);
const toggleExpanded = () => {
  isExpanded.value = !isExpanded.value;
};

// ----- Auto filter (modern UX): change is applied immediately (debounced) -----
let filterTimer = null;
const buildQuery = () => ({
  position: (pQ.value || "").trim(),
  industry: (selectedIndustries.value || "").trim(),
  province: (selectedProvince.value || "").trim(),
});

const syncQueryToUrl = (query) => {
  const q = {};
  if (query.position) q.position = query.position;
  if (query.industry) q.industry = query.industry;
  if (query.province) q.province = query.province;
  router.replace({ name: route.name, query: q });
};

const applyFilters = async () => {
  const query = buildQuery();
  isLoadingJobs.value = true;
  try {
    // Always start from page 1 when filters change
    store.commit("SET_PAGE", 1);
    await store.dispatch("searchJobs", query);
    syncQueryToUrl(query);
    nextTick(() => {
      jobItems.value[0]?.scrollIntoView({ behavior: "smooth", block: "start" });
    });
  } finally {
    isLoadingJobs.value = false;
  }
};

const applyFiltersDebounced = () => {
  if (filterTimer) clearTimeout(filterTimer);
  filterTimer = setTimeout(() => {
    applyFilters();
  }, 350);
};

const applyFiltersNow = async () => {
  if (filterTimer) clearTimeout(filterTimer);
  await applyFilters();
};
const changeDisplay = () => {
  isSorted.value = true;
};
const disableDisplay = () => {
  isSorted.value = false;
};

const calculateTimeAgo = (updateDate) => {
  const updateDateObj = new Date(updateDate); // Chuyển đổi thành đối tượng Date

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
  const result = []; // Thay vì đối tượng, sử dụng mảng
  jobs.value.forEach((job) => {
    const daysLeft = calculateDaysLeft(job.deadline, job.updateDate); // Tính số ngày còn lại
    const timeAgo = calculateTimeAgo(job.updateDate);

    // Gán đối tượng job vào mảng result
    result.push({ ...job, daysLeft, timeAgo });
  });

  // Đảo ngược mảng
  return result;
});

const visiblePages = computed(() => {
  const pages = [];
  const range = 1; // Số lượng trang liền kề trước và sau trang hiện tại

  if (currentPage.value > range + 1) pages.push(1);
  if (currentPage.value > range + 2) pages.push("...");

  for (
    let i = Math.max(1, currentPage.value - range);
    i <= Math.min(totalPages.value, currentPage.value + range);
    i++
  ) {
    pages.push(i);
  }

  if (currentPage.value < totalPages.value - range - 1) pages.push("...");
  if (currentPage.value < totalPages.value - range)
    pages.push(totalPages.value);

  return pages;
});
const vietnamProvinces = computed(() => {
  return store.state.provinces.provinces.map((province) => province.name);
});

const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};
const toggleDropdownId = () => {
  showDropdownId.value = !showDropdownId.value;
};

const selectProvince = (provinceName) => {
  selectedProvince.value = provinceName;
  showDropdown.value = false;
};
const selectIndustries = (industriesName) => {
  selectedIndustries.value = industriesName;
  // console.log(selectedIndustries.value);

  showDropdownId.value = false;
};

const handleClick = (id) => {
  router.push(`/homepage/listJob/jobDetail/${id}`);
};

watch([pQ, selectedIndustries, selectedProvince], () => {
  applyFiltersDebounced();
});

const handleClickA = async (jobId) => {
  router.push(`/homepage/listJob/jobDetail/${jobId}`);
};

const isFavorite = (jobId) => store.getters["favorites/isFavorite"]?.(jobId) ?? false;
const toggleFavorite = (jobId) => {
  store.dispatch("favorites/toggleFavorite", jobId);
};

const refesh = () => {
  pQ.value = "";
  selectedIndustries.value = "";
  selectedProvince.value = "";
  applyFiltersNow();
};

const changePage = async (page) => {
  if (page === "...") return;
  isLoadingJobs.value = true;
  try {
    store.commit("SET_PAGE", page);
    await nextTick();
    jobItems.value[0]?.scrollIntoView({ behavior: "smooth", block: "start" });
  } finally {
    isLoadingJobs.value = false;
  }
};

const pageSize = computed(() => store.state.jobs.limit || 9);
const paginatedJobsWithDaysLeft = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return jobsWithDaysLeft.value.slice(start, end);
});

onMounted(async () => {
  isLoadingJobs.value = true;
  try {
    // Load full list once so filtering is correct across all jobs
    await store.dispatch("getAllJobs");
    await Promise.all([
      store.dispatch("filteredIndustry"),
      store.dispatch("getAllProvince"),
    ]);
    jobItems.value = jobsWithDaysLeft.value;
    // Apply initial query (if any) without requiring a button click
    if (pQ.value || selectedIndustries.value || selectedProvince.value) {
      await applyFiltersNow();
    }
  } finally {
    isLoadingJobs.value = false;
  }
});
</script>

<style scoped></style>
