import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get CV languages data
 * @returns {Promise<Array>} Array of CV languages
 */
export const getLanguageData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CV_LANGUAGES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách ngôn ngữ CV");
    throw error;
  }
};

/**
 * Add new CV language
 * @param {Object} newLanguage - Language data
 * @returns {Promise<Object>} Created language
 */
export const addLanguageData = async (newLanguage) => {
  try {
    const response = await apiClient.post(
      API_ENDPOINTS.CV_LANGUAGES,
      newLanguage
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm ngôn ngữ CV");
    throw error;
  }
};

/**
 * Update CV language
 * @param {Object} newLanguage - Language data with id
 * @returns {Promise<Object>} Updated language
 */
export const updateLanguageData = async (newLanguage) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.CV_LANGUAGE_BY_ID(newLanguage.id),
      newLanguage
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật ngôn ngữ CV");
    throw error;
  }
};

/**
 * Delete CV language by ID
 * @param {string|number} id - Language ID
 * @returns {Promise<void>}
 */
export const deleteLanguageData = async (id) => {
  try {
    await apiClient.delete(API_ENDPOINTS.CV_LANGUAGE_BY_ID(id));
  } catch (error) {
    handleApiError(error, "xóa ngôn ngữ CV");
    throw error;
  }
};
