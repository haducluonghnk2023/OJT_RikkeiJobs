<template>
  <div
    class="relative h-[320px] flex items-center justify-center text-white rounded-[26px] overflow-hidden border border-white/40 shadow-[0_16px_36px_rgba(16,24,40,0.16)]"
  >
    <div
      class="absolute inset-0 image-banner"
      :style="{ backgroundImage: `url(${bannerImage})` }"
    ></div>

    <div class="absolute inset-0 bg-black/45"></div>
    <!-- Banner content -->
    <div class="relative z-10 text-center w-full px-4">
      <h1 class="text-4xl md:text-5xl font-bold mb-2">
        Khơi dậy tiềm lực - Mở lối thành công!
      </h1>
      <p class="text-lg mb-4">
        Rikkei Jobs - đồng hành trên phát triển con đường sự nghiệp của bạn.
      </p>

      <!-- Tag list -->
      <div class="flex justify-center space-x-4">
        <span class="tag">Lương cao</span>
        <span class="tag">Phúc lợi hấp dẫn</span>
        <span class="tag">Môi trường chuyên nghiệp</span>
        <span class="tag">Cơ hội thăng tiến</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import bannerImage from "@/assets/banner/banner_img.svg";
</script>

<style scoped>
.image-banner {
  background-size: cover;
  background-position: center;
  height: 100%;
}

.tag {
  display: inline-block;
  background-color: #fff;
  color: #b22222;
  font-weight: 600;
  padding: 8px 12px;
  border-radius: 9999px;
  text-align: center;
}
</style>
