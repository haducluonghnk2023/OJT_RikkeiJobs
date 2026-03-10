<template>
  <div class="page-enter min-h-screen relative overflow-hidden">
    <div class="pointer-events-none absolute -top-16 -left-24 w-72 h-72 rounded-full blur-3xl bg-[#c9d4ff]/40"></div>
    <div class="pointer-events-none absolute top-24 -right-24 w-72 h-72 rounded-full blur-3xl bg-[#ffd2d7]/40"></div>
    <Header />
    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-3">
      <div class="surface-glass p-1.5 sm:p-2 rounded-[30px] overflow-hidden">
        <Banner />
      </div>
    </section>
    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-8 mb-12">
      <div class="surface-glass lift-hover p-4 sm:p-6">
        <AllJobs />
      </div>
    </section>
    <Footer />
  </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import Banner from "./Banner.vue";
import AllJobs from "./AllJobs.vue";
import Footer from "@/layout/Footer.vue";
</script>

<style scoped></style>
