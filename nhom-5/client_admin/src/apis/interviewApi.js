import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all interview bookings
 * @returns {Promise<Array>} Array of interview bookings
 */
export const getAllInterviewBooking = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.INTERVIEW_BOOKINGS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách lịch phỏng vấn");
    throw error;
  }
};

/**
 * Get paginated interview bookings
 * @param {Object} payload - { page: pageNumber, limit: itemsPerPage }
 * @returns {Promise<Array>} Array of interview bookings
 */
export const paginatedInterviewBookings = async (payload) => {
  try {
    const params = buildQueryString({
      _page: payload.page,
      _limit: payload.limit,
    });

    const response = await apiClient.get(
      `${API_ENDPOINTS.INTERVIEW_BOOKINGS}?${params}`
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách lịch phỏng vấn theo trang");
    throw error;
  }
};

/**
 * Search interview booking by enterprise title
 * @param {Object} payload - { title: searchTerm }
 * @returns {Promise<Array>} Array of matching enterprises
 */
export const searchInterviewBooking = async (payload) => {
  try {
    const params = buildQueryString({ title_like: payload.title });
    const response = await apiClient.get(
      `${API_ENDPOINTS.ENTERPRISES}?${params}`
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "tìm kiếm lịch phỏng vấn");
    throw error;
  }
};

/**
 * Update interview booking
 * @param {Object} payload - Booking data with id
 * @returns {Promise<Object>} Updated booking
 */
export const updatedInterviewBookings = async (payload) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.INTERVIEW_BOOKING_BY_ID(payload.id),
      payload
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật lịch phỏng vấn");
    throw error;
  }
};

/**
 * Get all jobs
 * @returns {Promise<Array>} Array of jobs
 */
export const getAllJob = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách công việc");
    throw error;
  }
};
