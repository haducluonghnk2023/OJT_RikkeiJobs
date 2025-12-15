import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get certificate data for user
 * @param {string|number} userId - User ID (currently not used in API call)
 * @returns {Promise<Array>} Array of certificates
 */
export const getCertificateData = async (userId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USER_CERTIFICATES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy dữ liệu chứng chỉ");
    throw error;
  }
};

/**
 * Add new certificate
 * @param {Object} newCertificate - Certificate data
 * @returns {Promise<Object>} Created certificate
 */
export const addCertificateData = async (newCertificate) => {
  try {
    const response = await apiClient.post(
      API_ENDPOINTS.USER_CERTIFICATES,
      newCertificate
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm chứng chỉ");
    throw error;
  }
};

/**
 * Delete certificate by ID
 * @param {string|number} id - Certificate ID
 * @returns {Promise<Object>} Deleted certificate
 */
export const deleteCertificateData = async (id) => {
  try {
    const response = await apiClient.delete(
      API_ENDPOINTS.USER_CERTIFICATE_BY_ID(id)
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "xóa chứng chỉ");
    throw error;
  }
};

/**
 * Update certificate by ID
 * @param {string|number} id - Certificate ID
 * @param {Object} updateCertificate - Updated certificate data
 * @returns {Promise<Object>} Updated certificate
 */
export const updateCertificateData = async (id, updateCertificate) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.USER_CERTIFICATE_BY_ID(id),
      updateCertificate
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật chứng chỉ");
    throw error;
  }
};
