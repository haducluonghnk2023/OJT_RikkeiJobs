import axios from "axios";
import { handleApiError } from "@/utils/errorHandler";

// External API for provinces - using separate axios instance
const externalApiClient = axios.create({
  baseURL: "https://provinces.open-api.vn",
  timeout: 10000,
});

/**
 * Get provinces data from external API
 * @returns {Promise<Array>} Array of provinces
 */
export const province = async () => {
  try {
    // API provinces.open-api.vn trả về danh sách tỉnh/thành tại /api/provinces
    const response = await externalApiClient.get("/api/provinces");
    
    // Đảm bảo trả về array
    if (Array.isArray(response.data)) {
      return response.data;
    }
    
    // Nếu response.data là object, thử lấy provinces từ đó
    if (response.data && Array.isArray(response.data.provinces)) {
      return response.data.provinces;
    }
    
    // Fallback: trả về empty array
    console.warn("API response không đúng định dạng:", response.data);
    return [];
  } catch (error) {
    console.error("Lỗi khi lấy danh sách tỉnh thành:", error);
    handleApiError(error, "lấy danh sách tỉnh thành");
    throw error;
  }
};
