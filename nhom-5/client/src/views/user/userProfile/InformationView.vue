<template>
  <div class="page-enter max-w-[1200px] mx-auto p-6">
    <context-holder />

    <section class="surface-glass p-5 sm:p-6 mb-5">
      <h2 class="text-[20px] font-[700] mb-1">Thông tin cá nhân</h2>
      <p class="text-sm text-slate-600 mb-0">
        Quản lý hồ sơ để tăng độ tin cậy và cơ hội được nhà tuyển dụng chú ý.
      </p>
    </section>

    <div class="grid grid-cols-1 lg:grid-cols-[260px_1fr] gap-5">
      <aside class="surface-glass p-5">
        <h3 class="font-semibold text-base mb-3">Ảnh đại diện</h3>
        <div
          class="avatar mx-auto cursor-zoom-in"
          @click="openImagePreview"
          title="Bấm để xem ảnh lớn"
        >
          <img v-if="imageSrc" :src="imageSrc" />
          <div v-else class="w-full h-full flex items-center justify-center text-slate-400 text-sm">
            Chưa có ảnh
          </div>
        </div>
        <input
          ref="fileInputRef"
          type="file"
          class="hidden"
          @change="onFileChange"
          accept="image/*"
        />
        <button type="button" class="upload-btn mt-4" @click="triggerFilePicker" :disabled="isUploading">
          Chọn ảnh mới
        </button>
      </aside>

      <section class="surface-glass p-5 sm:p-6">
        <form @submit.prevent="updateProfile" class="space-y-4">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="input-item">
              <label>Tên đăng nhập</label>
              <input v-model="userProfile.userName" placeholder="Tên đăng nhập" />
              <p v-if="errors.userName" class="error">{{ errors.userName }}</p>
            </div>
            <div class="input-item">
              <label>Họ và tên</label>
              <input v-model="userProfile.fullName" placeholder="Họ và tên" />
              <p v-if="errors.fullName" class="error">{{ errors.fullName }}</p>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="input-item">
              <label>Email</label>
              <input disabled v-model="userProfile.email" type="email" placeholder="Email" />
            </div>
            <div class="input-item" v-if="userProfile.role === 'user'">
              <label>Ngày sinh</label>
              <input v-model="userProfile.birthdate" type="date" placeholder="Ngày sinh" />
              <p v-if="errors.birthdate" class="error">{{ errors.birthdate }}</p>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="input-item">
              <label>Số điện thoại</label>
              <input v-model="userProfile.phone" type="tel" placeholder="Số điện thoại" />
              <p v-if="errors.phone" class="error">{{ errors.phone }}</p>
            </div>
            <div class="input-item" v-if="userProfile.role === 'user'">
              <label>Giới tính</label>
              <select v-model="userProfile.gender">
                <option value="male">Nam</option>
                <option value="female">Nữ</option>
                <option value="other">Khác</option>
              </select>
              <p v-if="errors.gender" class="error">{{ errors.gender }}</p>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4" v-if="userProfile.role === 'user'">
            <div class="input-item">
              <label>Vị trí</label>
              <input v-model="userProfile.position" placeholder="Vị trí/Kỹ năng của bạn" />
              <p v-if="errors.position" class="error">{{ errors.position }}</p>
            </div>
            <div class="input-item opacity-80">
              <label>Vai trò</label>
              <input :value="userProfile.role || 'user'" disabled />
            </div>
          </div>

          <div class="flex justify-end gap-2 pt-2">
            <button type="button" class="cancel-btn" @click="cancelUpdate">
              Hủy
            </button>
            <button type="submit" class="update-btn" :disabled="isUploading">
              {{ isUploading ? "Đang lưu..." : "Cập nhật" }}
            </button>
          </div>
        </form>
      </section>
    </div>

    <div
      v-if="isImagePreviewOpen && imageSrc"
      class="fixed inset-0 z-[1200] bg-black/75 backdrop-blur-[2px] flex items-center justify-center p-4"
      @click="closeImagePreview"
    >
      <button
        type="button"
        class="absolute top-5 right-5 w-10 h-10 rounded-full bg-white/15 text-white text-xl flex items-center justify-center border border-white/20 hover:bg-white/25"
        @click.stop="closeImagePreview"
      >
        ×
      </button>
      <img
        :src="imageSrc"
        alt="Avatar preview"
        class="max-w-[92vw] max-h-[88vh] rounded-2xl shadow-2xl object-contain cursor-zoom-out"
        @click.stop
      />
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, onBeforeUnmount, watch, reactive, computed } from "vue";
import { useStore } from "vuex";
import { message } from "ant-design-vue";
import {
  ref as storageRef,
  uploadBytes,
  getDownloadURL,
} from "firebase/storage";
import { storage } from "../../../firebase/firebase";
const [messageApi, contextHolder] = message.useMessage();
const imageSrc = ref(null);
const isUploading = ref(false);
const fileInputRef = ref(null);
const isImagePreviewOpen = ref(false);
const pendingAvatarFile = ref(null);
const pendingAvatarPreviewUrl = ref("");
const store = useStore();

const tokenRaw = localStorage.getItem("token");
const userLoginId = ref(tokenRaw ? JSON.parse(tokenRaw) : null);
const user = computed(() => store.getters.User);

// console.log("test1", user.value);
const userProfile = reactive({
  userName: "",
  fullName: "",
  email: "",
  birthdate: "",
  phone: "",
  gender: "",
  avatar: "",
  level: "",
  position: "",
  yearsOfExperience: "",
});
const errors = reactive({
  userName: "",
  fullName: "",
  birthdate: "",
  phone: "",
  gender: "",
  position: "",
  yearsOfExperience: "",
  level: "",
  role: "",
});
//// hàm bất đồng bộ sẽ mất thời gian để thục hiện=> then sẽ dc chạy khi hàm bdb hoàn tất => để đảm bảo biến user có dữ liệu
onMounted(async () => {
  if (userLoginId.value == null) return;
  if (!user.value || String(user.value.id) !== String(userLoginId.value)) {
    await store.dispatch("getUser", userLoginId.value);
  }

  if (user.value) {
    userProfile.userName = user.value.userName;
    userProfile.fullName = user.value.fullName;
    userProfile.email = user.value.email;
    userProfile.birthdate = user.value.birthdate;
    userProfile.phone = user.value.phone;
    userProfile.gender = user.value.gender;
    userProfile.avatar = user.value.avatar;
    userProfile.position = user.value.position;
    userProfile.yearsOfExperience = user.value.yearsOfExperience;
    userProfile.level = user.value.level;
    userProfile.role = user.value.role;

    if (userProfile.avatar) {
      imageSrc.value = userProfile.avatar;
    }
  }
});

const onFileChange = async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  if (!file.type.startsWith("image/")) {
    messageApi.error("Vui lòng chọn file ảnh hợp lệ.");
    event.target.value = "";
    return;
  }
  if (file.size > 5 * 1024 * 1024) {
    messageApi.error("Ảnh tối đa 5MB.");
    event.target.value = "";
    return;
  }

  if (pendingAvatarPreviewUrl.value) {
    URL.revokeObjectURL(pendingAvatarPreviewUrl.value);
  }

  pendingAvatarFile.value = file;
  pendingAvatarPreviewUrl.value = URL.createObjectURL(file);
  imageSrc.value = pendingAvatarPreviewUrl.value;
  event.target.value = "";
  messageApi.info("Ảnh mới đã được chọn. Bấm 'Cập nhật' để lưu.");
};

const triggerFilePicker = () => {
  fileInputRef.value?.click();
};

const openImagePreview = () => {
  if (!imageSrc.value) return;
  isImagePreviewOpen.value = true;
};

const closeImagePreview = () => {
  isImagePreviewOpen.value = false;
};

watch(isImagePreviewOpen, (isOpen) => {
  document.body.style.overflow = isOpen ? "hidden" : "";
});

const validateForm = () => {
  let isValid = true;

  if (!userProfile.userName) {
    errors.userName = "Tên đăng nhập không được để trống.";
    isValid = false;
  } else {
    errors.userName = "";
  }

  if (!userProfile.fullName) {
    errors.fullName = "Tên không được để trống.";
    isValid = false;
  } else {
    errors.fullName = "";
  }

  if (!userProfile.birthdate) {
    errors.birthdate = "Ngày sinh không được để trống.";
    isValid = false;
  } else {
    errors.birthdate = "";
  }

  if (!checkPhoneNumber(userProfile.phone)) {
    errors.phone = "Số điện thoại không hợp lệ.";
    isValid = false;
  } else {
    errors.phone = "";
  }

  if (!userProfile.gender) {
    errors.gender = "Vui lòng chọn giới tính.";
    isValid = false;
  } else {
    errors.gender = "";
  }

  if (!userProfile.position) {
    errors.position = "Kỹ năng không được để trống.";
    isValid = false;
  } else {
    errors.position = "";
  }

  if (!userProfile.yearsOfExperience) {
    errors.yearsOfExperience = "Số năm làm việc không được để trống.";
    isValid = false;
  } else {
    errors.yearsOfExperience = "";
  }

  if (!userProfile.level) {
    errors.level = "Vui lòng chọn cấp độ.";
    isValid = false;
  } else {
    errors.level = "";
  }

  return isValid;
};

const clearPendingAvatar = () => {
  if (pendingAvatarPreviewUrl.value) {
    URL.revokeObjectURL(pendingAvatarPreviewUrl.value);
  }
  pendingAvatarPreviewUrl.value = "";
  pendingAvatarFile.value = null;
};

const updateProfile = async () => {
  // if (!validateForm()) {
  //   messageApi.error("Có lỗi trong biểu mẫu. Vui lòng kiểm tra lại.");
  //   return;
  // }

  const userId = store.getters.User.id;
  isUploading.value = true;
  try {
    let avatarUrl = userProfile.avatar;
    if (pendingAvatarFile.value) {
      const safeName = pendingAvatarFile.value.name.replace(/\s+/g, "_");
      const fileRef = storageRef(
        storage,
        `avatars/${userId || "user"}_${Date.now()}_${safeName}`
      );
      await uploadBytes(fileRef, pendingAvatarFile.value);
      avatarUrl = await getDownloadURL(fileRef);
    }

    await store.dispatch("updateUser", { ...userProfile, id: userId, avatar: avatarUrl });
    if (userLoginId.value != null) {
      await store.dispatch("getUser", userLoginId.value);
    }
    userProfile.avatar = avatarUrl;
    imageSrc.value = avatarUrl;
    clearPendingAvatar();
    messageApi.success("Cập nhật thành công!");
  } catch (error) {
    messageApi.error("Cập nhật thất bại. Vui lòng thử lại.");
    console.error("update profile error:", error);
  } finally {
    isUploading.value = false;
  }
};
const checkPhoneNumber = (phone) => {
  const phoneString = String(phone);
  const regexPhoneNumber = /(84|0[3|5|7|8|9])+([0-9]{8})\b/g;

  return phoneString.match(regexPhoneNumber) ? true : false;
};

const cancelUpdate = () => {
  if (!user.value) return;
  userProfile.userName = user.value.userName;
  userProfile.fullName = user.value.fullName;
  userProfile.email = user.value.email;
  userProfile.birthdate = user.value.birthdate;
  userProfile.phone = user.value.phone;
  userProfile.gender = user.value.gender;
  userProfile.avatar = user.value.avatar;
  userProfile.position = user.value.position;
  userProfile.yearsOfExperience = user.value.yearsOfExperience;
  userProfile.level = user.value.level;
  imageSrc.value = user.value.avatar || null;
  clearPendingAvatar();
};

onBeforeUnmount(() => {
  document.body.style.overflow = "";
});
</script>

<style scoped>
.avatar {
  width: 160px;
  height: 160px;
  border: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border-radius: 50%;
  margin-bottom: 10px;
}

.avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.input-item {
  display: flex;
  flex-direction: column;
}

.input-item label {
  margin-bottom: 5px;
  font-size: 15px;
  font-weight: 500;
  color: gray;
}

.input-item input {
  width: 100%;
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid #d1d5db;
  box-sizing: border-box;
  background-color: white;
}
.input-item select {
  width: 100%;
  padding: 10px 12px;
  border-radius: 10px;
  border: 1px solid #d1d5db;
  box-sizing: border-box;
  background-color: white;
}

.cancel-btn {
  background-color: #eef1f5;
  color: #111827;
  padding: 10px 28px;
  border: 1px solid #d8dee7;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
}

.update-btn {
  background: linear-gradient(135deg, #ab1f24, #d13037);
  color: white;
  padding: 10px 28px;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
}

.update-btn:hover,
.cancel-btn:hover {
  opacity: 0.9;
}
.error {
  color: #e63946;
  font-size: 12px;
  margin-top: 4px;
}
.upload-btn {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40px;
  border-radius: 10px;
  border: 1px solid #d1d5db;
  background: #fff;
  color: #374151;
  font-size: 14px;
  cursor: pointer;
}
</style>
