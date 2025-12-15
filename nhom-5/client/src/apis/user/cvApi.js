import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get CV data for user
 * @param {string|number} userId - User ID (currently not used in API call)
 * @returns {Promise<Array>} Array of CVs
 */
export const getCvData = async (userId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CVS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy dữ liệu CV");
    throw error;
  }
};

/**
 * Add new CV
 * @param {Object} newCv - CV data
 * @returns {Promise<Object>} Created CV
 */
export const addCvData = async (newCv) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.CVS, newCv);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm CV");
    throw error;
  }
};

/**
 * Delete CV by ID
 * @param {string|number} id - CV ID
 * @returns {Promise<Object>} Deleted CV
 */
export const deleteCvData = async (id) => {
  try {
    const response = await apiClient.delete(API_ENDPOINTS.CV_BY_ID(id));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "xóa CV");
    throw error;
  }
};
