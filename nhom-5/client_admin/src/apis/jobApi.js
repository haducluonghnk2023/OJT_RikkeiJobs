import { apiClient } from "@/config/axios";
import { API_ENDPOINTS } from "@/config/constants";
import { handleApiError } from "@/utils/errorHandler";
import { extractResponseData } from "@/utils/apiHelper";

/**
 * Get all jobs data
 * @returns {Promise<Array>} Array of jobs
 */
export const getJobsData = async () => {
  try {
    const response = await apiClient.get(API_ENDPOINTS.JOBS);
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "lấy danh sách công việc");
    throw error;
  }
};

/**
 * Update job data
 * @param {Object} job - Job object with id
 * @returns {Promise<Object>} Updated job data
 */
export const updateJobData = async (job) => {
  try {
    const response = await apiClient.patch(
      API_ENDPOINTS.JOB_BY_ID(job.id),
      job
    );
    return extractResponseData(response);
  } catch (error) {
    handleApiError(error, "cập nhật công việc");
    throw error;
  }
};
