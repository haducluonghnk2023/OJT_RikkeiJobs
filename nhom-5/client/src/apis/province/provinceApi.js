import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";

/**
 * Get provinces data from external API
 * @returns {Promise<Array>} Array of provinces
 */
export const province = async () => {
  try {
    // Use backend proxy to avoid browser CORS issues with provinces.open-api.vn
    const response = await apiClient.get(API_ENDPOINTS.PROVINCES);
    // Backend returns a plain array
    return Array.isArray(response.data?.data) ? response.data.data : response.data;
  } catch (error) {
    console.error("Lỗi khi lấy danh sách tỉnh thành:", error);
    handleApiError(error, "lấy danh sách tỉnh thành");
    throw error;
  }
};
