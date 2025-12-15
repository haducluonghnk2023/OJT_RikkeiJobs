import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all active carousel items
 * @returns {Promise<Array>} Array of carousel items
 */
export const getAllCarousel = async () => {
  try {
    const params = buildQueryString({ status: "active" });
    const response = await apiClient.get(`${API_ENDPOINTS.CAROUSEL}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách carousel");
    throw error;
  }
};

/**
 * Add new carousel item
 * @param {Object} newData - Carousel data
 * @returns {Promise<Object>} Created carousel item
 */
export const addCarousel = async (newData) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.CAROUSEL, {
      newData,
    });
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm carousel");
    throw error;
  }
};

/**
 * Update carousel item
 * @param {string|number} id - Carousel ID
 * @param {Object} updatedData - Updated carousel data
 * @returns {Promise<Object>} Updated carousel item
 */
export const updateCarousel = async (id, updatedData) => {
  try {
    const response = await apiClient.put(API_ENDPOINTS.CAROUSEL_BY_ID(id), {
      updatedData,
    });
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật carousel");
    throw error;
  }
};

/**
 * Delete carousel item by ID
 * @param {string|number} id - Carousel ID
 * @returns {Promise<void>}
 */
export const deleteCarousel = async (id) => {
  try {
    await apiClient.delete(API_ENDPOINTS.CAROUSEL_BY_ID(id));
  } catch (error) {
    handleApiError(error, "xóa carousel");
    throw error;
  }
};
