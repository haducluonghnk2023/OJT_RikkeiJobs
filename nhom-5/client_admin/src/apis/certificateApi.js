import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get certificate types data
 * @returns {Promise<Array>} Array of certificate types
 */
export const getCertificateTypesData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CERTIFICATE_TYPES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách loại chứng chỉ");
    throw error;
  }
};

/**
 * Add new certificate type
 * @param {Object} newCerti - Certificate type data
 * @returns {Promise<Object>} Created certificate type
 */
export const addCertificateTypeData = async (newCerti) => {
  try {
    const response = await apiClient.post(
      API_ENDPOINTS.CERTIFICATE_TYPES,
      newCerti
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm loại chứng chỉ");
    throw error;
  }
};

/**
 * Update certificate type
 * @param {Object} newCerti - Certificate type data with id
 * @returns {Promise<Object>} Updated certificate type
 */
export const updateCertificateTypeData = async (newCerti) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.CERTIFICATE_TYPE_BY_ID(newCerti.id),
      newCerti
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật loại chứng chỉ");
    throw error;
  }
};

/**
 * Delete certificate type by ID
 * @param {string|number} id - Certificate type ID
 * @returns {Promise<void>}
 */
export const deleteCertificateData = async (id) => {
  try {
    await apiClient.delete(API_ENDPOINTS.CERTIFICATE_TYPE_BY_ID(id));
  } catch (error) {
    handleApiError(error, "xóa loại chứng chỉ");
    throw error;
  }
};
