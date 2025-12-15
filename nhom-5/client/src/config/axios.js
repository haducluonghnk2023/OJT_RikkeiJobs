import axios from "axios";

// Base URL - có thể move vào .env file sau
const API_BASE_URL = "http://localhost:8080";

// Axios instance cho Java Backend (Spring Boot)
export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor
apiClient.interceptors.request.use(
  (config) => {
    // Có thể thêm token, logging, etc. ở đây
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    // Centralized error handling
    if (error.response) {
      // Server responded with error status
      // Java backend returns ResponseWrapper format: { status, code, data, message }
      const errorData = error.response.data;
      if (errorData?.data) {
        console.error("API Error:", error.response.status, errorData);
      } else {
        console.error("API Error:", error.response.status, error.response.data);
      }
    } else if (error.request) {
      // Request made but no response
      console.error("Network Error:", error.request);
    } else {
      // Something else happened
      console.error("Error:", error.message);
    }
    return Promise.reject(error);
  }
);

