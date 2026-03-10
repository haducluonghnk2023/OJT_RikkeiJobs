<template>
  <div class="font-sf-pro-display">
    <router-view v-slot="{ Component }">
      <component :is="Component" />
    </router-view>
  </div>
</template>

<script setup></script>

<style scoped>
.font-sf-pro-display {
  font-family: "SF Pro Display", sans-serif;
}
</style>
