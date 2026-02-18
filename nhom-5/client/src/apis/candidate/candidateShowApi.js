import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all active candidates
 * @returns {Promise<Array>} Array of candidates
 */
export const getAllCandidates = async () => {
  try {
    // User status in DB is typically "active"/"inActive" (sometimes "Active"); backend filters ignore-case.
    const params = buildQueryString({ status: "active", role: "user" });
    const response = await apiClient.get(`${API_ENDPOINTS.CANDIDATES}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách ứng viên");
    throw error;
  }
};

/**
 * Get candidates by page (DESC order)
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing candidates array and totalCandidates count
 */
export const getShowCandidateByPageDE = async (page, limit) => {
  try {
    const params = buildQueryString({
      _page: page,
      _limit: limit,
      status: "active",
      _sort: "id",
      _order: "DESC",
      role: "user",
    });

    const response = await apiClient.get(`${API_ENDPOINTS.CANDIDATES}?${params}`);
    const allCandidates = extractResponseData(response);
    
    // Client-side pagination (backend can be updated later)
    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedCandidates = allCandidates.slice(startIndex, endIndex);

    return {
      candidates: paginatedCandidates,
      totalCandidates: allCandidates.length,
    };
  } catch (error) {
    const errorMessage = handleApiError(error, "lấy danh sách ứng viên theo trang");
    throw new Error(errorMessage);
  }
};

/**
 * Get candidates by page (ASC order, user role only)
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing candidates array and totalCandidates count
 */
export const getShowCandidateByPageA = async (page, limit) => {
  try {
    const params = buildQueryString({
      _page: page,
      _limit: limit,
      status: "active",
      _sort: "id",
      _order: "ASC",
      role: "user",
    });

    const response = await apiClient.get(`${API_ENDPOINTS.CANDIDATES}?${params}`);
    const allCandidates = extractResponseData(response);
    
    // Client-side pagination (backend can be updated later)
    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedCandidates = allCandidates.slice(startIndex, endIndex);

    return {
      candidates: paginatedCandidates,
      totalCandidates: allCandidates.length,
    };
  } catch (error) {
    const errorMessage = handleApiError(error, "lấy danh sách ứng viên theo trang");
    throw new Error(errorMessage);
  }
};

/**
 * Get CV data (all CVs)
 * @param {string|number} userId - User ID (currently not used in API call)
 * @returns {Promise<Array>} Array of CVs
 */
export const getCvData = async (userId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.CVS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy dữ liệu CV");
    throw error;
  }
};
