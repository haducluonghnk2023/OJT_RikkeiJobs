import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all enterprises
 * @returns {Promise<Array>} Array of enterprises
 */
export const getAllEnterprise = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.ENTERPRISES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách doanh nghiệp");
    throw error;
  }
};

/**
 * Add new enterprise
 * @param {Object} payload - Enterprise data
 * @returns {Promise<Object>} Created enterprise
 */
export const addEnterprise = async (payload) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.ENTERPRISES, payload);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm doanh nghiệp");
    throw error;
  }
};

/**
 * Update enterprise (Note: This seems to update interview-booking, might be a bug)
 * @param {Object} payload - Enterprise data with id
 * @returns {Promise<Object>} Updated enterprise
 */
export const updateEnterprise = async (payload) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.INTERVIEW_BOOKING_BY_ID(payload.id),
      payload
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật doanh nghiệp");
    throw error;
  }
};

/**
 * Get paginated enterprises by user ID
 * @param {Object} payload - { id: userId, page: pageNumber, limit: itemsPerPage }
 * @returns {Promise<Array>} Array of enterprises
 */
export const paginatedEnterprise = async (payload) => {
  try {
    const params = buildQueryString({
      userId: payload.id,
      _page: payload.page,
      _limit: payload.limit,
    });

    const response = await apiClient.get(`${API_ENDPOINTS.ENTERPRISES}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách doanh nghiệp theo trang");
    throw error;
  }
};

/**
 * Search enterprises by name
 * @param {Object} payload - { name: searchTerm }
 * @returns {Promise<Array>} Array of matching enterprises
 */
export const searchEnterprise = async (payload) => {
  try {
    const params = buildQueryString({ name_like: payload.name });
    const response = await apiClient.get(`${API_ENDPOINTS.ENTERPRISES}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "tìm kiếm doanh nghiệp");
    throw error;
  }
};

/**
 * Get enterprise details by ID
 * @param {string|number} id - Enterprise ID
 * @returns {Promise<Object>} Enterprise details
 */
export const getDetailEnterprise = async (id) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.ENTERPRISE_BY_ID(id));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy chi tiết doanh nghiệp");
    throw error;
  }
};
