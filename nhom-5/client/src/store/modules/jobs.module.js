import {
  getAllJobs,
  getDistinctIndustries,
  getJobsByLocation,
  getRandomJobs,
  getShowJobByPage,
} from "@/apis/job/jobShowApi";

const jobs = {
  state: {
    jobs: [],
    totalJobs: 0,
    currentPage: 1,
    limit: 9,
    filteredJobs: [],
    industries: [],
  },
  getters: {
    currentPage: (state) => state.currentPage,
    totalPagesJob: (state) => {
      return Math.ceil(state.totalJobs / state.limit);
    },
    activeJobs: (state) => {
      return state.jobs.filter((job) => job.status == true);
    },
    Job: (state) => state.jobs,
  },
  mutations: {
    SET_JOBS: (state, jobs) => {
      state.jobs = jobs;
    },
    SET_JOB: (state, { jobs, totalJobs }) => {
      state.jobs = jobs;
      state.totalJobs = totalJobs;

      // Tính toán lại tổng số trang mỗi khi có thay đổi về công việc
      const totalPages = Math.ceil(totalJobs / state.limit);
      state.totalPages = totalPages; // Lưu tổng số trang vào state
    },
    SET_PAGE: (state, page) => {
      state.currentPage = page;
    },
    SET_FILTERED_JOBS(state, filteredJobs) {
      state.filteredJobs = filteredJobs;
    },
    SET_FILTERED_JOB(state, jobs) {
      state.jobs = jobs;
    },
    SET_INDUSTRIES(state, industries) {
      state.industries = industries;
    },
    SET_RANDOM_JOBS: (state, { jobs, totalJobs }) => {
      state.jobs = jobs;
      state.totalJobs = totalJobs;

      // Tính toán lại tổng số trang mỗi khi có thay đổi về công việc
      const totalPages = Math.ceil(totalJobs / state.limit);
      state.totalPages = totalPages; // Lưu tổng số trang vào state
    },
  },
  actions: {
    getAllJobs: async ({ commit }) => {
      try {
        const jobs = await getAllJobs();
        commit("SET_JOBS", jobs);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách công việc:", error);
      }
    },
    getJobsByPage: async ({ commit, state }, page = 1) => {
      try {
        const { jobs, totalJobs } = await getShowJobByPage(page, state.limit);
        commit("SET_JOB", { jobs, totalJobs });
        commit("SET_PAGE", page);
      } catch (error) {
        console.error("Lỗi khi lấy công việc theo trang:", error);
      }
    },
    async getJobsByLocation({ commit, state }, { location, page = 1 }) {
      try {
        // Gọi API để lấy công việc theo vị trí với phân trang
        const { jobs, totalJobs } = await getJobsByLocation(
          location,
          page,
          state.limit
        );

        // Commit dữ liệu công việc và tổng số công việc vào store
        commit("SET_JOB", { jobs, totalJobs });
        commit("SET_PAGE", page);
      } catch (error) {
        console.error("Lỗi khi lấy công việc theo địa điểm:", error);
      }
    },
    async getRandomJobs({ commit, state }, { page = 1 }) {
      try {
        const { jobs, totalJobs } = await getRandomJobs(page, state.limit);
        // Commit dữ liệu công việc và tổng số công việc vào store
        commit("SET_RANDOM_JOBS", { jobs, totalJobs });
        commit("SET_PAGE", page);
      } catch (error) {
        console.error("Lỗi khi lấy công việc ngẫu nhiên:", error);
      }
    },
    filteredIndustry: async ({ commit }) => {
      try {
        const industries = await getDistinctIndustries();
        commit("SET_INDUSTRIES", industries);
      } catch (error) {
        console.error("Lỗi khi lấy danh sách ngành nghề:", error);
      }
    },
    searchJobs: async ({ commit, state }, query) => {
      try {
        // Nếu không có bất kỳ trường nào trong query, trả về toàn bộ công việc
        if (!query.industry && !query.position && !query.province) {
          commit("SET_FILTERED_JOB", state.jobs);
          return;
        }
        // Lọc công việc theo các tiêu chí trong query
        const filteredJobs = state.jobs.filter((job) => {
          const matchesIndustry = query.industry
            ? job.industry?.toLowerCase().includes(query.industry.toLowerCase())
            : true; // Nếu không có ngành nghề, bỏ qua điều kiện

          const matchesPosition = query.position
            ? job.rank?.some((r) =>
                r.toLowerCase().includes(query.position.toLowerCase())
              ) // Duyệt mảng rank để tìm vị trí phù hợp
            : true; // Nếu không có vị trí, bỏ qua điều kiện

          const matchesProvince = query.province
            ? job.province?.toLowerCase().includes(query.province.toLowerCase())
            : true; // Nếu không có địa điểm, bỏ qua điều kiện

          return matchesIndustry && matchesPosition && matchesProvince;
        });
        const totalJobs = filteredJobs.length;

        // Lưu kết quả tìm kiếm vào state
        commit("SET_FILTERED_JOB", filteredJobs);
        commit("SET_JOB", { jobs: filteredJobs, totalJobs });
      } catch (error) {
        console.error("Lỗi khi tìm kiếm công việc:", error);
      }
    },
  },
};
export default jobs;
