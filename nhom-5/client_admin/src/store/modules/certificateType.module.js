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
    // Normalize API shape for UI:
    // Backend returns `values: [{ id, certificateTypeId, value, ... }]`
    // UI expects `value: string[]` for <a-select mode="tags">
    allCertificateTypes: (state) =>
      (state.certificateTypes || []).map((cert) => {
        const raw = cert?.value ?? cert?.values ?? [];
        const arr = Array.isArray(raw) ? raw : [];

        const value = arr
          .map((v) => (typeof v === "string" ? v : v?.value))
          .filter((v) => typeof v === "string" && v.trim() !== "");

        return {
          ...cert,
          value,
        };
      }),
  },
  mutations: {
    setCertificateTypes(state, certificateTypes) {
      // Ensure state is always an array
      if (Array.isArray(certificateTypes)) {
        state.certificateTypes = certificateTypes;
      } else if (Array.isArray(certificateTypes?.data)) {
        // Defensive: in case caller passes ResponseWrapper by mistake
        state.certificateTypes = certificateTypes.data;
      } else if (certificateTypes) {
        state.certificateTypes = [certificateTypes];
      } else {
        state.certificateTypes = [];
      }
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
      // Defensive: guarantee we commit an array
      const normalized = Array.isArray(data)
        ? data
        : Array.isArray(data?.data)
          ? data.data
          : data
            ? [data]
            : [];
      commit("setCertificateTypes", normalized);
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
