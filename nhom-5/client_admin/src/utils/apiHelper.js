/**
 * Helper function to extract data from Java Backend ResponseWrapper format
 * Java backend returns: { status, code, data, message }
 * 
 * @param {Object} response - Axios response object
 * @returns {*} The actual data from response.data.data
 */
export const extractResponseData = (response) => {
  // Java backend ResponseWrapper format
  if (response.data?.data !== undefined) {
    return response.data.data;
  }
  // Fallback to direct data if not ResponseWrapper format
  return response.data;
};

/**
 * Extract error message from ResponseWrapper error format
 * 
 * @param {Object} error - Axios error object
 * @returns {string} Error message
 */
export const extractErrorMessage = (error) => {
  if (error.response?.data) {
    const errorData = error.response.data;
    // Java backend error format: { status, code, data, message }
    if (errorData.message) {
      return errorData.message;
    }
    if (errorData.data) {
      return typeof errorData.data === 'string' ? errorData.data : JSON.stringify(errorData.data);
    }
    return errorData.toString();
  }
  return error.message || 'An unexpected error occurred';
};

