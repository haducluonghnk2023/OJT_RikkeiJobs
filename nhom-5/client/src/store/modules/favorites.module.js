const STORAGE_KEY = "favorite_job_ids";

const loadFromStorage = () => {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return [];
    const parsed = JSON.parse(raw);
    return Array.isArray(parsed) ? parsed : [];
  } catch {
    return [];
  }
};

const saveToStorage = (ids) => {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(ids));
  } catch (e) {
    console.warn("Lưu yêu thích thất bại:", e);
  }
};

const favorites = {
  namespaced: true,
  state: {
    favoriteIds: loadFromStorage(),
  },
  getters: {
    favoriteIds: (state) => state.favoriteIds,
    isFavorite: (state) => (jobId) => {
      const id = typeof jobId === "number" ? jobId : Number(jobId);
      return state.favoriteIds.includes(id);
    },
    favoriteCount: (state) => state.favoriteIds.length,
  },
  mutations: {
    SET_FAVORITES: (state, ids) => {
      state.favoriteIds = Array.isArray(ids) ? ids : [];
      saveToStorage(state.favoriteIds);
    },
    ADD_FAVORITE: (state, jobId) => {
      const id = typeof jobId === "number" ? jobId : Number(jobId);
      if (!state.favoriteIds.includes(id)) {
        state.favoriteIds = [...state.favoriteIds, id];
        saveToStorage(state.favoriteIds);
      }
    },
    REMOVE_FAVORITE: (state, jobId) => {
      const id = typeof jobId === "number" ? jobId : Number(jobId);
      state.favoriteIds = state.favoriteIds.filter((f) => f !== id);
      saveToStorage(state.favoriteIds);
    },
  },
  actions: {
    toggleFavorite: ({ commit, state }, jobId) => {
      const id = typeof jobId === "number" ? jobId : Number(jobId);
      if (state.favoriteIds.includes(id)) {
        commit("REMOVE_FAVORITE", id);
        return false;
      }
      commit("ADD_FAVORITE", id);
      return true;
    },
    addFavorite: ({ commit }, jobId) => {
      commit("ADD_FAVORITE", jobId);
    },
    removeFavorite: ({ commit }, jobId) => {
      commit("REMOVE_FAVORITE", jobId);
    },
    loadFavorites: ({ commit }) => {
      commit("SET_FAVORITES", loadFromStorage());
    },
  },
};

export default favorites;
