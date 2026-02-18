import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all CVs data
 * @returns {Promise<Array>} Array of CVs
 */
export const getCvsData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CVS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách CV");
    throw error;
  }
};

/**
 * Update CV data
 * @param {Object} cv - CV object with id
 * @returns {Promise<Object>} Updated CV data
 */
export const updateCvData = async (cv) => {
  try {
    const { id, ...payload } = cv || {};
    const response = await apiClient.patch(API_ENDPOINTS.CV_BY_ID(id), payload);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật CV");
    throw error;
  }
};

/**
 * Delete CV by ID
 * @param {string|number} id - CV ID
 * @returns {Promise<void>}
 */
export const deleteCvData = async (id) => {
  try {
    await apiClient.delete(API_ENDPOINTS.CV_BY_ID(id));
  } catch (error) {
    handleApiError(error, "xóa CV");
    throw error;
  }
};
