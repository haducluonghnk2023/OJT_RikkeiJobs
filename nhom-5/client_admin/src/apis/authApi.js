import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Login user
 * @param {Object} credentials - Login credentials (email, password)
 * @returns {Promise<Object>} User data
 */
export const login = async (credentials) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.LOGIN, credentials);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "đăng nhập");
    throw error;
  }
};

/**
 * Logout (server clears cookies/session if any)
 */
export const logout = async () => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.LOGOUT);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "đăng xuất");
    throw error;
  }
};

