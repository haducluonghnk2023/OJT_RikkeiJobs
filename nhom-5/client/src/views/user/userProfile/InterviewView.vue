<template>
  <div class="relative w-full h-full page-enter">
    <div class="p-6">
      <div class="max-w-[1200px] mx-auto">
        <section class="surface-glass p-5 sm:p-6 mb-6">
          <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-3">
            <div>
              <h2 class="text-[20px] font-[700] mb-0">Lịch phỏng vấn</h2>
              <p class="font-[400] text-[14px] text-gray-600 mb-0">
                Theo dõi lịch phỏng vấn mới nhất của bạn
              </p>
            </div>
            <div class="flex gap-2">
              <button
                class="px-3 h-9 rounded-xl border text-sm"
                :class="statusFilter === 'all' ? 'gradient-btn border-transparent' : 'bg-white border-slate-200 text-slate-600'"
                @click="statusFilter = 'all'"
              >
                Tất cả
              </button>
              <button
                class="px-3 h-9 rounded-xl border text-sm"
                :class="statusFilter === 'pending' ? 'gradient-btn border-transparent' : 'bg-white border-slate-200 text-slate-600'"
                @click="statusFilter = 'pending'"
              >
                Đang chờ
              </button>
              <button
                class="px-3 h-9 rounded-xl border text-sm"
                :class="statusFilter === 'accepted' ? 'gradient-btn border-transparent' : 'bg-white border-slate-200 text-slate-600'"
                @click="statusFilter = 'accepted'"
              >
                Đã xác nhận
              </button>
            </div>
          </div>
        </section>

        <section v-if="isLoading" class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
          <div
            v-for="idx in 6"
            :key="`interview-skeleton-${idx}`"
            class="bg-white/80 rounded-2xl border border-white/80 p-5"
          >
            <div class="h-5 w-1/3 rounded bg-slate-200 skeleton-shimmer mb-4"></div>
            <div class="flex gap-3 mb-4">
              <div class="w-12 h-12 rounded-lg bg-slate-200 skeleton-shimmer"></div>
              <div class="flex-1 space-y-2">
                <div class="h-4 w-4/5 rounded bg-slate-200 skeleton-shimmer"></div>
                <div class="h-4 w-3/5 rounded bg-slate-200 skeleton-shimmer"></div>
              </div>
            </div>
            <div class="space-y-2">
              <div class="h-4 w-full rounded bg-slate-200 skeleton-shimmer"></div>
              <div class="h-4 w-5/6 rounded bg-slate-200 skeleton-shimmer"></div>
              <div class="h-4 w-4/6 rounded bg-slate-200 skeleton-shimmer"></div>
            </div>
          </div>
        </section>

        <section
          v-else-if="filteredInterviews.length === 0"
          class="empty-state-card p-8 text-center"
        >
          <p class="text-lg font-semibold text-slate-800 mb-1">Chưa có lịch phỏng vấn</p>
          <p class="text-sm text-slate-500 mb-0">
            Khi có lịch mới từ nhà tuyển dụng, thông tin sẽ hiển thị tại đây.
          </p>
        </section>

        <section v-else class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
          <article
            v-for="(inter, index) in filteredInterviews"
            :key="inter.id"
            class="bg-white/85 rounded-2xl border border-white/80 p-5 lift-hover stagger-item shadow-sm"
            :style="{ '--i': index }"
          >
            <div class="flex items-center justify-between mb-3">
              <span
                class="px-3 py-1 rounded-full text-xs font-semibold"
                :class="statusClass(inter.status)"
              >
                {{ statusText(inter.status) }}
              </span>
              <span class="text-xs text-slate-500">
                {{ inter.interviewMode || "Chờ xác nhận" }}
              </span>
            </div>

            <div class="flex gap-3 p-0 mb-3">
              <img
                :src="getAvatar(inter.enterpriseId)"
                alt="Enterprise Avatar"
                class="w-12 h-12 rounded-lg object-cover border border-slate-100"
              />
              <div class="leading-tight">
                <h3 class="font-bold line-clamp-2 text-[15px] mb-1">
                  {{ getJobByName(inter.jobId) }}
                </h3>
                <p class="text-gray-500 text-[13px] font-[400] line-clamp-2 mb-0">
                  {{ getEnterpriseName(inter.enterpriseId) }}
                </p>
              </div>
            </div>

            <a-divider class="my-3"></a-divider>

            <div class="space-y-2 text-[14px]">
              <div class="flex items-start gap-2">
                <CalendarFilled class="icon mt-1" />
                <div>
                  <p class="label mb-0">Ngày</p>
                  <p class="value mb-0">{{ inter.date || "Chờ xác nhận" }}</p>
                </div>
              </div>
              <div class="flex items-start gap-2">
                <ClockCircleFilled class="icon mt-1" />
                <div>
                  <p class="label mb-0">Thời gian</p>
                  <p class="value mb-0">{{ inter.time || "Chờ xác nhận" }}</p>
                </div>
              </div>
              <div class="flex items-start gap-2">
                <EnvironmentFilled class="icon mt-1" />
                <div>
                  <p class="label mb-0">Địa điểm</p>
                  <template v-if="getLocation(inter)">
                    <a
                      v-if="inter.interviewMode === 'Online' && inter.meetingLink"
                      :href="inter.meetingLink"
                      target="_blank"
                      class="value-link"
                    >
                      {{ inter.meetingLink }}
                    </a>
                    <p v-else class="value mb-0">{{ getLocation(inter) }}</p>
                  </template>
                  <p v-else class="value mb-0">Chờ xác nhận</p>
                </div>
              </div>
            </div>
          </article>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { CalendarFilled, ClockCircleFilled, EnvironmentFilled } from "@ant-design/icons-vue";
import { computed, onMounted, ref } from "vue";
import { useStore } from "vuex";

const store = useStore();
const isLoading = ref(true);
const statusFilter = ref("all");

const interview = computed(() => store.getters.Interview || []);
const jobs = computed(() => store.getters.Job || []);
const enterprises = computed(() => store.getters.Enterprises || []);

const getJobByName = (jobId) => {
  const item = jobs.value.find((j) => String(j.id) === String(jobId));
  return item?.title || "Chưa có tên công việc";
};

const getEnterpriseName = (enterpriseId) => {
  const item = enterprises.value.find((e) => String(e.id) === String(enterpriseId));
  return item?.title || "Chưa có tên doanh nghiệp";
};

const getAvatar = (enterpriseId) => {
  const item = enterprises.value.find((e) => String(e.id) === String(enterpriseId));
  return item?.avatar || "https://via.placeholder.com/48x48.png?text=RJ";
};

const normalizeStatus = (status) => (status || "").toString().trim().toLowerCase();

const statusText = (status) => {
  const normalized = normalizeStatus(status);
  if (["accepted", "scheduled", "confirmed"].includes(normalized)) return "Đã xác nhận";
  if (["rejected", "cancelled", "canceled"].includes(normalized)) return "Đã hủy";
  return "Đang chờ";
};

const statusClass = (status) => {
  const normalized = normalizeStatus(status);
  if (["accepted", "scheduled", "confirmed"].includes(normalized)) {
    return "bg-emerald-50 text-emerald-700";
  }
  if (["rejected", "cancelled", "canceled"].includes(normalized)) {
    return "bg-rose-50 text-rose-700";
  }
  return "bg-amber-50 text-amber-700";
};

const getLocation = (inter) => {
  if (!inter) return "";
  if (inter.interviewMode === "Online") return inter.meetingLink || "";
  const parts = [inter.address, inter.district, inter.province].filter(Boolean);
  return parts.join(", ");
};

const filteredInterviews = computed(() => {
  const list = [...interview.value].sort((a, b) => Number(b.id) - Number(a.id));
  if (statusFilter.value === "all") return list;
  if (statusFilter.value === "pending") {
    return list.filter((item) => {
      const s = normalizeStatus(item.status);
      return !["accepted", "scheduled", "confirmed", "rejected", "cancelled", "canceled"].includes(s);
    });
  }
  if (statusFilter.value === "accepted") {
    return list.filter((item) => ["accepted", "scheduled", "confirmed"].includes(normalizeStatus(item.status)));
  }
  return list;
});

onMounted(async () => {
  isLoading.value = true;
  try {
    const userId = JSON.parse(localStorage.getItem("token"));
    const loadTasks = [];
    if (!Array.isArray(store.getters.Job) || store.getters.Job.length === 0) {
      loadTasks.push(store.dispatch("getAllJobs"));
    }
    if (!Array.isArray(store.getters.Enterprises) || store.getters.Enterprises.length === 0) {
      loadTasks.push(store.dispatch("getAllEnterprise"));
    }
    if (loadTasks.length > 0) {
      await Promise.all(loadTasks);
    }
    if (userId) {
      if (!store.getters.User || String(store.getters.User.id) !== String(userId)) {
        await store.dispatch("getUser", userId);
      }
      if (!Array.isArray(store.getters.Interview) || store.getters.Interview.length === 0) {
        await store.dispatch("getInterview");
      }
    }
  } finally {
    isLoading.value = false;
  }
});
</script>

<style scoped>
.icon {
  color: rgba(188, 34, 40, 1);
}
.label {
  color: #6b7280;
  font-size: 12px;
  font-weight: 600;
}
.value {
  color: #1f2937;
  font-size: 14px;
}
.value-link {
  color: #1f2937;
  text-decoration: underline;
  font-size: 14px;
}
</style>
