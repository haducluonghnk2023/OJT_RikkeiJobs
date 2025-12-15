import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get job details by ID
 * @param {string|number} jobId - Job ID
 * @returns {Promise<Object>} Job details
 */
export const getDetailJobs = async (jobId) => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOB_BY_ID(jobId));
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy chi tiết công việc");
    throw error;
  }
};
