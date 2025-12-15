import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get certificate types data
 * @returns {Promise<Array>} Array of certificate types
 */
export const getCertificateTypesData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CERTIFICATE_TYPES);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách loại chứng chỉ");
    throw error;
  }
};
