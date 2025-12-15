import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get candidate details by user ID
 * @param {string|number} userId - User ID
 * @returns {Promise<Object>} Candidate details
 */
export const getDetailCandidate = async (userId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CANDIDATE_BY_ID(userId));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy chi tiết ứng viên");
    throw error;
  }
};
