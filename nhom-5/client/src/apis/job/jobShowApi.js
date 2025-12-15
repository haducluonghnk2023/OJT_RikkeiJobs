import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { shuffleArray, getDistinctValues } from "@/utils/arrayUtils";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all jobs
 * @returns {Promise<Array>} Array of jobs
 */
export const getAllJobs = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách công việc");
    throw error;
  }
};

/**
 * Get jobs by page with pagination
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing jobs array and totalJobs count
 */
export const getShowJobByPage = async (page, limit) => {
  try {
    // Java backend uses flight parameter to filter
    const params = buildQueryString({
      flight: "verified",
    });

    const response = await apiClient.get(`${API_ENDPOINTS.JOBS}${params ? '?' + params : ''}`);
    const allJobs = extractData(response);
    
    // Client-side pagination (backend can be updated later to support pagination)
    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedJobs = allJobs.slice(startIndex, endIndex);

    return {
      jobs: paginatedJobs,
      totalJobs: allJobs.length,
    };
  } catch (error) {
    const errorMessage = handleApiError(error, "lấy danh sách công việc theo trang");
    throw new Error(errorMessage);
  }
};

/**
 * Get distinct industries from jobs
 * @returns {Promise<Array>} Array of distinct industry values
 */
export const getDistinctIndustries = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    const jobs = extractData(response);
    const industries = getDistinctValues(jobs, "industry");
    return industries;
  } catch (error) {
    handleApiError(error, "lấy danh sách ngành nghề");
    throw error;
  }
};

/**
 * Get jobs by location with pagination
 * @param {string} location - Location/province name
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing jobs array and totalJobs count
 */
export const getJobsByLocation = async (location, page = 1, limit = 10) => {
  try {
    // Get all jobs first, then filter by location (can be optimized later with backend filter)
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    const allJobs = extractData(response);
    
    // Filter by province
    const filteredJobs = allJobs.filter(job => job.province === location);
    
    // Client-side pagination
    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedJobs = filteredJobs.slice(startIndex, endIndex);

    return {
      jobs: paginatedJobs,
      totalJobs: filteredJobs.length,
    };
  } catch (error) {
    handleApiError(error, "lấy công việc theo địa điểm");
    throw error;
  }
};

/**
 * Get random jobs with pagination
 * @param {number} page - Page number
 * @param {number} limit - Items per page
 * @returns {Promise<Object>} Object containing shuffled jobs array and totalJobs count
 */
export const getRandomJobs = async (page = 1, limit = 10) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    const allJobs = extractData(response);
    const shuffledJobs = shuffleArray(allJobs);
    
    // Client-side pagination
    const startIndex = (page - 1) * limit;
    const endIndex = startIndex + limit;
    const paginatedJobs = shuffledJobs.slice(startIndex, endIndex);

    return {
      jobs: paginatedJobs,
      totalJobs: allJobs.length,
    };
  } catch (error) {
    handleApiError(error, "lấy công việc ngẫu nhiên");
    throw error;
  }
};
