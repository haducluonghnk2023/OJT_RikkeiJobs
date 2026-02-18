export const extractResponseData = (response) => {
  if (response.data?.data !== undefined) {
    return response.data.data;
  }
  return response.data;
};

export const extractErrorMessage = (error) => {
  if (error.response?.data) {
    const errorData = error.response.data;
    if (errorData.message) {
      return errorData.message;
    }
    if (errorData.data) {
      return typeof errorData.data === "string"
        ? errorData.data
        : JSON.stringify(errorData.data);
    }
    return errorData.toString();
  }
  return error.message || "An unexpected error occurred";
};
