import {
  addCertificateTypeData,
  deleteCertificateData,
  getCertificateTypesData,
  updateCertificateTypeData,
} from "@/apis/certificateApi";

const certificateTypes = {
  state: {
    certificateTypes: [],
  },
  getters: {
    allCertificateTypes: (state) => state.certificateTypes,
  },
  mutations: {
    setCertificateTypes(state, certificateTypes) {
      state.certificateTypes = certificateTypes;
    },
    updateCertificateType(state, updateCertificate) {
      const index = state.certificateTypes.findIndex(
        (item) => item.id === updateCertificate.id
      );
      if (index !== -1) {
        state.certificateTypes[index] = updateCertificate;
      }
    },
    addCertificateType(state, newCertificate) {
      state.certificateTypes.push(newCertificate);
    },
    removeCertificate(state, certificateId) {
      state.certificateTypes = state.certificateTypes.filter(
        (cert) => cert.id !== certificateId
      );
    },
  },
  actions: {
    async getCertificateTypes({ commit }) {
      const data = await getCertificateTypesData();
      commit("setCertificateTypes", data);
    },
    async addCertificateType({ commit }, newCertificate) {
      const data = await addCertificateTypeData(newCertificate);
      commit("addCertificateType", data);
    },

    async updateCertificate({ commit }, updateCertificate) {
      try {
        const res = await updateCertificateTypeData(updateCertificate);
        commit("updateCertificateType", res);
      } catch (error) {
        console.error("Lỗi khi cập nhật loại chứng chỉ:", error);
        throw error;
      }
    },
    async removeCertificate({ commit }, certificateId) {
      await deleteCertificateData(certificateId);
      commit("removeCertificate", certificateId);
    },
  },
};

export default certificateTypes;
