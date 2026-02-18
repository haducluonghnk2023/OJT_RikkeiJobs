import { extractErrorMessage } from "./apiHelper";

export const handleApiError = (error, context = "API") => {
  if (error.response?.data) {
    const backendMessage = extractErrorMessage(error);
    if (backendMessage && backendMessage !== "An unexpected error occurred") {
      console.error(`[${context}]`, error);
      return backendMessage;
    }
  }

  let errorMessage = `Lỗi khi ${context}`;

  if (error.response) {
    const status = error.response.status;
    const data = error.response.data;

    if (data?.message) {
      errorMessage = data.message;
    } else if (data?.data) {
      errorMessage =
        typeof data.data === "string" ? data.data : JSON.stringify(data.data);
    } else {
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
    errorMessage = "Không thể kết nối đến server";
  } else {
    errorMessage = error.message || errorMessage;
  }

  console.error(`[${context}]`, error);
  return errorMessage;
};
