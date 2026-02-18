<template>
  <div>
    <Header />
    <Banner />
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 mt-10 mb-15">
      <h1 class="text-2xl font-bold text-[rgba(17,17,17,1)] mb-6">
        Việc làm đã lưu ({{ favoriteJobs.length }})
      </h1>

      <div v-if="favoriteJobs.length === 0" class="text-center py-16">
        <font-awesome-icon
          :icon="['fas', 'heart']"
          class="text-6xl text-gray-300 mb-4"
        />
        <p class="text-gray-500 text-lg">Chưa có việc làm nào được lưu</p>
        <router-link
          to="/homepage/listJob"
          class="inline-block mt-4 px-6 py-2 bg-[rgba(171,31,36,1)] text-white rounded-md hover:opacity-90"
        >
          Khám phá việc làm
        </router-link>
      </div>

      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        <div
          v-for="(job, index) in favoriteJobs"
          :key="job?.id ?? index"
          class="bg-white w-full min-w-0 rounded-lg shadow-md border border-gray-200 p-4 sm:p-5 hover:shadow-lg transition-shadow relative overflow-hidden"
        >
          <div class="flex gap-5">
            <img
              :src="job.image"
              alt="Job"
              class="rounded-md object-cover mb-4 w-20 h-20 shrink-0"
            />
            <div class="justify-between items-start gap-4 mb-3 min-w-0 flex-1 overflow-hidden">
              <p
                @click="handleClick(job.id)"
                class="!text-[16px] font-semibold text-red-700 leading-tight hover:cursor-pointer"
              >
                <span class="line-clamp-2">{{ job.title }}</span>
              </p>
              <div class="flex flex-wrap gap-2">
                <p
                  v-for="(rank, eduIndex) in job?.rank?.slice(0, 3)"
                  :key="eduIndex"
                  :class="[
                    'px-3 h-[26px] rounded-xl flex justify-center items-center text-[14px] font-semibold',
                    {
                      'bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)]':
                        rank === 'Fresher',
                      'bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]':
                        rank === 'Junior',
                      'bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]':
                        rank === 'Middle',
                      'bg-[rgba(245,246,255,1)] text-[#8c70a7]': rank === 'Senior',
                      'bg-[#f1f2f5] text-[#573775]': rank === 'Lead',
                    },
                  ]"
                >
                  {{ rank }}
                </p>
              </div>
            </div>
          </div>
          <div class="flex flex-wrap items-center gap-x-3 gap-y-1 text-[12px] mb-4">
            <div class="flex gap-[8px] items-center">
              <font-awesome-icon
                :icon="['fas', 'money-bill']"
                class="text-[rgba(188,34,40,1)]"
              />
              <span>{{ formatSalary(job.salary) }}</span>
            </div>
            <div class="flex items-center truncate gap-1">
              <span class="truncate max-w-[160px]">{{ job.province }}</span>
            </div>
            <div class="flex items-center">
              <font-awesome-icon
                :icon="['fas', 'briefcase']"
                class="text-[rgba(188,34,40,1)] mr-1"
              />
              <span>{{ job.workingTime }}</span>
            </div>
          </div>
          <p class="text-gray-500 text-[12px] mb-4 leading-tight break-words">
            Cập nhật {{ job.timeAgo }} - Còn
            <span class="font-semibold text-black">{{ job.daysLeft }}</span>
            ngày để ứng tuyển
          </p>

          <button
            @click.stop="toggleFavorite(job.id)"
            class="absolute w-[32px] top-4 h-[32px] right-4 flex justify-center items-center rounded border border-gray-200 hover:border-red-400 transition-colors"
            :class="{
              'text-red-500 bg-red-50': isFavorite(job.id),
              'text-gray-400': !isFavorite(job.id),
            }"
          >
            <font-awesome-icon
              :icon="['fas', 'heart']"
              :class="{ 'text-red-500': isFavorite(job.id) }"
            />
          </button>
        </div>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { formatSalary } from "@/utils/formatters";
import Header from "@/layout/Header.vue";
import Banner from "./Banner.vue";
import Footer from "@/layout/Footer.vue";

const router = useRouter();
const store = useStore();

const favoriteIds = computed(() => store.getters["favorites/favoriteIds"]);
const allJobs = computed(() => store.state.jobs.allJobs || store.state.jobs.jobs || []);

const calculateTimeAgo = (updateDate) => {
  if (!updateDate) return "";
  const d = new Date(updateDate);
  const now = new Date();
  const diff = now - d;
  const days = Math.floor(diff / (1000 * 60 * 60 * 24));
  const hours = Math.floor(diff / (1000 * 60 * 60));
  if (days > 0) return `${days} ngày trước`;
  if (hours > 0) return `${hours} giờ trước`;
  const mins = Math.floor(diff / (1000 * 60));
  return `${mins} phút trước`;
};

const calculateDaysLeft = (deadline, updateDate) => {
  if (!deadline || !updateDate) return 0;
  try {
    const deadlineDate = new Date(deadline.split("/").reverse().join("-"));
    const updateDateObj = new Date(updateDate);
    const diff = deadlineDate - updateDateObj;
    const days = Math.ceil(diff / (1000 * 60 * 60 * 24));
    return days >= 0 ? days : 0;
  } catch {
    return 0;
  }
};

const favoriteJobs = computed(() => {
  const ids = favoriteIds.value;
  const jobs = allJobs.value;
  return ids
    .map((id) => jobs.find((j) => Number(j.id) === Number(id)))
    .filter(Boolean)
    .map((j) => ({
      ...j,
      timeAgo: calculateTimeAgo(j.updateDate),
      daysLeft: calculateDaysLeft(j.deadline, j.updateDate),
    }));
});

const isFavorite = (jobId) => store.getters["favorites/isFavorite"](jobId);

const toggleFavorite = (jobId) => {
  store.dispatch("favorites/toggleFavorite", jobId);
};

const handleClick = (id) => {
  router.push(`/homepage/listJob/jobDetail/${id}`);
};

onMounted(async () => {
  if (allJobs.value.length === 0) {
    await store.dispatch("getAllJobs");
  }
});
</script>
