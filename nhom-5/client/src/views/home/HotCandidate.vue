<template>
  <div class="bg-[rgba(250,250,250,1)] relative z-10">
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8 py-10">
      <div class="absolute top-0 left-0 -z-10">
        <img :src="decorJobSvg" alt="" />
      </div>
      <!-- Header -->
      <div class="flex flex-col sm:flex-row sm:justify-between sm:items-center gap-3 mb-8 z-10">
        <h2 class="text-xl sm:text-2xl font-bold">Ứng viên nổi bật</h2>
        <router-link
          to="/homepage/candidate"
          class="header-item flex items-center"
        >
          Xem tất cả
          <font-awesome-icon
            :icon="['fas', 'angle-right']"
            class="h-4 w-4 ml-1 header-item"
          />
        </router-link>
      </div>

      <!-- Candidates Grid -->
      <div v-if="limitedCandidates.length > 0" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 sm:gap-4">
        <div
          v-for="candidate in limitedCandidates"
          :key="candidate.id"
          class="w-full p-4 gap-4 bg-[rgba(255,255,255,1)] rounded-lg shadow-md"
          @click="handleClick(candidate.id)"
          :class="{
            'cursor-not-allowed':
              !currentUser || String(currentUser.status || '').toLowerCase() !== 'active',
            'hover:shadow-lg hover:cursor-pointer':
              currentUser && String(currentUser.status || '').toLowerCase() === 'active',
          }"
        >
            <div class="flex w-full justify-between gap-3">
              <div
                class="gap-2 min-w-0"
              >
                <img
                  class="w-12 h-12 img rounded-lg"
                  :src="candidate.avatar"
                  :alt="candidate.name"
                />
                <div class="min-w-0">
                  <p
                    class="text-[rgba(0,0,0,1)] text-[16px] font-semibold truncate"
                  >
                    {{ candidate.fullName }}
                  </p>
                  <p class="text-[rgba(103,103,103,1)] mt-1">
                    {{ calculateAge(candidate.birthdate) }} tuổi
                  </p>
                </div>
              </div>
              <p
                :class="[
                  'px-3 text-[12px] h-[22px] flex items-center justify-center rounded-xl shrink-0',
                  {
                    'bg-[rgba(236,253,243,1)] text-[rgba(2,122,72,1)] ':
                      candidate.position === 'Project Manager',
                    'bg-[rgba(238,244,255,1)] text-[rgba(53,56,205,1)]':
                      candidate.position === 'Full-stack Developer',
                    'bg-[rgba(255,246,237,1)] text-[rgba(196,50,10,1)]':
                      candidate.position === 'Front-end Developer',
                    'bg-[rgba(245,246,255,1)] text-[#8c70a7]':
                      candidate.position === 'Tester',
                    'bg-[#e1e1e6] text-[#c77138]':
                      candidate.position === 'Software Engineer',
                    'bg-[#e1e1e6] text-[#f05151]':
                      candidate.position === 'Team Lead',
                  },
                ]"
              >
                {{ candidate.position }}
              </p>
            </div>
            <hr class="my-2" />

            <!--  -->
            <div>
              <div class="w-full">
                <div class="w-full flex mb-2">
                  <div class="flex items-center w-[16px] h-[16px]">
                    <font-awesome-icon
                      :icon="['fas', 'graduation-cap']"
                      class="text-[rgba(188,34,40,1)]"
                    />
                  </div>

                  <div class="flex gap-3 ml-2 min-w-0">
                    <p
                      class="text-[rgba(45,44,44,1)] text-[14px] min-w-[70px] lv"
                    >
                      Trình độ:
                    </p>
                    <p
                      class="text-[rgba(103,103,103,1)] flex items-center truncate"
                    >
                      {{ candidate.level }}
                    </p>
                  </div>
                </div>

                <div class="flex">
                  <div class="flex items-center w-[16px] h-[16px]">
                    <font-awesome-icon
                      :icon="['fas', 'box-archive']"
                      class="text-[rgba(188,34,40,1)]"
                    />
                  </div>

                  <div class="flex ml-2 min-w-0">
                    <p
                      class="text-[rgba(45,44,44,1)] text-[14px] min-w-[80px] h-[20px] lv"
                    >
                      Ngoại Ngữ:
                    </p>
                    <p
                      v-for="(edu, index) in candidate.foreignLanguage?.slice(
                        0,
                        1
                      )"
                      :key="index"
                      class="ml-0 h-[20px] text-[rgba(103,103,103,1)] flex items-center truncate"
                    >
                      {{ edu }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>
      <div v-else class="text-gray-500 text-sm text-center py-8">
        Chưa có ứng viên nổi bật để hiển thị.
      </div>
      <!--  -->
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import decorJobSvg from "@/assets/decorJob.svg";

const router = useRouter();
const store = useStore();

// candidates list (always array)
const candidates = computed(() => store.state.candidate?.candidates || []);

const handleClick = (id) => {
  if (!currentUser.value || String(currentUser.value?.status || "").toLowerCase() !== "active") {
    return;
  }
  router.push(`/homepage/candidate/candidateDetail/${id}`);
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
const limitedCandidates = computed(() => {
  const returnCandidate = candidates.value.filter(
    (c) =>
      c?.role === "user" &&
      String(c?.status || "").toLowerCase() === "active"
  );
  return returnCandidate.slice(0, 4); // Lấy tối đa 4 ứng viên
});

// current logged-in user comes from `user` module (set by Header.vue)
const currentUser = computed(() => store.state.user?.userLogin || null);

onMounted(() => {
  store.dispatch("getAllCandidates");
});
</script>

<style scoped>
.header-item {
  font-size: 16px;
  font-family: "SF Pro Display", sans-serif;
  color: rgba(188, 34, 40, 1);
}

.img {
  border: 1px solid rgba(204, 204, 204, 1);
  color: rgba(250, 250, 250, 1);
}

.lv {
  font-family: "SF Pro Display", sans-serif;
}

@media (max-width: 768px) {
  .md\:h-\[478px\] {
    height: auto;
  }

  .w-full {
    width: 100%;
  }

  .max-w-\[1440px\] {
    max-width: 100%;
  }

  .p-4 {
    padding: 1rem;
  }

  .max-w-\[282px\] {
    max-width: 100%;
  }

  .h-\[222px\] {
    height: auto;
  }
}
</style>
