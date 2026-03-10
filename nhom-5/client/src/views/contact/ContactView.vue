<template>
  <div class="page-enter min-h-screen relative overflow-hidden">
    <Header />

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mt-4 mb-10">
      <div class="surface-glass p-6 sm:p-8">
        <h1 class="text-3xl sm:text-4xl font-bold m-0 text-[rgba(23,23,23,1)]">
          Liên hệ với chúng tôi
        </h1>
        <p class="mt-3 mb-0 text-[15px] sm:text-base text-gray-600">
          Để lại thông tin, đội ngũ Rikkei Jobs sẽ phản hồi sớm nhất.
        </p>
      </div>
    </section>

    <section class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 mb-12">
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div class="lg:col-span-2 bg-white/90 border border-white/80 rounded-2xl shadow-sm p-5 sm:p-6">
          <h2 class="text-xl font-semibold mb-4">Gửi tin nhắn</h2>
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <input
              type="text"
              placeholder="Họ và tên"
              class="h-11 px-4 rounded-xl border border-slate-200 outline-none focus:border-[rgba(171,31,36,1)]"
            />
            <input
              type="text"
              placeholder="Số điện thoại"
              class="h-11 px-4 rounded-xl border border-slate-200 outline-none focus:border-[rgba(171,31,36,1)]"
            />
            <input
              type="email"
              placeholder="Email"
              class="h-11 px-4 rounded-xl border border-slate-200 outline-none focus:border-[rgba(171,31,36,1)] sm:col-span-2"
            />
            <textarea
              rows="5"
              placeholder="Nội dung"
              class="px-4 py-3 rounded-xl border border-slate-200 outline-none resize-y focus:border-[rgba(171,31,36,1)] sm:col-span-2"
            ></textarea>
          </div>
          <button class="gradient-btn mt-4 px-6 h-11 rounded-xl font-semibold">
            Gửi liên hệ
          </button>
        </div>

        <div class="bg-white/90 border border-white/80 rounded-2xl shadow-sm p-5 sm:p-6">
          <h2 class="text-xl font-semibold mb-4">Thông tin</h2>
          <div class="text-gray-700 space-y-3 text-sm sm:text-base">
            <p class="m-0">
              <span class="font-semibold">Hotline:</span> 0862 069 233
            </p>
            <p class="m-0">
              <span class="font-semibold">Email:</span> academy@rikkeisoft.com
            </p>
            <p class="m-0">
              <span class="font-semibold">Địa chỉ:</span> Tầng 7, Tháp A, Tòa
              Sông Đà, Đường Phạm Hùng, Mỹ Đình, Nam Từ Liêm, Hà Nội
            </p>
          </div>
        </div>
      </div>
    </section>

    <Footer />
  </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import Footer from "@/layout/Footer.vue";
</script>

<style scoped></style>
