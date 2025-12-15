import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get users by page with pagination
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing users array and totalUsers count
 */
export const getUserDatasByPage = async (page, limit) => {
  try {
    const params = buildQueryString({
      _page: page,
      _limit: limit,
    });

    const response = await apiClient.get(`${API_ENDPOINTS.USERS}?${params}`);
    const totalUsers = parseInt(response.headers["x-total-count"], 10);
    const users = extractResponseData(response);

    return {
      users: users,
      totalUsers: totalUsers,
    };
  } catch (error) {
    const errorMessage = handleApiError(error, "lấy danh sách người dùng");
    throw new Error(errorMessage);
  }
};

/**
 * Update user role
 * @param {string|number} userId - User ID
 * @param {string} roleUser - New role
 * @returns {Promise<Object>} Updated user data
 */
export const updateRoleUserData = async (userId, roleUser) => {
  try {
    const response = await apiClient.patch(API_ENDPOINTS.USER_BY_ID(userId), {
      role: roleUser,
    });
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật vai trò người dùng");
    throw error;
  }
};

/**
 * Update user status
 * @param {string|number} userId - User ID
 * @param {string} statusUser - New status
 * @returns {Promise<Object>} Updated user data
 */
export const updateStatusUserData = async (userId, statusUser) => {
  try {
    // Lấy dữ liệu hiện tại của người dùng
    const userResponse = await apiClient.get(API_ENDPOINTS.USER_BY_ID(userId));
    const currentUserData = extractResponseData(userResponse);

    // Cập nhật trường status
    const updatedData = {
      ...currentUserData,
      status: statusUser,
    };

    // Gửi dữ liệu cập nhật đến API
    const response = await apiClient.put(
      API_ENDPOINTS.USER_BY_ID(userId),
      updatedData
    );

    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật trạng thái người dùng");
    throw error;
  }
};

/**
 * Delete user by ID
 * @param {string|number} userId - User ID
 * @returns {Promise<Object>} Deleted user data
 */
export const deleteUserData = async (userId) => {
  try {
    const response = await apiClient.delete(API_ENDPOINTS.USER_BY_ID(userId));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "xóa người dùng");
    throw error;
  }
};

/**
 * Update user lock status
 * @param {string|number} userId - User ID
 * @param {boolean} lock - Lock status (true or false)
 * @returns {Promise<Object>} Updated user data
 */
export const updateUserLockStatus = async (userId, lock) => {
  try {
    const response = await apiClient.patch(API_ENDPOINTS.USER_BY_ID(userId), {
      lock: lock,
    });
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật trạng thái khóa tài khoản");
    throw error;
  }
};

/**
 * Get all users
 * @returns {Promise<Array>} Array of users
 */
export const getAllUserData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.USERS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách người dùng");
    throw error;
  }
};

/**
 * Get CV data (all CVs)
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
