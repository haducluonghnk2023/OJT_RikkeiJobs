import {
  getAllUserData,
  getCvData,
  getUserDatasByPage,
  updateRoleUserData,
  updateStatusUserData,
  updateUserLockStatus,
} from "@/apis/userManagerApi";

const user = {
  state: {
    users: [],
    totalUsers: 0,
    currentPage: 1,
    limit: 10,
    cv: [],
  },
  getters: {},
  mutations: {
    SET_USERS: (state, users) => {
      state.users = users;
    },
    updateUser(state, updatedUser) {
      const index = state.users.findIndex((user) => user.id === updatedUser.id);
      if (index !== -1) {
        state.users.splice(index, 1, updatedUser);
      }
    },
    allUsers: (state, users) => {
      state.users = users;
    },
    UPDATE_USER_STATUS(state, updatedUser) {
      const index = state.users.findIndex((user) => user.id === updatedUser.id);
      if (index !== -1) {
        state.users[index].status = updatedUser.status;
      }
    },
    UPDATE_USER_ROLE(state, updatedUser) {
      const userIndex = state.users.findIndex(
        (user) => user.id === updatedUser.id
      );
      if (userIndex !== -1) {
        // Tạo một object mới để Vue phản ứng lại
        state.users.splice(userIndex, 1, {
          ...state.users[userIndex],
          role: updatedUser.role,
        });
      }
    },
    SET_CV(state, cvData) {
      state.cv = cvData;
    },
  },
  actions: {
    async getUsers({ commit }) {
      try {
        const response = await getAllUserData();
        commit("SET_USERS", response);
      } catch (error) {
        console.error("loi khi lay data:", error);
      }
    },
    async lockUser({ commit }, { userId, lock }) {
      try {
        const updatedUser = await updateUserLockStatus(userId, lock);
        commit("updateUser", updatedUser);
      } catch (error) {
        console.error("Lỗi khi khóa tài khoản:", error);
      }
    },
    async unLockUser({ commit }, { userId, lock }) {
      try {
        const updatedUser = await updateUserLockStatus(userId, lock);
        commit("updateUser", updatedUser);
      } catch (error) {
        console.error("Lỗi khi mở khóa tài khoản:", error);
      }
    },
    async getAllUsers({ commit }) {
      try {
        const res = await getAllUserData();
        commit("allUsers", res);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách người dùng:", error);
      }
    },
    async updateUserStatus({ commit }, { id, status }) {
      try {
        const response = await updateStatusUserData(id, status);
        commit("UPDATE_USER_STATUS", response);
      } catch (error) {
        console.error("Lỗi khi cập nhật trạng thái người dùng:", error);
      }
    },
    async updateUserRole({ commit }, { id, role }) {
      try {
        const response = await updateRoleUserData(id, role);
        commit("UPDATE_USER_ROLE", response);
      } catch (error) {
        console.error("Lỗi khi cập nhật vai trò:", error);
        throw error;
      }
    },
    async getCvCdd({ commit, state }) {
      const allCvs = await getCvData();
      commit("SET_CV", allCvs);
      return allCvs;
    },
  },
};
export default user;
