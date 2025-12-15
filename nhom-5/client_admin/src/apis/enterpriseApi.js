import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all enterprises data
 * @returns {Promise<Array>} Array of enterprises
 */
export const getEnterprisesData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.ENTERPRISES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách doanh nghiệp");
    throw error;
  }
};

/**
 * Update enterprise status
 * @param {Object} enter - Enterprise object with id
 * @returns {Promise<Object>} Updated enterprise data
 */
export const updateEnterpriseStatus = async (enter) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.ENTERPRISE_BY_ID(enter.id),
      enter
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật trạng thái doanh nghiệp");
    throw error;
  }
};
