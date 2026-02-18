import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

export const getAllUsers = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USERS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách người dùng");
    throw error;
  }
};

export const register = async (userData) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.REGISTER, userData);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "đăng ký người dùng");
    throw error;
  }
};

/**
 * Login user
 * @param {Object} credentials - Login credentials (email, password)
 * @returns {Promise<Object>} User data and token (if applicable)
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
 * @returns {Promise<Object>} Response wrapper data (if any)
 */
export const logout = async () => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.LOGOUT);
    return extractResponseData(response);
  } catch (error) {
    // Logout should be best-effort; still bubble so caller can ignore if desired
    handleApiError(error, "đăng xuất");
    throw error;
  }
};
