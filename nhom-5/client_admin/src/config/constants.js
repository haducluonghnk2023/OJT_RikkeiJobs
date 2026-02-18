// API Base Path
const API_BASE_PATH = "/api/v1";

// API Endpoints Constants
export const API_ENDPOINTS = {
  // Auth
  LOGIN: `${API_BASE_PATH}/auth/login`,
  LOGOUT: `${API_BASE_PATH}/auth/logout`,
  
  // Users
  USERS: `${API_BASE_PATH}/users`,
  USER_BY_ID: (id) => `${API_BASE_PATH}/users/${id}`,
  
  // Jobs
  JOBS: `${API_BASE_PATH}/jobs`,
  JOB_BY_ID: (id) => `${API_BASE_PATH}/jobs/${id}`,
  
  // CVs
  CVS: `${API_BASE_PATH}/cvs`,
  CV_BY_ID: (id) => `${API_BASE_PATH}/cvs/${id}`,
  
  // Enterprises
  ENTERPRISES: `${API_BASE_PATH}/enterprises`,
  ENTERPRISE_BY_ID: (id) => `${API_BASE_PATH}/enterprises/${id}`,
  
  // Interview Bookings
  INTERVIEW_BOOKINGS: `${API_BASE_PATH}/interview-bookings`,
  INTERVIEW_BOOKING_BY_ID: (id) => `${API_BASE_PATH}/interview-bookings/${id}`,
  
  // Certificate Types
  CERTIFICATE_TYPES: `${API_BASE_PATH}/CertificateTypes`,
  CERTIFICATE_TYPE_BY_ID: (id) => `${API_BASE_PATH}/CertificateTypes/${id}`,
  
  // CV Languages
  CV_LANGUAGES: `${API_BASE_PATH}/cvLanguages`,
  CV_LANGUAGE_BY_ID: (id) => `${API_BASE_PATH}/cvLanguages/${id}`,
  
  // Carousel
  CAROUSEL: `${API_BASE_PATH}/carousel`,
  CAROUSEL_BY_ID: (id) => `${API_BASE_PATH}/carousel/${id}`,
};

// Query Parameters Helpers
export const buildQueryString = (params) => {
  const queryParams = new URLSearchParams();
  
  Object.keys(params).forEach((key) => {
    if (params[key] !== null && params[key] !== undefined && params[key] !== "") {
      queryParams.append(key, params[key]);
    }
  });
  
  return queryParams.toString();
};

