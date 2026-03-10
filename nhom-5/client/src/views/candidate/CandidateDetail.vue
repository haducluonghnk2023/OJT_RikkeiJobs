<template>
  <div class="page-enter min-h-screen">
    <div v-if="isOpen">
      <BookingModel @cancel="cancel" />
    </div>
    <Header class="mb-8" />

    <main class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <section class="surface-glass p-5 sm:p-7 lift-hover">
        <div class="flex flex-col lg:flex-row lg:items-start lg:justify-between gap-6">
          <div class="flex gap-5">
            <img
              class="w-[140px] h-[160px] sm:w-[170px] sm:h-[190px] rounded-2xl object-cover shadow-md"
              :src="candidate?.avatar"
              alt=""
            />
            <div>
              <p class="text-2xl sm:text-3xl font-bold text-slate-900">
                {{ candidate?.fullName }}
              </p>
              <div class="flex flex-wrap items-center gap-2 mt-2">
                <span class="text-slate-500 text-sm sm:text-base">
                  {{ calculateAge(candidate?.birthdate) }} tuổi
                </span>
                <span class="px-3 py-1 rounded-full text-sm bg-[var(--primary-soft)] text-[var(--primary)] font-semibold">
                  {{ candidate?.position }}
                </span>
              </div>

              <div class="mt-4 space-y-2 text-[15px]">
                <div class="flex items-center gap-2">
                  <font-awesome-icon :icon="['fas', 'graduation-cap']" class="text-[var(--primary)]" />
                  <span class="font-semibold text-slate-800">Trình độ:</span>
                  <span class="text-slate-600">{{ candidate?.level }}</span>
                </div>
                <div class="flex items-start gap-2">
                  <font-awesome-icon :icon="['fas', 'language']" class="text-[var(--primary)] mt-1" />
                  <span class="font-semibold text-slate-800 min-w-[78px]">Ngoại ngữ:</span>
                  <span class="text-slate-600">
                    <template v-for="(edu, index) in candidate?.foreignLanguage" :key="index">
                      {{ edu }}<span v-if="index < candidate?.foreignLanguage.length - 1">, </span>
                    </template>
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div v-if="isAllowedToViewCVP">
            <button @click="openModal" class="gradient-btn px-5 h-11 rounded-xl font-semibold shadow-md">
              Hẹn lịch phỏng vấn
            </button>
          </div>
        </div>
      </section>

      <section class="mt-8" v-if="isAllowedToViewCV">
        <div class="flex items-center gap-2 mb-4">
          <font-awesome-icon :icon="['fas', 'book']" class="text-[var(--primary)]" />
          <p class="text-lg sm:text-xl font-bold text-slate-800">Danh sách CV</p>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-4 gap-4">
          <a-card
            v-for="cv in filteredCvs"
            :key="cv.id"
            class="rounded-2xl overflow-hidden lift-hover"
            hoverable
            style="width: 100%"
          >
            <template #cover>
              <div class="cv-cover-container bg-gradient-to-t from-[rgba(19,12,45,1)] to-[rgba(19,12,45,0)]">
                <img
                  class="cv-cover-image"
                  alt="example"
                  src="https://i.pinimg.com/236x/02/51/a3/0251a343c25f47b11800d8014364332b.jpg"
                />
                <div class="cv-cover-overlay">
                  <h1 class="font-medium text-base inline-block text-pretty">
                    [CV]{{ cv.pdf }}
                  </h1>
                  <div class="text-xs font-light">
                    Cập nhật lần cuối {{ cv.date }}
                  </div>
                </div>
              </div>
            </template>
            <template #actions>
              <button class="custom-btn">
                <a :href="cv.pdfDataUrl" target="blank" class="flex justify-center items-center gap-1 text-slate-800 hover:text-[var(--primary)]">
                  <font-awesome-icon :icon="['fas', 'eye']" />
                  Xem
                </a>
              </button>
              <div v-if="cv.status === false" class="text-red-500 mt-0 text-sm">
                Đang chờ xét duyệt
              </div>
            </template>
          </a-card>
        </div>
      </section>
    </main>

    <Footer class="mt-10" />
  </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import Footer from "@/layout/Footer.vue";
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import BookingModel from "@/components/BookingModel.vue";
const cancel = () => {
  isOpen.value = false;
};
const store = useStore();
const route = useRoute();
const userId = route.params.id;
const isOpen = ref(false);
const loggedUser = computed(() => {
  const tokenRaw = localStorage.getItem("token");
  const loggedUserId = tokenRaw ? JSON.parse(tokenRaw) : null;
  const fromUserModule = store.state.user?.userLogin || null;
  if (fromUserModule?.id != null) return fromUserModule;
  const list = store.state.auth?.users || [];
  if (loggedUserId == null) return null;
  return list.find((u) => u.id == loggedUserId) || null;
});
// console.log(loggedUser.value);

const isAllowedToViewCV = computed(() => {
  return (
    loggedUser.value?.role === "partner" ||
    loggedUser.value?.role === "admin" ||
    loggedUser.value?.role === "user"
  );
});
const isAllowedToViewCVP = computed(() => {
  return loggedUser.value?.role === "partner";
});

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

const cv = computed(() => store.state.user.cv);

const filteredCvs = computed(() => {
  if (cv.value && cv.value.length > 0) {
    // console.log(loggedUser);

    // console.log(loggedUser.value?.id == userId);
    if (loggedUser.value?.id == userId) {
      // Nếu người dùng hiện tại là người dùng đang đăng nhập, hiển thị tất cả CV của người đó
      return cv.value.filter((item) => item.userId == userId);
    } else {
      // Nếu không phải người dùng đăng nhập, chỉ hiển thị CV có status là true
      // console.log("1111111");

      return cv.value.filter((item) => {
        return item.userId == userId && item.status == true;
      });
    }
  }
  return []; // Trả về mảng rỗng nếu không có dữ liệu
});

// console.log(filteredCvs);

onMounted(async () => {
  await store.dispatch("getDetailCandidate", userId);
  await store.dispatch("auth/getAllUsers");
  await store.dispatch("getCvCdd");
});
const candidate = computed(() => {
  return store.state.candidateDetail.candidateDetail;
});
const openModal = () => {
  isOpen.value = true;
};
</script>

<style scoped>
.custom-btn {
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  border: 1px solid #e2e8f0;
  transition: border-color 220ms ease;
}

.custom-btn:hover {
  border-color: var(--primary);
}

.cv-cover-container {
  position: relative;
  height: 200px;
  overflow: hidden;
}

.cv-cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
  mask-image: linear-gradient(to top, transparent 0%, black 100%);
}

.cv-cover-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 10px;
  color: white;
}
</style>
