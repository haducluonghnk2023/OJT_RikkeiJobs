import { getEnterprisesData } from "@/apis/enterpriseApi";
import { getJobsData, updateJobData } from "@/apis/jobApi";
import enterprises from "./enterprises.module";

const jobs = {
  state: {
    jobs: [],
    enterprises: [],
  },
  getters: {
    Jobs: (state) => {
      return state.jobs.map((job) => {
        const enter = state.enterprises.find(
          (enter) => enter.id === job.enterpriseId
        );

        return {
          ...job,
          enterpriseName: enter ? enter.title : "unknow",
          enterpriseEmail: enter ? enter.email : "unknow",
        };
      });
    },
  },
  mutations: {
    setJobs(state, jobs) {
      state.jobs = jobs;
    },
    updateStatus(state, res) {
      const index = state.jobs.findIndex((item) => item.id === res.id);
      if (index !== -1) {
        state.jobs[index] = res;
      }
    },
    setEnterprises(state, enterprises) {
      state.enterprises = enterprises;
    },
  },
  actions: {
    async getJobs({ commit }) {
      const res = await getJobsData();

      commit("setJobs", res);
    },
    async updateJobStatus({ commit }, newStatus) {
      const res = await updateJobData(newStatus);
      commit("updateStatus", res);
    },
    async getEnterprises({ commit }) {
      const data = await getEnterprisesData();

      commit("setEnterprises", data);
    },
  },
};
export default jobs;
