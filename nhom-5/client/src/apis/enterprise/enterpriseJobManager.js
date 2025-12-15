import { apiClient } from "@/config/axios";
import { API_ENDPOINTS, buildQueryString } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

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

/**
 * Add new job
 * @param {Object} newJob - Job data
 * @returns {Promise<Object>} Created job
 */
export const addJob = async (newJob) => {
  try {
    const response = await apiClient.post(API_ENDPOINTS.JOBS, newJob);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "thêm công việc");
    throw error;
  }
};

/**
 * Update job
 * @param {Object} updatedJob - { id: jobId, data: jobData }
 * @returns {Promise<Object>} Updated job
 */
export const updateJob = async (updatedJob) => {
  try {
    const response = await apiClient.put(
      API_ENDPOINTS.JOB_BY_ID(updatedJob.id),
      updatedJob.data
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật công việc");
    throw error;
  }
};

/**
 * Delete job by ID
 * @param {string|number} jobId - Job ID
 * @returns {Promise<Object>} Deleted job
 */
export const deleteJob = async (jobId) => {
  try {
    const response = await apiClient.delete(API_ENDPOINTS.JOB_BY_ID(jobId));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "xóa công việc");
    throw error;
  }
};

/**
 * Get job details by ID
 * @param {string|number} jobId - Job ID
 * @returns {Promise<Object>} Job details
 */
export const getDetailJob = async (jobId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOB_BY_ID(jobId));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy chi tiết công việc");
    throw error;
  }
};

/**
 * Search jobs by keywords
 * @param {string} keyWords - Search keywords
 * @returns {Promise<Array>} Array of matching jobs
 */
export const searchJob = async (keyWords) => {
  try {
    const params = buildQueryString({ title_like: keyWords });
    const response = await apiClient.get(`${API_ENDPOINTS.JOBS}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "tìm kiếm công việc");
    throw error;
  }
};

/**
 * Get paginated jobs
 * @param {Object} payload - { page: pageNumber, limit: itemsPerPage }
 * @returns {Promise<Array>} Array of jobs
 */
export const getPaginatedJobs = async (payload) => {
  try {
    const params = buildQueryString({
      _start: payload.page,
      _limit: payload.limit,
    });
    const response = await apiClient.get(`${API_ENDPOINTS.JOBS}?${params}`);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách công việc theo trang");
    throw error;
  }
};
