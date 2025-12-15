import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get user data by ID
 * @param {string|number} id - User ID
 * @returns {Promise<Object>} User data
 */
export const getUserById = async (id) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USER_BY_ID(id));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy thông tin người dùng");
    throw error;
  }
};

