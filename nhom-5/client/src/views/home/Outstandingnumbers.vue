<template>
  <div>
    <section
      class="py-12 sm:py-16 dark:bg-red-800 bg-cover bg-center bg-no-repeat overflow-hidden"
      style="
        background-image: url('https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595676/2250f891827ff07cd21ae1be104e11e1b601b7c4_qa1lol.png');
      "
    >
      <div class="max-w-7xl mx-auto px-4">
        <h2 class="text-2xl sm:text-3xl font-bold text-white mb-8 sm:mb-12">
          Tại sao chọn chúng tôi?
        </h2>

        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
            v-for="(stat, index) in stats"
            :key="index"
            v-scroll-reveal="{ delay: 200 }"
            class="bg-white rounded-lg p-6 w-full transform hover:-translate-y-1 transition-transform duration-300"
          >
            <p class="text-gray-600 text-sm mb-2">{{ stat.label }}</p>
            <p class="text-red-600 text-4xl font-bold">{{ stat.value }}</p>
          </div>
        </div>
      </div>
    </section>

    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4">
        <h2 class="text-3xl font-bold text-center mb-12">Đối tác tuyển dụng</h2>

        <div
          class="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-6 gap-4 sm:gap-6 lg:gap-8"
        >
          <div
            v-for="(partner, index) in partners"
            :key="index"
            class="p-4 flex items-center justify-center hover:opacity-75 transition-opacity duration-300"
          >
            <img
              loading="lazy"
              :src="partner.logo"
              :alt="partner.name"
              class="max-h-12 w-auto"
            />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from "vue";

const stats = ref([
  {
    label: "Số năm kinh nghiệm",
    value: "10+",
  },
  {
    label: "Khách hàng quốc tế",
    value: "500+",
  },
  {
    label: "Dự án thành công",
    value: "1000+",
  },
  {
    label: "Văn phòng và chi nhánh",
    value: "10",
  },
  {
    label: "Top công ty Công nghệ tại Việt Nam",
    value: "Top 3",
  },
  {
    label: "Tỷ lệ khách hàng hài lòng",
    value: "97%",
  },
]);

const partners = ref([
  {
    name: "U.S.M.Holdings",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767596162/ba9b90afa95d4df69f56c7044cceaf8db38c3401_ogq6bt.png"
  },
  {
    name: "Studio NE63",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595959/99c14e9beaedef77670568d349ccd38810aca795_oo8va6.png"
  },
  {
    name: "Sunloft",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595958/62fb741d1029c644481ff6fdb28b781bad3418cc_oyr2oz.png"
  },
  {
    name: "Systena",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595958/e5a7d176dccc4222da4aeb757c467033bca0f3c6_dytzsf.png"
  },
  {
    name: "Fujikin",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595958/9e4fcaefa8d37d5d334caf03894ca555fa238a0f_lsa0pa.png"
  },
  {
    name: "Cloud",
    logo:"https://res.cloudinary.com/dlcfbiayq/image/upload/v1767595958/ba0388f8fae8c5e33ba402f5160697ee16424570_nsrnso.png"
  },
]);
</script>

<style scoped></style>
