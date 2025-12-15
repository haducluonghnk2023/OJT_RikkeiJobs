import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get interview bookings data
 * @returns {Promise<Array>} Array of interview bookings
 */
export const getInterviewData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.INTERVIEW_BOOKINGS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy dữ liệu lịch phỏng vấn");
    throw error;
  }
};
