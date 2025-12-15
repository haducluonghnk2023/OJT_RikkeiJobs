import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get enterprise information by ID
 * @param {string|number} id - Enterprise ID
 * @returns {Promise<Object>} Enterprise information
 */
export const getEnterpriseInfo = async (id) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.ENTERPRISE_BY_ID(id));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy thông tin doanh nghiệp");
    throw error;
  }
};

/**
 * Update enterprise information
 * @param {Object} enterpriseData - Enterprise data with id
 * @returns {Promise<Object>} Updated enterprise information
 */
export const updateEnterpriseInfo = async (enterpriseData) => {
  try {
    const response = await apiClient.put(
      API_ENDPOINTS.ENTERPRISE_BY_ID(enterpriseData.id),
      enterpriseData
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật thông tin doanh nghiệp");
    throw error;
  }
};
