<template>
  <div
    class="relative min-h-[320px] sm:min-h-[380px] lg:min-h-[435px] flex items-center justify-center text-white rounded-[26px] overflow-hidden border border-white/35 shadow-[0_20px_45px_rgba(16,24,40,0.16)]"
  >
    <div
      class="absolute inset-0 bg-cover bg-center"
      :style="{ backgroundImage: `url(${bannerImage})` }"
    ></div>
    <div class="absolute inset-0 banner-overlay"></div>

    <div class="relative flex flex-col items-center gap-6 w-full px-4">
      <h1 class="text-3xl sm:text-4xl lg:text-5xl font-bold m-0 max-w-4xl text-center drop-shadow-sm">
        Tìm kiếm việc làm cùng Rikkei Jobs!
      </h1>

      <div
        class="flex flex-col sm:flex-row items-stretch sm:items-center gap-3 p-3 rounded-2xl w-full max-w-4xl bg-white/12 backdrop-blur-md border border-white/35 shadow-lg"
      >
        <div class="gap-3 flex flex-col sm:flex-row w-full">
          <div class="flex items-center bg-white p-2 rounded-xl w-full h-12 shadow-sm">
            <SearchOutlined class="text-gray-500 w-5 h-5" />
            <input
              type="text"
              placeholder="Vị trí ứng tuyển"
              class="text-gray-700 outline-none ml-2 w-full min-w-0 bg-transparent"
              v-model="positionQuery"
            />
          </div>

          <div
            class="relative flex bg-white p-2 rounded-xl w-full sm:w-60 h-12 items-center justify-between shadow-sm"
          >
            <div class="flex items-center min-w-0">
              <EnvironmentOutlined class="text-gray-500 w-5 h-5" />
              <p class="text-gray-700 ml-2 my-0 truncate">
                {{ selectedProvince || "Tất cả địa điểm" }}
              </p>
            </div>
            <DownOutlined class="text-gray-800 mr-2 cursor-pointer" @click="toggleDropdown" />

            <ul
              v-if="showDropdown"
              class="absolute left-0 top-full mt-2 bg-white text-black w-full shadow-xl rounded-xl z-10 overflow-y-auto max-h-60 border border-slate-100"
            >
              <input
                type="text"
                placeholder="Tìm kiếm"
                class="w-full px-3 py-2 outline-none border-b"
                v-model="searchQuery"
              />
              <li
                v-for="(item, index) in filteredProvinces"
                :key="index"
                class="p-2 hover:bg-gray-100 cursor-pointer"
                @click="selectProvince(item.name)"
              >
                {{ item.name }}
              </li>
            </ul>
          </div>
        </div>

        <button
          @click="searchPosition"
          class="gradient-btn px-6 py-2 rounded-xl w-full sm:w-36 h-12 font-semibold shadow-sm"
        >
          Tìm kiếm
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  SearchOutlined,
  EnvironmentOutlined,
  DownOutlined,
} from "@ant-design/icons-vue";
import { computed, onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import bannerImage from "@/assets/banner/banner_img.svg";

const router = useRouter();
const store = useStore();

// Lấy danh sách tỉnh thành từ Vuex store
const province = computed(() => store.state.provinces.provinces);

// Các state
const showDropdown = ref(false);
const selectedProvince = ref("");
const searchQuery = ref("");
const positionQuery = ref(""); // Từ khóa vị trí ứng tuyển

// Hàm toggle dropdown
const toggleDropdown = () => {
  showDropdown.value = !showDropdown.value;
};

// Hàm chọn tỉnh thành
const selectProvince = (provinceName) => {
  selectedProvince.value = provinceName;
  showDropdown.value = false;
  searchQuery.value = ""; // Reset ô tìm kiếm
};

// Hàm tìm kiếm danh sách tỉnh thành
const filteredProvinces = computed(() =>
  province.value.filter((item) =>
    item.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
);

// Hàm gửi dữ liệu tìm kiếm
const searchPosition = () => {
  const query = {
    position: positionQuery.value, // Dùng ref thay vì querySelector
    province: selectedProvince.value,
  };
  console.log(query);
  router.push({ name: "listJob", query }); // Chuyển hướng với query
};

// Fetch danh sách tỉnh thành khi component được mount
onMounted(() => {
  store.dispatch("getAllProvince");
});
</script>

<style scoped>
.banner-overlay {
  background: linear-gradient(
    180deg,
    rgba(16, 24, 40, 0.25) 0%,
    rgba(16, 24, 40, 0.45) 100%
  );
}
</style>
