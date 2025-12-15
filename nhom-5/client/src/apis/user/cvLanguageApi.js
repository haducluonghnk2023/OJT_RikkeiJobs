import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get CV languages data (active only)
 * @returns {Promise<Array>} Array of CV languages
 */
export const getCvLanguagesData = async () => {
  try {
    const params = buildQueryString({ status: true });
    const response = await apiClient.get(`${API_ENDPOINTS.CV_LANGUAGES}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách ngôn ngữ CV");
    throw error;
  }
};
