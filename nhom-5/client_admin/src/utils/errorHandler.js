import { extractErrorMessage } from "./apiHelper";

/**
 * Handle API errors consistently
 * Ưu tiên lấy message từ backend, chỉ fallback khi không có
 * @param {Error} error - Error object
 * @param {string} context - Context where error occurred (chỉ dùng để log)
 * @returns {string} - Error message từ backend hoặc fallback
 */
export const handleApiError = (error, context = "API") => {
  // Ưu tiên lấy message từ backend
  if (error.response?.data) {
    const backendMessage = extractErrorMessage(error);
    if (backendMessage && backendMessage !== 'An unexpected error occurred') {
      console.error(`[${context}]`, error);
      return backendMessage;
    }
  }

  // Fallback chỉ khi không có response từ server hoặc không có message
  let errorMessage = `Lỗi khi ${context}`;

  if (error.response) {
    // Server responded with error status nhưng không có message
    const status = error.response.status;
    const data = error.response.data;

    // Vẫn ưu tiên lấy từ data.message hoặc data.data
    if (data?.message) {
      errorMessage = data.message;
    } else if (data?.data) {
      errorMessage = typeof data.data === 'string' ? data.data : JSON.stringify(data.data);
    } else {
      // Chỉ fallback khi thực sự không có message từ backend
      switch (status) {
        case 400:
          errorMessage = "Dữ liệu không hợp lệ";
          break;
        case 401:
          errorMessage = "Không có quyền truy cập";
          break;
        case 403:
          errorMessage = "Truy cập bị từ chối";
          break;
        case 404:
          errorMessage = "Không tìm thấy dữ liệu";
          break;
        case 500:
          errorMessage = "Lỗi server, vui lòng thử lại sau";
          break;
        default:
          errorMessage = `Lỗi ${status}`;
      }
    }
  } else if (error.request) {
    // Request made but no response - đây là lỗi network
    errorMessage = "Không thể kết nối đến server";
  } else {
    // Something else happened
    errorMessage = error.message || errorMessage;
  }

  console.error(`[${context}]`, error);
  return errorMessage;
};

