import { deleteCvData, getCvsData, updateCvData } from "@/apis/cvApi";
import { getAllUserData } from "@/apis/userManagerApi";

const cvs = {
  state: {
    cvs: [],
    users: [],
  },
  getters: {
    // Cvs: (state) => state.cvs,
    Cvs: (state) => {
      return state.cvs.map((cv) => {
        const user = state.users.find((user) => user.id === cv.userId);
        return {
          ...cv,
          userName: user ? user.fullName : "Unknown",
        };
      });
    },
  },
  mutations: {
    setCvs(state, cvs) {
      state.cvs = cvs;
    },
    updateCvStatus(state, cv) {
      const index = state.cvs.findIndex((item) => item.id === cv.id);

      if (index !== -1) {
        state.cvs[index] = cv;
      }
    },

    deleteCvData(state, cvId) {
      state.cvs = state.cvs.filter((cv) => cv.id !== cvId);
    },
    allUsers: (state, users) => {
      state.users = users;
    },
  },
  actions: {
    async getCvs({ commit }) {
      const data = await getCvsData();
      commit("setCvs", data);
    },

    async updateCvStatus({ commit }, cv) {
      try {
        const res = await updateCvData(cv);
        commit("updateCvStatus", res);
      } catch (error) {
        console.error("Lỗi khi cập nhật CV:", error);
        throw error;
      }
    },
    async deleteCv({ commit }, cvId) {
      try {
        await deleteCvData(cvId);
        commit("deleteCvData", cvId);
      } catch (error) {
        console.error("Lỗi khi xóa CV:", error);
        throw error;
      }
    },
    async getAllUsers({ commit }) {
      const res = await getAllUserData();
      // console.log(res);
      commit("allUsers", res);
    },
  },
};

export default cvs;
