import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Add new booking
 * @param {Object} booking - Booking data
 * @returns {Promise<Object>} Created booking
 */
export const addBooking = async (booking) => {
  try {
    const response = await apiClient.post(
      API_ENDPOINTS.INTERVIEW_BOOKINGS,
      booking
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm lịch đặt phỏng vấn");
    throw error;
  }
};
