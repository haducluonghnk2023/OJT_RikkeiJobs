<template>
  <div>
    <Header></Header>
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      <div class="flex">
        <Sidebar />

        <router-view v-slot="{ Component }">
          <keep-alive>
            <component
              :is="Component"
              class="bg-[#fafafa] sm:min-h-[720px] w-full flex-1 min-w-0 px-4 sm:px-6 py-6"
            />
          </keep-alive>
        </router-view>
      </div>
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Sidebar from "./Sidebar.vue";
import Footer from "@/layout/Footer.vue";
import { onMounted } from "vue";
import { useStore } from "vuex";
import Header from "@/layout/Header.vue";

const store = useStore();

onMounted(async () => {
  const userId = JSON.parse(localStorage.getItem("token"));
  if (!userId) return;

  const tasks = [];

  if (!store.getters.User || String(store.getters.User.id) !== String(userId)) {
    tasks.push(store.dispatch("getUser", userId));
  }
  if (!Array.isArray(store.getters.Cv) || store.getters.Cv.length === 0) {
    tasks.push(store.dispatch("getCv"));
  }
  if (!Array.isArray(store.getters.Certificate) || store.getters.Certificate.length === 0) {
    tasks.push(store.dispatch("getCertificate"));
  }
  if (!Array.isArray(store.getters.Interview) || store.getters.Interview.length === 0) {
    tasks.push(store.dispatch("getInterview"));
  }
  if (!Array.isArray(store.getters.Job) || store.getters.Job.length === 0) {
    tasks.push(store.dispatch("getAllJobs"));
  }
  if (!Array.isArray(store.getters.Enterprises) || store.getters.Enterprises.length === 0) {
    tasks.push(store.dispatch("getAllEnterprise"));
  }

  if (tasks.length > 0) {
    await Promise.allSettled(tasks);
  }
});
</script>

<style scoped></style>
