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
 * Add new interview booking
 * @param {Object} newBooking - Booking data
 * @returns {Promise<Object>} Created booking
 */
export const addInterviewBooking = async (newBooking) => {
  try {
    const response = await apiClient.post(
      API_ENDPOINTS.INTERVIEW_BOOKINGS,
      newBooking
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm lịch phỏng vấn");
    throw error;
  }
};

/**
 * Update interview booking
 * @param {Object} updatedBooking - Booking data with id
 * @returns {Promise<Object>} Updated booking
 */
export const updateInterviewBooking = async (updatedBooking) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.INTERVIEW_BOOKING_BY_ID(updatedBooking.id),
      updatedBooking
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật lịch phỏng vấn");
    throw error;
  }
};

/**
 * Delete interview booking by ID
 * @param {string|number} bookingId - Booking ID
 * @returns {Promise<Object>} Deleted booking
 */
export const deleteInterviewBooking = async (bookingId) => {
  try {
    const response = await apiClient.delete(
      API_ENDPOINTS.INTERVIEW_BOOKING_BY_ID(bookingId)
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "xóa lịch phỏng vấn");
    throw error;
  }
};

/**
 * Get interview booking details by ID
 * @param {string|number} bookingId - Booking ID
 * @returns {Promise<Object>} Booking details
 */
export const getDetailInterviewBooking = async (bookingId) => {
  try {
    const response = await apiClient.get(
      API_ENDPOINTS.INTERVIEW_BOOKING_BY_ID(bookingId)
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy chi tiết lịch phỏng vấn");
    throw error;
  }
};

/**
 * Search interview bookings by keywords
 * @param {string} keywords - Search keywords
 * @returns {Promise<Array>} Array of matching bookings
 */
export const searchInterviewBooking = async (keywords) => {
  try {
    const params = buildQueryString({ title_like: keywords });
    const response = await apiClient.get(
      `${API_ENDPOINTS.INTERVIEW_BOOKINGS}?${params}`
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "tìm kiếm lịch phỏng vấn");
    throw error;
  }
};

/**
 * Get paginated interview bookings by enterprise ID
 * @param {Object} payload - { id: enterpriseId, page: pageNumber, limit: itemsPerPage }
 * @returns {Promise<Array>} Array of bookings
 */
export const paginatedInterviewBooking = async (payload) => {
  try {
    const params = buildQueryString({
      enterpriseId: payload.id,
      _page: payload.page,
      _limit: payload.limit,
    });
    const response = await apiClient.get(
      `${API_ENDPOINTS.INTERVIEW_BOOKINGS}?${params}`
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách lịch phỏng vấn theo trang");
    return [];
  }
};

/**
 * Update interview booking by job ID
 * @param {Object} payload - { jobId: jobId, ...otherFields }
 * @returns {Promise<Object>} Updated booking
 */
export const enterpriseUpdateInterviewBooking = async (payload) => {
  try {
    // Note: This endpoint seems unusual - might need to verify with backend
    const params = buildQueryString({ jobId: payload.jobId });
    const response = await apiClient.patch(
      `${API_ENDPOINTS.INTERVIEW_BOOKINGS}?${params}`,
      payload
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật lịch phỏng vấn theo jobId");
    throw error;
  }
};
