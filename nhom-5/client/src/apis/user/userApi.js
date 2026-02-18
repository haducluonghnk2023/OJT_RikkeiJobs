import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get user data by ID
 * @param {string|number} id - User ID
 * @returns {Promise<Object>} User data
 */
export const getUserData = async (id) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USER_BY_ID(id));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy thông tin người dùng");
    throw error;
  }
};

/**
 * Update user data
 * @param {Object} updatedUser - Updated user object with id
 * @returns {Promise<Object>} Updated user data
 */
export const updateUserData = async (updatedUser) => {
  try {
    const response = await apiClient.put(
      API_ENDPOINTS.USER_BY_ID(updatedUser.id),
      updatedUser
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật thông tin người dùng");
    throw error;
  }
};

/**
 * Get all users
 * @returns {Promise<Array>} Array of users
 */
export const getAllUsers = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USERS);
    console.log(11111,response);
    
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách người dùng");
    throw error;
  }
};
