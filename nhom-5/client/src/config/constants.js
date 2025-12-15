// API Base Path
const API_BASE_PATH = "/api/v1";

// API Endpoints Constants
export const API_ENDPOINTS = {
  // Auth
  REGISTER: `${API_BASE_PATH}/auth/register`,
  LOGIN: `${API_BASE_PATH}/auth/login`,
  
  // Users
  USERS: `${API_BASE_PATH}/users`,
  USER_BY_ID: (id) => `${API_BASE_PATH}/users/${id}`,
  
  // Jobs
  JOBS: `${API_BASE_PATH}/jobs`,
  JOB_BY_ID: (id) => `${API_BASE_PATH}/jobs/${id}`,
  
  // Candidates
  CANDIDATES: `${API_BASE_PATH}/users`,
  CANDIDATE_BY_ID: (id) => `${API_BASE_PATH}/users/${id}`,
  
  // CVs
  CVS: `${API_BASE_PATH}/cvs`,
  CV_BY_ID: (id) => `${API_BASE_PATH}/cvs/${id}`,
  
  // Enterprises
  ENTERPRISES: `${API_BASE_PATH}/enterprises`,
  ENTERPRISE_BY_ID: (id) => `${API_BASE_PATH}/enterprises/${id}`,
  
  // Interview Bookings
  INTERVIEW_BOOKINGS: `${API_BASE_PATH}/interview-bookings`,
  INTERVIEW_BOOKING_BY_ID: (id) => `${API_BASE_PATH}/interview-bookings/${id}`,
  
  // Certificates
  CERTIFICATE_TYPES: `${API_BASE_PATH}/CertificateTypes`,
  USER_CERTIFICATES: `${API_BASE_PATH}/UserCertificates`,
  USER_CERTIFICATE_BY_ID: (id) => `${API_BASE_PATH}/UserCertificates/${id}`,
  
  // CV Languages
  CV_LANGUAGES: `${API_BASE_PATH}/cvLanguages`,
  
  // Provinces
  PROVINCES: `${API_BASE_PATH}/provinces`,
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

