import {
  getAllCandidates,
  getShowCandidateByPageA,
  getShowCandidateByPageDE,
} from "@/apis/candidate/candidateShowApi";
const candidate = {
  state: {
    candidates: [],
    totalCandidates: 0,
    currentPage: 1,
    limit: 8,
  },
  getters: {
    activeCandidates: (state) => {
      return state.candidates.filter(
        (candidate) => String(candidate?.status || "").toLowerCase() === "active"
      );
    },
    totalPagesCandidate: (state) => {
      return Math.ceil(state.totalCandidates / state.limit);
    },
  },
  mutations: {
    SET_CANDIDATES: (state, { candidates, totalCandidates }) => {
      state.totalCandidates = totalCandidates;
      state.candidates = candidates;
    },
    SET_PAGE: (state, page) => {
      state.currentPage = page;
    },
    SET_TOTAL_CANDIDATES(state, totalCandidates) {
      state.totalCandidates = totalCandidates;
    },
    SET_CANDIDATE: (state, candidate) => {
      state.candidates = candidate;
    },
  },
  actions: {
    getCandidatesByPageA: async ({ commit, state }, page = 1) => {
      try {
        const { candidates, totalCandidates } = await getShowCandidateByPageA(
          page,
          state.limit
        );
        // console.log("API response:", { candidates, totalCandidates });

        // Lọc chỉ các ứng viên active + role user
        const filteredCandidates = candidates.filter(
          (candidate) =>
            candidate?.role === "user" &&
            String(candidate?.status || "").toLowerCase() === "active"
        );
        // console.log(filteredCandidates);

        commit("SET_CANDIDATES", {
          candidates: filteredCandidates,
          totalCandidates,
        });
        commit("SET_PAGE", page);
      } catch (error) {
        console.error("Lỗi khi lấy ứng viên:", error);
      }
    },

    getCandidatesByPageDe: async ({ commit, state }, page = 1) => {
      try {
        const { candidates, totalCandidates } = await getShowCandidateByPageDE(
          page,
          state.limit
        );
        commit("SET_CANDIDATES", { candidates, totalCandidates });
        commit("SET_PAGE", page);
      } catch (error) {
        console.error("lỗi khi lấy ứng viên:", error);
      }
    },
    getAllCandidates: async ({ commit }) => {
      try {
        const candidates = await getAllCandidates();
        const list = Array.isArray(candidates) ? candidates : [];
        // Ensure only active "user" role candidates are shown in candidate flows
        const filtered = list.filter(
          (c) =>
            c?.role === "user" &&
            String(c?.status || "").toLowerCase() === "active"
        );
        commit("SET_CANDIDATE", filtered);
        return filtered;
      } catch (error) {
        console.error("Lỗi khi lấy danh sách ứng viên:", error);
        throw error;
      }
    },
  },
};

export default candidate;
