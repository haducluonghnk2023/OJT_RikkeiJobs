<template>
  <div>
    <Header></Header>
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      <div class="flex">
        <Sidebar />

        <router-view
          class="bg-[#fafafa] sm:min-h-[720px] w-full flex-1 min-w-0 px-4 sm:px-6 py-6"
        ></router-view>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Sidebar from "./Sidebar.vue";
import Footer from "@/layout/Footer.vue";

import Header from "@/layout/Header.vue";
</script>

<style scoped></style>
