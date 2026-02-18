import { province } from "@/apis/province/provinceApi";

const provinces = {
  state: {
    provinces: [],
  },
  getters: {},
  mutations: {
    setProvince(state, provinces) {
      state.provinces = Array.isArray(provinces) ? provinces : [];
    },
  },
  actions: {
    getAllProvince: async ({ commit }) => {
      try {
        const data = await province();
        commit("setProvince", data);
      } catch (e) {
        // Don't break the home page if provinces API fails
        commit("setProvince", []);
      }
    },
  },
};
export default provinces;
