import {
  addLanguageData,
  deleteLanguageData,
  getLanguageData,
  updateLanguageData,
} from "@/apis/languageApi";

const language = {
  state: {
    language: [],
  },
  getters: {
    Language: (state) => state.language,
  },
  mutations: {
    setLanguage(state, data) {
      state.language = data;
    },
    updateLanguage(state, updateLanguage) {
      const index = state.language.findIndex(
        (item) => item.id === updateLanguage.id
      );

      if (index !== -1) {
        state.language[index] = updateLanguage;
      }
    },
    addLanguage(state, newLanguage) {
      state.language.push(newLanguage);
    },
    removeLanguage(state, languageId) {
      state.language = state.language.filter((cert) => cert.id !== languageId);
    },
  },
  actions: {
    async getLanguage({ commit }) {
      try {
        const data = await getLanguageData();
        commit("setLanguage", data);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách ngôn ngữ:", error);
      }
    },
    async addLanguage({ commit }, newLanguage) {
      try {
        const data = await addLanguageData(newLanguage);
        commit("addLanguage", data);
      } catch (error) {
        console.error("Lỗi khi thêm ngôn ngữ:", error);
        throw error;
      }
    },
    async updateLanguage({ commit }, updateLanguage) {
      try {
        const res = await updateLanguageData(updateLanguage);
        commit("updateLanguage", res);
      } catch (error) {
        console.error("Lỗi khi cập nhật ngôn ngữ:", error);
        throw error;
      }
    },
    async removeLanguage({ commit }, languageId) {
      await deleteLanguageData(languageId);
      commit("removeLanguage", languageId);
    },
  },
};

export default language;
