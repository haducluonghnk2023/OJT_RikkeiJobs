<template>
  <div class="flex min-h-screen w-[100%] h-[680px]">
    <div class="flex flex-col justify-center w-full md:w-1/2 p-12 bg-gray-50">
      <div class="mb-1 absolute top-[-200px] left-[1px] w-[490px] h-[300px]">
        <img class="w-[550px]" :src="decorSvg" alt="" />
      </div>
      <div class="flex relative top-0 mb-8 left-[110px]">
        <img :src="rikkeiSvg" alt="Rikkei" class="h-8" />
        <img :src="jobsSvg" alt="Jobs" class="h-8 ml-1" />
      </div>
      <div class="mx-auto w-[451px] h-[445px]">
        <h2 class="text-3xl font-bold w-[158px]">Đăng nhập</h2>
        <p class="text-gray-600 font-sf-pro-display mb-8">
          Cùng phát triển con đường sự nghiệp với Rikkei Jobs
        </p>

        <form @submit.prevent="handleLogin">
          <div class="mb-4 relative">
            <label class="block text-gray-700 font-sf-pro-display" for="email"
              >Email</label
            >
            <div class="flex items-center border rounded-md">
              <UserOutlined class="text-gray-400 w-5 h-5 absolute left-3" />
              <input
                v-model="formState.email"
                type="email"
                id="email"
                placeholder="you@company.com"
                class="w-full px-10 py-2 border-none rounded-md outline-none focus:border-red-500"
              />
            </div>
          </div>

          <div class="mb-4 relative">
            <label
              class="block font-sf-pro-display text-gray-700"
              for="password"
              >Mật khẩu</label
            >
            <div class="flex items-center border rounded-md">
              <LockOutlined class="text-gray-400 w-5 h-5 absolute left-3" />
              <input
                v-model="formState.password"
                type="password"
                id="password"
                placeholder="********"
                class="w-full px-10 py-2 border-none rounded-md outline-none focus:border-red-500"
              />
            </div>
          </div>

          <div class="flex items-center justify-between mb-4">
            <label class="inline-flex items-center">
              <input type="checkbox" class="form-checkbox text-red-500" />
              <span class="ml-2 text-gray-700 font-sf-pro-display"
                >Ghi nhớ đăng nhập</span
              >
            </label>
            <a href="#" class="text-red-500 text-1xl font-sf-pro-display"
              >Quên mật khẩu?</a
            >
          </div>

          <button
            type="submit"
            :disabled="disabled"
            style="background-color: rgba(188, 34, 40, 1)"
            class="w-full text-white py-2 rounded-md font-sf-pro-display hover:bg-red-600 transition duration-200"
          >
            Đăng nhập
          </button>
        </form>

        <p class="text-center font-sf-pro-display text-gray-600 mt-8">
          Bạn chưa có tài khoản?
          <router-link class="text-red-500 font-sf-pro-display" to="/register"
            >Đăng ký</router-link
          >
        </p>
      </div>
    </div>

    <div class="hidden md:block w-1/2 bg-image-container">
      <div
        class="w-full h-full flex items-center justify-center bg-image"
        :style="{
          backgroundImage:
            'url(https://s3-alpha-sig.figma.com/img/aa61/7314/6c58510196398d3f31c24c25353a36a2?Expires=1733702400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GFaNKyVNJF6f23X9b5Vtr5exJab~uvDzGe1XtwwRY3c~gplubOvWxG4rAA45SmzC7DOE6uZQeegeEtZw5lIACIlj2HO~03HIksO25-7TTLwygUdCM1CQlJlK7psnEIJ0AIHseFHzzbYVm2Xn6kW38aaTODmgK1WCNSlYGAhb37njMSz3ndCByskwJeYpC9zSOJUuwcxa~XGccf5na5fTrQCqHg4~M4RDUQkYMeqVVN9JnF6bfw2W5PQmkAujvQIM47WftO55Rkfk6hSDEpCA-Bp93hHtzIi71pXdm0qH0bFbnfP8oL5oQ73sYXVqh~5UmTB7Z17EAiVLCdCtjcrj4w__)',
        }"
      ></div>
    </div>
  </div>
</template>
<script setup>
import { reactive, onMounted } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { UserOutlined, LockOutlined } from "@ant-design/icons-vue";
import { message } from "ant-design-vue";
import { extractErrorMessage } from "@/utils/apiHelper";
import decorSvg from "@/assets/decor.svg";
import rikkeiSvg from "@/assets/rikkei.svg";
import jobsSvg from "@/assets/jobs.svg";

const store = useStore();
const router = useRouter();

const formState = reactive({
  email: "",
  password: "",
});

const handleLogin = async () => {
  const { email, password } = formState;

  if (!email && !password) {
    message.error("Vui lòng nhập email và mật khẩu");
    return;
  }
  if (!email) {
    message.error("Vui lòng nhập email");
    return;
  }
  if (!password) {
    message.error("Vui lòng nhập mật khẩu");
    return;
  }

  try {
    const credentials = {
      email: formState.email,
      password: formState.password,
    };

    const response = await store.dispatch("auth/loginUser", credentials);

    // extractResponseData đã extract data rồi, nên response là UserResponse object trực tiếp
    if (response) {
      // Chỉ lưu access token, không lưu email và password để bảo mật
      // Store as JSON so other places can safely JSON.parse()
      localStorage.setItem("token", JSON.stringify(response.id));

      message.success("Đăng nhập thành công!");

      // Redirect dựa trên role
      if (response.role === "admin") {
        // Cross-port redirect: localStorage không share giữa 5173 và 5174,
        // nên phải truyền token (ở đây là userId) qua query để admin app nhận và lưu.
        const adminUrl = new URL("http://localhost:5174/admin");
        adminUrl.searchParams.set("token", String(response.id));
        window.location.href = adminUrl.toString();
      } else {
        router.push("/");
      }
    }
  } catch (error) {
    if (error.response) {
      // Lấy message từ backend - ưu tiên message từ server
      const errorMessage = extractErrorMessage(error);
      // Hiển thị message từ backend, không có fallback tự config
      message.error(errorMessage);
    } else {
      // Chỉ hiển thị lỗi network khi không có response
      message.error("Không thể kết nối đến server");
    }
  }
};

onMounted(() => {
  // Không cần load tất cả users nữa vì đã có API login
  // store.dispatch("auth/getAllUsers");
});
</script>

<style scoped>
.bg-image-container {
  position: relative;
  overflow: hidden;
}

.bg-image {
  background-size: cover;
  background-position: right;
  background-repeat: no-repeat;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.font-sf-pro-display {
  font-family: "SF Pro Display", sans-serif;
}
</style>
