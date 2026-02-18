<template>
  <a-modal
    :open="true"
    title="Interview Details"
    :footer="null"
    :width="800"
    @cancel="handleClose"
  >
    <div class="flex space-x-8">
      <div class="w-1/2 flex flex-col min-h-[560px]">
        <div class="flex-1 overflow-y-auto pr-2">
          <a-steps direction="vertical" :current="currentStepIndex">
            <a-step
              v-for="(step, index) in stepsWithTimes"
              :key="index"
              :title="step.label"
              :description="step.time"
              :status="getStepStatus(index)"
            >
              <template #icon>
                <component :is="step.icon" />
              </template>
            </a-step>
          </a-steps>
        </div>

        <div class="mt-6 flex gap-3 flex-col">
          <hr />
          <a-button
            type="primary"
            class="bg-blue-500 hover:bg-blue-600 w-full border-none"
            @click="handleMove"
            :disabled="isLastStep || interview.status === 'cancelled'"
          >
            Move to: {{ nextStepTitle }}
          </a-button>
          <hr />
          <a-button
            class="bg-red-500 hover:bg-red-600 w-full border-none"
            @click="showCancelModal"
            :disabled="isLastStep || interview.status === 'cancelled'"
          >
            Cancel Interview
          </a-button>
        </div>
      </div>

      <!-- Rest of the template remains the same -->
      <div class="w-1/2">
        <div class="mb-4">
          <h3 class="text-lg font-semibold">Meeting Time</h3>
          <p class="text-gray-600">
            <strong>Start:</strong> {{ interview?.time || "Not updated" }}
          </p>
          <p class="text-gray-600">
            <strong>Date:</strong> {{ interview?.date || "Not updated" }}
          </p>
          <p class="text-gray-600 truncate">
            <strong>Meeting Link:</strong>
            {{ interview?.meetingLink || "Not updated" }}
          </p>
        </div>

        <!-- Schedule editor (required to move to interview day) -->
        <div class="mb-6 p-4 rounded-lg border border-gray-200">
          <h3 class="text-lg font-semibold mb-3">Cập nhật lịch phỏng vấn</h3>

          <div class="grid grid-cols-2 gap-3">
            <div>
              <div class="text-sm font-medium mb-1">Ngày</div>
              <a-date-picker
                class="w-full"
                :value="scheduleDate"
                format="DD/MM/YYYY"
                @change="(v) => (scheduleDate = v)"
              />
            </div>

            <div>
              <div class="text-sm font-medium mb-1">Giờ</div>
              <a-time-picker
                class="w-full"
                :value="scheduleTime"
                format="HH:mm"
                @change="(v) => (scheduleTime = v)"
              />
            </div>
          </div>

          <div class="mt-3">
            <div class="text-sm font-medium mb-1">Hình thức</div>
            <a-select
              class="w-full"
              :value="scheduleMode"
              @change="(v) => (scheduleMode = v)"
            >
              <a-select-option value="Online">Online</a-select-option>
              <a-select-option value="In-person">In-person</a-select-option>
            </a-select>
          </div>

          <div v-if="scheduleMode === 'Online'" class="mt-3">
            <div class="text-sm font-medium mb-1">Meeting link</div>
            <a-input
              :value="scheduleMeetingLink"
              placeholder="Nhập meeting link"
              @change="(e) => (scheduleMeetingLink = e.target.value)"
            />
          </div>

          <div v-else class="mt-3">
            <div class="grid grid-cols-2 gap-3">
              <div>
                <div class="text-sm font-medium mb-1">Tỉnh/Thành</div>
                <a-input
                  :value="scheduleProvince"
                  placeholder="VD: Hà Nội"
                  @change="(e) => (scheduleProvince = e.target.value)"
                />
              </div>
              <div>
                <div class="text-sm font-medium mb-1">Quận/Huyện</div>
                <a-input
                  :value="scheduleDistrict"
                  placeholder="VD: Cầu Giấy"
                  @change="(e) => (scheduleDistrict = e.target.value)"
                />
              </div>
            </div>
            <div class="mt-3">
              <div class="text-sm font-medium mb-1">Địa chỉ</div>
              <a-input
                :value="scheduleAddress"
                placeholder="VD: Tầng 5, tòa nhà..."
                @change="(e) => (scheduleAddress = e.target.value)"
              />
            </div>
          </div>

          <div class="mt-4 flex justify-end">
            <a-button type="primary" class="bg-blue-500 border-none" @click="saveSchedule">
              Lưu lịch
            </a-button>
          </div>
        </div>

        <div class="mb-4">
          <h3 class="text-lg font-semibold">Enterprise Information</h3>
          <p class="text-gray-600">
            <strong>Name:</strong> {{ enterpriseInfo?.title || "Not updated" }}
          </p>
          <p class="text-gray-600">
            <strong>Email:</strong> {{ enterpriseInfo?.email || "Not updated" }}
          </p>
          <p class="text-gray-600">
            <strong>Phone:</strong>
            {{ enterpriseInfo?.phoneNumber || "Not updated" }}
          </p>
          <p class="text-gray-600">
            <strong>Address:</strong>
            {{ enterpriseInfo?.address || "Not updated" }}
          </p>
        </div>

        <div class="mb-4">
          <h3 class="text-lg font-semibold">Interview Manager</h3>
          <p class="text-gray-600">{{ enterpriseManager }}</p>
        </div>

        <div class="mb-4">
          <h3 class="text-lg font-semibold">Candidate Information</h3>
          <p class="text-gray-600">{{ candidateInfo?.fullName }}</p>
          <p class="text-gray-600">{{ candidateInfo?.email }}</p>
          <p class="text-gray-600">{{ candidateInfo?.phone }}</p>
        </div>
        <div class="" v-if="interview.status == 'cancelled'">
          <h3 class="text-lg font-semibold">Cancelled Reason</h3>
          <p class="text-gray-600">{{ interview?.cancelReason }}</p>
        </div>
      </div>
    </div>

    <!-- Cancel Interview Modal -->
    <a-modal
      :open="cancelModalVisible"
      title="Cancel Interview"
      @ok="confirmCancel"
      @cancel="cancelModalVisible = false"
    >
      <a-form>
        <a-form-item label="Reason for cancellation">
          <a-textarea
            :value="cancelReason"
            @change="
              (event) => {
                cancelReason = event.target.value;
              }
            "
            :rows="4"
            placeholder="Please provide a reason for cancellation"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </a-modal>
</template>

<script setup>
import {
  ClockCircleOutlined,
  CheckCircleOutlined,
  UserOutlined,
  CalendarOutlined,
  TeamOutlined,
  HourglassOutlined,
  FileSearchOutlined,
} from "@ant-design/icons-vue";
import { computed, ref, watch, onMounted } from "vue";
import { useStore } from "vuex";
import { message } from "ant-design-vue";
import dayjs from "dayjs";

const store = useStore();
const props = defineProps({
  inter: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["moveToNext", "cancelInterview", "closeModal"]);

// State
const interview = ref({ ...props.inter });
const cancelModalVisible = ref(false);
const cancelReason = ref("");
const enterpriseInfo = ref(null);
const candidateInfo = ref(null);

// Schedule editor state (kept separate from interview object until save/move)
const scheduleDate = ref(null);
const scheduleTime = ref(null);
const scheduleMode = ref("Online");
const scheduleMeetingLink = ref("");
const scheduleProvince = ref("");
const scheduleDistrict = ref("");
const scheduleAddress = ref("");

// Computed
const enterprises = computed(() => store.state.enterprises.enterprises);
const users = computed(() => store.state.user.users);

const INTERVIEW_STEPS = [
  { code: "pending", label: "Pending", icon: ClockCircleOutlined },
  { code: "enterprise_verified", label: "Enterprise Verified", icon: CheckCircleOutlined },
  { code: "student_verified", label: "Student Verified", icon: UserOutlined },
  { code: "waiting_for_interview_day", label: "Waiting for Interview Day", icon: CalendarOutlined },
  { code: "interviewing", label: "Interviewing", icon: TeamOutlined },
  { code: "waiting_for_result", label: "Waiting for Result", icon: HourglassOutlined },
  { code: "done", label: "Done", icon: FileSearchOutlined },
];

const currentStepIndex = computed(() => {
  if (interview.value.status === "done") {
    return INTERVIEW_STEPS.length - 1;
  }
  return interview.value?.updateStatusTime?.length - 1 || 0;
});
const isLastStep = computed(() => {
  return currentStepIndex.value >= INTERVIEW_STEPS.length - 1;
});

const stepsWithTimes = computed(() => {
  return INTERVIEW_STEPS.map((step, index) => {
    const time = interview.value?.updateStatusTime?.[index];
    return {
      ...step,
      time: time || "pending",
    };
  });
});

const nextStepTitle = computed(() => {
  const nextIndex = currentStepIndex.value + 1;
  return nextIndex < INTERVIEW_STEPS.length
    ? INTERVIEW_STEPS[nextIndex].label
    : "Process Complete";
});

const enterpriseManager = computed(() => {
  const enterprise = enterprises.value?.find(
    (e) => e.id === interview.value?.enterpriseId
  );
  const user = users.value?.find((u) => u.id === enterprise?.userId);
  return user?.fullName || "Not found";
});

const getStepStatus = (index) => {
  if (interview.value.status === "cancelled") return "error";
  if (index === currentStepIndex.value) return "process";
  if (index < currentStepIndex.value) return "finish";
  return "wait";
};

const syncScheduleFromInterview = () => {
  const it = interview.value || {};
  scheduleMode.value = it.interviewMode || "Online";
  scheduleMeetingLink.value = it.meetingLink || "";
  scheduleProvince.value = it.province || "";
  scheduleDistrict.value = it.district || "";
  scheduleAddress.value = it.address || "";

  scheduleDate.value = it.date ? dayjs(it.date, ["DD/MM/YYYY", "YYYY-MM-DD"], true) : null;
  scheduleTime.value = it.time ? dayjs(it.time, ["HH:mm"], true) : null;
};

const validateSchedule = () => {
  const hasDate = !!scheduleDate.value;
  const hasTime = !!scheduleTime.value;
  if (!hasDate || !hasTime) return "Vui lòng nhập ngày và giờ phỏng vấn";

  if (scheduleMode.value === "Online") {
    if (!String(scheduleMeetingLink.value || "").trim()) return "Vui lòng nhập meeting link";
  } else {
    if (!String(scheduleProvince.value || "").trim()) return "Vui lòng nhập tỉnh/thành";
    if (!String(scheduleDistrict.value || "").trim()) return "Vui lòng nhập quận/huyện";
    if (!String(scheduleAddress.value || "").trim()) return "Vui lòng nhập địa chỉ";
  }
  return null;
};

const buildSchedulePayload = () => {
  const payload = {
    interviewMode: scheduleMode.value,
    date: scheduleDate.value ? dayjs(scheduleDate.value).format("DD/MM/YYYY") : null,
    time: scheduleTime.value ? dayjs(scheduleTime.value).format("HH:mm") : null,
  };

  if (scheduleMode.value === "Online") {
    payload.meetingLink = String(scheduleMeetingLink.value || "").trim();
    payload.province = null;
    payload.district = null;
    payload.address = null;
  } else {
    payload.meetingLink = null;
    payload.province = String(scheduleProvince.value || "").trim();
    payload.district = String(scheduleDistrict.value || "").trim();
    payload.address = String(scheduleAddress.value || "").trim();
  }

  return payload;
};

const saveSchedule = async () => {
  const err = validateSchedule();
  if (err) return message.error(err);

  try {
    const updated = {
      ...interview.value,
      ...buildSchedulePayload(),
    };
    await store.dispatch("updatedInterviewBookings", updated);
    interview.value = updated;
    message.success("Đã lưu lịch phỏng vấn");
    await store.dispatch("getAllInterviewBooking");
    emit("moveToNext");
  } catch (e) {
    message.error("Lưu lịch thất bại");
  }
};

watch(
  () => props.inter.value,
  (newValue) => {
    if (newValue.cancelReason) {
      console.log("Cancel reason available:", newValue.cancelReason);
    }
  }
);

const handleMove = async () => {
  if (isLastStep.value) return;

  const nextIndex = currentStepIndex.value + 1;
  const updatedTimes = [...(interview.value.updateStatusTime || [])];
  updatedTimes.push(new Date().toLocaleString());

  const isMovingToLastStep = nextIndex === INTERVIEW_STEPS.length - 1;
  const nextStatus = INTERVIEW_STEPS[nextIndex].code;

  try {
    // Require schedule before moving to (or past) waiting_for_interview_day
    if (nextStatus === "waiting_for_interview_day" || nextStatus === "interviewing" || nextStatus === "waiting_for_result" || nextStatus === "done") {
      const err = validateSchedule();
      if (err) {
        message.error(err);
        return;
      }
    }

    const updatedInterview = {
      ...interview.value,
      updateStatusTime: updatedTimes,
      status: isMovingToLastStep ? "done" : nextStatus,
      ...((nextStatus === "waiting_for_interview_day" || nextStatus === "interviewing" || nextStatus === "waiting_for_result" || nextStatus === "done")
        ? buildSchedulePayload()
        : {}),
    };

    await store.dispatch("updatedInterviewBookings", updatedInterview);
    interview.value = updatedInterview;

    if (updatedInterview.status === "done") {
      message.success("Hoàn tất quy trình phỏng vấn!");
      await store.dispatch("getAllInterviewBooking");
      emit("moveToNext");
      emit("closeModal");
      return;
    }

    message.success("Interview status updated successfully");
    await store.dispatch("getAllInterviewBooking");
    emit("moveToNext");
  } catch (error) {
    message.error("Failed to update interview status");
  }
};
const showCancelModal = () => {
  cancelModalVisible.value = true;
};

const confirmCancel = async () => {
  if (!cancelReason.value.trim()) {
    message.error("Please provide a reason for cancellation");
    return;
  }

  const newValue = {
    ...interview.value,
    cancelReason: cancelReason.value,
    status: "cancelled",
  };

  try {
    await store.dispatch("updatedInterviewBookings", newValue);
    cancelModalVisible.value = false;
    message.success("Interview cancelled successfully");
    await store.dispatch("getAllInterviewBooking");
    interview.value = newValue;
    emit("cancelInterview");
  } catch (error) {
    message.error("Failed to cancel interview");
  }
};

const handleClose = () => {
  emit("closeModal");
};

const fetchEnterpriseInfo = () => {
  if (interview.value?.enterpriseId && enterprises.value) {
    const enterprise = enterprises.value.find(
      (e) => e.id === interview.value.enterpriseId
    );
    if (enterprise) {
      enterpriseInfo.value = {
        title: enterprise.title,
        email: enterprise.email,
        phoneNumber: enterprise.phoneNumber,
        address: enterprise.address,
      };
    }
  }
};

const fetchCandidateInfo = () => {
  if (interview.value?.userId && users.value) {
    const user = users.value.find((u) => u.id === interview.value.userId);
    if (user) {
      candidateInfo.value = {
        fullName: user.fullName,
        email: user.email,
        phone: user.phone,
      };
    }
  }
};

// Watchers
watch(
  [users, enterprises, () => props.inter],
  () => {
    interview.value = { ...props.inter };
    syncScheduleFromInterview();
    fetchCandidateInfo();
    fetchEnterpriseInfo();
  },
  { immediate: true }
);

// Lifecycle
onMounted(() => {
  store.dispatch("getUsers");
  store.dispatch("getEnterprises");
  syncScheduleFromInterview();
});
</script>

<style scoped>
.ant-steps-item-icon {
  background: none;
}
</style>
