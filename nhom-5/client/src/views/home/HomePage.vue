<template>
  <div class="page-enter min-h-screen relative overflow-hidden">
    <div class="pointer-events-none absolute -top-20 -left-24 w-72 h-72 rounded-full blur-3xl bg-[#c9d4ff]/50"></div>
    <div class="pointer-events-none absolute top-24 -right-24 w-72 h-72 rounded-full blur-3xl bg-[#ffd2d7]/50"></div>
    <Header />

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-2 sm:mt-3">
      <Banner />
    </section>

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-8">
      <div class="bg-white/85 border border-white/80 rounded-2xl shadow-sm lift-hover p-4 sm:p-6">
        <hot-job />
      </div>
    </section>

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-8">
      <div class="bg-white/85 border border-white/80 rounded-2xl shadow-sm lift-hover p-4 sm:p-6">
        <hot-candidate />
      </div>
    </section>

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-10 mb-12">
      <div class="bg-white/85 border border-white/80 rounded-2xl shadow-sm lift-hover p-4 sm:p-6">
        <outstandingnumbers />
      </div>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import Banner from "@/layout/Banner.vue";
import HotJob from "./HotJob.vue";
import HotCandidate from "./HotCandidate.vue";
import outstandingnumbers from "./Outstandingnumbers.vue";
import Footer from "@/layout/Footer.vue";
</script>

<style scoped></style>
